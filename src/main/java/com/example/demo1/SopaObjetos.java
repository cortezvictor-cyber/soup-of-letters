package com.example.demo1;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Vector;
public class SopaObjetos {
    private String[][] matriz;
    private int filas;
    private int columnas;


    public void cargarArchivo(String ruta) throws IOException {
        Vector<String> lineas = new Vector<>();
        BufferedReader br = new BufferedReader(new FileReader(ruta));
        String linea;

        while ((linea = br.readLine()) != null) {
            if (!linea.trim().isEmpty()) {
                lineas.add(linea.trim());
            }
        }
        br.close();

        filas = lineas.size();
        String[] primerRenglon = lineas.get(0).split("\\s+");
        columnas = primerRenglon.length;

        // Uso obligatorio de la clase Array (de java.lang.reflect)
        matriz = (String[][]) Array.newInstance(String.class, filas, columnas);

        for (int i = 0; i < filas; i++) {
            String[] tokens = lineas.get(i).split("\\s+");
            for (int j = 0; j < columnas; j++) {
                if (j < tokens.length) {
                    matriz[i][j] = tokens[j];
                }
            }
        }

        // Uso obligatorio de la clase Arrays (de java.util) - Requisito c
        String[][] copia = Arrays.copyOf(matriz, matriz.length);
    }

    // Muestra la cuadrícula enumerando renglones y columnas con espacios
    public void mostrarSopa() {
        System.out.println("\nSoup Letters ");
        System.out.print("   ");
        for (int j = 0; j < columnas; j++) {
            System.out.print(j + " ");
        }
        System.out.println();

        for (int i = 0; i < filas; i++) {
            System.out.print(i + " |");
            for (int j = 0; j < columnas; j++) {
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println();
        }
    }

    public String[][] getMatriz() {

        return matriz;
    }

    public int getFilas() {

        return filas;
    }

    public int getColumnas() {

        return columnas;
    }
}
