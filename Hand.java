package juego;

import java.util.ArrayList;

public class Hand {

    private ArrayList<Card> cartas;

    public Hand() {
        cartas = new ArrayList<>();
    }

    public void agregarCarta(Card c) {
        if (c != null) {
            cartas.add(c);
        }
    }

    public Card jugarCarta(int indice) {
        if (indice < 0 || indice >= cartas.size()) {
            return null;
        }
        return cartas.remove(indice);
    }

    public int size() {
        return cartas.size();
    }

    public boolean estaVacia() {
        return cartas.isEmpty();
    }

    public void mostrarMano() {
        System.out.println("Cartas en mano:");
        for (int i = 0; i < cartas.size(); i++) {
            System.out.println(i + ": " + cartas.get(i));
        }
    }

    public boolean tieneJugadaValida(Card cartaMesa) {
        for (Card c : cartas) {
            if (c.esJugableSobre(cartaMesa)) {
                return true;
            }
        }
        return false;
    }
}
