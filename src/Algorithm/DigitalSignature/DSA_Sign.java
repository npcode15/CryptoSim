package Algorithm.DigitalSignature;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

import Basic.Activity;
import Basic.SendByEmail;

public class DSA_Sign extends JFrame implements ActionListener
{
	static JButton startp, startsign, showKeyPair, home, refresh, sendByMail, saveToFile;
	static JDialog option;
	static JFrame frame;
	static JTextField textinput;
	static JLabel fileinfo, unSignedTextInfo;
	static JTextArea signedtf, UnSignedDoc;

	private static PublicKey pukey; 
	private static PrivateKey prkey;
	private static byte[] realSig;
	private static byte[] strByte;

	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	int scrW=screenSize.width, scrH=screenSize.height;
	private Object flag_entry;
	private String plaintext;

	public DSA_Sign()
	{
		setTitle("Digital Signature");
		setLayout(null);
		setSize(scrW, scrH);

		home=new JButton("Home");												// Redirect to Activity
		home.setBounds(5, 5, 100, 40);
		add(home);
		home.setVisible(true);
		home.addActionListener(this);

		refresh=new JButton("Refresh");											// Restart Same Frame
		refresh.setBounds((scrW-5-100), 5, 100, 40);
		add(refresh);
		refresh.setVisible(true);
		refresh.addActionListener(this);

		sendByMail=new JButton("Send Encrypted Text By E-Mail");			// Send by Mail 
		sendByMail.setBounds((scrW-5-250), (scrH-100-5), 250, 40);
		add(sendByMail);
		sendByMail.setVisible(true);
		sendByMail.setEnabled(false);
		sendByMail.addActionListener(this);

		saveToFile=new JButton("Save Encrypted-Text to a File");			// Save To File
		saveToFile.setBounds((5), (scrH-100-5), 250, 40);
		add(saveToFile);
		saveToFile.setVisible(true);
		saveToFile.setEnabled(false);
		saveToFile.addActionListener(this);

		showKeyPair=new JButton("Show Public Key");								// Show counter-part of the entered Key 
		showKeyPair.setBounds((scrW-1000)/2+350+300, scrH/2, 250, 50);
		add(showKeyPair);
		showKeyPair.setVisible(false);
		showKeyPair.setEnabled(false);
		showKeyPair.addActionListener(this);

		startp=new JButton("Start Process");									// Start Process Button
		startp.setBounds((scrW-1000)/2, scrH/3, 250, 50);
		add(startp);
		startp.setVisible(true);
		startp.addActionListener(this);

		startsign=new JButton("Sign the Document");								// Start Signing the Document Button
		startsign.setBounds((scrW-1000)/2+350, scrH/2, 250, 50);
		add(startsign);
		startsign.setVisible(false);
		startsign.addActionListener(this);

		textinput = new JTextField();											// CipherText Input TextField
		textinput.setBounds((scrW-1000)/2+300, scrH/3, 650, 50);
		textinput.setVisible(false);
		textinput.addActionListener(this);
		add(textinput);

		unSignedTextInfo =new JLabel();										// UnSigned Text Information Label
		unSignedTextInfo.setBounds((scrW-750)/3+300, scrH/3-40, 250, 75);
		unSignedTextInfo.setText("Enter Text to be Digitally Signed");
		unSignedTextInfo.setVisible(false);
		add(unSignedTextInfo);

		fileinfo=new JLabel();													// CipherText Location Label		
		fileinfo.setBounds((scrW-500)/2-40, scrH/3+50, 500, 50);
		fileinfo.setVisible(false);
		add(fileinfo);

		UnSignedDoc= new JTextArea();												// Signed Text TextArea
		UnSignedDoc.setEditable(false);  
		UnSignedDoc.setCursor(null);  
		UnSignedDoc.setOpaque(false);  
		UnSignedDoc.setFocusable(false);
		UnSignedDoc.setLineWrap(true);
		UnSignedDoc.setBounds((scrW-1000)/2+300, scrH/3, 650, 65);
		UnSignedDoc.setVisible(false);
		UnSignedDoc.setWrapStyleWord(true);
		add(UnSignedDoc);

		signedtf= new JTextArea();												// Signed Text TextArea
		signedtf.setEditable(false);  
		signedtf.setCursor(null);  
		signedtf.setOpaque(false);  
		signedtf.setFocusable(false);
		signedtf.setLineWrap(true);
		signedtf.setBounds(0, scrH-scrH/3, scrW-20, 75);
		signedtf.setVisible(false);
		signedtf.setWrapStyleWord(true);
		add(signedtf);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public static void main(String[] args) throws Exception 
	{
		try 
		{
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} 
		catch(Exception e) 
		{
			e.printStackTrace(); 
		}
		frame = new DSA_Sign();
	}

	public void process() throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, UnsupportedEncodingException
	{
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("EC");
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");

		keyGen.initialize(256, random);

		KeyPair pair = keyGen.generateKeyPair();
		prkey = pair.getPrivate();
		pukey = pair.getPublic();

		Signature dsa = Signature.getInstance("SHA1withECDSA");

		dsa.initSign(prkey);

		String str = plaintext;
		strByte = str.getBytes("UTF-8");
		dsa.update(strByte);

		realSig = dsa.sign();		
		System.out.println("Signature: " + new BigInteger(1, realSig).toString(16));
	}

	public void actionPerformed(ActionEvent evt) 
	{
		String str=evt.getActionCommand();

		if(str.equals("Show Public Key"))
		{
			JOptionPane p=new JOptionPane();
			String strs="Public Key is : "+pukey;
			JOptionPane.showMessageDialog(p, strs); 
		}

		if(str.equals("Home"))
		{
			this.dispose();
			new Activity();
		}

		if(str.equals("Refresh"))
		{
			this.dispose();
			new DSA_Sign();
		}

		if(str.equals("Save Signed-Document to a File"))
			saveFile("Signature: " + new BigInteger(1, realSig).toString(16));

		if(str.equals("Send Encrypted Text By E-Mail"))
			new SendByEmail();
		
		if(str.equals("Start Process"))
		{
			startp.setEnabled(false);
			
			int answer = JOptionPane.showConfirmDialog(frame, "Do you want to take INPUT from FILE?");
			if(answer == JOptionPane.YES_OPTION) 
			{
				JFileChooser fileChooser = new JFileChooser();

				int returnValue = fileChooser.showOpenDialog(null);
				if(returnValue == JFileChooser.APPROVE_OPTION)
				{
					File selectedFile = fileChooser.getSelectedFile();
					try
					{
						BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(selectedFile), "UTF-8"));
						String line;
						while((line = reader.readLine()) != null)
							plaintext+=line;

						flag_entry="File";

						UnSignedDoc.setText(plaintext);
						UnSignedDoc.setVisible(true);

						startsign.setVisible(true);
						showKeyPair.setVisible(true);

						fileinfo.setVisible(true);
						fileinfo.setLocation((scrW-1000)/2+300, scrH/3+50);
					}
					catch(IOException ex)
					{
						System.out.println(ex);
					}
					fileinfo.setText("(Location of Plain Text : "+selectedFile.getAbsolutePath()+")");
				}
			}
			else 
				if(answer == JOptionPane.NO_OPTION) 
				{
					flag_entry="Manual";

					startsign.setVisible(true);
					showKeyPair.setVisible(true);
					textinput.setVisible(true);
				}
		}

		if(str.equals("Sign the Document"))
		{	  
			if(flag_entry.equals("Manual"))
			{	
				if((!textinput.getText().equals("")))
				{
					plaintext=textinput.getText();
					try
					{
						process();
						signedtf.setVisible(true);
						signedtf.setText("Signature: "+new BigInteger(1, realSig).toString(16));

						sendByMail.setEnabled(true);
						saveToFile.setEnabled(true);
					} 
					catch (Exception e1) 
					{
						e1.printStackTrace();
					}
				}   
				else 
					{
						JOptionPane p=new JOptionPane();
						JOptionPane.showMessageDialog(p,"select option for key!!".toUpperCase()); 
					}
			}
			else 
			if(flag_entry.equals("File"))
				{
					if((!UnSignedDoc.getText().equals("")))
					{
						try
						{
							process();
							signedtf.setVisible(true);
							signedtf.setText("Signature: "+new BigInteger(1, realSig).toString(16));

							sendByMail.setEnabled(true);
							saveToFile.setEnabled(true);
						} 
						catch (Exception e1) 
						{
							e1.printStackTrace();
						}
					}
					else
					{
						JOptionPane p=new JOptionPane();
						JOptionPane.showMessageDialog(p,"select option for key!!"); 
					}
				}
		}
	}

	public static void saveFile(String cipher) 
	{
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("/Libraries/Documents"));
		int retrival = chooser.showSaveDialog(null);
		if (retrival == JFileChooser.APPROVE_OPTION) 
		{
			try 
			{
				FileWriter fw = new FileWriter(chooser.getSelectedFile()+".txt");
				fw.write(cipher.toString());
			}
			catch (Exception ex) 
			{
				ex.printStackTrace();
			}
		}
	}
}