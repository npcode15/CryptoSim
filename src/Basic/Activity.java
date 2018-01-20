package Basic;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.UIManager;

import Algorithm.DigitalSignature.DSA_Sign;

public class Activity extends JFrame implements ActionListener 
{
	private JButton encrypt, decrypt, d_Sign, symm, asymm;

	private static String clicked="";

	static Activity activity_frame;

	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	int scrW=screenSize.width, scrH=screenSize.height;
	int i=1, start_x=0, start_y=scrH/2, grid_length=scrW/4, grid_breadth=75, btn_location=0;

	String flag="";

	String type[]={"SELECT TYPE OF ALGORITHM", "Symmetric/Private Key Cryptography", "Asymmetric/Public Key Cryptography"};

	String privatealgo[]={"SPECIFY ALGORITHM", "AES (Advanced Encryption Standard)",  "DES (Data Encryption Standard)",
			"Triple-DES", "Blowfish", "Twofish", "TEA (Tiny Encryption Algorithm)", "RC2", "RC4"};

	String publicalgo[]={"SPECIFY ALGORITHM", "DSA/DSS (Digital Signature Standard)", "ElGamal", "RSA algorithm", "Pailler Cryptosystem"};

	public Activity() 
	{
		setTitle("Activity Selection Window");
		setSize(scrW, scrH);
		setLayout(null);

		btn_location=(scrW-250*3)/3;

		encrypt=new JButton("Encryption");
		encrypt.setBounds(btn_location, scrH/4, 250, 50);
		add(encrypt);
		encrypt.setVisible(true);
		encrypt.addActionListener(this);

		decrypt=new JButton("Decryption");
		decrypt.setBounds(btn_location+350, scrH/4, 250, 50);
		add(decrypt);
		decrypt.setVisible(true);
		decrypt.addActionListener(this);


		d_Sign=new JButton("Digital Signature");
		d_Sign.setBounds(btn_location+700, scrH/4, 250, 50);
		add(d_Sign);
		d_Sign.setVisible(true);
		d_Sign.addActionListener(this);

		symm=new JButton("Symmetric Algorithm");
		symm.setBounds((scrW-250*2)/2-50, scrH/4+200, 250, 50);
		add(symm);
		symm.setVisible(false);
		symm.addActionListener(this);

		asymm=new JButton("Asymmetric Algorithm");
		asymm.setBounds((scrW-250*2)/2+300, scrH/4+200, 250, 50);
		add(asymm);
		asymm.setVisible(false);
		asymm.addActionListener(this);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public static void main(String[] args) 
	{
		try 
		{
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} 
		catch(Exception e) 
		{
			e.printStackTrace(); 
		}
		activity_frame=new Activity();
	}

	public void actionPerformed(ActionEvent evt)
	{		
		clicked=evt.getActionCommand();

		System.out.println(clicked);

		if(clicked.equals("Encryption") ||  flag.equals("Enc"))
		{			
			d_Sign.setEnabled(false);
			decrypt.setEnabled(false);
			encrypt.setEnabled(true);

			symm.setVisible(true);
			asymm.setVisible(true);

			flag="Enc";

			if(clicked.equals("Symmetric Algorithm"))
			{
				new Symm_Algo_Enc();
				this.dispose();
			}

			if(clicked.equals("Asymmetric Algorithm"))
			{
				new Asymm_Algo_Enc();
				this.dispose();
			}
		}
		else
			if(clicked.equals("Decryption") ||  flag.equals("Dec"))
			{
				symm.setVisible(true);
				asymm.setVisible(true);

				d_Sign.setEnabled(false);
				decrypt.setEnabled(true);

				flag="Dec";

				if(clicked.equals("Symmetric Algorithm"))
				{
					new Symm_Algo_Dec();
					this.dispose();
				}

				if(clicked.equals("Asymmetric Algorithm"))
				{
					new Asymm_Algo_Dec();
					this.dispose();
				}
			}
			else
				if(clicked.equals("Digital Signature"))
				{
					d_Sign.setEnabled(true);
					decrypt.setEnabled(false);
					encrypt.setEnabled(false);

					new DSA_Sign();
					this.dispose();
				}
	}
}