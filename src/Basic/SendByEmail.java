package Basic;
import java.io.File;

import java.util.ArrayList;
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
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class SendByEmail extends JFrame
{
	Properties emailProperties;
	Session mailSession;
	MimeMessage emailMessage;
	String path, name;
	ArrayList toMail;

	public SendByEmail()
	{
		JOptionPane.showMessageDialog(null,"Choose a File to be Mailed","Choose File",JOptionPane.WARNING_MESSAGE);
	
		JFileChooser fileChooser = new JFileChooser();
		int returnValue = fileChooser.showOpenDialog(null);
		if(returnValue == JFileChooser.APPROVE_OPTION)
		{
			File selectedFile = fileChooser.getSelectedFile();
			path=selectedFile.getAbsolutePath();

			toMail=new ArrayList();

			int answer;
			do
			{
				toMail.add(JOptionPane.showInputDialog("Enter E-mail Recipient(s)"));
				answer = JOptionPane.showConfirmDialog(null, "Do you want to add more Recipients");
			}while(answer != JOptionPane.NO_OPTION);
		}
	}

	public static void main(String args[]) throws AddressException, MessagingException 
	{
		SendByEmail javaEmail = new SendByEmail();

		javaEmail.setMailServerProperties();
		javaEmail.createEmailMessage();
		javaEmail.sendEmail();
	}

	public void setMailServerProperties() 
	{
		String smtp_Port = "587";										// Gmail's SMTP Port

		emailProperties = System.getProperties();
		emailProperties.put("mail.smtp.auth", "true");
		emailProperties.put("mail.smtp.port", smtp_Port);
		emailProperties.put("mail.smtp.starttls.enable", "true");
		emailProperties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
	}

	public void createEmailMessage() throws AddressException,MessagingException 
	{	
		String []  toEmails=new String[toMail.size()];
		for(int i=0; i<toMail.size(); i++) 
		     toEmails[i]=(String) toMail.get(i);
			
		String emailSubject = "JavaMail";
		String emailBody = "This is an email sent by JavaMail api";

		mailSession = Session.getDefaultInstance(emailProperties, null);
		emailMessage = new MimeMessage(mailSession);

		for(int i=0; i<toEmails.length; i++) 
			emailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmails[i]));

		emailMessage.setSubject(emailSubject);
		emailMessage.setContent(emailBody, "text/html");					//for a HTML+Text email
	}

	public void sendEmail() throws AddressException, MessagingException 
	{
		String emailHost = "smtp.gmail.com";
		String fromUser = "navneetpandey4@gmail.com"; 						//Sender's Gmail Username
		String fromUserEmailPassword = "Life_np4_Google_Boundaries";		//Sender's Gmail Password

		// Create the message part 
		BodyPart messageBodyPart = new MimeBodyPart();

		// Fill the message
		messageBodyPart.setText("This is message body");

		// Create a multipart message
		Multipart multipart = new MimeMultipart();

		// Set text message part
		multipart.addBodyPart(messageBodyPart);

		// Part two is attachment
		messageBodyPart = new MimeBodyPart();
		String filename = path;
		DataSource source = new FileDataSource(filename);
		messageBodyPart.setDataHandler(new DataHandler(source));
		messageBodyPart.setFileName(filename);
		multipart.addBodyPart(messageBodyPart);

		// Send the complete message parts
		emailMessage.setContent(multipart);

		Transport transport = mailSession.getTransport("smtp");
		transport.connect(emailHost, fromUser, fromUserEmailPassword);
		transport.sendMessage(emailMessage, emailMessage.getAllRecipients());
		transport.close();
		System.out.println("Email sent successfully.");
	}
}