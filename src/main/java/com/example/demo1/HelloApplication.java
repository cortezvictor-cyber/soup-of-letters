package com.example.demo1;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) {
        try {
            SopaLetras sopa = new SopaLetras();

            // 1. Pedir el archivo al iniciar
            String archivo = JOptionPane.showInputDialog(null, "Nombre del archivo de texto (ej. sopa.txt):", "Cargar Sopa", JOptionPane.QUESTION_MESSAGE);
            if (archivo == null || archivo.trim().isEmpty()) {
                archivo = "sopa.txt";
            }

            // Cargar archivo
            sopa.cargarArchivo(archivo.trim());

            // 2. Configurar la Ventana Principal de JavaFX
            stage.setTitle("Buscador de Sopa de Letras");

            VBox root = new VBox(10);
            root.setPadding(new Insets(15));
            root.setStyle("-fx-background-color: #f0f4f8;"); // Color de fondo de la ventana

            // Título y Área de texto para mostrar la sopa alineada
            Label lblTitulo = new Label("SOUP OF LETTERS:");
            lblTitulo.setStyle("-fx-text-fill: #2c3e50; -fx-font-weight: bold;");

            // --- CABECERA DE COLUMNAS (Alineada para 1 y 2 dígitos) ---
            StringBuilder sb = new StringBuilder("     ");
            for (int j = 0; j < sopa.getColumnas(); j++) {
                sb.append(String.format("%-2d", j)); // Cada número ocupa 2 espacios fijos
            }
            sb.append("\n");

            // --- FILAS DE LA MATRIZ ---
            String[][] m = sopa.getMatriz();
            for (int i = 0; i < sopa.getFilas(); i++) {
                sb.append(i < 10 ? i + "   | " : i + "  | ");
                for (int j = 0; j < sopa.getColumnas(); j++) {
                    sb.append(String.format("%-2s", m[i][j])); // Cada celda ocupa 2 espacios fijos
                }
                sb.append("\n");
            }

            TextArea txtSopa = new TextArea(sb.toString());
            txtSopa.setEditable(false);

            // Fuente monoespaciada y tamaño adecuado para matrices grandes
            txtSopa.setStyle("-fx-font-family: monospace; -fx-font-size: 13px; -fx-control-inner-background: #ffffff;");
            txtSopa.setPrefHeight(350);

            // Controles de búsqueda en la misma ventana
            Label lblBuscar = new Label("Ingrese la palabra a buscar:");
            lblBuscar.setStyle("-fx-text-fill: #2c3e50; -fx-font-weight: bold;");

            TextField txtPalabra = new TextField();
            txtPalabra.setStyle("-fx-background-color: #ffffff; -fx-border-color: #bdc3c7; -fx-padding: 5px;");

            Button btnBuscar = new Button("Buscar Palabra");
            btnBuscar.setStyle("-fx-background-color: #2980b9; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand;");

            Label lblResultado = new Label("Resultado: (Escribe una palabra y presiona buscar)");
            lblResultado.setWrapText(true);
            lblResultado.setStyle("-fx-font-weight: bold; -fx-text-fill: #1a5276;");

            // Acción del botón
            btnBuscar.setOnAction(e -> {
                String palabra = txtPalabra.getText();
                if (palabra != null && !palabra.trim().isEmpty()) {
                    String resultado = sopa.buscarPalabra(palabra.trim());
                    lblResultado.setText(resultado.replace("\n", "  |  "));
                } else {
                    lblResultado.setText("Por favor ingrese una palabra.");
                }
            });

            // Agregar los elementos al contenedor principal
            root.getChildren().addAll(lblTitulo, txtSopa, lblBuscar, txtPalabra, btnBuscar, lblResultado);

            // Configurar escena y ventana
            Scene scene = new Scene(root, 650, 650);
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ocurrió un error al cargar el archivo: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}