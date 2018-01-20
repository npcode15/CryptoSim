package Basic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import Algorithm.Symmetric.AES_D;
import Algorithm.Symmetric.DES_D;
import Algorithm.Symmetric.RC2_D;
import Algorithm.Symmetric.RC4_D;
import Algorithm.Symmetric.TEA_D;
import Algorithm.Symmetric.Twofish_D;
import Algorithm.Symmetric.BlowFish_D;
import Algorithm.Symmetric.TripleDES_D;

public class Symm_Algo_Dec extends JFrame implements ActionListener 
{
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	int scrW=screenSize.width, scrH=screenSize.height;

	private JButton[][] grid;

	String privatealgo[]={"AES (Advanced Encryption Standard)",  "DES (Data Encryption Standard)", "Triple-DES", "Blowfish", "Twofish", "TEA (Tiny Encryption Algorithm)", "RC2", "RC4"};

	private int i=1, start_x=0, start_y=scrH/2, grid_length=scrW/4, grid_breadth=75, btn_location=0;

	public Symm_Algo_Dec() 
	{
		setTitle("Activity Selection Window");
		setSize(scrW, scrH);
		setLayout(null);

		ButtonGrid(2, 4, privatealgo);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public void ButtonGrid(int row, int col, String[] list_type)
	{
		setLayout(new GridLayout(row, col)); 
		grid=new JButton[row][col];
		Border thickBorder = new LineBorder(Color.WHITE, 4);

		i=0;
		start_y=scrH/2;
		for(int x=0; x<row; x++)
		{ 
			start_x=0;
			start_y+=grid_breadth+5;
			for(int y=0; y<col; y++)
			{
				grid[x][y]=new JButton(list_type[i++]);
				grid[x][y].setBounds(start_x, start_y, grid_length, grid_breadth+5);
				grid[x][y].setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));
				add(grid[x][y]);
				grid[x][y].addActionListener(this);
				start_x=(start_x+grid_length);
			}
		}
		repaint();
	}

	public void actionPerformed(ActionEvent evt)
	{
		String clicked=evt.getActionCommand();

		if(clicked.equals(privatealgo[0]))
		{
			new AES_D().setVisible(true);
			this.dispose();
		}

		if(clicked.equals(privatealgo[1]))
		{
			new DES_D().setVisible(true);
			this.dispose();
		}

		if(clicked.equals(privatealgo[2]))
		{
			new TripleDES_D().setVisible(true);
			this.dispose();
		}

		if(clicked.equals(privatealgo[3]))
		{
			new BlowFish_D().setVisible(true);
			this.dispose();
		}

		if(clicked.equals(privatealgo[4]))
		{
			new Twofish_D().setVisible(true);
			this.dispose();
		}

		if(clicked.equals(privatealgo[5]))
		{
			new TEA_D().setVisible(true);
			this.dispose();
		}

		if(clicked.equals(privatealgo[6]))
		{
			new RC2_D().setVisible(true);
			this.dispose();
		}

		if(clicked.equals(privatealgo[7]))
		{
			new RC4_D().setVisible(true);
			this.dispose();
		}
	}
}