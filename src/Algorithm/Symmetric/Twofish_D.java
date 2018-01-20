package Algorithm.Symmetric;

import gnu.crypto.cipher.Twofish;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.security.InvalidKeyException;

import javax.crypto.SecretKey;

import org.apache.commons.codec.binary.Base64;

import Basic.Activity;

public class Twofish_D extends JFrame implements ActionListener
{
	static String IV = "AAAAAAAAAAAAAAAA";
	static String ciphertext = "";
	static String plaintext = "";
	static String decryptionKey= "";
	static String flag_entry=""; 

	static JButton startp, startd, home, refresh ;
	static JDialog option;
	static JFrame frame;
	static JTextField textinput, keyinput;
	static JLabel fileinfo, ctextinfo, keyinputinfo;
	static JTextArea cipherop;

	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	int scrW=screenSize.width, scrH=screenSize.height;

	public Twofish_D()
	{
		setTitle("TEA Decryption");
		setLayout(null);
		setSize(scrW, scrH);

		home=new JButton("Home");											// Redirect to Activity
		home.setBounds(5, 5, 100, 40);
		add(home);
		home.setVisible(true);
		home.addActionListener(this);

		refresh=new JButton("Refresh");										// Restart Same Frame
		refresh.setBounds((scrW-5-100), 5, 100, 40);
		add(refresh);
		refresh.setVisible(true);
		refresh.addActionListener(this);

		startd=new JButton("Start Decryption");									// Start Decryption Button
		startd.setBounds((scrW-250)/2, scrH/2-25, 250, 50);
		add(startd);
		startd.setVisible(false);
		startd.addActionListener(this);

		startp=new JButton("Start Process");									// Start Process Button
		startp.setBounds((scrW-750)/3, scrH/3, 250, 50);
		add(startp);
		startp.setVisible(true);
		startp.addActionListener(this);

		textinput = new JTextField();											// CipherText Input TextField
		textinput.setBounds((scrW-750)/3+300, scrH/3, 250, 50);
		textinput.setVisible(false);
		textinput.addActionListener(this);
		add(textinput);

		keyinput = new JTextField();											// Key Input TextField
		keyinput.setBounds((scrW-750)/3+600, scrH/3, 250, 50);
		keyinput.setVisible(false);
		keyinput.setToolTipText("Minimum Key-Size Required : 8".toUpperCase());
		add(keyinput);

		ctextinfo =new JLabel();												// CipherText Information Label
		ctextinfo.setBounds((scrW-750)/3+300, scrH/3-40, 250, 50);
		ctextinfo.setText("Enter Text to be Decrypted");
		ctextinfo.setVisible(false);
		add(ctextinfo);

		keyinputinfo =new JLabel();												// Key Information Label
		keyinputinfo.setBounds((scrW-750)/3+600, scrH/3-40, 250, 50);
		keyinputinfo.setText("Enter Secret Key");
		keyinputinfo.setVisible(false);
		add(keyinputinfo);

		fileinfo=new JLabel();													// CipherText Location Label		
		fileinfo.setBounds((scrW-500)/2-40, scrH/3+50, 500, 50);
		fileinfo.setVisible(false);
		add(fileinfo);

		cipherop= new JTextArea();												// PlainText TextArea
		cipherop.setEditable(false);  
		cipherop.setCursor(null);  
		cipherop.setOpaque(false);  
		cipherop.setFocusable(false);
		cipherop.setLineWrap(true);
		cipherop.setBounds(0, scrH-scrH/3, scrW-20, 75);
		cipherop.setVisible(false);
		cipherop.setWrapStyleWord(true);
		add(cipherop);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}  

	public static void main(String [] args)
	{
		try 
		{
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} 
		catch(Exception e) 
		{
			e.printStackTrace(); 
		}
		frame = new Twofish_D();
	}

	public void actionPerformed(ActionEvent evt)
	{
		String str=evt.getActionCommand();

		if(str.equals("Home"))
		{
			this.dispose();
			new Activity();
		}

		if(str.equals("Refresh"))
		{
			this.dispose();
			new Twofish_D();
		}

		if(str.equals("Start Process"))
		{
			startp.setEnabled(false); 
			int answer = JOptionPane.showConfirmDialog(frame, "Do you want to take CIPHERTEXT INPUT from FILE?");
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
							ciphertext+=line;
						flag_entry="File";

						startd.setVisible(true);
						fileinfo.setVisible(true);
						keyinput.setVisible(true);
						keyinputinfo.setVisible(true);
					}
					catch(IOException ex)
					{
						System.out.println(ex);
					}
					fileinfo.setText("(Location of Cipher Text : "+selectedFile.getAbsolutePath()+")");
				}
			}
			else 
				if(answer == JOptionPane.NO_OPTION) 
				{
					flag_entry="Manual";

					startd.setVisible(true);
					textinput.setVisible(true);
					keyinput.setVisible(true);
					ctextinfo.setVisible(true);
					keyinputinfo.setVisible(true);
				}
		}

		if(str.equals("Start Decryption"))
		{	  
			if(flag_entry.equals("Manual"))
			{
				if(!textinput.getText().equals(""))
				{	
					if(!keyinput.getText().equals(""))
					{
						ciphertext=textinput.getText();
						try
						{
							if(!keyinput.getText().equals(""))
								decryptionKey=keyinput.getText();

							process();
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
					if(textinput.getText().equals(""))
					{
						JOptionPane p=new JOptionPane();
						JOptionPane.showMessageDialog(p,"enter the message!!".toUpperCase()); 
					}
			}
			else 
				if(flag_entry.equals("File"))
				{
					System.out.println(keyinput.getText());
					if(!keyinput.getText().equals(""))
					{
						if(!keyinput.getText().equals(""))
							decryptionKey=keyinput.getText();
						try
						{
							process();
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

	public static void process()
	{	
		try
		{  
			if(decryptionKey.length()<8)
			{
				JOptionPane jop=new JOptionPane();
				JOptionPane.showMessageDialog(jop,"Minimum 8 characters required".toUpperCase()); 
			}
			else
			{
				if((decryptionKey.length()%16)!=0 && decryptionKey.length()<16 && decryptionKey.length()>8) 
					while((decryptionKey.length()%16)!=0)
						decryptionKey+=" ";

				if((decryptionKey.length()%16)!=0 && decryptionKey.length()>16)
					decryptionKey=decryptionKey.substring(0,16);

				System.out.println("ciphertext : Length >> "+ciphertext+" : "+ciphertext.length());
				System.out.println("Key : Length >> "+decryptionKey.trim()+" : "+decryptionKey.trim().length());

				plaintext = decrypt(ciphertext, decryptionKey);

				System.out.println("plaintext : Length >>"+plaintext.trim()+" : "+plaintext.trim().length());
				cipherop.setText("PLAIN TEXT : \n"+plaintext);
				cipherop.setVisible(true);

				System.out.println(cipherop.getText());
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		} 
	}

	public static String decrypt (String cookieValue, String key) throws InvalidKeyException, UnsupportedEncodingException 
	{
		byte[] encryptedText=Base64.decodeBase64(cookieValue);
		byte[] decryptedText;
		Twofish twofish = new Twofish();

		byte[] keyBytes = key.getBytes();
		Object keyObject = twofish.makeKey(keyBytes, 16);

		decryptedText = new byte[cookieValue.length()];

		for(int i=0; i<Array.getLength(encryptedText); i+=16) 
			twofish.decrypt(encryptedText, i, decryptedText, i, keyObject, 16);

		String decryptedString = new String(decryptedText, "UTF8");
		return decryptedString;
	}

	public static void saveFile(String cipher) 
	{
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("/Libraries/Documents"));
		int retrival = chooser.showSaveDialog(null);
		if(retrival == JFileChooser.APPROVE_OPTION) 
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