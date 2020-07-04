/*
Computer Security
by Talha Mangarah - tmang002 - 33551591
-Internet was used for part of work
*/

package me.talhamangarah.cipher;

import java.math.BigInteger;
import java.security.SecureRandom;

public class RSA{
    //initialise variables
    protected BigInteger privateKey;
    protected BigInteger publicKey;
    protected BigInteger modulus;

    private final static BigInteger one      = new BigInteger("1");
    private final static SecureRandom random = new SecureRandom();

    public RSA(int numForRandPrimes){
        //Get random primes and calculate tuotient
        BigInteger p = BigInteger.probablePrime(numForRandPrimes, random);
        BigInteger q = BigInteger.probablePrime(numForRandPrimes, random);
        BigInteger r = (p.subtract(one)).multiply(q.subtract(one));

        modulus = p.multiply(q);

        //pubKey for random generation
        publicKey = BigInteger.probablePrime(numForRandPrimes / 2, random);
        while (r.gcd(publicKey).compareTo(BigInteger.ONE) > 0 && publicKey.compareTo(r) < 0)
        {
            publicKey = publicKey.add(BigInteger.ONE);
        }

        //pubKey for preset key of 65537
        //publicKey  = new BigInteger("65537");     // 2^16 + 1
        //while(!publicKey.gcd(r).equals(one)){
        //     publicKey = BigInteger.probablePrime(16, random);
        //}

        privateKey = publicKey.modInverse(r);
    }

    public String toString() {
        String s = "";
        s += "public  = " + publicKey  + "\n";
        s += "private = " + privateKey + "\n";
        s += "modulus = " + modulus;
        return s;
    }

    //encrypt: c = m^e mod n
    BigInteger encrypt(BigInteger message) {
        return message.modPow(publicKey, modulus);
    }

    //decrypt: m = c^d mod n
    BigInteger decrypt(BigInteger encrypted) {
        return encrypted.modPow(privateKey, modulus);
    }

    //Switch between string and bigInt
    BigInteger plainToCipher(String plainText) {
        byte[] bytes = plainText.getBytes();
        return new BigInteger(bytes);
    }

    //Switch from BigInt to byte array
    byte[] cipherToPlain(BigInteger cipherText) {
        return cipherText.toByteArray();
    }

}
