package org.t3as.metamap.options;

/*
 * #%L
 * MetaMap Tagger
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
 * it with H2, GWT, JUnit, or JavaBeans Activation Framework (JAF) (or a
 * modified version of those libraries), containing parts covered by the
 * terms of the H2 License, the GWT Terms, the Common Public License
 * Version 1.0, or the Common Development and Distribution License (CDDL)
 * version 1.0 ,the licensors of this Program grant you additional
 * permission to convey the resulting work.
 * #L%
 */

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class OptionTest {

    @Test
    public void test() throws Exception {
        final Option o1 = Option.strToOpt("foobar");
        assertNull(o1);

        final Option o2 = Option.strToOpt("word_sense_disambiguation");
        assertNotNull(o2);
        assertEquals(WordSenseDisambiguation.class, o2.getClass());
        assertEquals("word_sense_disambiguation", o2.name());
        assertNull(o2.param());
        assertEquals("--word_sense_disambiguation", o2.toMmOptStr());

        final Option o3 = Option.strToOpt("composite_phrases");
        assertNull(o3);

        final Option o4 = Option.strToOpt("composite_phrases X");
        assertNull(o4);

        final Option o5 = Option.strToOpt("composite_phrases 20");
        assertNull(o5);

        final Option o6 = Option.strToOpt("composite_phrases 4");
        assertNotNull(o6);
        assertEquals(CompositePhrases.class, o6.getClass());
        assertEquals("--composite_phrases 4", o6.toMmOptStr());
    }
}