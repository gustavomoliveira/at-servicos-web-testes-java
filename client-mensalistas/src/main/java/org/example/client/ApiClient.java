package org.example.client;

import org.example.util.Util;

import java.io.*;
import java.net.*;

public class ApiClient {

    private final String url = "http://localhost:8080";

    private HttpURLConnection gerarConexao(String recurso, String metodo) {
        HttpURLConnection conn;

        try {
            URI uri = new URI(url + recurso);
            URL url = uri.toURL();
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(metodo);

            if ("POST".equals(metodo)) {
                conn.setRequestProperty("Content-Type", "application/json");
            }
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }

        return conn;
    }

    private void configurarbody(HttpURLConnection conn, String body) {
        try {
            conn.setDoOutput(true);
            OutputStream os = conn.getOutputStream();
            byte[] bBody = body.getBytes();
            os.write(bBody);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String tratarResposta(HttpURLConnection conn) {
        StringBuilder sbResponse;

        try {
            sbResponse = new StringBuilder();
            InputStream is = conn.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String responseLine;

            while ((responseLine = br.readLine()) != null) {
                sbResponse.append(responseLine);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        conn.disconnect();
        return sbResponse.toString();
    }

    public void inserirMensalista() {
        String jsonMensalista = Util.criarJsonMensalista();

        try {
            HttpURLConnection conn = gerarConexao("/mensalista", "POST");
            configurarbody(conn, jsonMensalista);
            int responseCode = conn.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_BAD_REQUEST) {
                System.out.println("Dados inválidos fornecidos para criar o mensalista.");
            } else if (responseCode == HttpURLConnection.HTTP_INTERNAL_ERROR) {
                System.out.println("Erro na API. Status: " + responseCode);
            } else {
                System.out.println(tratarResposta(conn));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void pegarTodosMensalistas() {
        try {
            HttpURLConnection conn = gerarConexao("/mensalista", "GET");
            int responseCode = conn.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_INTERNAL_ERROR) {
                System.out.println("Erro na API. Status: " + responseCode);
            } else {
                System.out.println(tratarResposta(conn));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void pegarMensalistaPorMatricula() {
        int matricula = Util.capturarMatriculaComValidacao();

        try {
            HttpURLConnection conn = gerarConexao("/mensalista/" + matricula, "GET");
            int responseCode = conn.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_NOT_FOUND) {
                System.out.println("Não existe um mensalista cadastrado com esse número de matrícula.");
            } else if (responseCode == HttpURLConnection.HTTP_BAD_REQUEST) {
                System.out.println("Número de matrícula inválido.");
            } else if (responseCode == HttpURLConnection.HTTP_INTERNAL_ERROR) {
                System.out.println("Erro na API. Status: " + responseCode);
            } else {
                System.out.println(tratarResposta(conn));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void pegarStatusEHora() {
        try {
            HttpURLConnection conn = gerarConexao("/status", "GET");
            int responseCode = conn.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_INTERNAL_ERROR) {
                System.out.println("Erro na API. Status: " + responseCode);
            } else {
                System.out.println(tratarResposta(conn));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
