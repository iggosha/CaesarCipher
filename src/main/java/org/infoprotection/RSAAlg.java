package org.infoprotection;

import java.math.BigInteger;
import java.util.Random;

public class RSAAlg {
    private BigInteger p;
    private BigInteger q;
    private BigInteger phi;
    private BigInteger n;
    private BigInteger e;
    private BigInteger d;

    public RSAAlg(int numBits) {
        // Генерация двух случайных простых чисел p и q
        Random rand = new Random();
        p = BigInteger.probablePrime(numBits, rand);
        q = BigInteger.probablePrime(numBits, rand);

        phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        n = p.multiply(q);
        e = BigInteger.valueOf(65537); // Выбрано e = 65537, так как оно является большим простым числом

        // Вычисление закрытого ключа d
        d = e.modInverse(phi);
    }

    public BigInteger encrypt(String message) {
        BigInteger m = new BigInteger(message.getBytes());
        return m.modPow(e, n); // Зашифровка сообщения с помощью открытого ключа
    }

    public String decrypt(BigInteger encryptedMessage) {
        BigInteger decryptedMessage = encryptedMessage.modPow(d, n); // Дешифровка зашифрованного сообщения с помощью закрытого ключа
        return new String(decryptedMessage.toByteArray());
    }
}