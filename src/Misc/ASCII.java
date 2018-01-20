package Misc;

public class ASCII {

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub

		//String xyz=StringToASCII("plainText");
		//System.out.println("ASCII"+xyz);
		String back=ASCIIToString("655565");
		System.out.println("back"+back);
		System.out.println((char) Integer.parseInt("65"));
	}
	
	public static String ASCIIToString(String msg) 
	{
		String res="",  plain="";
		char ch;
		for(int i=0;i<msg.length();i+=2)
		{
			ch=msg.charAt(i);
			int x=ch+msg.charAt(i+1);
		//	char o = (char) Integer.parseInt(65);
		//	plain+=o;
		} 
		return msg;
	}
	
	public static String StringToASCII(String msg) 
	{
		String res="";
		for(int i=0;i<msg.length();i++)
		{
			char ch=msg.charAt(i);
			int ascii = ch; 

			if(ascii<=99)		   
				res+=("0"+ascii);
			else
				res+=(ascii);  
		} 
		return msg;
	}

}
