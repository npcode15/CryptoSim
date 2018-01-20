package Algorithm.Symmetric;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import Basic.Activity;
import Basic.SendByEmail;

public class DES_E extends JFrame implements ActionListener
{
	static String IV = "AAAAAAAA";
	static String plaintext = "";
	static String ciphertext = "";
	static String encryptionKey="";
	static String flag_entry=""; 

	static SecretKey secretkey=null;
	static Cipher cipher;

	static JButton startp, starte, home, refresh, sendByMail, saveToFile;
	static JDialog option;
	static JFrame frame;
	static JTextField textinput, keyinput;
	static JLabel fileinfo, ptextinfo, keyinputinfo, keyselectinfo, choice;
	static JTextArea cipherop;
	static JCheckBox keyselect;

	static int response=0;
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	int scrW=screenSize.width, scrH=screenSize.height;

	public DES_E()
	{
		setTitle("DES Encryption");
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
		
		startp=new JButton("Start Process");								// Start Process Button
		startp.setBounds((scrW-1000)/3, scrH/3-10, 250, 50);
		add(startp);
		startp.setVisible(true);
		startp.addActionListener(this);

		starte=new JButton("Start Encryption");								// Start Encryption Button
		starte.setBounds((scrW-250)/2, scrH/2, 250, 50);
		add(starte);
		starte.setVisible(false);
		starte.addActionListener(this);

		textinput = new JTextField();										// PlainText Input TextField
		textinput.setVisible(false);
		textinput.setEditable(true);
		textinput.setBounds((scrW-1000)/3+300, scrH/3, 250, 50);
		textinput.addActionListener(this);
		add(textinput);

		keyselect=new JCheckBox("Auto Generate Key");						// Auto Key CheckBox
		keyselect.setBounds((scrW-1000)/3+600+50, scrH/3-15, 200, 50);
		keyselect.setVisible(false);
		keyselect.addActionListener(this);
		add(keyselect);

		keyinput = new JTextField();										// Manual Key TextField
		keyinput.setBounds((scrW-1000)/3+900, scrH/3, 250, 50);
		keyinput.setVisible(false);
		keyinput.setToolTipText("Minimum Key-Size Required : 8".toUpperCase());
		add(keyinput);

		fileinfo=new JLabel();												// PlainText Location Label
		fileinfo.setBounds((scrW-500)/2, scrH/3+60, 500, 50);
		fileinfo.setVisible(false);
		add(fileinfo);

		ptextinfo =new JLabel();											// PlainText Information Label
		ptextinfo.setBounds((scrW-1000)/3+300, scrH/3-40, 250, 50);
		ptextinfo.setText("Enter Text to be Encrypted");
		ptextinfo.setVisible(false);
		add(ptextinfo);

		keyinputinfo =new JLabel();											// Key Information Label
		keyinputinfo.setBounds((scrW-1000)/3+900, scrH/3-40, 250, 50);
		keyinputinfo.setText("Enter Secret Key");
		keyinputinfo.setVisible(false);
		add(keyinputinfo);

		keyselectinfo =new JLabel();										// Label regarding Selecting key Automatically
		keyselectinfo.setBounds((scrW-1000)/3+600+50, scrH/3+5, 200, 50);
		keyselectinfo.setText("(Select to AUTOGENERATE Key)");
		keyselectinfo.setVisible(false);
		add(keyselectinfo);

		choice =new JLabel();												// Label representing OR
		choice.setBounds((scrW-1000)/3+600+250, scrH/3+10, 200, 50);
		choice.setText("OR");
		choice.setVisible(false);
		add(choice);

		cipherop= new JTextArea();											// cipher Text TextArea
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

		frame = new DES_E();
	}

	public void actionPerformed(ActionEvent evt)
	{
		String str=evt.getActionCommand();

		if(str.equals("Auto Generate Key"))
		{
			if(keyselect.isSelected()==true)
			{
				keyinput.setText("");
				keyinput.setEnabled(false);
			}
			if(keyselect.isSelected()==false)
				keyinput.setEnabled(true);
		}

		if(str.equals("Home"))
		{
			this.dispose();
			new Activity();
		}
		
		if(str.equals("Refresh"))
		  {
			this.dispose();
			new DES_E();
		  }
		
		if(str.equals("Save Encrypted-Text to a File"))
		   saveFile(ciphertext);
		
		if(str.equals("Send Encrypted Text By E-Mail"))
			new SendByEmail();
		
		if(str.equals("Start Process"))
		{
			startp.setEnabled(false); 
			int answer = JOptionPane.showConfirmDialog(frame, "Do you want to take PLAINTEXT INPUT from FILE?");
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

						starte.setVisible(true);

						keyinput.setLocation((scrW-1000)/3+900-200, scrH/3);
						keyinput.setVisible(true);
						keyinputinfo.setLocation((scrW-1000)/3+900-200, scrH/3-40);
						keyinputinfo.setVisible(true);

						keyselect.setLocation((scrW-1000)/3+600-200, scrH/3-10);
						keyselect.setVisible(true);
						keyselectinfo.setLocation((scrW-1000)/3+600-200, scrH/3+10);
						keyselectinfo.setVisible(true);

						fileinfo.setVisible(true);
						fileinfo.setLocation((scrW-500)/2-40, scrH/3+50);
						choice.setLocation((scrW-1000)/3+600+25, scrH/3);
						choice.setVisible(true);
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

					starte.setVisible(true);
					textinput.setVisible(true);
					keyinput.setVisible(true);
					keyselect.setVisible(true);
					keyinputinfo.setVisible(true);
					ptextinfo.setVisible(true);
					keyselectinfo.setVisible(true);
					choice.setVisible(true);
				}
		}

		if(str.equals("Start Encryption"))
		{	  
			if(flag_entry.equals("Manual"))
			{
				if(!textinput.getText().equals(""))
				{	
					if((!keyinput.getText().equals(""))||(keyselect.isSelected()==true))
					{
						plaintext=textinput.getText();

						try
						{
							if(!keyinput.getText().equals(""))
								encryptionKey=keyinput.getText();

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
					if(((!keyinput.getText().equals(""))||(keyselect.isSelected()==true)))
					{
						if(!keyinput.getText().equals(""))
							encryptionKey=keyinput.getText();
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

	public static void process() throws Exception
	{ 
		try
		{   
			if(keyselect.isSelected())
			{
				String autoStr = UUID.randomUUID().toString();
				encryptionKey=autoStr.substring(0, 8);
				
				FileOutputStream stream = null;
				PrintStream out = null;
				try
				{
					stream = new FileOutputStream("PrivateKey"+".txt"); 
					String text = encryptionKey;
					out = new PrintStream(stream);
					out.print(text);
				} 
				catch (Exception ex)
				{
					ex.printStackTrace();
				} 
				finally 
				{
					try
					{
						if(stream!=null) stream.close();
						if(out!=null) out.close();
					}
					catch(Exception ex) 
					{
						ex.printStackTrace();
					}
				}
			}

			if((plaintext.length()%8)!=0) 
				while((plaintext.length()%8)!=0)
					plaintext+=" ";

			if(encryptionKey.length()<8)
			{
				JOptionPane jop=new JOptionPane();
				JOptionPane.showMessageDialog(jop,"Minimum 8 characters required".toUpperCase()); 
			}
			else
			{
				if(encryptionKey.length()>=8)
					encryptionKey=encryptionKey.substring(0,8);

				System.out.println("plaintext : Length >> "+plaintext+" : "+plaintext.length());
				System.out.println("Key : Length >> "+encryptionKey+" : "+encryptionKey.length());

				ciphertext=encrypt(plaintext, encryptionKey);

				System.out.println("Ciphertext : Length >> "+ciphertext+" : "+ciphertext.length());
				cipherop.setText("CIPHER TEXT : "+ciphertext);
				cipherop.setVisible(true);

				sendByMail.setEnabled(true);
				saveToFile.setEnabled(true);
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	public static String encrypt (String plaintext, String encryptionKey) throws UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException
	{
		SecretKey key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "DES");
		Cipher cipher = Cipher.getInstance("DES/CBC/NoPadding");
		cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(IV.getBytes("UTF-8")));
		byte[] encval = cipher.doFinal(plaintext.getBytes());
		String encryptedValue = new Base64().encodeToString(encval);
		return encryptedValue;
	}

	public static void saveFile(String cipher) 
	{
		JFileChooser chooser = new JFileChooser();

		int returnVal = chooser.showSaveDialog(null);

		if(returnVal == JFileChooser.APPROVE_OPTION) 
		{
			FileOutputStream stream = null;
			PrintStream out = null;
			try{
				File file = chooser.getSelectedFile();
				stream = new FileOutputStream(file+".txt"); 
				String text = cipher;
				out = new PrintStream(stream);
				out.print(text);
			} 
			catch (Exception ex)
			{
				ex.printStackTrace();
			} 
			finally 
			{
				try
				{
					if(stream!=null) stream.close();
					if(out!=null) out.close();
				}
				catch(Exception ex) 
				{
					ex.printStackTrace();
				}
			}
		}
	}
}