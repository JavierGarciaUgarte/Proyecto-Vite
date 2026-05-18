package com.mycompany.uno;

public class RuleEngine {

    public boolean esJugable(Card jugada, Card mesa) {

        if (mesa == null) return true;

        if (jugada.getNumero() == 13 || jugada.getNumero() == 14) {
            return true;
        }

        return jugada.getColor().equals(mesa.getColor())
                || jugada.getNumero() == mesa.getNumero();
    }

    public boolean debeSaltarSiguiente(Card carta) {
        int num = carta.getNumero();
        return num == 10 || num == 11 || num == 14;
    }

    public void aplicarEfecto(
            Card carta,
            Player actual,
            TurnManager turnManager,
            Deck deck,
            String colorElegido) {

        int num = carta.getNumero();

        switch (num) {

            case 10: // +2
                Player objetivoMas2 = turnManager.getSiguienteJugador();
                for (int i = 0; i < 2; i++) {
                    objetivoMas2.getMano().agregarCarta(deck.robaCarta());
                }
                break;

            case 11: // salto
                break;

            case 12: // reversa
                turnManager.invertirDireccion();
                break;

            case 13: // comodín
                aplicarCambioColor(carta, colorElegido);
                break;

            case 14: // roba 4
                Player objetivoMas4 = turnManager.getSiguienteJugador();
                for (int i = 0; i < 4; i++) {
                    objetivoMas4.getMano().agregarCarta(deck.robaCarta());
                }
                aplicarCambioColor(carta, colorElegido);
                break;
        }
    }

    private void aplicarCambioColor(Card carta, String colorElegido) {
        if (colorElegido != null) {
            carta.setColor(colorElegido);
        }
    }
}