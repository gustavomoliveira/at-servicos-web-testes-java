package org.example.view;

import org.example.util.Util;
import org.example.client.ApiClient;

public class MenuCLI {
    private ApiClient apiClient;

    public MenuCLI() {
        this.apiClient = new ApiClient();
    }

    public void exibirMenu() {
        while (true) {
            System.out.println("==== SISTEMA DE MENSALISTAS ====");
            System.out.println("[1] - Criar Mensalista");
            System.out.println("[2] - Buscar Todos Mensalistas");
            System.out.println("[3] - Buscar Mensalista por Matrícula");
            System.out.println("[4] - Mostrar Status e Hora");
            System.out.println("[5] - Sair");

            int opcao = Util.capturarOpcaoMenuPrincipalComValidacao();

            switch (opcao) {

                case 1:
                    apiClient.inserirMensalista();
                    break;
                case 2:
                    apiClient.pegarTodosMensalistas();
                    break;
                case 3:
                    apiClient.pegarMensalistaPorMatricula();
                    break;
                case 4:
                    apiClient.pegarStatusEHora();
                    break;
                case 5:
                    System.out.println("Obrigado por usar o sistema!");
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }
}
