package com.appviewx.connector.email;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;
import java.util.*;

public class EmailTest {

    public static void main(String[] args) throws MessagingException {
        // SMTP info
        String host = "10.10.100.39";
        String port = "25";

        // message info
        String mailTo = "kumar.thangavel@appviewx.com";
        String subject = "Test e-mail with inline images";
        StringBuffer body
                = new StringBuffer("<html>This message contains two inline images.<br>");
        body.append("The first image is a chart:<br>");
        body.append("<img src=\"cid:image1\" width=\"30%\" height=\"30%\" /><br>");
        body.append("The second one is a cube:<br>");
        body.append("<img src=\"cid:image2\" width=\"15%\" height=\"15%\" /><br>");
        body.append("End of message.");
        body.append("</html>");

        // inline images
        Map<String, String> inlineImages = new HashMap<>();
        inlineImages.put("image1", "/home/kumar.t/Pictures/1.png");
        inlineImages.put("image2", "/home/kumar.t/Pictures/1.png");
        send(host, port, mailTo,
                subject, body.toString(), inlineImages);
    }

    public static void send(String host, String port,
                            String toAddress,
                            String subject, String htmlBody,
                            Map<String, String> mapInlineImages)
            throws AddressException, MessagingException {
        String mailFrom = "support@appviewx.com";

        // sets SMTP server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);


        Session session = Session.getInstance(properties);

        // creates a new e-mail message
        Message msg = new MimeMessage(session);

        msg.setFrom(new InternetAddress(mailFrom));
        InternetAddress[] toAddresses = { new InternetAddress(toAddress) };
        msg.setRecipients(Message.RecipientType.TO, toAddresses);
        msg.setSubject(subject);
        msg.setSentDate(new Date());

        // creates message part
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(htmlBody, "text/html");

        // creates multi-part
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        // adds inline image attachments
        if (mapInlineImages != null && mapInlineImages.size() > 0) {
            Set<String> setImageID = mapInlineImages.keySet();

            for (String contentId : setImageID) {
                MimeBodyPart imagePart = new MimeBodyPart();
               imagePart.setHeader("Content-ID", "<" + contentId + ">");
               imagePart.setDisposition(MimeBodyPart.INLINE);

                String imageFilePath = mapInlineImages.get(contentId);
                try {
                    imagePart.attachFile(imageFilePath);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                multipart.addBodyPart(imagePart);
            }
        }

        msg.setContent(multipart);

        Transport.send(msg);
    }

}
