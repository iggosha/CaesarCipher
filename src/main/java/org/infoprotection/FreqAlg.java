package org.infoprotection;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

public class FreqAlg {


    public static void main(String[] args) {

        Map<Character, Double> frequencyMap;
        Map<Character, Double> theoreticalFrequencyMap = getNativeFrequencyMap();

        String fileContent = readFileContent("1.txt");
        System.out.println("Изначальный текст: " + fileContent);

        frequencyMap = getFrequencyMap(fileContent);

        StringBuilder fileLetters = getFileLetters(fileContent);
        System.out.println("Буквы начального текста: " + fileLetters);

        StringBuilder printableFrequencies = getPrintableFrequencies(fileLetters, frequencyMap);
        String printableFrequenciesStr = printableFrequencies.toString().replaceAll(",", ".");
        System.out.println("Частоты букв начального текста: " + printableFrequenciesStr);

        List<Double> orderedPrintableFrequencies = new ArrayList<>();
        for (String frequencyStr : printableFrequenciesStr.split(" ")) {
            orderedPrintableFrequencies.add(Double.parseDouble(frequencyStr));
        }
        System.out.println("Предполагаемый текст при замене частот на буквы: ");
        for (Double frequency : orderedPrintableFrequencies) {
            Character closestChar =  'щ';
            for (Character currentCharacter : theoreticalFrequencyMap.keySet()) {
                if (theoreticalFrequencyMap.get(closestChar) != null && Math.abs(frequency - theoreticalFrequencyMap.get(currentCharacter)) < Math.abs(frequency - theoreticalFrequencyMap.get(closestChar))) {
                    closestChar = currentCharacter;
                }
            }
            System.out.print(closestChar);
        }

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

    private static Map<Character, Double> getNativeFrequencyMap() {
        String input = "о/0,09;в/0,038;з/0,016;ж/0,007;е,ё/0,072;л/0,035;ы/0,016;ш/0,006;а/0,062;к/0,028;б/0,014;" +
                "ю/0,006;и/0,062;м/0,026;ь,ъ/0,014;ц/0,004;н/0,053;д/0,025;г/0,013;щ/0,003;т/0,053;п/0,023;ч/0,012" +
                ";э/0,003;с/0,045;у/0,021;й/0,01;ф/0,002;р/0,04;я/0,018;х/0,009";
        input = input.replaceAll(",", ".");
        Map<Character, Double> map = new HashMap<>();

        String[] kvPairs = input.split(";");
        for (String pair : kvPairs) {
            String[] keyValue = pair.split("/");
            Character character = keyValue[0].charAt(0);
            Double frequency = Double.parseDouble(keyValue[1]);
            map.put(character, frequency);
        }
        return map;
    }

    private static String readFileContent(String fileName) {
        String fileContent;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(fileName)));) {
            fileContent = bufferedReader.readLine();
            fileContent = fileContent.toLowerCase();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fileContent;
    }

    private static StringBuilder getFileLetters(String fileContent) {
        StringBuilder fileLetters = new StringBuilder();
        for (int i = 0; i < fileContent.length(); i++) {
            Character currentChar = fileContent.charAt(i);
            if (isRusLetter(currentChar)) {
                fileLetters.append(currentChar);
            }
        }
        return fileLetters;
    }

    private static StringBuilder getPrintableFrequencies(StringBuilder fileLetters, Map<Character, Double> frequencyMap) {
        DecimalFormat format = new DecimalFormat("0.000");
        StringBuilder printableFrequencies = new StringBuilder();
        for (int i = 0; i < fileLetters.length(); i++) {
            Character currentChar = fileLetters.charAt(i);
            if (!isRusLetter(currentChar)) {
                continue;
            }
            printableFrequencies.append(format.format(frequencyMap.get(currentChar)))
                    .append(" ");
        }
        return printableFrequencies;
    }


    private static boolean isRusLetter(Character character) {
        return character.toString().matches("[а-яА-ЯёЁ]+");
    }
}
