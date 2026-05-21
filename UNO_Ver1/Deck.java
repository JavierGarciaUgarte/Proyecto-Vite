package juego;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {

    private ArrayList<Card> cartas;

    public Deck() {
        cartas = new ArrayList<>();
        crearBaraja();
        barajar();
    }

    
    private void crearBaraja() {

        String[] colores = {"rojo", "azul", "verde", "amarillo"};

        for (String color : colores) {
            for (int i = 0; i <= 9; i++) {
                cartas.add(new Card(color, i));

                                if (i != 0) {
                    cartas.add(new Card(color, i));
                }
            }
        }
    }

    public void barajar() {
        Collections.shuffle(cartas);
    }

    public Card robaCarta() {
        if (cartas.isEmpty()) {
            return null;
        }
        return cartas.remove(0);
    }

    public int cartasRestantes() {
        return cartas.size();
    }
}