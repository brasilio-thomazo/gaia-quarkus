package br.dev.optimus.gaia;

import java.util.Scanner;

public class Ninja {
    public static void main(String[] args) {
        var scanner = new Scanner(System.in); // instaciando o scanner
        System.out.print("Digite seu sobrenome: ");
        var sobrenome = scanner.nextLine();
        System.out.print("Digite o calçado que vc está usando: ");
        var calcado = scanner.nextLine();
        System.out.print("Digite seu peso: ");
        var peso = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("Seu sobrenome é: " + sobrenome);
        System.out.println("Seu calcado é: " + calcado);
        System.out.println("Seu peso é: " + peso);

        // em uma unica linha
        System.out.println("Seu sobrenome é: " + sobrenome + " seu calcado é: " + calcado + " e seu peso é: " + peso);

        scanner.close(); // fechando o scanner
    }
}
