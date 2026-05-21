package juego;

import java.util.Random;
import java.util.Scanner;

public class RuleEngine {

    public boolean esJugable(Card jugada, Card mesa) {
        if (mesa == null) return true;

        if (jugada.getNumero() == 13 || jugada.getNumero() == 14)
            return true;

        return jugada.getColor().equals(mesa.getColor()) ||
               jugada.getNumero() == mesa.getNumero();
    }

    //AHORA recibe colorElegido
    public void aplicarEfecto(Card carta, Player actual,
                             TurnManager turnManager,
                             Deck deck,
                             String colorElegido) {

        int num = carta.getNumero();

        if (num == 10) { // +2
            System.out.println("Se juega +2");

            Player objetivo = turnManager.getSiguienteJugador();

            for (int i = 0; i < 2; i++) {
                objetivo.getMano().agregarCarta(deck.robaCarta());
            }

            System.out.println(objetivo.getNombre() + " roba 2 y pierde turno");

            turnManager.siguienteTurno();
        }

        else if (num == 11) { // salto
            System.out.println("Se juega SALTO");
            turnManager.siguienteTurno();
        }

        else if (num == 12) { // reversa
            System.out.println("Se juega REVERSA");
            turnManager.invertirDireccion();
        }

        else if (num == 13) { // comodín
            aplicarCambioColor(carta, colorElegido);
        }

        else if (num == 14) { // roba 4
            System.out.println("Se juega ROBA 4");

            Player objetivo = turnManager.getSiguienteJugador();

            for (int i = 0; i < 4; i++) {
                objetivo.getMano().agregarCarta(deck.robaCarta());
            }

            System.out.println(objetivo.getNombre() + " roba 4 y pierde turno");

            aplicarCambioColor(carta, colorElegido);

            turnManager.siguienteTurno();
        }
    }

    private void aplicarCambioColor(Card carta, String colorElegido) {
        if (colorElegido != null) {
            carta.setColor(colorElegido);
        }
    }
}
