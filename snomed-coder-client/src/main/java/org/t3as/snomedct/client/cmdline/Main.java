/*
 * #%L
 * NICTA t3as SNOMED CT service REST client
 * %%
 * Copyright (C) 2014 NICTA
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * 
 * Additional permission under GNU GPL version 3 section 7
 * 
 * If you modify this Program, or any covered work, by linking or combining
 * it with H2, GWT, or JavaBeans Activation Framework (JAF) (or a
 * modified version of those libraries), containing parts covered by the
 * terms of the H2 License, the GWT Terms, or the Common Development and
 * Distribution License (CDDL) version 1.0 ,the licensors of this Program
 * grant you additional permission to convey the resulting work.
 * #L%
 */
package org.t3as.snomedct.client.cmdline;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.io.Files;
import org.t3as.metamap.jaxb.Candidate;
import org.t3as.metamap.jaxb.Mapping;
import org.t3as.metamap.jaxb.Phrase;
import org.t3as.metamap.jaxb.SemType;
import org.t3as.metamap.jaxb.Utterance;
import org.t3as.metamap.options.MetaMapOptions;
import org.t3as.snomedct.client.SnomedClient;
import org.t3as.snomedct.service.AnalysisRequest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/** Command line implementation of the SNOMED CT web service client. */
public final class Main {

    private Main() {}

    /** Get the relevant options from the command line and invoke the client. */
    @SuppressWarnings("MethodNamesDifferingOnlyByCase")
    public static void main(final String[] args) throws IOException {
        final Options opts = getOptions(args);

        // create a web service client
        final SnomedClient client = new SnomedClient(opts.url);

        // call the webservice with the text and any passed options
        if (opts.text.isEmpty()) processFiles(opts, client);
        else System.out.println(callService(opts.text, opts, client));
    }

    private static void processFiles(final Options opts, final SnomedClient client) throws IOException {
        // read each file and call the web service
        for (final File f : opts.files) {
            final String input = Files.toString(f, Charsets.UTF_8);
            System.out.printf("%s:\n", f);
            System.out.println(callService(input, opts, client));
        }
    }

    private static String callService(final String text, final Options opts, final SnomedClient client) {
        // create a request
        final AnalysisRequest request = new AnalysisRequest(text, opts.splitOptions());
        // perform the call and get the output
        final Collection<Utterance> utterances = client.call(request);
        // turn the output into a human readable string
        return toString(utterances);
    }

    /**
     * Turn utterances into MetaMap-ish output.
     * @param utterances Utterances to get data from
     * @return a MetaMap-ish string meant for printing
     */
    public static String toString(final Collection<Utterance> utterances) {
        final StringBuilder sb = new StringBuilder();
        for (final Utterance u : utterances) {
            for (final Phrase phrase : u.getPhrases().getPhrase()) {
                sb.append(String.format("Phrase: %s\n", phrase.getPhraseText()));
                for (final Mapping mapping : phrase.getMappings().getMapping()) {
                    sb.append(String.format("Score: %s\n", mapping.getMappingScore()));
                    for (final Candidate candidate : mapping.getMappingCandidates().getCandidate()) {
                        final Collection<String> semTypes = new ArrayList<>();
                        for (final SemType st : candidate.getSemTypes().getSemType()) {
                            semTypes.add(st.getvalue());
                        }

                        sb.append(String.format("  %-5s %-9s %s %s %s %s\n",
                                          candidate.getCandidateScore(),
                                          candidate.getCandidateCUI(),
                                          candidate.getSnomedId(),
                                          candidate.getCandidatePreferred(),
                                          semTypes,
                                          candidate.getSources().getSource()));
                    }
                }
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    @SuppressWarnings("CallToSystemExit")
    private static Options getOptions(final String[] args) {
        final Options opts = new Options();
        JCommander jc = null;
        try { jc = new JCommander(opts, args); }
        catch (final RuntimeException e) {
            System.err.println("Could not parse the options: " + e.getMessage());
            System.exit(1);
        }
        if (opts.showUsage) {
            jc.usage();
            System.exit(0);
        }
        if (opts.text.isEmpty() && opts.files.isEmpty()) {
            System.err.println("Pass either text to analyse or files to read text from.");
            System.exit(1);
        }
        if (!opts.text.isEmpty() && !opts.files.isEmpty()) {
            System.err.println("Pass either text to analyse or files to read text from, not both.");
            System.exit(1);
        }
        // do nothing unless all files are readable
        for (final File f : opts.files) {
            if (!f.canRead()) {
                System.err.printf("Can not read file '%s'\n", f);
                System.exit(1);
            }
        }
        return opts;
    }

    private static class Options {
        @Parameter(help = true, names = {"-h", "--help"}, description = "Show this help message.")
        boolean showUsage;

        @Parameter(names = "-url", description = "The base URL of the SNOMED web service to access.")
        String url = SnomedClient.DEFAULT_BASE_URL;

        @Parameter(names = "-text", description = "The text to analyse for SNOMED CT codes.")
        String text = "";

        @Parameter(names = "-options", description = "Semicolon separated list of options to pass to the SNOMED CT "
                                                     + "coder - see web service documentation for which ones are "
                                                     + "supported. To run with no options at all pass an empty string.")
        String options = Joiner.on("; ").join(MetaMapOptions.DEFAULT_MM_OPTIONS);

        @Parameter(description = "[files]")
        Collection<File> files = new ArrayList<>();

        List<String> splitOptions() {
            // split on comma and trim off any surrounding whitespace
            return Splitter.on(';').omitEmptyStrings().trimResults().splitToList(options);
        }
    }
}
