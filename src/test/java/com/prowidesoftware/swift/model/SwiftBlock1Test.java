/* 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
*/
package com.prowidesoftware.swift.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.junit.Before;
import org.junit.Test;

/**
 * Block1 tests.
 * 
 * @author www.prowidesoftware.com
 * @since 4.0
 */
public class SwiftBlock1Test {

	private SwiftBlock1 b;

	@Before
	public void setUp() {
		b = new SwiftBlock1();
	}
	
	@Test
	public void testSetValue() {
		b.setValue("F01BANKBEBBAXXX1234567890");
		assertEquals("F", b.getApplicationId());
		assertEquals("01", b.getServiceId());
		assertEquals("BANKBEBBAXXX", b.getLogicalTerminal());
		assertEquals("1234", b.getSessionNumber());
		assertEquals("567890", b.getSequenceNumber());
	}

	@Test
	public void testSetValue_2() {
		b.setValue("1:F01BANKBEBBAXXX1234567890");
		assertEquals("F", b.getApplicationId());
		assertEquals("01", b.getServiceId());
		assertEquals("BANKBEBBAXXX", b.getLogicalTerminal());
		assertEquals("1234", b.getSessionNumber());
		assertEquals("567890", b.getSequenceNumber());
	}
	
	@Test
	public void testSetValue_3() {
		try {
			//set an invalid length value
			b.setValue("1:F01BANKBEBBAXXX12345678");
		} catch (IllegalArgumentException e) {
			return;
		}
		fail("IllegalArgumentException not thrown");
	}
	
	@Test
	public void testSetValue_4() {
		try {
			//set an invalid starting substring
			b.setValue("a:F01BANKBEBBAXXX1234567890");
		} catch (IllegalArgumentException e) {
			return;
		}
		fail("IllegalArgumentException not thrown");
	}
	
	@Test
	public void testConstructor() {
		SwiftBlock1 bb = new SwiftBlock1("F01BANKBEBBAXXX1234567890");
		assertEquals("F", bb.getApplicationId());
		assertEquals("01", bb.getServiceId());
		assertEquals("BANKBEBBAXXX", bb.getLogicalTerminal());
		assertEquals("1234", bb.getSessionNumber());
		assertEquals("567890", bb.getSequenceNumber());
	}
	
	@Test
	public void testConstructor_2() {
		SwiftBlock1 bb = new SwiftBlock1("1:F01BANKBEBBAXXX1234567890");
		assertEquals("F", bb.getApplicationId());
		assertEquals("01", bb.getServiceId());
		assertEquals("BANKBEBBAXXX", bb.getLogicalTerminal());
		assertEquals("1234", bb.getSessionNumber());
		assertEquals("567890", bb.getSequenceNumber());
	}

	@Test
	public void testConstructor_3() {
		SwiftBlock1 bb = new SwiftBlock1("F01BANKBEBBXXXX1234567890");
		assertEquals("F", bb.getApplicationId());
		assertEquals("01", bb.getServiceId());
		assertEquals("BANKBEBBXXXX", bb.getLogicalTerminal());
		assertEquals("1234", bb.getSessionNumber());
		assertEquals("567890", bb.getSequenceNumber());
	}
	
	@Test
	public void testConstructor_4() {
		SwiftBlock1 bb = new SwiftBlock1("1:F01BANKBEBBCXXX1234567890");
		assertEquals("F", bb.getApplicationId());
		assertEquals("01", bb.getServiceId());
		assertEquals("BANKBEBBCXXX", bb.getLogicalTerminal());
		assertEquals("1234", bb.getSessionNumber());
		assertEquals("567890", bb.getSequenceNumber());
	}

	@Test
	public void testGetValue() {
		String value = "F01BANKBEBBAXXX1234567890";
		b.setValue(value);
		assertEquals(value, b.getValue());
	}
	
	@Test
	public void testIsEmptyAndSize() {
		//assertTrue(b.isEmpty());
		//assertTrue(b.size() == 0);
		b.setValue("F01BANKBEBBAXXX1234567890");
		assertFalse(b.isEmpty());
		assertTrue(b.size() == 25);
	}
	
	@Test
	public void testConstructorLenient() {
		SwiftBlock1 bb = new SwiftBlock1("1:F01BANKBEBBAXXX1234567890", true);
		assertEquals("F", bb.getApplicationId());
		assertEquals("01", bb.getServiceId());
		assertEquals("BANKBEBBAXXX", bb.getLogicalTerminal());
		assertEquals("1234", bb.getSessionNumber());
		assertEquals("567890", bb.getSequenceNumber());
	}

	@Test
	public void testConstructorLenient_lessThanExpected() {
		SwiftBlock1 bb = new SwiftBlock1("1:F01BANKBEBBAXXX123456789", true);
		assertEquals("F", bb.getApplicationId());
		assertEquals("01", bb.getServiceId());
		assertEquals("BANKBEBBAXXX", bb.getLogicalTerminal());
		assertEquals("1234", bb.getSessionNumber());
		assertEquals("56789", bb.getSequenceNumber());	//less than expected
	}

	@Test
	public void testConstructorLenient_moreThanExpected() {
		SwiftBlock1 bb = new SwiftBlock1("1:F01BANKBEBBAXXX1234567890123", true);
		assertEquals("F", bb.getApplicationId());
		assertEquals("01", bb.getServiceId());
		assertEquals("BANKBEBBAXXX", bb.getLogicalTerminal());
		assertEquals("1234", bb.getSessionNumber());
		assertEquals("567890123", bb.getSequenceNumber());	//more than expected
	}

	@Test
	public void testConstructorLenient_noSequeceNumber() {
		SwiftBlock1 bb = new SwiftBlock1("1:F01BANKBEBBAXXX1234", true);
		assertEquals("F", bb.getApplicationId());
		assertEquals("01", bb.getServiceId());
		assertEquals("BANKBEBBAXXX", bb.getLogicalTerminal());
		assertEquals("1234", bb.getSessionNumber());
		assertNull(bb.getSequenceNumber());
	}

	@Test
	public void testConstructorLenient_noSession() {
		SwiftBlock1 bb = new SwiftBlock1("1:F01BANKBEBBAXXX", true);
		assertEquals("F", bb.getApplicationId());
		assertEquals("01", bb.getServiceId());
		assertEquals("BANKBEBBAXXX", bb.getLogicalTerminal());
		assertNull(bb.getSessionNumber());
		assertNull(bb.getSequenceNumber());
	}

	@Test
	public void testConstructorLenient_incompleteLogicalTerminal() {
		SwiftBlock1 bb = new SwiftBlock1("1:F01BANKBEBBA", true);
		assertEquals("F", bb.getApplicationId());
		assertEquals("01", bb.getServiceId());
		assertEquals("BANKBEBBA", bb.getLogicalTerminal()); //missign branch code
		assertNull(bb.getSessionNumber());
		assertNull(bb.getSequenceNumber());
	}

	@Test
	public void testConstructorLenient_missignLogicalTerminal() {
		SwiftBlock1 bb = new SwiftBlock1("1:F01", true);
		assertEquals("F", bb.getApplicationId());
		assertEquals("01", bb.getServiceId());
		assertNull(bb.getLogicalTerminal());
		assertNull(bb.getSessionNumber());
		assertNull(bb.getSequenceNumber());
	}

	@Test
	public void testConstructorLenient_missignServiceId() {
		SwiftBlock1 bb = new SwiftBlock1("1:F", true);
		assertEquals("F", bb.getApplicationId());
		assertNull(bb.getServiceId());
		assertNull(bb.getLogicalTerminal());
		assertNull(bb.getSessionNumber());
		assertNull(bb.getSequenceNumber());
	}

	@Test
	public void testConstructorLenient_missignApplicationId() {
		SwiftBlock1 bb = new SwiftBlock1("1:", true);
		assertNull(bb.getApplicationId());
		assertNull(bb.getServiceId());
		assertNull(bb.getLogicalTerminal());
		assertNull(bb.getSessionNumber());
		assertNull(bb.getSequenceNumber());
	}

	@Test
	public void testConstructorLenient_empty() {
		SwiftBlock1 bb = new SwiftBlock1("", true);
		assertNull(bb.getApplicationId());
		assertNull(bb.getServiceId());
		assertNull(bb.getLogicalTerminal());
		assertNull(bb.getSessionNumber());
		assertNull(bb.getSequenceNumber());
	}

	@Test
	public void testConstructorLenient_null() {
		SwiftBlock1 bb = new SwiftBlock1(null, true);
		assertNull(bb.getApplicationId());
		assertNull(bb.getServiceId());
		assertNull(bb.getLogicalTerminal());
		assertNull(bb.getSessionNumber());
		assertNull(bb.getSequenceNumber());
	}

}
