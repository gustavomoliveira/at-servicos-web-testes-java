package org.example.controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import org.example.model.Mensalista;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

public class MensalistaController {

    public static Map<String, Mensalista> mensalistas = new HashMap<>();
    private static int ultimaMatricula = 0;

    public static void configuracao(Javalin app) {
        app.get("/mensalista", MensalistaController::pegarTodosMensalistas);
        app.get("/mensalista/{matricula}", MensalistaController::pegarMensalistaPorMatricula);
        app.post("/mensalista", MensalistaController::inserirMensalista);
        app.get("/hello", MensalistaController::pegarResposta);
        app.get("/status", MensalistaController::pegarStatus);
        app.post("/echo", MensalistaController::inserirMensagem);
        app.get("/saudacao/{nome}", MensalistaController::pegarSaudacao);
    }

    public static void pegarTodosMensalistas(Context context) {
        context.status(200);
        context.json(mensalistas);
    }

    public static void pegarMensalistaPorMatricula(Context context) {
        String matricula = context.pathParam("matricula");
        Mensalista mensalista = mensalistas.get(matricula);

        if (mensalista != null) {
            context.status(200);
            context.json(mensalista);
        } else {
            context.status(404);
            context.result("Mensalista não encontrado");
        }
    }

    public static void inserirMensalista(Context context) {
        Mensalista mensalista = context.bodyAsClass(Mensalista.class);

        if (mensalista.getNome() == null || mensalista.getNome().trim().isEmpty()) {
            context.status(400);
            context.result("O nome do mensalista é obrigatório.");
            return;
        }

        criarMensalista(mensalista);
        context.status(201);
        context.json(mensalista);
    }

    private static void criarMensalista(Mensalista mensalista) {
        int novaMatricula = ultimaMatricula + 1;
        mensalista.setMatricula(novaMatricula);
        ultimaMatricula = novaMatricula;
        mensalistas.put(Integer.toString(novaMatricula), mensalista);
    }

    public static void pegarResposta(Context context) {
        context.status(200);
        context.result("Hello, Javalin!");
    }

    public static void pegarStatus(Context context) {
        Instant horaAgora = Instant.now().truncatedTo(ChronoUnit.SECONDS);

        context.status(200);
        context.json(Map.of("status", "ok", "hora atual", horaAgora.toString()));
    }

    public static void inserirMensagem(Context context) {
        Map mensagem = context.bodyAsClass(Map.class);

        context.status(201);
        context.json(mensagem);
    }

    public static void pegarSaudacao(Context context) {
        String nome = context.pathParam("nome");
        String valorMensagem = String.format("Olá, %s!", nome);

        context.status(200);
        context.json(Map.of("mensagem", valorMensagem));
    }
}
