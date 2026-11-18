package utils;

import java.util.Scanner;

public class Utils {

    // Um único Scanner para todo o programa
    private static final Scanner scanner = new Scanner(System.in);

    /** Lê uma linha de texto. */
    public static String lerTexto(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine().trim();
    }

    /** Lê um número inteiro. */
    public static int lerInteiro(String mensagem) {
        System.out.print(mensagem);
        int valor = scanner.nextInt();
        scanner.nextLine(); // consome o Enter que o nextInt() deixa para trás
        return valor;
    }

    /** Lê um número decimal (no formato brasileiro, com vírgula: 10,50). */
    public static double lerDouble(String mensagem) {
        System.out.print(mensagem);
        double valor = scanner.nextDouble();
        scanner.nextLine(); // consome o Enter que o nextDouble() deixa para trás
        return valor;
    }
}
