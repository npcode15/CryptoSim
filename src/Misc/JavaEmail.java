package Misc;

/*import java.util.*;  
import javax.mail.*;  
import javax.mail.internet.*;  
import javax.activation.*;  

class MailProjectClass
{  
 public static void main(String [] args)
 {  

  String to="navneet.navneet.pandey6@gmail.com";//change accordingly  
  final String user="navneetpandey4@gmail.com";//change accordingly  
  final String password="Life_np4_Google_Boundaries";//change accordingly  

  //1) get the session object     
  Properties props = System.getProperties();  
  //properties.setProperty("mail.smtp.host", "mail.javatpoint.com");  
  props.put("mail.smtp.auth", "true");  
  props.put("mail.smtp.port", "465");
  props.put("mail.smtp.host", "smtp.gmail.com");
  props.put("mail.smtp.starttls.enable", "true");
  
  /*props.put("mail.smtp.host", "smtp.gmail.com");
	props.put("mail.smtp.socketFactory.port", "465");
	props.put("mail.smtp.socketFactory.class",
			"javax.net.ssl.SSLSocketFactory");
	props.put("mail.smtp.auth", "true");
	props.put("mail.smtp.port", "465");*/
  
  /*Session session = Session.getDefaultInstance(properties,  new javax.mail.Authenticator() 
  {  
   protected PasswordAuthentication getPasswordAuthentication() 
   {  
    return new PasswordAuthentication(user,password);  
   }  
  });  */
/*  Session session = Session.getInstance(props, new javax.mail.Authenticator() {
	    protected PasswordAuthentication getPasswordAuthentication() {
	        return new PasswordAuthentication(user, password);
	    }
	});
  
  
  //2) compose message     
  try{  
    MimeMessage message = new MimeMessage(session);  
    message.setFrom(new InternetAddress(user));  
    message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
    message.setSubject("Message Alert");  

    //3) create MimeBodyPart object and set your message text     
    BodyPart messageBodyPart1 = new MimeBodyPart();  
    messageBodyPart1.setText("This is message body");  

    //4) create new MimeBodyPart object and set DataHandler object to this object      
    MimeBodyPart messageBodyPart2 = new MimeBodyPart();  

    String filename = "C:/Users/Navneet/Downloads/TS010168648";//change accordingly  
    DataSource source = new FileDataSource(filename);  
    messageBodyPart2.setDataHandler(new DataHandler(source));  
    messageBodyPart2.setFileName(filename);  


    //5) create Multipart object and add MimeBodyPart objects to this object      
    Multipart multipart = new MimeMultipart();  
    multipart.addBodyPart(messageBodyPart1);  
    multipart.addBodyPart(messageBodyPart2);  

    //6) set the multiplart object to the message object  
    message.setContent(multipart );  

    //7) send message  
    Transport.send(message);  

   System.out.println("message sent....");  
   }catch (MessagingException ex) {ex.printStackTrace();}  
 }  
}  */
 
/*
//File Name SendFileEmail.java

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class SendAttachment
{
	public static void main(String [] args)
	{
		// Recipient's email ID needs to be mentioned.
		//String to = "my@gmail.com";

		// Sender's email ID needs to be mentioned
		String from = "web@gmail.com";

		// Assuming you are sending email from localhost
		String host = "www.gmail.com";
		 String to="navneet.navneet.pandey6@gmail.com";//change accordingly  
		final String user="navneetpandey4@gmail.com";//change accordingly  
  final String password="Life_np4_Google_Boundaries";//change accordingly  

		// Get system properties
		Properties properties = System.getProperties();

		properties.put("mail.smtp.auth", true);
		properties.put("mail.smtp.starttls.enable", true);
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");

		// Setup mail server
		properties.setProperty("smtp.gmail.com", host);

		// Get the default Session object.
		//Session session = Session.getDefaultInstance(properties);
		 Session session = Session.getDefaultInstance(properties,  
   new javax.mail.Authenticator() {  
   protected PasswordAuthentication getPasswordAuthentication() {  
   return new PasswordAuthentication(user,password);  
   }  
  });  
		

		try{
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO,
					new InternetAddress(to));

			// Set Subject: header field
			message.setSubject("This is the Subject Line!");

			// Create the message part 
			BodyPart messageBodyPart = new MimeBodyPart();

			// Fill the message
			messageBodyPart.setText("This is message body");

			// Create a multipar message
			Multipart multipart = new MimeMultipart();

			// Set text message part
			multipart.addBodyPart(messageBodyPart);

			// Part two is attachment
			messageBodyPart = new MimeBodyPart();
			String filename = "C:/Users/Navneet/Downloads/thesis.odt";
			DataSource source = new FileDataSource(filename);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(filename);
			multipart.addBodyPart(messageBodyPart);

			// Send the complete message parts
			message.setContent(multipart );

			// Send message
			Transport.send(message);
			System.out.println("Sent message successfully....");
		}catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}
}*/

import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class JavaEmail {

	Properties emailProperties;
	Session mailSession;
	MimeMessage emailMessage;

	public static void main(String args[]) throws AddressException,
			MessagingException {

		JavaEmail javaEmail = new JavaEmail();

		javaEmail.setMailServerProperties();
		javaEmail.createEmailMessage();
		javaEmail.sendEmail();
	}

	public void setMailServerProperties() 
	{
		String emailPort = "587";//gmail's smtp port

		emailProperties = System.getProperties();
		emailProperties.put("mail.smtp.port", emailPort);
		emailProperties.put("mail.smtp.auth", "true");
		emailProperties.put("mail.smtp.starttls.enable", "true");
		emailProperties.put("mail.smtp.ssl.trust", "smtp.gmail.com");

	}

	public void createEmailMessage() throws AddressException,
			MessagingException {
		String[] toEmails = { "navneetpandey4@gmail.com" };
		String emailSubject = "Java Email";
		String emailBody = "This is an email sent by JavaMail api.";

		mailSession = Session.getDefaultInstance(emailProperties, null);
		emailMessage = new MimeMessage(mailSession);

		for (int i = 0; i < toEmails.length; i++) {
			emailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmails[i]));
		}

		emailMessage.setSubject(emailSubject);
		emailMessage.setContent(emailBody, "text/html");//for a html email
		//emailMessage.setText(emailBody);// for a text email

	}

	public void sendEmail() throws AddressException, MessagingException {

		String emailHost = "smtp.gmail.com";
		String fromUser = "navneetpandey4@gmail.com";//just the id alone without @gmail.com
		String fromUserEmailPassword = "Life_np4_Google_Boundaries";

		// Create the message part 
					BodyPart messageBodyPart = new MimeBodyPart();

					// Fill the message
					messageBodyPart.setText("This is message body");

					// Create a multipar message
					Multipart multipart = new MimeMultipart();

					// Set text message part
					multipart.addBodyPart(messageBodyPart);

					// Part two is attachment
					messageBodyPart = new MimeBodyPart();
					String filename = "C:/Users/Navneet/Downloads/feb_wall5.jpg";
					DataSource source = new FileDataSource(filename);
					messageBodyPart.setDataHandler(new DataHandler(source));
					messageBodyPart.setFileName(filename);
					multipart.addBodyPart(messageBodyPart);

					// Send the complete message parts
					emailMessage.setContent(multipart);

		
		Transport transport = mailSession.getTransport("smtp");
		System.out.println("HERE2");
		transport.connect(emailHost, fromUser, fromUserEmailPassword);
		System.out.println("HERE3");
		transport.sendMessage(emailMessage, emailMessage.getAllRecipients());
		transport.close();
		System.out.println("Email sent successfully.");
	}

}