***Only use for learning, do not use for actual security***

RSA implementation using Java's BigInteger Class

This is a command line program to generate Keys, encrypt and decrypt. 

There are three available options when running the program.  
-g numBits = print random modulus n and exponents e, d of bit length numBits  
-e keyfile = encrypts standard input stream  
-d keyfile = decrypts standard input stream  
  
The keyfile is a text file that must contain the modulus and encryption exponent or decryption exponent.  
You can generate these values by running the command java RSA -g 1024  
  
To encrypt a file run the command java RSA -e keyfile.txt < fileToBeEncrypted.txt  
To decrypt the file run the command java RSA -d keyfile.txt < fileToBeDecrypted.txt   
