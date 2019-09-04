package com.star.sud.ntfn.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.springframework.context.annotation.Description;

@Description(value = "Notification status")
@XmlRootElement(name = "NotificationStatus")
@XmlType(propOrder = { "status", "msg", "from", "asynSessionId" })
public class NotificationStatus extends ServiceStatus {

	// Static Attributes
	/////////////////////
	private static final long serialVersionUID = 3311527938034579904L;

	// Enumeration
	//////////////
	// public enum STATUS {NEW, SUCESS, EXCEPTION};

	// Attributes
	/////////////
	private STATUS status;
	private String message;
	private String asynSessionId;

	/**
	 * Constructor
	 * 
	 * @param status
	 */
	public NotificationStatus() {
		super();
		this.status = STATUS.SUCCESS;
		this.message = "";
		this.asynSessionId = "";
	}

	// Override Methods
	///////////////////
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "NotificationStatus [status=" + status + ", msg=" + message + ", asynSessionId=" + asynSessionId + "]";
	}

	// Properties
	/////////////
	/**
	 * @return the status
	 */
	public STATUS getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(STATUS status) {
		this.status = status;
	}

	/**
	 * @return the asynSessionId
	 */
	public String getAsynSessionId() {
		return asynSessionId;
	}

	/**
	 * @return the msg
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param msg the msg to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @param asynSessionId the asynSessionId to set
	 */
	public void setAsynSessionId(String asynSessionId) {
		this.asynSessionId = asynSessionId;
	}
}
