package com.example.demo1;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;
import java.util.Vector;

public class SopaObjetos {
    private String[][] matriz;
    private int filas;
    private int columnas;

    public void cargarArchivo(String ruta) throws IOException {
        // defino el tamanio deseado
        this.filas = 20;
        this.columnas = 20;

        //lee el archivo
        Vector<String> palabrasVector = new Vector<>();
        BufferedReader br = new BufferedReader(new FileReader(ruta));
        String linea;

        while ((linea = br.readLine()) != null) {
            linea = linea.trim();
            if (!linea.isEmpty()) {
                palabrasVector.add(linea);
            }
        }
        br.close();
//inicializo la matriz
        matriz = (String[][]) Array.newInstance(String.class, filas, columnas);

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                matriz[i][j] = "_";
            }
        }

        Random random = new Random();

        // Las 8 direcciones
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

        // pone cada palabra de forma aleatoria
        for (String palabra : palabrasVector) {
            palabra = palabra.toUpperCase();
            boolean colocada = false;
            int intentos = 0;

            // hace que no se traslapen las letras
            while (!colocada && intentos < 200) {
                intentos++;
                int f = random.nextInt(filas);
                int c = random.nextInt(columnas);
                int d = random.nextInt(dirs.length);
                int df = dirs[d][0];
                int dc = dirs[d][1];

                // Verificamos si cabe y no choca mal con otra palabra
                if (cabePalabra(f, c, df, dc, palabra)) {
                    for (int k = 0; k < palabra.length(); k++) {
                        int nf = f + k * df;
                        int nc = c + k * dc;
                        matriz[nf][nc] = String.valueOf(palabra.charAt(k));
                    }
                    colocada = true;
                }
            }
        }

        // se llenan los espacios vacíos
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (matriz[i][j].equals("_")) {
                    char letraAleatoria = (char) (random.nextInt(26) + 'A');
                    matriz[i][j] = String.valueOf(letraAleatoria);
                }
            }
        }

        String[][] copia = Arrays.copyOf(matriz, matriz.length);
    }

// f(fila donde inicia) c(columna donde empieza) df(como se va moviendo la fila) dc(como se mueve la columna)
    private boolean cabePalabra(int f, int c, int df, int dc, String palabra) {
        int len = palabra.length();  //mide la palabra
        int finF = f + (len - 1) * df; //calcula donde es el fin de la palabra en fila
        int finC = c + (len - 1) * dc; //y este la columna

        if (finF < 0 || finF >= filas || finC < 0 || finC >= columnas) {
            return false;
        }

        for (int k = 0; k < len; k++) {
            int nf = f + k * df; //va indicando el nuevo posicionamiento de la letra de la palabra en la fila
            int nc = c + k * dc; // en la columna
            String actual = matriz[nf][nc];
            char letra = palabra.charAt(k);

            if (!actual.equals("_") && !actual.equalsIgnoreCase(String.valueOf(letra))) {
                return false;
            }
        }
        return true;
    }

    // Muestra la Sopa de letras enumerando renglones y columnas con espacios
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