package Basic;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


public class Login extends JFrame implements ActionListener
{
	 Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	 int scrW=screenSize.width, scrH=screenSize.height;
	
	 JLabel username, password, title, background;
	 JTextField user, pass;
	 JButton submit,reset;
	 Font heading;
	 
	 Login()
	 {
	  setLayout(null); 
	  setSize(scrW,scrH);
	  
	  //background=new JLabel(new ImageIcon("IMG.JPG"));
	  //background.setBounds(0,0,scrW,scrH);
	  //add(background);
	  
	  heading = new Font("Lucida Sans", Font.BOLD, 30);
	  
	  title=new JLabel("Welcome to Secure Environment!!");
	  title.setFont(heading);
	  //title.setForeground (Color.WHITE);
	  title.setBounds((scrW-550)/2, 25, 550 , 50);
	  add(title);
	  title.setVisible(true);

	  username=new JLabel("Name");
	  username.setBounds((scrW-75)/10, 200, 75, 20);
	  add(username);
	  //username.setForeground (Color.WHITE);
	  username.setVisible(true);
	  
	  password=new JLabel("Password");
	  password.setBounds((scrW-75)/10, 250, 75, 20);
	  add(password);
	  //password.setForeground (Color.WHITE);
	  password.setVisible(true);
	  
	  user=new JTextField();
	  user.setBounds(scrW/10+75, 200, 100, 20);
	  add(user);
	  user.setVisible(true);
	  
	  pass=new JTextField();
	  pass.setBounds(scrW/10+75, 250, 100, 20);
	  add(pass);
	  pass.setVisible(true);
	  
	  submit=new JButton("Ok");
	  submit.setBounds((scrW-75)/10, 300, 75, 25);
	  add(submit);
	  submit.setVisible(true);
	  submit.addActionListener(this);
	  
	  reset=new JButton("Reset");
	  reset.setBounds(scrW/10+100, 300, 75, 25);
	  add(reset);
	  reset.setVisible(true);
	  reset.addActionListener(this);
	  
	  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  setVisible(true);
	 }
	 
	 public static void main(String args[])
	 { 
		 try 
		 {
			 UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel"); 
		 } 
		 catch(Exception e) 
		 {
			 e.printStackTrace(); 
		 }
		 new Login();
	 }

	public void actionPerformed(ActionEvent e) 
	{
	 String clicked = e.getActionCommand();
	 
	 if(clicked.equals("Ok"))
	   {
		this.dispose();
		new Activity().setVisible(true);
	   }
	}
}