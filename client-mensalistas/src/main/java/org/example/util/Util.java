package org.example.util;

import java.util.Scanner;

public class Util {

    public static boolean validarOpcaoMenuPrincipal(int opcao) {
        if (opcao < 1 || opcao > 5) return false;

        return true;
    }

    public static int capturarOpcaoMenuPrincipalComValidacao() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Escolha uma opção:");
            String opcaoStr = scanner.nextLine().trim();

            try {
                int opcao = Integer.parseInt(opcaoStr);

                if (validarOpcaoMenuPrincipal(opcao)) {
                    return opcao;
                } else {
                    System.out.println("ERRO: Digite uma opção entre 1 e 5.");
                }
            } catch (NumberFormatException e) {
                System.out.println("ERRO: Digite apenas números para selecionar uma opção.");
            }
        }
    }

    public static boolean validarEntrada(String entrada) {
        if (entrada == null || entrada.trim().isEmpty()) return false;

        return true;
    }

    public static String capturarNomeComValidacao() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Digite o nome do novo mensalista:");
            String nome = scanner.nextLine().trim();

            if (validarEntrada(nome)) {
                return nome;
            } else {
                System.out.println("ERRO: Nome não pode estar vazio!");
            }
        }
    }

    public static int capturarMatriculaComValidacao() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Digite o número de matrícula do mensalista:");
            String matriculaStr = scanner.nextLine().trim();

            try {
                if (validarEntrada(matriculaStr)) {
                    return Integer.parseInt(matriculaStr);
                } else {
                    System.out.println("ERRO: Matrícula não pode estar vazia!");
                }
            } catch (NumberFormatException e) {
                System.out.println("ERRO: Digite apenas números para a matrícula.");
            }
        }
    }

    public static String criarJsonMensalista() {
        String nome = capturarNomeComValidacao();
        return String.format("{\"nome\":\"%s\"}", nome);
    }
}
