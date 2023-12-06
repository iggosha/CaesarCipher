package org.infoprotection;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class FreqAlg {

    public static void main(String[] args) {

        DecimalFormat format = new DecimalFormat("0.000");
        Map<Character, Double> frequencyMap;
        String fileContent;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("1.txt")));) {
            fileContent = bufferedReader.readLine();
            System.out.println("Изначальный текст: " + fileContent);
            fileContent = fileContent.toUpperCase();
            frequencyMap = getFrequencyMap(fileContent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        StringBuilder fileLetters = new StringBuilder();
        for (int i = 0; i < fileContent.length(); i++) {
            Character currentChar = fileContent.charAt(i);
            if (isRusLetter(currentChar)) {
                fileLetters.append(currentChar);
            }
        }
        System.out.println("Преобразованный текст: " + fileLetters);

        StringBuilder frequencyOfLetters = new StringBuilder();
        for (int i = 0; i < fileLetters.length(); i++) {
            Character currentChar = fileLetters.charAt(i);
            if (!isRusLetter(currentChar)) {
                continue;
            }
            frequencyOfLetters.append(format.format(frequencyMap.get(currentChar))).append(" ");
        }

        String result = frequencyOfLetters.toString();
        System.out.println("Частоты букв начального текста: " + result);
    }

    private static Map<Character, Double> getFrequencyMap(String str) {
        Map<Character, Integer> charCount = new HashMap<>();
        for (Character character : str.toCharArray()) {
            charCount.merge(character, 1, Integer::sum);
        }
        Map<Character, Double> charFreq = new TreeMap<>(Comparator.comparing(charCount::get));
        for (Character character : charCount.keySet())
            if (isRusLetter(character)) {
                charFreq.put(character, (double) (charCount.get(character)) / str.length());
            }
        return charFreq;
    }


    private static boolean isRusLetter(Character character) {
        return character.toString().matches("[а-яА-ЯёЁ]+");
    }
}
