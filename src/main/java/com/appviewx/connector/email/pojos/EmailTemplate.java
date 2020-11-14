package com.appviewx.connector.email.pojos;

import java.util.List;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class EmailTemplate.
 * 
 * @author karthikeyan.v
 */
public final class EmailTemplate {

	private static final Logger LOGGER = LoggerFactory.getLogger(EmailTemplate.class);

	/** The icon. */
	private MailType mailType;
	
	private List<MailAttachment> mailAttachments;

	/** The email template content. */
	private Object emailTemplateContent;

	/** The email recipients. */
	private List<String> emailRecipients;

	/** The subject. */
	private String subject;

	private String emailContent;

	private boolean isHtml;
	
	private boolean createTemplateContent;

	private List<String> emailCCRecipients;

	private List<String> emailBCCRecipients;

	public boolean isCreateTemplateContent() {
		return createTemplateContent;
	}

	public void setCreateTemplateContent(boolean createTemplateContent) {
		this.createTemplateContent = createTemplateContent;
	}

	public String getEmailContent() {
		return emailContent;
	}

	public void setEmailContent(String emailContent) {
		this.emailContent = emailContent;
	}

	public Object getEmailTemplateContent() {
		
		if(isCreateTemplateContent()) {
			this.applyAllProperties();
		}
		return emailTemplateContent;
	}

	public void setEmailTemplateContent(Object emailTemplateContent) {
		this.emailTemplateContent = emailTemplateContent;
	}

	public List<String> getEmailRecipients() {
		return emailRecipients;
	}

	public void setEmailRecipients(List<String> emailRecipients) {
		this.emailRecipients = emailRecipients;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public List<String> getEmailCCRecipients() {
		return emailCCRecipients;
	}

	public void setEmailCCRecipients(List<String> emailCCRecipients) {
		this.emailCCRecipients = emailCCRecipients;
	}

	public List<String> getEmailBCCRecipients() {
		return emailBCCRecipients;
	}

	public void setEmailBCCRecipients(List<String> emailBCCRecipients) {
		this.emailBCCRecipients = emailBCCRecipients;
	}

	public List<MailAttachment> getMailAttachments() {
		return mailAttachments;
	}

	public void setMailAttachments(List<MailAttachment> mailAttachments) {
		this.mailAttachments = mailAttachments;
	}

	public MailType getMailType() {
		return mailType;
	}

	public void setMailType(MailType mailType) {
		this.mailType = mailType;
	}

	/**
	 * Apply all properties.
	 *
	 * @throws Exception
	 *             the exception
	 * @return this class instance
	 */
	private void applyAllProperties() {
		try {

			final String emailContentVal = (null == getEmailContent() ? "" : getEmailContent());
			final MimeMultipart multipart = new MimeMultipart("related");

			BodyPart messageBodyPart = new MimeBodyPart();

			// first part (the html)
			messageBodyPart.setContent(emailContentVal, "text/html");

			multipart.addBodyPart(messageBodyPart);
			// second part (the image)
			messageBodyPart = new MimeBodyPart();
			final String avxPath = System.getProperty("appviewx.property.path");
			if (null == avxPath) {
				LOGGER.error("Exception: EmailTemplate: appviewx.property.path is not configured");
			} else {
				final StringBuilder imgpath = new StringBuilder(avxPath);
				imgpath.append("/resources/img/").append(getMailType().getImageFileName()).append(".png");
				final DataSource fds = new FileDataSource(imgpath.toString());
				messageBodyPart.setDataHandler(new DataHandler(fds));
			}

			messageBodyPart.setHeader("Content-ID", "<mailType>");
			messageBodyPart.setDisposition(MimeBodyPart.INLINE);

			multipart.addBodyPart(messageBodyPart);
			this.emailTemplateContent = multipart;
		} catch (MessagingException e) {
			LOGGER.error("Exception: EmailTemplate: Error while applying properties to the email template", e);
		}
	}

	/**
	 * 
	 * @return boolean value
	 */
	public boolean isMailAttachmentAvailable() {
		return null != mailAttachments && !mailAttachments.isEmpty();
	}

	/**
	 * @return the isHtml
	 */
	public boolean isHtml() {
		return isHtml;
	}

	public void setHtml(boolean isHtml) {
		this.isHtml = isHtml;
	}
}
