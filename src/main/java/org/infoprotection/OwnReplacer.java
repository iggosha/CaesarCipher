package org.infoprotection;

import java.io.*;
import java.util.Scanner;

public class OwnReplacer {

    static Scanner read = new Scanner(System.in);

    public static void main(String[] args) {
        OwnReplacer ownReplacer = new OwnReplacer();
        ownReplacer.chooseMode();
    }

    private void chooseMode() {
        BufferedReader bufferedReader;
        BufferedWriter bufferedWriter;
        System.out.println("""
                Введите номер требуемой функции программы:
                1. Зашифровать текст
                2. Расшифровать текст
                0. Завершить работу
                """);
        switch (read.nextInt()) {
            case 1 -> {
                bufferedReader = initBufferedReader();
                bufferedWriter = initBufferedWriter();
                encryptCipher(bufferedReader, bufferedWriter, true);
                System.out.println("Зашифровано");
            }
            case 2 -> {
                bufferedReader = initBufferedReader();
                bufferedWriter = initBufferedWriter();
                encryptCipher(bufferedReader, bufferedWriter, false);
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

    private void encryptCipher(BufferedReader bufferedReader, BufferedWriter bufferedWriter, boolean isForEncrypt) {
        char[] ABC = {
                'А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ё', 'Ж', 'З', 'И', 'Й', 'К', 'Л', 'М', 'Н', 'О', 'П', 'Р', 'С', 'Т', 'У',
                'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ', 'Ъ', 'Ы', 'Ь', 'Э', 'Ю', 'Я', 'а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з',
                'и', 'й', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь',
                'э', 'ю', 'я', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0'
        };
        char[] cryptABC = {
                'н', 'С', 'з', '6', '4', '9', 'Я', 'л', 'й', 'м', 'р', 'Ы', 'т', 'в', '8', 'У', 'с', 'Ф', 'А', 'и', 'З',
                'х', 'ю', 'Н', 'д', 'о', '7', 'Ч', 'е', 'Т', '3', 'Ц', 'Е', 'Ю', 'Щ', 'у', 'к', 'ц', 'г', '5', 'Ш', 'Ж',
                'ъ', 'ё', 'я', 'Й', 'ф', 'щ', 'В', 'П', 'п', 'Д', 'Ё', 'К', 'И', 'б', 'Ь', 'Б', 'ч', 'О', 'ж', 'а', '1',
                'Ъ', 'э', 'Х', '2', 'Г', 'Э', 'ш', '0', 'ы', 'Р', 'М', 'Л', 'ь'
        };
        String text;
        StringBuilder encryptedText = new StringBuilder();
        try {
            text = bufferedReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < text.length(); i++) {
            String character = String.valueOf(text.charAt(i));
            if (Character.isDigit(character.charAt(0)) || Character.isLetter(character.charAt(0))) {
                for (int j = 0; j < ABC.length; j++) {
                    if (isForEncrypt) {
                        if (character.equals(String.valueOf(ABC[j]))) {
                            character = character.replace(ABC[j], cryptABC[j]);
                            encryptedText.append(character);
                            break;
                        }
                    } else {
                        if (character.equals(String.valueOf(cryptABC[j]))) {
                            character = character.replace(cryptABC[j], ABC[j]);
                            encryptedText.append(character);
                            break;
                        }
                    }
                }
            } else {
                encryptedText.append(character);
            }
        }
        try {
            bufferedWriter.write(encryptedText.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
