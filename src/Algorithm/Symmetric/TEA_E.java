package Algorithm.Symmetric;

import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
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

import org.apache.commons.codec.binary.Base64;

import Basic.Activity;
import Basic.SendByEmail;

public class TEA_E extends JFrame implements ActionListener
{
	private final static int ADD = 0x9E3779B9;
	private final static int CUPS  = 32;

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

	private int[] S = new int[4];
	static int response=0;
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	int scrW=screenSize.width, scrH=screenSize.height;

	public TEA_E()
	{
		setTitle("TEA Encryption");
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

	public TEA_E(byte[] key) throws HeadlessException 
	{
		for(int off=0, i=0; i<4; i++)
			S[i] = ((key[off++] & 0xff)) | ((key[off++] & 0xff) <<  8) | ((key[off++] & 0xff) << 16) | ((key[off++] & 0xff) << 24);
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
		frame = new TEA_E();
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
			new TEA_E();
		  }
		
		if(str.equals("Save Encrypted-Text to a File"))
		   saveFile(ciphertext);
		
		if(str.equals("Send Encrypted Text By E-Mail"))
			new SendByEmail();
		
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

	public void process()
	{
		try
		{   
			if(keyselect.isSelected())
			{
				String autoStr = UUID.randomUUID().toString();
				encryptionKey=autoStr.substring(0, 16);
				
				FileOutputStream stream = null;
				PrintStream out = null;
				try
				{
					stream = new FileOutputStream("PrivateKey[TEA]"+".txt"); 
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

			if((plaintext.length()%16)!=0) 
				while((plaintext.length()%16)!=0)
					plaintext+=" ";

			if(encryptionKey.length()<8 )
			{
				JOptionPane jop=new JOptionPane();
				JOptionPane.showMessageDialog(jop,"Minimum 8 characters required".toUpperCase()); 
			}
			else
			{
				if((encryptionKey.length()%16)!=0 && encryptionKey.length()<16 && encryptionKey.length()>8) 
					while((encryptionKey.length()%16)!=0)
						encryptionKey+=" ";

				if((encryptionKey.length()%16)!=0 && encryptionKey.length()>16)
					encryptionKey=encryptionKey.substring(0,16);

				System.out.println("plaintext : Length >> "+plaintext+" : "+plaintext.length());
				System.out.println("Key : Length >> "+encryptionKey+" : "+encryptionKey.length());

				TEA_E tea = new TEA_E(encryptionKey.getBytes());
				byte[] original = plaintext.getBytes();
				byte[] crypt = tea.encrypt(original);
				ciphertext = new Base64().encodeToString(crypt);

				System.out.println("Ciphertext : Length >> "+ciphertext+" : "+ciphertext.length());
				cipherop.setText("CIPHER TEXT : "+ciphertext);
				cipherop.setVisible(true);

				sendByMail.setEnabled(true);
				saveToFile.setEnabled(true);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}	

	public byte[] encrypt(byte[] clear) 
	{
		int paddedSize = ((clear.length/8) + (((clear.length%8)==0)?0:1)) * 2;
		int[] buffer = new int[paddedSize + 1];
		buffer[0] = clear.length;
		pack(clear, buffer, 1);
		brew(buffer);
		return unpack(buffer, 0, buffer.length * 4);
	}

	void brew(int[] buf) 
	{
		assert buf.length % 2 == 1;
		int i, v0, v1, sum, n;
		i = 1;
		while (i<buf.length) 
		{
			n = CUPS;
			v0 = buf[i];
			v1 = buf[i+1];
			sum = 0;
			while (n-->0) 
			{
				sum += ADD;
				v0  += ((v1 << 4 ) + S[0] ^ v1) + (sum ^ (v1 >>> 5)) + S[1];
				v1  += ((v0 << 4 ) + S[2] ^ v0) + (sum ^ (v0 >>> 5)) + S[3];
			}
			buf[i] = v0;
			buf[i+1] = v1;
			i+=2;
		}
	}

	void pack(byte[] src, int[] dest, int destOffset) {
		assert destOffset + (src.length / 4) <= dest.length;
		int i = 0, shift = 24;
		int j = destOffset;
		dest[j] = 0;
		while (i<src.length) {
			dest[j] |= ((src[i] & 0xff) << shift);
			if (shift==0) {
				shift = 24;
				j++;
				if (j<dest.length) dest[j] = 0;
			}
			else {
				shift -= 8;
			}
			i++;
		}
	}

	byte[] unpack(int[] src, int srcOffset, int destLength) 
	{
		assert destLength <= (src.length - srcOffset) * 4;
		byte[] dest = new byte[destLength];
		int i = srcOffset;
		int count = 0;
		for (int j = 0; j < destLength; j++) {
			dest[j] = (byte) ((src[i] >> (24 - (8*count))) & 0xff);
			count++;
			if (count == 4) {
				count = 0;
				i++;
			}
		}
		return dest;
	}

	public static void saveFile(String cipher) 
	{
		JFileChooser chooser = new JFileChooser();

		int returnVal = chooser.showSaveDialog(null);

		if(returnVal == JFileChooser.APPROVE_OPTION) 
		{
			FileOutputStream stream = null;
			PrintStream out = null;
			try
			{
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