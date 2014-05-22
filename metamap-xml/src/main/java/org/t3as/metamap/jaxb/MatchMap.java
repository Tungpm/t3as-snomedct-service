//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.03.07 at 11:12:55 AM EST 
//


package org.t3as.metamap.jaxb;

/*
 * #%L
 * MetaMap XML JAXB
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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "textMatchStart",
    "textMatchEnd",
    "concMatchStart",
    "concMatchEnd",
    "lexVariation"
})
@XmlRootElement(name = "MatchMap")
public class MatchMap {

    @XmlElement(name = "TextMatchStart", required = true)
    protected String textMatchStart;
    @XmlElement(name = "TextMatchEnd", required = true)
    protected String textMatchEnd;
    @XmlElement(name = "ConcMatchStart", required = true)
    protected String concMatchStart;
    @XmlElement(name = "ConcMatchEnd", required = true)
    protected String concMatchEnd;
    @XmlElement(name = "LexVariation", required = true)
    protected String lexVariation;

    /**
     * Gets the value of the textMatchStart property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTextMatchStart() {
        return textMatchStart;
    }

    /**
     * Sets the value of the textMatchStart property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTextMatchStart(String value) {
        this.textMatchStart = value;
    }

    /**
     * Gets the value of the textMatchEnd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTextMatchEnd() {
        return textMatchEnd;
    }

    /**
     * Sets the value of the textMatchEnd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTextMatchEnd(String value) {
        this.textMatchEnd = value;
    }

    /**
     * Gets the value of the concMatchStart property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConcMatchStart() {
        return concMatchStart;
    }

    /**
     * Sets the value of the concMatchStart property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConcMatchStart(String value) {
        this.concMatchStart = value;
    }

    /**
     * Gets the value of the concMatchEnd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConcMatchEnd() {
        return concMatchEnd;
    }

    /**
     * Sets the value of the concMatchEnd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConcMatchEnd(String value) {
        this.concMatchEnd = value;
    }

    /**
     * Gets the value of the lexVariation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLexVariation() {
        return lexVariation;
    }

    /**
     * Sets the value of the lexVariation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLexVariation(String value) {
        this.lexVariation = value;
    }

}