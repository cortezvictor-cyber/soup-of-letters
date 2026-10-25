package com.example.demo1;

import java.util.Random;

public class SopaLetras extends SopaObjetos {

    public String buscarPalabra(String palabra) {
        String[][] m = getMatriz();
        int fTotal = getFilas();
        int cTotal = getColumnas();
        palabra = palabra.toUpperCase();

        // Las 8 direcciones posibles en una matriz (Fila, Columna)
        int[][] dirs = {
                {0, 1},   // Derecha
                {0, -1},  // Izquierda
                {1, 0},   // Abajo
                {-1, 0},  // Arriba
                {1, 1},   // Diagonal Abajo-Derecha
                {1, -1},  // Diagonal Abajo-Izquierda
                {-1, 1},  // Diagonal Arriba-Derecha
                {-1, -1}  // Diagonal Arriba-Izquierda
        };

        String[] nombresDirs = {
                "Derecha", "Izquierda", "Abajo", "Arriba",
                "Diagonal Abajo-Derecha", "Diagonal Abajo-Izquierda",
                "Diagonal Arriba-Derecha", "Diagonal Arriba-Izquierda"
        };

        // 1. Recorremos cada celda de la matriz
        for (int f = 0; f < fTotal; f++) {
            for (int c = 0; c < cTotal; c++) {
                // 2. Probamos las 8 direcciones desde la celda actual (f, c)
                for (int d = 0; d < dirs.length; d++) {
                    if (revisarDireccion(m, fTotal, cTotal, f, c, dirs[d][0], dirs[d][1], palabra)) {
                        return "¡Palabra encontrada!\n" +
                                "Renglón inicial: " + f + "\n" +
                                "Columna inicial: " + c + "\n" +
                                "Dirección: " + nombresDirs[d];
                    }
                }
            }
        }
        return "La palabra NO esta.";
    }

    private boolean revisarDireccion(String[][] m, int fTotal, int cTotal, int f, int c, int df, int dc, String p) {
        for (int k = 0; k < p.length(); k++) {
            int nf = f + k * df; // nueva fila
            int nc = c + k * dc; // nueva columna

            // Si se sale de los límites de la matriz, esta dirección no es válida
            if (nf < 0 || nf >= fTotal || nc < 0 || nc >= cTotal) {
                return false;
            }

            // Si alguna letra no coincide, devolvemos falso
            if (!m[nf][nc].equalsIgnoreCase(String.valueOf(p.charAt(k)))) {
                return false;
            }
        }
        return true;
    }


    public void lettersRandom() {
        Random r = new Random();
        String[][] m = getMatriz();
        int filas = getFilas();
        int columnas = getColumnas();

        if (m == null) return;

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                // Si la celda contiene un guión bajo, lo cambiamos por una letra aleatoria de la A a la Z
                if (m[i][j] != null && m[i][j].equals("_")) {
                    char letraAleatoria = (char) (r.nextInt(26) + 'A');
                    m[i][j] = String.valueOf(letraAleatoria);
                }
            }
        }
    }
}