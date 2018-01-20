package Algorithm.Symmetric;

import java.awt.Dimension;
import java.awt.HeadlessException;
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

import org.apache.commons.codec.binary.Base64;

import Basic.Activity;

public class TEA_D extends JFrame implements ActionListener
{
	private final static int ADD = 0x9E3779B9;
	private final static int COUNT  = 32;
	private final static int SUB = 0xC6EF3720;

	static String IV = "AAAAAAAAAAAAAAAA";
	static String ciphertext = "";
	static String plaintext = "";
	static String decryptionKey= "";
	static String flag_entry=""; 

	static JButton startp, startd, home, refresh;
	static JDialog option;
	static JFrame frame;
	static JTextField textinput, keyinput;
	static JLabel fileinfo, ctextinfo, keyinputinfo;
	static JTextArea cipherop;

	private int[] S = new int[4];
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	int scrW=screenSize.width, scrH=screenSize.height;

	public TEA_D()
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

	public TEA_D(byte[] key) throws HeadlessException 
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
		frame = new TEA_D();
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
			new TEA_D();
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

				TEA_D tea = new TEA_D(decryptionKey.getBytes());
				byte[] buf = new Base64().decode(ciphertext);
				byte[] result = tea.decrypt(buf);
				plaintext= new String(result);

				System.out.println("plaintext : Length >>"+plaintext.trim()+" : "+plaintext.trim().length());
				cipherop.setText("PLAIN TEXT : \n"+plaintext);
				cipherop.setVisible(true);
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		} 
	}

	public byte[] decrypt(byte[] crypt) 
	{
		assert crypt.length % 4 == 0;
		assert (crypt.length / 4) % 2 == 1;
		int[] buffer = new int[crypt.length / 4];
		pack(crypt, buffer, 0);
		unbrew(buffer);
		return unpack(buffer, 1, buffer[0]);
	}

	void brew(int[] buf) 
	{
		assert buf.length % 2 == 1;
		int i, v0, v1, sum, n;
		i = 1;
		while (i<buf.length) 
		{
			n = COUNT;
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

	void unbrew(int[] buf) {
		assert buf.length % 2 == 1;
		int i, v0, v1, sum, n;
		i = 1;
		while (i<buf.length) {
			n = COUNT;
			v0 = buf[i];
			v1 = buf[i+1];
			sum = SUB;
			while (n--> 0) {
				v1  -= ((v0 << 4 ) + S[2] ^ v0) + (sum ^ (v0 >>> 5)) + S[3];
				v0  -= ((v1 << 4 ) + S[0] ^ v1) + (sum ^ (v1 >>> 5)) + S[1];
				sum -= ADD;
			}
			buf[i] = v0;
			buf[i+1] = v1;
			i+=2;
		}
	}

	void pack(byte[] src, int[] dest, int destOffset) 
	{
		assert destOffset + (src.length / 4) <= dest.length;
		int i = 0, shift = 24;
		int j = destOffset;
		dest[j] = 0;
		while (i<src.length) 
		{
			dest[j] |= ((src[i] & 0xff) << shift);
			if (shift==0) 
			{
				shift = 24;
				j++;
				if (j<dest.length) dest[j] = 0;
			}
			else 
			{
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

	private static String Caesar(String str)
	{
		int key=3;
		String result="";

		for(int i=0;i<str.length();i++)
		{
			char ch=str.charAt(i);
			char chn=encryptchar(ch,key);
			result+=chn;
		}
		return result;
	}

	private static char encryptchar(char ch,int key)
	{
		if(Character.isLowerCase(ch))
			ch=(char)('a'+(ch-'a'+key)%26);

		if(Character.isUpperCase(ch))
			ch=(char)('A'+(ch-'A'+key)%26);
		return ch;
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