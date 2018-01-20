package Algorithm.Asymmetric;

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

import Basic.Activity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;

public class Paillier_D extends JFrame implements ActionListener
{
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
	private static int bitlen;
	private static BigInteger secretKey;

	private static BigInteger lambda;
	public static BigInteger n;
	public static BigInteger nsquare;
	private static BigInteger pukey;
	
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	int scrW=screenSize.width, scrH=screenSize.height;

	public Paillier_D(int bitLengthVal, int certainty)
	{
	}

	public Paillier_D()
	{
		setTitle("ELGAMAL_D Decryption");
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
		frame = new Paillier_D();
		
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
			new Paillier_D();
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
				{System.out.println(keyinput.getText());
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
				System.out.println("ciphertext : Length >> "+ciphertext+" : "+ciphertext.length());
				System.out.println("Key : Length >> "+decryptionKey.trim()+" : "+decryptionKey.trim().length());

				plaintext = decrypt(ciphertext, decryptionKey);

				System.out.println("plaintext : Length >>"+plaintext.trim()+" : "+plaintext.trim().length());
				cipherop.setText("plaintext : ".toUpperCase()+"\n"+plaintext);
				cipherop.setVisible(true);
				
				}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			
		} 
	}
	
	public static String decrypt(String cipherText, String decryptionKey) throws Exception
	{
		Paillier_D paillier = new Paillier_D(512, 64);
		
		String arr[]=cipherText.split("-");
		n=new BigInteger(arr[1].trim());						//BigInteger p=new BigInteger("9454385892127593527093101404768921006937686406167785879329239977675253668476291716087458976029630353188451293121960886479396018646420135647833816715069289");	
		lambda=new BigInteger(arr[2].trim());					//BigInteger EC=new BigInteger("80121914340064351924517808514990855990997342425150727790925762522671641258272000900948893753783441707036570217394840352560584603408635702613973014779550");
		pukey=new BigInteger(decryptionKey);					//BigInteger u=new BigInteger("2699576091841094913343784794787913028376745038069913716169096199500361893414246269518410030619127077687826601125876524116264737067694244280263072003250438");
		

		System.out.println("n : "+n);
		System.out.println("Public Key : "+pukey);
		System.out.println("lambda : "+lambda);
		System.out.println("cipher : "+arr[0]);

		String cipher=arr[0];									//14787947295544824671164044306848504375373797331538448974031130713540873243589425625255814386193251581346303793003385939196135651429483326207293797131334874394951823537292040395516739037953468437316062207037948137576961646620106040800518155923057163494527300080355193098056743296304989581325231093590837368214-108359989574527527436055372274199232946878099700319160851981349316499962779839-87249785914985555748176385861808236422846777546716283349141390068501008302551
        return paillier.Decryption(new BigInteger(cipher)).toString();
	}

	public BigInteger Decryption(BigInteger c) 
	{	
		nsquare = n.multiply(n);
		return c.modPow(lambda, nsquare).subtract(BigInteger.ONE).divide(n).multiply(pukey).mod(n);
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