import java.math.BigInteger;
import java.util.Random;
import java.io.*;
import java.util.Scanner;


public class RSA 
{
    private BigInteger d; // exponent d used to decrypt
    private BigInteger e; // exponent e used to encrypt
    private BigInteger n; // modulus n used to encrypt or decrypt
    private static Random rnd = new Random(); // source of pseudo randomness used by all 

    // -g numBits = print random modulus n and exponents e, d of bit length numBits
    // -e keyfile = encrypts standard input stream
    // -d keyfile = decrypts standard input stream
    public static void main(String [] args) throws Exception
    {
        if(args[0].equals("-g"))
        {
            RSA rsa = new RSA(Integer.parseInt(args[1]));
            rsa.printKeys();
        }	
        else if(args[0].equals("-e"))
        {
            RSA rsa = new RSA(args[1]);
            rsa.encrypt();
        }
        else if(args[0].equals("-d"))
        {
            RSA rsa = new RSA(args[1]);
            rsa.decrypt();
        }
        else
            throw new Exception("invalid selection");
    }

    // Constructs an RSA cryptosystem with a random modulus n and exponents e,d of specified bit length
    public RSA(int numBits)
    {
        BigInteger p = BigInteger.probablePrime(numBits/2, rnd);
        BigInteger q = BigInteger.probablePrime(numBits/2, rnd);

        n = p.multiply(q);
        BigInteger m = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

        e = randomRelativePrime(m);
        d = e.modInverse(m);
    }

    // Constructs an RSA cryptosystem with a modulus n and exponent e=d read from a key file
    public RSA(String keyFile) throws FileNotFoundException, IOException
    {
        Scanner sc = new Scanner(new File(keyFile));
        String mod = sc.nextLine();
        String exp = sc.nextLine();

        n = new BigInteger(mod, 16);
        d = new BigInteger(exp, 16);
        e = new BigInteger(exp, 16);
    }

    // Encrypt the standard input stream using the modulus n and exponent e in this RSA cryptosystem	
    public void encrypt() throws IOException
    {
        byte[] block = new byte[n.bitLength()/8];
        int length;

        while((length = System.in.read(block)) != -1)
        {
            BigInteger x = new BigInteger(1, block);
            BigInteger y = x.modPow(e, n);
            System.out.println(y.toString(16));
        }
    }

    // Decrypt the standard input stream using the modulus n and exponent d in this RSA cryptosystem	
    public void decrypt() throws IOException
    {
        Scanner sc = new Scanner(System.in);
        while(sc.hasNextBigInteger(16))
        {
            BigInteger y = sc.nextBigInteger(16);
            BigInteger x = y.modPow(d, n);
            System.out.write(x.toByteArray());
        }
    }

    // Print the modulus n and exponents e,d in this RSA cryptosystem 
    public void printKeys()
    {
        System.out.println("\nn: " + n.toString(16));
        System.out.println("e: " + e.toString(16));
        System.out.println("d: " + d.toString(16) + "\n");
    }

    // Returns a random relative prime 
    private static BigInteger randomRelativePrime(BigInteger m)
    {
        BigInteger b;
        while(true)
        {
            b = new BigInteger(m.bitLength(), rnd);
            if(b.gcd(m).equals(BigInteger.ONE)) return b;
        }
    }
}
