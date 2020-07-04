/*
Computer Security
by Talha Mangarah - tmang002 - 33551591
-Internet was used for part of work
*/

package me.talhamangarah.cipher;

import java.math.BigInteger;
import java.security.SecureRandom;

public class Part2 extends RSA{
    private final static SecureRandom random = new SecureRandom();

    public Part2(int numForRandPrimes) {
        super(numForRandPrimes);
    }

    //used to generate a random nonce
    public String nonceGenerator(){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 15; i++) {
            stringBuilder.append(random.nextInt(10));
        }
        return stringBuilder.toString();
    }

    //generate and verifies a signature for a message/input
    BigInteger generateAndVerifySignature(BigInteger key, BigInteger mod, BigInteger keyInput){
        BigInteger message = BigInteger.valueOf(Math.abs(keyInput.hashCode()));
        return message.modPow(key, mod);
    }

}