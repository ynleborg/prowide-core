/*******************************************************************************
 * Copyright (c) 2016 Prowide Inc.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as 
 *     published by the Free Software Foundation, either version 3 of the 
 *     License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 *     
 *     Check the LGPL at <http://www.gnu.org/licenses/> for more details.
 *******************************************************************************/
 package com.prowidesoftware.swift.model.field;

import com.prowidesoftware.swift.model.Tag;
import com.prowidesoftware.Generated;
import com.prowidesoftware.deprecation.ProwideDeprecated;
import com.prowidesoftware.deprecation.TargetYear;

import java.io.Serializable;
import java.util.Locale;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;

import com.prowidesoftware.swift.model.field.SwiftParseUtils;
import com.prowidesoftware.swift.model.field.Field;
import com.prowidesoftware.swift.model.*;
import com.prowidesoftware.swift.utils.SwiftFormatUtils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * <h2>SWIFT MT Field 97C</h2>
 * Model and parser for field 97C of a SWIFT MT message.
 *
 * <h4>Subfields (components) Data types</h4>
 * <ol> 
 * 		<li><code>String</code></li> 
 * 		<li><code>String</code></li> 
 * </ol>
 *
 * <h4>Structure definition</h4>
 * <ul>
 * 		<li>validation pattern: <code>:4!c//4!c</code></li>
 * 		<li>parser pattern: <code>:S//S</code></li>
 * 		<li>components pattern: <code>SS</code></li>
 * </ul>
 *		 
 * <p>This class complies with standard release <strong>SRU2017</strong></p>
 * <p>NOTE: this source code has been generated from template</p>
 */
@SuppressWarnings("unused") 
@Generated
public class Field97C extends Field implements Serializable, com.prowidesoftware.swift.model.field.GenericField {
	/**
	 * Constant identifying the SRU to which this class belongs to.
	 */
	public static final int SRU = 2017;

	private static final long serialVersionUID = 1L;
	/**
	 * Constant with the field name 97C
	 */
    public static final String NAME = "97C";
    /**
     * same as NAME, intended to be clear when using static imports
     */
    public static final String F_97C = "97C";
	public static final String PARSER_PATTERN =":S//S";
	public static final String COMPONENTS_PATTERN = "SS";

	/**
	 * Component number for the Qualifier subfield
	 */
	public static final Integer QUALIFIER = 1;

	/**
	 * Component number for the Code subfield
	 */
	public static final Integer CODE = 2;

	/**
	 * Default constructor. Creates a new field setting all components to null.
	 */
	public Field97C() {
		super(2);
	}
	    					
	/**
	 * Creates a new field and initializes its components with content from the parameter value.
	 * @param value complete field value including separators and CRLF
	 */
	public Field97C(final String value) {
		super(value);
	}
	
	/**
	 * Creates a new field and initializes its components with content from the parameter tag.
	 * The value is parsed with {@link #parse(String)} 	 
	 * @throws IllegalArgumentException if the parameter tag is null or its tagname does not match the field name
	 * @since 7.8
	 */
	public Field97C(final Tag tag) {
		this();
		if (tag == null) {
			throw new IllegalArgumentException("tag cannot be null.");
		}
		if (!StringUtils.equals(tag.getName(), "97C")) {
			throw new IllegalArgumentException("cannot create field 97C from tag "+tag.getName()+", tagname must match the name of the field.");
		}
		parse(tag.getValue());
	}
	
	/**
	 * Parses the parameter value into the internal components structure.
	 * <br />
	 * Used to update all components from a full new value, as an alternative
	 * to setting individual components. Previous component values are overwritten.
	 *
	 * @param value complete field value including separators and CRLF
	 * @since 7.8
	 */
	@Override
	public void parse(final String value) {
		init(2);
		setComponent1(SwiftParseUtils.getTokenFirst(value, ":", "//"));
		setComponent2(SwiftParseUtils.getTokenSecondLast(value, "//"));
	}
	
	/**
	 * Copy constructor.<br>
	 * Initializes the components list with a deep copy of the source components list.
	 * @param source a field instance to copy
	 * @since 7.7
	 */
	public static Field97C newInstance(Field97C source) {
		Field97C cp = new Field97C();
		cp.setComponents(new ArrayList<String>(source.getComponents()));
		return cp;
	}
	
	/**
	 * Serializes the fields' components into the single string value (SWIFT format)
	 */
	@Override
	public String getValue() {
		final StringBuilder result = new StringBuilder();
		result.append(":");
		append(result, 1);
		result.append("//");
		append(result, 2);
		return result.toString();
	}

	/**
	* Create a Tag with this field name and the given value.
	* Shorthand for <code>new Tag(NAME, value)</code>
	* @see #NAME
	* @since 7.5
	*/
	public static Tag tag(final String value) {
		return new Tag(NAME, value);
	}

	/**
	* Create a Tag with this field name and an empty string as value
	* Shorthand for <code>new Tag(NAME, "")</code>
	* @see #NAME
	* @since 7.5
	*/
	public static Tag emptyTag() {
		return new Tag(NAME, "");
	}
	
	/**
	 * Gets the component1
	 * @return the component1
	 */
	public String getComponent1() {
		return getComponent(1);
	}

	/**
	 * Same as getComponent(1)
	 * @deprecated use {@link #getComponent(int)} instead
	 */
	@Deprecated
	@ProwideDeprecated(phase2=TargetYear._2018)
	public java.lang.String getComponent1AsString() {
		return getComponent(1);
	}

	/**
	 * Gets the Qualifier (component1).
	 * @return the Qualifier from component1
	 */
	public String getQualifier() {
		return getComponent(1);
	}

	/**
	 * Set the component1.
	 * @param component1 the component1 to set
	 */
	public Field97C setComponent1(String component1) {
		setComponent(1, component1);
		return this;
	}
	
	/**
	 * Set the Qualifier (component1).
	 * @param component1 the Qualifier to set
	 */
	public Field97C setQualifier(String component1) {
		setComponent(1, component1);
		return this;
	}
	/**
	 * Gets the component2
	 * @return the component2
	 */
	public String getComponent2() {
		return getComponent(2);
	}

	/**
	 * Same as getComponent(2)
	 * @deprecated use {@link #getComponent(int)} instead
	 */
	@Deprecated
	@ProwideDeprecated(phase2=TargetYear._2018)
	public java.lang.String getComponent2AsString() {
		return getComponent(2);
	}

	/**
	 * Gets the Code (component2).
	 * @return the Code from component2
	 */
	public String getCode() {
		return getComponent(2);
	}

	/**
	 * Set the component2.
	 * @param component2 the component2 to set
	 */
	public Field97C setComponent2(String component2) {
		setComponent(2, component2);
		return this;
	}
	
	/**
	 * Set the Code (component2).
	 * @param component2 the Code to set
	 */
	public Field97C setCode(String component2) {
		setComponent(2, component2);
		return this;
	}

   /**
    * Given a component number it returns true if the component is optional,
    * regardless of the field being mandatory in a particular message.<br />
    * Being the field's value conformed by a composition of one or several 
    * internal component values, the field may be present in a message with
    * a proper value but with some of its internal components not set.
    *
    * @param component component number, first component of a field is referenced as 1
    * @return true if the component is optional for this field, false otherwise
    */
   @Override
   public boolean isOptional(int component) {   
       return false;
   }

   /**
    * Returns true if the field is a GENERIC FIELD as specified by the standard.
    *
    * @return true if the field is generic, false otherwise
    */
   @Override
   public boolean isGeneric() {   
       return true;
   }

   /**
    * Returns the issuer code (or Data Source Scheme or DSS).
    * The DSS is only present in some generic fields, when present, is equals to component two.
    *
    * @return DSS component value or <code>null</code> if the DSS is not set or not available for this field.
    */
   public String getDSS() {
       return null;
   }

   /**
    * Checks if the issuer code (or Data Source Scheme or DSS) is present.
    *
    * @see #getDSS()
    * @return true if DSS is present, false otherwise.
    */
   public boolean isDSSPresent() {
       return getDSS() != null;
   }

	/**
	 * Component number for the conditional qualifier subfield
	 */
    public static final Integer CONDITIONAL_QUALIFIER = 2;
   
   /**
    * Gets the conditional qualifier.<br />
    * The conditional qualifier is the the component following the DSS of generic fields, being component 2 or 3 depending on the field structure definition.
    *
    * @return for generic fields returns the value of the conditional qualifier or <code>null</code> if not set or not applicable for this kind of field.
    */
   public String getConditionalQualifier() {
       return getComponent(CONDITIONAL_QUALIFIER);
   }
   
   public String parserPattern() {
           return PARSER_PATTERN;
   }

	/**
	 * Returns the field's name composed by the field number and the letter option (if any)
	 * @return the static value of Field97C.NAME
	 */
	@Override
	public String getName() {
		return NAME;
	}
	
	/**
	 * Returns the field's components pattern
	 * @return the static value of Field97C.COMPONENTS_PATTERN
	 */
	@Override
	public final String componentsPattern() {
		return COMPONENTS_PATTERN;
	}

	/**
	 * Returns the field's validators pattern
	 */
	@Override
	public final String validatorPattern() {
		return ":4!c//4!c";
	}

	/**
	 * Gets the first occurrence form the tag list or null if not found.
	 * @return null if not found o block is null or empty
	 * @param block may be null or empty 
	 */
	public static Field97C get(final SwiftTagListBlock block) {
		if (block == null || block.isEmpty()) {
			return null;
		}
		final Tag t = block.getTagByName(NAME);
		if (t == null) {
			return null;
		}
		return new Field97C(t) ;
	}
	
	/**
	 * Gets the first instance of Field97C in the given message.
	 * @param msg may be empty or null
	 * @return null if not found or msg is empty or null
	 * @see #get(SwiftTagListBlock)
	 */
	public static Field97C get(final SwiftMessage msg) {
		if (msg == null || msg.getBlock4()==null || msg.getBlock4().isEmpty())
			return null;
		return get(msg.getBlock4());
	}

	/**
	 * Gets a list of all occurrences of the field Field97C in the given message
	 * an empty list is returned if none found.
	 * @param msg may be empty or null in which case an empty list is returned
	 * @see #getAll(SwiftTagListBlock)
	 */ 
	public static List<Field97C> getAll(final SwiftMessage msg) {
		if (msg == null || msg.getBlock4()==null || msg.getBlock4().isEmpty())
			return java.util.Collections.emptyList();
		return getAll(msg.getBlock4());
	}

	/**
	 * Gets a list of all occurrences of the field Field97C from the given block
	 * an empty list is returned if none found.
	 *
	 * @param block may be empty or null in which case an empty list is returned 
	 */ 
	public static List<Field97C> getAll(final SwiftTagListBlock block) {
		if (block == null || block.isEmpty()) {
			return java.util.Collections.emptyList();
		}
		final Tag[] arr = block.getTagsByName(NAME);
		if (arr != null && arr.length>0) {
			final ArrayList<Field97C> result = new ArrayList<Field97C>(arr.length);
			for (final Tag f : arr) {
				result.add( new Field97C(f));
			}
			return result;
		}
		return java.util.Collections.emptyList();
	}
	
	/**
	 * Returns the defined amount of components.<br>
	 * This is not the amount of components present in the field instance, but the total amount of components 
	 * that this field accepts as defined. 
	 * @since 7.7
	 */
	@Override
	public int componentsSize() {
		return 2;
	}

	/**
	 * Returns a localized suitable for showing to humans string of a field component.<br>
	 *
	 * @param component number of the component to display
	 * @param locale optional locale to format date and amounts, if null, the default locale is used
	 * @return formatted component value or null if component number is invalid or not present
	 * @throws IllegalArgumentException if component number is invalid for the field
	 * @since 7.8
	 */
	@Override
	public String getValueDisplay(int component, Locale locale) {
		if (component < 1 || component > 2) {
			throw new IllegalArgumentException("invalid component number "+component+" for field 97C");
		}
		if (component == 1) {
			//default format (as is)
			return getComponent(1);
		}
		if (component == 2) {
			//default format (as is)
			return getComponent(2);
		}
		return null;	
	}
	
	/**
	 * Returns english label for components.
	 * <br />
	 * The index in the list is in sync with specific field component structure.
	 * @see #getComponentLabel(int)
	 * @since 7.8.4
	 */
	@Override
	protected List<String> getComponentLabels() {
		List<String> result = new ArrayList<String>();
		result.add("Qualifier");
		result.add("Code");
		return result;
	}

	/**
	 * Returns a mapping between component numbers and their label in camel case format.
	 * @since 7.10.2
	 */
	protected Map<Integer, String> getComponentMap() {
		Map<Integer, String> result = new HashMap<Integer, String>();
		result.put(1, "qualifier");
		result.put(2, "code");
		return result;
	}

	/**
	 * This method deserializes the JSON data into a Field97C object.
	 * @param json JSON structure including tuples with label and value for all field components
	 * @return a new field instance with the JSON data parsed into field components or an empty field id the JSON is invalid
	 * @since 7.10.2
	 * @see Field#fromJson(String)
	 */
	public static Field97C fromJson(final String json) {
		Field97C field = new Field97C();
		JsonParser parser = new JsonParser();
		JsonObject jsonObject = (JsonObject) parser.parse(json);
		if (jsonObject.get("qualifier") != null) {
			field.setComponent1(jsonObject.get("qualifier").getAsString());
		}
		if (jsonObject.get("code") != null) {
			field.setComponent2(jsonObject.get("code").getAsString());
		}
		return field;
	}
	

}
