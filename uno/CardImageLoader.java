package com.mycompany.uno;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class CardImageLoader {

    private BufferedImage sheet;

    private final int columnas = 14;
    private final int filas = 8;

    private final int anchoIcono = 90;
    private final int altoIcono = 130;

    public CardImageLoader() {

        try {

            sheet = ImageIO.read(
                    new File(
                            "src/main/java/com/mycompany/uno/img/uno_spritesheet.png"
                    )
            );

        } catch (Exception e) {

            System.out.println(
                    "No se pudo cargar la imagen: "
                    + e.getMessage()
            );
        }
    }

    public ImageIcon obtenerIcono(Card carta) {

        BufferedImage img = obtenerImagen(carta);

        Image escalada = img.getScaledInstance(
                anchoIcono,
                altoIcono,
                Image.SCALE_SMOOTH
        );

        return new ImageIcon(escalada);
    }

    public ImageIcon obtenerReverso() {

        int anchoCarta = sheet.getWidth() / columnas;
        int altoCarta = sheet.getHeight() / filas;

        BufferedImage img = sheet.getSubimage(
                13 * anchoCarta,
                0,
                anchoCarta,
                altoCarta
        );

        Image escalada = img.getScaledInstance(
                70,
                100,
                Image.SCALE_SMOOTH
        );

        return new ImageIcon(escalada);
    }

    private BufferedImage obtenerImagen(Card carta) {

        int fila = obtenerFila(carta);
        int columna = obtenerColumna(carta);

        int anchoCarta = sheet.getWidth() / columnas;
        int altoCarta = sheet.getHeight() / filas;

        int x = columna * anchoCarta;
        int y = fila * altoCarta;

        return sheet.getSubimage(
                x,
                y,
                anchoCarta,
                altoCarta
        );
    }

    private int obtenerFila(Card carta) {

        String color = carta.getColor();
        int numero = carta.getNumero();

        // COMODINES

        if (numero == 13) {
            return 0;
        }

        if (numero == 14) {
            return 4;
        }

        // COLORES

        if (color.equals("rojo")) {
            return 0;
        }

        if (color.equals("amarillo")) {
            return 1;
        }

        if (color.equals("verde")) {
            return 2;
        }

        if (color.equals("azul")) {
            return 3;
        }

        return 0;
    }

    private int obtenerColumna(Card carta) {

        int n = carta.getNumero();

        // NÚMEROS

        if (n >= 0 && n <= 9) {
            return n;
        }

        // ESPECIALES

        if (n == 10) {
            return 12; // +2
        }

        if (n == 11) {
            return 10; // salto
        }

        if (n == 12) {
            return 11; // reversa
        }

        if (n == 13) {
            return 13; // comodín
        }

        if (n == 14) {
            return 13; // roba4
        }

        return 0;
    }
}