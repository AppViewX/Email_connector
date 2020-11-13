package com.appviewx.connector.email.pojos;

/**
 * @author mageshwaran.p
 *
 */
public enum MailType {

	/**
	 * 
	 */
	CRITICAL("Critical", "Critical"),
	/**
	 * 
	 */
	MAJOR("Major", "Major"),
	/**
	 * 
	 */
	NOTIFICATION("Notification", "Notification");

	private String mailTypeName;
	private String imageFileName;

	private MailType(String mailTypeName, String imageFileName) {
		this.mailTypeName = mailTypeName;
		this.imageFileName = imageFileName;
	}

	/**
	 * 
	 * @return mailTypeName
	 */
	public String getMailTypeName() {
		return mailTypeName;
	}

	/**
	 * 
	 * @return imageFileName
	 */
	public String getImageFileName() {
		return imageFileName;
	}

}
