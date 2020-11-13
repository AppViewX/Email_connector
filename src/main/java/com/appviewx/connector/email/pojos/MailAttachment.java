package com.appviewx.connector.email.pojos;

/**
 * @author nareshkumar.r
 * 
 */
public class MailAttachment {

	private String attachmentBytes;
	
	private StringBuilder attachmentType;
	private StringBuilder fileName;

	/**
	 * @return the attachmentType
	 */
	public StringBuilder getAttachmentType() {
		return attachmentType;
	}

	/**
	 * @param attachmentType
	 *            the attachmentType to set
	 */
	public void setAttachmentType(StringBuilder attachmentType) {
		this.attachmentType = attachmentType;
	}

	/**
	 * @return the fileName
	 */
	public StringBuilder getFileName() {
		return fileName;
	}

	/**
	 * @param fileName
	 *            the fileName to set
	 */
	public void setFileName(StringBuilder fileName) {
		this.fileName = fileName;
	}

	public String getAttachmentBytes() {
		return attachmentBytes;
	}

	public void setAttachmentBytes(String attachmentBytes) {
		this.attachmentBytes = attachmentBytes;
	}
}
