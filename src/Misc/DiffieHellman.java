package Misc;

/*import java.math.BigInteger;
import java.util.Scanner;

public class DiffieHellman {

final static BigInteger one = new BigInteger("1");

public static void main(String args[]) {

    Scanner stdin = new Scanner(System.in);
    BigInteger p;

    // Get a start spot to pick a prime from the user.
    System.out.println("Enter the approximate value of p you want.");
    String ans = stdin.next();
    p = getNextPrime(ans);
    System.out.println("Your prime is "+p+".");
    int x=Integer.parseInt(p+"");
    // Get the base for exponentiation from the user.
    System.out.println("Now, enter a number in between 2 and "+(x-1)+".");
    BigInteger g = new BigInteger(stdin.next());

    // Get A's secret number.
    System.out.println("Person A: enter your secret number now.");
    BigInteger a = new BigInteger(stdin.next().getBytes());

    // Make A's calculation.
    BigInteger resulta = g.modPow(a,p);

    System.out.println("Person A sends to person B "+resulta+".");

    // Get B's secret number.
    System.out.println("Person B: enter your secret number now.");
    BigInteger b = new BigInteger(stdin.next());

    // Make B's calculation.
    BigInteger resultb = g.modPow(b,p);

    System.out.println("Person B sends to person A "+resultb+".");

    BigInteger KeyACalculates = resultb.modPow(a,p);
    BigInteger KeyBCalculates = resulta.modPow(b,p);

    // Print out the Key A calculates.
    System.out.println("A takes "+resultb+" raises it to the power "+a+" mod "+p);
    System.out.println("The Key A calculates is "+KeyACalculates+".");

    // Print out the Key B calculates.
    System.out.println("B takes "+resulta+" raises it to the power "+b+" mod "+p);
    System.out.println("The Key B calculates is "+KeyBCalculates+".");
}

public static BigInteger getNextPrime(String ans) {

    BigInteger test = new BigInteger(ans);
    while (!test.isProbablePrime(99))
        test = test.add(one);
    return test;        
}

}/*

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;

import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.DHPublicKeySpec;

public class DiffieHellman {
  public final static int pValue = 47;

  public final static int gValue = 71;

  public final static int XaValue = 9;

  public final static int XbValue = 14;

  public static void main(String[] args) throws Exception {
    BigInteger p = new BigInteger(Integer.toString(pValue));
    BigInteger g = new BigInteger(Integer.toString(gValue));
    BigInteger Xa = new BigInteger(Integer.toString(XaValue));
    BigInteger Xb = new BigInteger(Integer.toString(XbValue));

    createKey();

    int bitLength = 512; // 512 bits
    SecureRandom rnd = new SecureRandom();
    p = BigInteger.probablePrime(bitLength, rnd);
    g = BigInteger.probablePrime(bitLength, rnd);
    
    createSpecificKey(p, g);
  }

  public static void createKey() throws Exception {
    KeyPairGenerator kpg = KeyPairGenerator.getInstance("DiffieHellman");

    kpg.initialize(512);
    KeyPair kp = kpg.generateKeyPair();
    KeyFactory kfactory = KeyFactory.getInstance("DiffieHellman");

    DHPublicKeySpec kspec = (DHPublicKeySpec) kfactory.getKeySpec(kp.getPublic(),
        DHPublicKeySpec.class);
  }

  public static void createSpecificKey(BigInteger p, BigInteger g) throws Exception {
    KeyPairGenerator kpg = KeyPairGenerator.getInstance("DiffieHellman");

    DHParameterSpec param = new DHParameterSpec(p, g);
    kpg.initialize(param);
    KeyPair kp = kpg.generateKeyPair();

    KeyFactory kfactory = KeyFactory.getInstance("DiffieHellman");

    DHPublicKeySpec kspec = (DHPublicKeySpec) kfactory.getKeySpec(kp.getPublic(),
        DHPublicKeySpec.class);
  }
}*/
import java.util.*;
import java.math.BigInteger;

public class DiffieHellman {
	
	final static BigInteger one = new BigInteger("1");
	
	public static void main(String args[]) {
		
		Scanner stdin = new Scanner(System.in);
		BigInteger p;
		
		// Get a start spot to pick a prime from the user.
		System.out.println("Enter the approximate value of p you want.");
		String ans = stdin.next();
		p = getNextPrime(ans);
		System.out.println("Your prime is "+p+".");
		
		// Get the base for exponentiation from the user.
		System.out.println("Now, enter a number in between 2 and p-1.");
		BigInteger g = new BigInteger(stdin.next());
		
		// Get A's secret number.
		System.out.println("Person A: enter your secret number now.");
		BigInteger a = new BigInteger(stdin.next());
		
		// Make A's calculation.
		BigInteger resulta = g.modPow(a,p);
		
		// This is the value that will get sent from A to B.
		// This value does NOT compromise the value of a easily.
		System.out.println("Person A sends to person B "+resulta+".");
		
		// Get B's secret number.
		System.out.println("Person B: enter your secret number now.");
		BigInteger b = new BigInteger(stdin.next());
		
		// Make B's calculation.
		BigInteger resultb = g.modPow(b,p);
		
		// This is the value that will get sent from B to A.
		// This value does NOT compromise the value of b easily.
		System.out.println("Person B sends to person A "+resultb+".");
		
		// Once A and B receive their values, they make their new calculations.
		// This involved getting their new numbers and raising them to the 
		// same power as before, their secret number.
		BigInteger KeyACalculates = resultb.modPow(a,p);
		BigInteger KeyBCalculates = resulta.modPow(b,p);
		
		// Print out the Key A calculates.
		System.out.println("A takes "+resultb+" raises it to the power "+a+" mod "+p);
		System.out.println("The Key A calculates is "+KeyACalculates+".");
		
		// Print out the Key B calculates.
		System.out.println("B takes "+resulta+" raises it to the power "+b+" mod "+p);
		System.out.println("The Key B calculates is "+KeyBCalculates+".");
		
	}
	
	public static BigInteger getNextPrime(String ans) 
	{	
		BigInteger test = new BigInteger(ans);
		while (!test.isProbablePrime(99))
			test = test.add(one);
		return test;		
	}
}