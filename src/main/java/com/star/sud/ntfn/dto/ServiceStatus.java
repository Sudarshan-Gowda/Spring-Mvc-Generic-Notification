package com.star.sud.ntfn.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "ServiceStatus")
@XmlType(propOrder = { "status", "message", "exception", "sbMessage" })
public class ServiceStatus implements Cloneable, Serializable {

	// Static Attributes
	/////////////////////
	private static final long serialVersionUID = 9139057973220546733L;

	public enum STATUS {
		NEW, ABORT, COMPLETED, FAILED, SUCCESS, EXCEPTION
	}

	// Attributes
	/////////////
	private STATUS status;
	@SuppressWarnings("unused")
	private String message;
	private String exception;
	private StringBuffer sbMessage;
	private List<String> results;

	// Override Methods
	///////////////////
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ServiceStatus [status=" + status + ", message=" + getMessage() + ", exception=" + exception
				+ ", reuslts=" + results + "]";
	}

	// Public Methods
	//////////////////
	/**
	 * @param result
	 */
	public void addResult(String result) {
		if (null != result)
			this.results.add(result);
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
	 * @return the message
	 */
	public String getMessage() {
		return sbMessage.toString();
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		sbMessage.append(message);
		sbMessage.append("\r\n");
	}

	/**
	 * @return the exception
	 */
	public String getException() {
		return exception;
	}

	/**
	 * @param exception the exception to set
	 */
	public void setException(String exception) {
		this.exception = exception;
	}

	/**
	 * @return the results
	 */
	public List<String> getResults() {
		return results;
	}

	/**
	 * @param results the results to set
	 */
	public void setResults(List<String> results) {
		this.results = results;
	}

}
