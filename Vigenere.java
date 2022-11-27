import java.util.Arrays;
import java.util.Scanner;

public class Vigenere {
    static Scanner read = new Scanner(System.in);
    public static void main(String[] args) {
        System.out.println("Шифр Виженера");
        System.out.println("Введите слово: ");
        char[] word = read.nextLine().toCharArray();
        toCipherVigenere(word);
        fromCipherVigenere(word);

    }
    static public void toCipherVigenere(char[] $word) {
        System.out.println("Введите ключ: ");
        char[] key = read.nextLine().toCharArray();
        for (int i = 0; i < $word.length; i++) $word[i] += (int) key[i%(key.length)]-1040;
        System.out.println("Криптограмма: " + Arrays.toString($word));
    }

    static public void fromCipherVigenere(char[] $word) {
        System.out.println("Введите ключ: ");
        char[] key = read.nextLine().toCharArray();
        for (int i = 0; i < $word.length; i++) $word[i] -= (int) key[i%(key.length)]-1040;
        System.out.println("Расшифрованное слово: " + Arrays.toString($word));
    }
}
