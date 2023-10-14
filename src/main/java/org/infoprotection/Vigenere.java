package org.infoprotection;

import java.io.*;
import java.util.Scanner;

public class Vigenere {

    static Scanner read = new Scanner(System.in);

    public static void main(String[] args) {
        Vigenere vigenere = new Vigenere();
        vigenere.chooseMode();
    }

    private void chooseMode() {
        BufferedReader bufferedReader;
        BufferedWriter bufferedWriter;
        System.out.println("""
                Введите номер требуемой функции программы:
                1. Зашифровать с помощью шифра Виженера
                2. Расшифровать с помощью шифра Виженера
                0. Завершить работу
                """);
        switch (Integer.parseInt(read.nextLine())) {
            case 1 -> {
                bufferedReader = initBufferedReader();
                bufferedWriter = initBufferedWriter();
                encryptVigenere(bufferedReader, bufferedWriter);
                System.out.println("Зашифровано");
            }
            case 2 -> {
                bufferedReader = initBufferedReader();
                bufferedWriter = initBufferedWriter();
                decryptVigenere(bufferedReader, bufferedWriter);
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

    private void encryptVigenere(BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        System.out.println("Введите ключ: ");
        char[] key = read.nextLine().toCharArray();
        try {
            char[] word = bufferedReader.readLine().toCharArray();
            for (int i = 0; i < word.length; i++) {
                word[i] += (char) (key[i % (key.length)] - 'А');
                bufferedWriter.write((word[i]));
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    private void decryptVigenere(BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        System.out.println("Введите ключ");
        char[] key = read.nextLine().toCharArray();
        try {
            char[] word = bufferedReader.readLine().toCharArray();
            for (int i = 0; i < word.length; i++) {
                word[i] -= (char) (key[i % (key.length)] - 'А');
                bufferedWriter.write((word[i]));
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}