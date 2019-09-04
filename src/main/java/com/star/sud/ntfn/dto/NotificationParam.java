package com.star.sud.ntfn.dto;

import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.springframework.context.annotation.Description;

@Description(value = "Notification paarmeters")
@XmlRootElement(name = "NotificationParam")
@XmlType(propOrder = { "appsCode", "templateId", "from", "subjectFields", "contentFeilds", "recipients", "ccs", "bccs",
		"attachments" })
public class NotificationParam implements Serializable, Cloneable {

	// Static Attributes
	////////////////////
	private static final long serialVersionUID = -4801246922349564160L;

	public enum FIELDS {
		SUBJECT, CONTENT
	}

	// Attributes
	//////////////
	private String appsCode;
	private String templateId;
	private String from;
	private HashMap<String, String> subjectFields;
	private HashMap<String, String> contentFeilds;
	private ArrayList<String> recipients;
	private ArrayList<String> ccs;
	private ArrayList<String> bccs;
	private ArrayList<String> attachments;

	// Override Methods
	////////////////////
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "NotificationParam [appsCode=" + appsCode + ", templateId=" + templateId + ", from=" + from
				+ ", subjectFields=" + subjectFields + ", contentFeilds=" + contentFeilds + ", recipients=" + recipients
				+ ", ccs=" + ccs + ", bccs=" + bccs + ", attachments=" + attachments + "]";
	}

	public String toXML() throws Exception {

		try {
			@SuppressWarnings("rawtypes")
			Class entityClass = this.getClass();
			JAXBContext context = JAXBContext.newInstance(entityClass);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			StringWriter writer = new StringWriter();
			m.marshal(this, writer);
			return writer.toString();
		} catch (Exception ex) {
			throw ex;
		}
	}

	// Properties
	/////////////
	/**
	 * @return the appsCode
	 */
	public String getAppsCode() {
		return appsCode;
	}

	/**
	 * @param appsCode the appsCode to set
	 */
	public void setAppsCode(String appsCode) {
		this.appsCode = appsCode;
	}

	/**
	 * @return the templateId
	 */
	public String getTemplateId() {
		return templateId;
	}

	/**
	 * @param templateId the templateId to set
	 */
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	/**
	 * @return the from
	 */
	public String getFrom() {
		return from;
	}

	/**
	 * @param from the from to set
	 */
	public void setFrom(String from) {
		this.from = from;
	}

	/**
	 * @return the subjectFields
	 */
	public HashMap<String, String> getSubjectFields() {
		return subjectFields;
	}

	/**
	 * @param subjectFields the subjectFields to set
	 */
	public void setSubjectFields(HashMap<String, String> subjectFields) {
		this.subjectFields = subjectFields;
	}

	/**
	 * @return the contentFeilds
	 */
	public HashMap<String, String> getContentFeilds() {
		return contentFeilds;
	}

	/**
	 * @param contentFeilds the contentFeilds to set
	 */
	public void setContentFeilds(HashMap<String, String> contentFeilds) {
		this.contentFeilds = contentFeilds;
	}

	/**
	 * @return the recipients
	 */
	public ArrayList<String> getRecipients() {
		return recipients;
	}

	/**
	 * @param recipients the recipients to set
	 */
	public void setRecipients(ArrayList<String> recipients) {
		this.recipients = recipients;
	}

	/**
	 * @return the ccs
	 */
	public ArrayList<String> getCcs() {
		return ccs;
	}

	/**
	 * @param ccs the ccs to set
	 */
	public void setCcs(ArrayList<String> ccs) {
		this.ccs = ccs;
	}

	/**
	 * @return the bccs
	 */
	public ArrayList<String> getBccs() {
		return bccs;
	}

	/**
	 * @param bccs the bccs to set
	 */
	public void setBccs(ArrayList<String> bccs) {
		this.bccs = bccs;
	}

	/**
	 * @return the attachments
	 */
	public ArrayList<String> getAttachments() {
		return attachments;
	}

	/**
	 * @param attachments the attachments to set
	 */
	public void setAttachments(ArrayList<String> attachments) {
		this.attachments = attachments;
	}

}
