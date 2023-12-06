package org.infoprotection;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        while (true) {
            System.out.println("""
                    Меню:
                    1. Зашифровать текст
                    2. Расшифровать текст
                    3. Создать ключ
                    0. Выход
                    Выберите опцию:""");
            switch (Integer.parseInt(scanner.nextLine())) {
                case 1 -> encryptText();
                case 2 -> decryptText();
                case 3 -> createKey();
                case 4 -> {
                    return;
                }
                default -> System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }

    private static Key loadKeyFromFile(String keyFileName) throws Exception {
        byte[] keyBytes;
        try (FileInputStream keyFile = new FileInputStream(keyFileName);) {
            keyBytes = new byte[16];
            keyFile.read(keyBytes);
        }
        return new SecretKeySpec(keyBytes, "AES");
    }

    private static void saveKeyToFile(Key key, String keyFileName) throws Exception {
        // Сохранение секретного ключа в файл
        try (FileOutputStream keyFile = new FileOutputStream(keyFileName);) {
            keyFile.write(key.getEncoded());
        }
    }

    private static void createKey() throws Exception {
        // Создание секретного ключа и сохранение его в файл
        System.out.print("Введите имя файла для сохранения ключа: ");
        String keyFileName = scanner.nextLine();
        System.out.print("Введите ключ для шифрования (16 символов): ");
        String keyString = scanner.nextLine();
        if (keyString.length() != 16) {
            System.out.println("Ключ должен содержать ровно 16 символов.");
            return;
        }
        byte[] keyBytes = keyString.getBytes(StandardCharsets.UTF_8);
        Key key = new SecretKeySpec(keyBytes, "AES");
        saveKeyToFile(key, keyFileName);
        System.out.println("Созданный ключ сохранен в файл " + keyFileName);
    }


    private static void encryptText() throws Exception {
        // Зашифрование текста
        System.out.print("Введите имя файла с ключом: ");
        Key key = loadKeyFromFile(scanner.nextLine());
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        System.out.print("Введите текст для шифрования: ");
        byte[] plainTextBytes = scanner.nextLine().getBytes(StandardCharsets.UTF_8);
        cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(new byte[16]));
        byte[] encryptedBytes = cipher.doFinal(plainTextBytes);
        System.out.print("Введите имя файла для сохранения зашифрованного текста:");
        String outputFileName = scanner.nextLine();
        try (FileOutputStream encryptedFile = new FileOutputStream(outputFileName)) {
            encryptedFile.write(encryptedBytes);
        }
        System.out.println("Зашифрованный текст сохранен в файл " + outputFileName);
    }

    private static void decryptText() throws Exception {
        System.out.print("Введите имя файла с ключом: ");
        Key key = loadKeyFromFile(scanner.nextLine());
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        System.out.print("Введите имя файла для расшифровки: ");
        byte[] inputBytes;
        try (FileInputStream inputFile = new FileInputStream(scanner.nextLine())) {
            inputBytes = new byte[inputFile.available()];
            inputFile.read(inputBytes);
        }
        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(new byte[16]));
        byte[] decryptedBytes = cipher.doFinal(inputBytes);
        System.out.println("Дешифрованный текст: " + new String(decryptedBytes, StandardCharsets.UTF_8));
    }
}