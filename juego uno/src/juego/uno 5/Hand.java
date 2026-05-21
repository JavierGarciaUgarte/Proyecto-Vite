package juego;

import java.util.ArrayList;

public class Hand {

    private ArrayList<Card> cartas;

    public Hand() {
        cartas = new ArrayList<>();
    }

    public void agregarCarta(Card c) {
        cartas.add(c); 
    }

    public Card jugarCarta(int indice) {
        if (indice < 0 || indice >= cartas.size()) return null;
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

    //IMPORTANTE: ahora usa RuleEngine
    public boolean tieneJugadaValida(Card cartaMesa, RuleEngine reglas) {
        for (Card c : cartas) {
            if (reglas.esJugable(c, cartaMesa)) {
                return true;
            }
        }
        return false;
    }
}
