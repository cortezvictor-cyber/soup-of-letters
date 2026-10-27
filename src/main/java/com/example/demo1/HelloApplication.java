package com.example.demo1;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) {
        try {
            SopaLetras sopa = new SopaLetras();

            // 1. Pedir el archivo al iniciar
            String archivo = JOptionPane.showInputDialog(null, "Nombre del archivo de texto", "Cargar Sopa", JOptionPane.QUESTION_MESSAGE);
            if (archivo == null || archivo.trim().isEmpty()) {
                archivo = "sopa.txt";
            }

            // Cargar archivo
            sopa.cargarArchivo(archivo.trim());

            // 2. Configurar la Ventana
            stage.setTitle("Buscador de Sopa de Letras");

            VBox root = new VBox(12);
            root.setPadding(new Insets(15));
            root.setAlignment(Pos.CENTER); // Centra todos los elementos vertical y horizontalmente en la ventana
            root.setStyle("-fx-background-color: #f0f4f8;");

            // Título
            Label lblTitulo = new Label("SOUP OF LETTERS:");
            lblTitulo.setStyle("-fx-text-fill: #2c3e50; -fx-font-weight: bold;");

            // --- CREAR LA CUADRÍCULA CON NÚMEROS Y CAJITAS ---
            GridPane gridSopa = new GridPane();
            gridSopa.setHgap(3);
            gridSopa.setVgap(3);
            gridSopa.setAlignment(Pos.CENTER);

            int filas = sopa.getFilas();
            int columnas = sopa.getColumnas();
            String[][] m = sopa.getMatriz();

            // 1. Números de las columnas (parte superior)
            for (int j = 0; j < columnas; j++) {
                Label lblCol = new Label(String.valueOf(j));
                lblCol.setMinSize(28, 28);
                lblCol.setMaxSize(28, 28);
                lblCol.setStyle("-fx-alignment: center; -fx-font-weight: bold; -fx-text-fill: #7f8c8d; -fx-font-size: 11px;");
                gridSopa.add(lblCol, j + 1, 0);
            }

            // 2. Números de los renglones (parte izquierda)
            for (int i = 0; i < filas; i++) {
                Label lblRow = new Label(String.valueOf(i));
                lblRow.setMinSize(28, 28);
                lblRow.setMaxSize(28, 28);
                lblRow.setStyle("-fx-alignment: center; -fx-font-weight: bold; -fx-text-fill: #7f8c8d; -fx-font-size: 11px;");
                gridSopa.add(lblRow, 0, i + 1);
            }

            // 3. Cajitas con cada letra de la matriz
            for (int i = 0; i < filas; i++) {
                for (int j = 0; j < columnas; j++) {
                    Label lblCell = new Label(m[i][j] != null ? m[i][j] : " ");
                    lblCell.setMinSize(28, 28);
                    lblCell.setMaxSize(28, 28);
                    lblCell.setStyle("-fx-border-color: #bdc3c7; " +
                            "-fx-background-color: #ffffff; " +
                            "-fx-alignment: center; " +
                            "-fx-font-family: monospace; " +
                            "-fx-font-weight: bold; " +
                            "-fx-text-fill: #2c3e50;");

                    gridSopa.add(lblCell, j + 1, i + 1);
                }
            }

            // Contenedor StackPane para obligar a que la sopa de letras quede centrada dentro del ScrollPane
            StackPane gridContainer = new StackPane(gridSopa);
            gridContainer.setAlignment(Pos.CENTER);
            gridContainer.setStyle("-fx-background-color: #ffffff;");

            // ScrollPane configurado para ajustar y centrar la cuadrícula
            ScrollPane scrollPane = new ScrollPane(gridContainer);
            scrollPane.setFitToWidth(true);
            scrollPane.setFitToHeight(true);
            scrollPane.setPrefHeight(350);
            scrollPane.setStyle("-fx-background: #ffffff; -fx-border-color: #bdc3c7;");

            // Controles de búsqueda
            Label lblBuscar = new Label("Ingrese la palabra a buscar:");
            lblBuscar.setStyle("-fx-text-fill: #2c3e50; -fx-font-weight: bold;");

            TextField txtPalabra = new TextField();
            txtPalabra.setStyle("-fx-background-color: #ffffff; -fx-border-color: #bdc3c7; -fx-padding: 5px; -fx-max-width: 250px;");

            Button btnBuscar = new Button("Buscar Palabra");
            btnBuscar.setStyle("-fx-background-color: #2980b9; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand;");

            // Botón para guardar en TXT
            Button btnGuardar = new Button("Guardar Sopa en TXT");
            btnGuardar.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand;");

            btnGuardar.setOnAction(e -> {
                sopa.guardarSopa();
            });

            Label lblResultado = new Label("Resultado: (Escribe una palabra y presiona buscar)");
            lblResultado.setWrapText(true);
            lblResultado.setStyle("-fx-font-weight: bold; -fx-text-fill: #1a5276;");

            // Acción del botón buscar
            btnBuscar.setOnAction(e -> {
                String palabra = txtPalabra.getText();
                if (palabra != null && !palabra.trim().isEmpty()) {
                    String resultado = sopa.buscarPalabra(palabra.trim());
                    lblResultado.setText(resultado.replace("\n", "  |  "));
                } else {
                    lblResultado.setText("Por favor ingrese una palabra.");
                }
            });

            // Agregar elementos al contenedor principal
            root.getChildren().addAll(lblTitulo, scrollPane, lblBuscar, txtPalabra, btnBuscar, btnGuardar, lblResultado);

            // Configurar escena y ventana
            Scene scene = new Scene(root, 650, 700);
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