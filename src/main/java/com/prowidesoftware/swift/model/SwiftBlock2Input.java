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
package com.prowidesoftware.swift.model;

import java.io.Serializable;
import java.util.logging.Level;
import com.prowidesoftware.deprecation.ProwideDeprecated;
import com.prowidesoftware.deprecation.TargetYear;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.commons.lang.Validate;

/**
 * Base class for SWIFT <b>Application Header Block (block 2)
 * for INPUT (to SWIFT)</b>.<br>
 * This block is used to construct messages that are going 
 * to be <i>input</i> to the SWIFT network. From the application point
 * of view, it correspond to the <i>SENT</i> messages.<br><br>
 * 
 * <p>It's value is fixed-length and continuous with no field delimiters. 
 * This class contains its elements as individual attributes for 
 * easier management of the block value.</p>
 * 
 * <p>This class does not provide explicit API to get the MIR of an 
 * outgoing message because it should be created using the session and 
 * sequence numbers at header block 1, and this information is not usually 
 * generated by the application creating the message but by the SWIFT 
 * Alliance interface. Therefore neither SwiftBLock1 or SwiftBlock2Input 
 * provide a getter for the MIR.<br />
 * It also does not provide any API to get the MOR of a sent message 
 * because that information will be available only when the message 
 * is delivered at destination.</p>
 * 
 * @author www.prowidesoftware.com
 * @since 4.0
 * @see MIR
 */
//TODO: add parameter checks (Validate.*) and complete javadocs 
public class SwiftBlock2Input extends SwiftBlock2 implements Serializable {
	private static final transient java.util.logging.Logger log = java.util.logging.Logger.getLogger(SwiftBlock2.class.getName());
	private static final long serialVersionUID = 6094810199379196198L;

	/**
	 * Receiver's address with X in position 9.<br> 
	 * It is fixed at 12 characters; it must have X in position 9
	 * (padded with "X" if no branch is required).
	 */
	private String receiverAddress;

	/**
	 * String of 1 character containing the Delivery Monitoring field is as follows:<br>
	 * 1 = Non-Delivery Warning<br>
	 * 2 = Delivery Notification<br>
	 * 3 = Both valid, Non-Delivery Warning and Delivery Notification<br>
	 * This value is optional.<br>
	 * If the priority is U, delivery monitoring must be: 1 or 3.<br> 
	 * If the priority is N, delivery monitoring must be: 2 or not included.
	 */
	private String deliveryMonitoring;

	/**
	 * String of 3 characters containing the Obsolescence Period.<br> 
	 * It specifies when a non-delivery notification is generated as follows:<br>
	 * Valid for U = 003 (15 minutes)<br>
	 * Valid for N = 020 (100 minutes)<br>
	 * This value is optional.
	 */
	private String obsolescencePeriod;

	/**
	 * Constructor for specific values
	 * 
	 * @param messageType the message type
	 * @param receiverAddress the receiver address
	 * @param messagePriority the message priority (S=system, U=urgent, N=normal)
	 * @param deliveryMonitoring the delivery monitoring option (1 or 3 for U priority, 2 for N priority)
	 * @param obsolescencePeriod the obsolescence period, measured in 5 minutes units (3 for priority U, 20 for priority N).<br>
	 * According to SWIFT documentation, this value is ignored by the system
	 */
	public SwiftBlock2Input(final String messageType, final String receiverAddress, final String messagePriority, final String deliveryMonitoring, final String obsolescencePeriod) {
		this.input = true;
		this.messageType = messageType;
		this.receiverAddress = receiverAddress;
		this.messagePriority = messagePriority;
		this.deliveryMonitoring = deliveryMonitoring;
		this.obsolescencePeriod = obsolescencePeriod;
	}

	/**
	 * Creates the block with lenient false, meaning it expects a fixed length value.
	 * Example of supported values:<br> 
	 * "I100BANKDEFFXXXXU3003" (21) or "2:I100BANKDEFFXXXXU3003" (23)
	 * "I100BANKDEFFXXXXU3" (18) or "2:I100BANKDEFFXXXXU3" (20)
	 * "I100BANKDEFFXXXXU" (17) or "2:I100BANKDEFFXXXXU" (19)
	 * 
	 * @param value a string with length between 16 and 23 containing the blocks value
 	 * @throws IllegalArgumentException if parameter has an invalid total size
	 * @see #setValue(String, boolean)
	 */
	public SwiftBlock2Input(final String value) {
		this(value, false);
	}
	
	/**
	 * Creates a block 2 output object setting attributes by parsing the string argument containing the blocks value. 
	 * This value can be in different flavors because some fields are optional.<br>
	 * 
	 * @param value string containing the entire blocks value
	 * @param lenient if true the value will be parsed with a best effort heuristic, if false it will throw a IllegalArgumentException if the value has an invalid total size
	 * @see #setValue(String, boolean)
	 * @since 7.7
	 */
	public SwiftBlock2Input(final String value, boolean lenient) {
		super();
		this.setValue(value, lenient);
	}

	/**
	 * Default constructor
	 */
	public SwiftBlock2Input() {
		super();
	}

	/**
	 * Sets the Message Type (MT) as classified and numbered by SWIFT.
	 * Three-digit FIN message type, example: 103
	 * 
	 * @param messageType
	 *            String of 3 character
	 */
	public void setMessageType(final String messageType) {
		this.messageType = messageType;
	}

	public String getMessageType() {
		return messageType;
	}

	/**
	 * Sets the receiver's address.<br> 
	 * It is fixed at 12 characters; it must have X in position 9
	 * (padded with "X" if no branch is required).
	 * 
	 * @param receiverAddress 12 characters String
	 */
	public void setReceiverAddress(final String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}
	
	/**
	 * Sets the receiver's address.<br> 
	 * 
	 * @see LogicalTerminalAddress#getReceiverLogicalTerminalAddress()
	 * @param logicalTerminal
	 * @since 7.6
	 */
	public void setReceiverAddress(final LogicalTerminalAddress logicalTerminal) {
		this.receiverAddress = logicalTerminal.getReceiverLogicalTerminalAddress();
	}

	/**
	 * Creates a full LT address using the parameter BIC code and a default LT identifier.
	 * 
	 * @see #setReceiverAddress(LogicalTerminalAddress)
	 * @param bic
	 * @since 7.6
	 */
	public void setReceiver(final BIC bic) {
		setReceiverAddress(new LogicalTerminalAddress(bic.getBic11()));
	}
	
	/**
	 * Completes if necessary and sets the LT address of the receiver.<br />
 	 * The receiver addresses will be filled with proper default LT identifier and branch codes if not provided.
	 * 
	 * @see #setReceiverAddress(LogicalTerminalAddress)
	 * @since 7.6
	 */
	public void setReceiver(final String receiver) {
		setReceiverAddress(new LogicalTerminalAddress(receiver));
	}


	/**
	 * Gets the LT receiver's address field in block 2 input
	 * @return receiver address
	 */
	public String getReceiverAddress() {
		return receiverAddress;
	}
	
	/**
	 * Gets the receiver's BIC code from the receiver LT address.
	 * 
	 * @return receiver BIC address
	 * @see BIC
	 * @since 7.6
	 */
	public BIC getReceiverBIC() {
		return new BIC(this.receiverAddress);
	}

	/**
	 * Sets the message priority as follows:<br>
	 * S = System<br>
	 * N = Normal<br>
	 * U = Urgent
	 * 
	 * @param messagePriority String of 1 character 
	 */
	public void setMessagePriority(final String messagePriority) {
		this.messagePriority = messagePriority;
	}

	/**
	 * Gets the the message priority field in block 2 input
	 * @return message priority
	 */
	public String getMessagePriority() {
		return messagePriority;
	}

	/**
	 * Sets the Delivery Monitoring field is as follows:<br>
	 * 1 = Non-Delivery Warning<br>
	 * 2 = Delivery Notification<br>
	 * 3 = Both valid, Non-Delivery Warning and Delivery Notification<br>
	 * This value is optional.<br>
	 * If the priority is U, delivery monitoring must be: 1 or 3.<br> 
	 * If the priority is N, delivery monitoring must be: 2 or not included.
	 * 
	 * @param deliveryMonitoring String of 1 character containing the Delivery Monitoring field
	 */
	public void setDeliveryMonitoring(final String deliveryMonitoring) {
		this.deliveryMonitoring = deliveryMonitoring;
	}

	/**
	 * Gets the Delivery Monitoring field in block 2 input
	 * @return the delivery monitoring
	 */
	public String getDeliveryMonitoring() {
		return deliveryMonitoring;
	}
	
	/**
	 * Gets the delivery monitoring as enum
	 * @return delivery monitoring enum value or null if the delivery monitoring is not set or contains an invalid value
	 * @since 7.8.4
	 */
	public DeliveryMonitoring getDeliveryMonitoringType() {
		if (this.deliveryMonitoring != null) {
			try {
				return DeliveryMonitoring.valueOf("_"+this.deliveryMonitoring);
			} catch (Exception e) {
				final String text = "Block2 deliveryMonitoring contains an invalid value ["+ this.deliveryMonitoring +"]";
				log.warning(text);
				log.log(Level.FINEST, text, e);
			}
		}
		return null;
	}

	/**
	 * Sets the Obsolescence Period.<br> 
	 * It specifies when a non-delivery notification is generated as follows:<br>
	 * Valid for U = 003 (15 minutes)<br>
	 * Valid for N = 020 (100 minutes)<br>
	 * This value is optional.
	 * 
	 * @param obsolescencePeriod String of 3 characters containing the Obsolescence Period
	 */
	public void setObsolescencePeriod(final String obsolescencePeriod) {
		this.obsolescencePeriod = obsolescencePeriod;
	}

	/**
	 * Gets the obsolescence period field in block 2 input
	 * @return the obsolescence period
	 */
	public String getObsolescencePeriod() {
		return obsolescencePeriod;
	}

	/**
	 * Tell if this block is empty or not.
	 * This block is considered to be empty if all its attributes are set to <code>null</code>.
	 * @return <code>true</code> if all fields are <code>null</code> and false in other case
	 */
	public boolean isEmpty() {
		return messageType == null && receiverAddress == null && messagePriority == null && deliveryMonitoring == null && obsolescencePeriod == null;
	}

	/**
	 * Gets the fixed length block 2 value, as a result of
	 * concatenating its individual elements as follow:<br>
	 * Message Type +
	 * Receivers address +
	 * Message Priority +
	 * Delivery Monitoring +
	 * Obsolescence Period.
	 */
	public String getValue() {
		if (isEmpty()) {
			return null;
		}
		final StringBuilder v = new StringBuilder("I");
		if (messageType != null) {
			v.append(messageType);
		}
		if (receiverAddress != null) {
			v.append(receiverAddress);
		}
		if (messagePriority != null) {
			v.append(messagePriority);
		}
		if (deliveryMonitoring != null) {
			v.append(deliveryMonitoring);
		}
		if (obsolescencePeriod != null) {
			v.append(obsolescencePeriod);
		}
		return v.toString();
	}

	/**
	 * Returns the block value
	 * @see #getValue()
	 */
	public String getBlockValue() {
		return getValue();
	}

	/**
	 * Sets the block's attributes by parsing the fixed length string argument.
	 * 
	 * @param value a fixed length (between 16 and 23) string containing the blocks' value
	 * @throws IllegalArgumentException if parameter has an invalid total size.
	 * @see #setValue(String, boolean) 
	 */
	public void setValue(final String value) {
		setValue(value, false);
	}
	
	/**
	 * Sets the block's attributes by parsing the string argument containing the blocks value.<br /> 
	 * This value can be in different flavors because some fields are optional.<br />
	 * Example of supported values:<br />
	 * <pre>
	 * "I100BANKDEFFXXXX"      (16) or "2:I100BANKDEFFXXXX"      (18)   // used for service/system messages
	 * "I100BANKDEFFXXXXU"     (17) or "2:I100BANKDEFFXXXXU"     (19)
	 * "I100BANKDEFFXXXXU3"    (18) or "2:I100BANKDEFFXXXXU3"    (20)
	 * "I100BANKDEFFXXXXU3003" (21) or "2:I100BANKDEFFXXXXU3003" (23)
	 * </pre><br />
	 * 
	 * @param value string containing the entire blocks value
	 * @param lenient if true the value will be parsed with a best effort heuristic, if false it will throw a IllegalArgumentException if the value has an invalid total size
	 */
	public void setValue(final String value, boolean lenient) {
		if (lenient) {
			//leave all attributes as null (cleaning defaults)
			clean();
		} else {
    		// check parameters
    		Validate.notNull(value, "value must not be null");
		}

		if (value != null) {
    		int slen = value.length();
    		
    		if (!lenient) {
        		// check parameters
        		Validate.notNull(value, "value must not be null");
        		Validate.isTrue(slen >= 16 && slen <= 23, "expected a string value of 17 up to 23 chars and obtained a " + slen + " chars string: '" + value + "'");
    		}
    		
    		// figure out the starting point and check the input value has proper optional
    		int offset = 0;
    		if (value.startsWith("2:")) { // accept 2:...
    			offset = 2;
    		}
    		
    		slen -= offset;
    		if (!lenient) {
        		if (slen != 16 && slen != 17 && slen != 18 && slen != 21) {
        			throw new IllegalArgumentException("Value must match: I<mt><address>[<pri>[<monitoring>[<obsolescence>]]]");
        		}
        		if (Character.toUpperCase(value.charAt(offset)) != 'I') {
        			throw new IllegalArgumentException("Value must match: I<mt><address>[<pri>[<monitoring>[<obsolescence>]]]");
        		}
    		}
    		offset++; // skip the input mark

    		// separate value fragments
    		int len = 3;
    		this.setMessageType(this.getValuePart(value, offset, len));
    		offset += len;
    		
    		len = 12;
    		this.setReceiverAddress(this.getValuePart(value, offset, len));
    		offset += len;
    		
    		len = 1;
    		this.setMessagePriority(this.getValuePart(value, offset, len));
    		offset += len; // optional (system messages)
    		
    		len = 1;
    		this.setDeliveryMonitoring(this.getValuePart(value, offset, len));
    		offset += len; // optional
    		
        	if (lenient) {
        		//get all remaining text
        		this.setObsolescencePeriod(this.getValuePart(value, offset));
        	} else {
        		len = 3;
        		this.setObsolescencePeriod(this.getValuePart(value, offset, len));
        	}
        	
		}
	}

	/**
	 * @see #setValue(String)
	 */
	public void setBlockValue(final String value) {
		setValue(value);
	}

	/**
	 * Sets all attributes to null
	 * @since 6.4
	 */
	public void clean() {
		super.messagePriority = null;
		super.messageType = null;
		this.receiverAddress = null;
		this.deliveryMonitoring = null;
		this.obsolescencePeriod = null;
	}

	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((deliveryMonitoring == null) ? 0 : deliveryMonitoring.hashCode());
		result = prime * result + ((messagePriority == null) ? 0 : messagePriority.hashCode());
		result = prime * result + ((messageType == null) ? 0 : messageType.hashCode());
		result = prime * result + ((obsolescencePeriod == null) ? 0 : obsolescencePeriod.hashCode());
		result = prime * result + ((receiverAddress == null) ? 0 : receiverAddress.hashCode());
		return result;
	}

	public boolean equals(final Object obj) {
		if (obj == null) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final SwiftBlock2Input other = (SwiftBlock2Input) obj;
		if (deliveryMonitoring == null) {
			if (other.deliveryMonitoring != null) {
				return false;
			}
		} else if (!deliveryMonitoring.equals(other.deliveryMonitoring)) {
			return false;
		}
		if (messagePriority == null) {
			if (other.messagePriority != null) {
				return false;
			}
		} else if (!messagePriority.equals(other.messagePriority)) {
			return false;
		}
		if (messageType == null) {
			if (other.messageType != null) {
				return false;
			}
		} else if (!messageType.equals(other.messageType)) {
			return false;
		}
		if (obsolescencePeriod == null) {
			if (other.obsolescencePeriod != null) {
				return false;
			}
		} else if (!obsolescencePeriod.equals(other.obsolescencePeriod)) {
			return false;
		}
		if (receiverAddress == null) {
			if (other.receiverAddress != null) {
				return false;
			}
		} else if (!receiverAddress.equals(other.receiverAddress)) {
			return false;
		}
		return true;
	}

	/**
	 * Legacy (version 1) json representation of this object.
	 *
	 * <p>This implementation has been replaced by version 2, based on Gson.</p>
	 *
	 * @deprecated use {@link #toJson()} instead
	 * @since 7.9.8
	 */
	@Deprecated
	@ProwideDeprecated(phase2 = TargetYear._2019)
	public String toJsonV1() {
		final StringBuilder sb = new StringBuilder();
		sb.append("{ \n");

		sb.append(" \"messageType\" : \"").append(messageType).append("\", \n");
		sb.append(" \"receiverAddress\" : \"").append(receiverAddress).append("\", \n");
		sb.append(" \"messagePriority\" : \"").append(messagePriority).append("\", \n");
		sb.append(" \"deliveryMonitoring\" : \"").append(deliveryMonitoring).append("\", \n"); 
		sb.append(" \"obsolescencePeriod\" : \"").append(obsolescencePeriod).append("\" \n");

		sb.append("} ");
		return sb.toString();
	}
	
	/**
	 * Generic getter for block attributes based on qualified names from {@link SwiftBlock2InputField}
	 * @param field field to get
	 * @return field value or <code>null</code> if attribute is not set
	 * @since 7.7
	 */
	public String field(SwiftBlock2InputField field) {
		switch (field) {
			case MessageType:
				return getMessageType();
			case MessagePriority:
				return getMessagePriority();
			case ReceiverAddress:
				return getReceiverAddress();
			case DeliveryMonitoring:
				return getDeliveryMonitoring();
			case ObsolescencePeriod:
				return getObsolescencePeriod();
			default:
				return null;
		}
	}
	
	/**
	 * Generic setter for block attributes based on qualified names from {@link SwiftBlock2InputField}
	 * @param field field to get
	 * @param value content to set
	 * @since 7.8
	 */
	public void setField(SwiftBlock2InputField field, final String value) {
		switch (field) {
			case MessageType:
				setMessageType(value);
				break;
			case MessagePriority:
				setMessagePriority(value);
				break;
			case ReceiverAddress:
				setReceiverAddress(value);
				break;
			case DeliveryMonitoring:
				setDeliveryMonitoring(value);
				break;
			case ObsolescencePeriod:
				setObsolescencePeriod(value);
				break;
			default:
				log.warning("don't know how to set "+field +" to block2");
				break;
		}
	}
	
	/**
	 * Delivery monitoring values
	 * @since 7.8.4
	 */
	public enum DeliveryMonitoring {
		_1("Non-Delivery Warning"),
		_2("Delivery Notification"),
		_3("Non-Delivery Warning and Delivery Notification");
		
		private String label;
		
		DeliveryMonitoring(final String label) {
			this.label = label;
		}
		
		public String getLabel() {
			return this.label;
		}
	}

	/**
	 * This method deserializes the JSON data into an outgoing (input) block 2 object.
	 * @see #toJson()
	 * @since 7.9.8
	 */
	public static SwiftBlock2Input fromJson(String json){
		final GsonBuilder gsonBuilder = new GsonBuilder();
		final Gson gson = gsonBuilder.create();
		return gson.fromJson(json, SwiftBlock2Input.class);
	}
}
