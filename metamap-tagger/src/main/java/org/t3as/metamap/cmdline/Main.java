/*
 * #%L
 * NICTA t3as MetaMap Tagger
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
package org.t3as.metamap.cmdline;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.google.common.io.Files;
import org.t3as.metamap.MetaMap;

import java.io.File;

import static com.google.common.base.Charsets.UTF_8;
import static org.t3as.metamap.options.MetaMapOptions.DEFAULT_MM_OPTIONS;

/**
 * Command line tool to exercise MetaMap. Produces output that can then be passed to the snomedct-lookup tool.
 * Runs with default options.
 */
public final class Main {

    public static void main(final String[] args) throws Exception {
        final CmdOpts opts = new CmdOpts();
        JCommander jc = null;
        try {
            jc = new JCommander(opts, args);
        }
        catch (final Exception e) {
            System.err.println("Could not parse the options: " + e.getMessage());
            System.exit(1);
        }
        if (opts.showUsage) {
            jc.usage();
            System.exit(0);
        }

        // sanitise the input
        final File sanitised = File.createTempFile("metamap-sanitised-", ".txt");
        final String s = Files.toString(opts.input, UTF_8);
        Files.write(s, sanitised, UTF_8);

        // process the data with MetaMap
        final MetaMap metaMap = new MetaMap(opts.publicMm, DEFAULT_MM_OPTIONS);
        if (!metaMap.process(sanitised, opts.output)) {
            System.err.println("MetaMap processing failed, aborting.");
            System.exit(1);
        }
        //noinspection ResultOfMethodCallIgnored
        sanitised.delete();
    }

    private static class CmdOpts {
        @Parameter(help = true, names = {"-h", "--help"}, description = "Show this help message.")
        boolean showUsage = false;

        @Parameter(names = "-public_mm", description = "Path to the MetaMap 'public_mm' directory.")
        File publicMm = new File("/opt/metamap/public_mm");

        @Parameter(names = "-infile", description = "The file containing the input text.")
        File input = new File("input.txt");

        @Parameter(names = "-outfile", description = "The file to write the output XML to.")
        File output = new File("output.xml");
    }
}
