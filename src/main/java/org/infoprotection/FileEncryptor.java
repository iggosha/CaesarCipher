package org.infoprotection;

import java.io.*;
import java.math.BigInteger;
import java.util.Scanner;

public class FileEncryptor {

    static Scanner read = new Scanner(System.in);
    static RSAAlg rsaAlg = new RSAAlg(1024);
    static BigInteger encryptedMessageDigit;

    public static void main(String[] args) {
        FileEncryptor fileEncryptor = new FileEncryptor();
        fileEncryptor.chooseMode();
    }

    private void chooseMode() {
        System.out.println("""
                Введите номер требуемой функции программы:
                1. Зашифровать с помощью шифра RSA
                2. Расшифровать с помощью шифра RSA
                0. Завершить работу
                """);
        switch (Integer.parseInt(read.nextLine())) {
            case 1 -> {
                encrypt();
                System.out.println("Зашифровано");
            }
            case 2 -> {
                decrypt();
                System.out.println("Расшифровано");
            }
            default -> {
                return;
            }
        }
        chooseMode();
    }

    private void encrypt() {

        System.out.println("Введите имя файла для чтения исходного сообщения и имя файла для записи зашифрованного");
        String fileName1 = read.nextLine();
        String fileName2 = read.nextLine();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader((new FileInputStream(new File(fileName1 + ".txt")))));
             BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter((new FileOutputStream(new File(fileName2 + ".txt")))));) {
            encryptedMessageDigit = rsaAlg.encrypt(bufferedReader.readLine());
            String encryptedMessageStr = String.valueOf(encryptedMessageDigit);
            bufferedWriter.write(encryptedMessageStr);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void decrypt() {
        System.out.println("Введите имя файла для записи расшифрованного");
        String fileName2 = read.nextLine();
        try (BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter((new FileOutputStream(fileName2 + ".txt"))));) {
            bufferedWriter.write(rsaAlg.decrypt(encryptedMessageDigit));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
