package org.example;

import io.javalin.Javalin;
import org.example.controller.MensalistaController;

public class Main {
    public static void main(String[] args) {
        var app = Javalin.create();

        MensalistaController.configuracao(app);

        app.start();
    }
}