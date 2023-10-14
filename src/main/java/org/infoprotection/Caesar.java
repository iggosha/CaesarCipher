package org.infoprotection;

import java.io.*;
import java.util.Scanner;

public class Caesar {

    static Scanner read = new Scanner(System.in);

    private static final int ALPHABET_SIZE = 26;
    private static int MULTIPLIER = 5;
    private static int SHIFT = 7;


    public static void main(String[] args) {
        Caesar caesarCipher = new Caesar();
        caesarCipher.chooseMode();
    }

    private void chooseMode() {
        BufferedReader bufferedReader;
        BufferedWriter bufferedWriter;

        System.out.println("""
                Введите номер требуемой функции программы:
                1. Зашифровать с помощью афинного шифра Цезаря
                2. Расшифровать с помошью афинного шифра Цезаря
                0. Заврешить работу
                """);
        switch (read.nextInt()) {
            case 1 -> {
                bufferedReader = initBufferedReader();
                bufferedWriter = initBufferedWriter();
                encryptAffineCaesar(bufferedReader, bufferedWriter);
                System.out.println("Зашифровано");
            }
            case 2 -> {
                bufferedReader = initBufferedReader();
                bufferedWriter = initBufferedWriter();
                decryptAffineCaesar(bufferedReader, bufferedWriter);
                System.out.println("Расшифровано");
            }
            default -> {
                return;
            }
        }

        closeBufferedReader(bufferedReader);
        closeBufferedWriter(bufferedWriter);
        chooseMode();
    }

    private BufferedReader initBufferedReader() {
        System.out.println("Введите имя входного файла: ");
        read.skip("((?<!\\R)\\s)*");
        String fileNameRead = read.nextLine();
        try {
            return new BufferedReader(new InputStreamReader((new FileInputStream(new File(fileNameRead)))));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void closeBufferedReader(BufferedReader bufferedReader) {
        try {
            bufferedReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private BufferedWriter initBufferedWriter() {
        System.out.println("Введите имя выходного файла : ");
        String fileNameWrite = read.nextLine();
        try {
            return new BufferedWriter(new OutputStreamWriter((new FileOutputStream(new File(fileNameWrite)))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void closeBufferedWriter(BufferedWriter bufferedWriter) {
        try {
            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void encryptAffineCaesar(BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        System.out.println("Введите в двух следующих строках a и b для E(x) = (a*x + b) mod m");
        MULTIPLIER = read.nextInt();
        SHIFT = read.nextInt();
        try {
            char[] word = bufferedReader.readLine().toCharArray();
            for (Character ch : word) {
                if (!Character.isLetter(ch)) {
                    bufferedWriter.write(ch);
                    continue;
                }
//                ch = Character.toUpperCase(ch);
                int charValue = ch - 'a';
                int encryptedValue = (MULTIPLIER * charValue + SHIFT) % ALPHABET_SIZE;
                bufferedWriter.write((char) ('a' + encryptedValue));
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    private void decryptAffineCaesar(BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        System.out.println("Введите в двух следующих строках a и b для E(x) = (a*x + b) mod m");
        MULTIPLIER = read.nextInt();
        SHIFT = read.nextInt();
        try {
            char[] word = bufferedReader.readLine().toCharArray();
            for (Character ch : word) {
                if (!Character.isLetter(ch)) {
                    bufferedWriter.write(ch);
                    continue;
                }
//                ch = Character.toUpperCase(ch); + ch - 'A';
                int charValue = ch - 'a';
                int modInverse = findModularInverse(MULTIPLIER, ALPHABET_SIZE);
                int decryptedValue = modInverse * (charValue - SHIFT + ALPHABET_SIZE) % ALPHABET_SIZE;
                bufferedWriter.write((char) ('a' + decryptedValue));
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    private static int findModularInverse(int a, int m) {
        for (int x = 1; x < m; x++) {
            if (((a % m) * (x % m)) % m == 1) {
                return x;
            }
        }
        throw new ArithmeticException();
    }
}