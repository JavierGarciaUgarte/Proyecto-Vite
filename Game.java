package juego;

import java.util.Scanner;

public class Game {

    private Deck deck;
    private Hand jugador;
    private Hand computadora;
    private Card cartaMesa;
    private Scanner scanner;

    private boolean jugadorDijoUNO = false;

    public Game() {
        deck = new Deck();
        jugador = new Hand();
        computadora = new Hand();
        scanner = new Scanner(System.in);

        for (int i = 0; i < 7; i++) {
            jugador.agregarCarta(deck.robaCarta());
            computadora.agregarCarta(deck.robaCarta());
        }

        cartaMesa = deck.robaCarta();
    }

    public void iniciar() {

        while (true) {

            turnoJugador();
            verificarPenalizacionUNO();

            if (jugador.estaVacia()) {
                System.out.println("¡Jugador gana!");
                break;
            }

            turnoComputadora();

            if (computadora.estaVacia()) {
                System.out.println("¡Computadora gana!");
                break;
            }
        }
    }

    private void turnoJugador() {
        System.out.println("\nCarta en mesa: " + cartaMesa);
        jugador.mostrarMano();

        System.out.println("Elige carta o -1 para robar:");
        int opcion = scanner.nextInt();

        if (opcion == -1) {
            jugador.agregarCarta(deck.robaCarta());
            return;
        }

        Card carta = jugador.jugarCarta(opcion);

        if (carta != null && carta.esJugableSobre(cartaMesa)) {
            cartaMesa = carta;
            aplicarEfecto(carta, true);
        } else {
            System.out.println("Movimiento inválido.");
            jugador.agregarCarta(carta);
        }

        if (jugador.size() == 1) {
            System.out.println("¿Deseas decir UNO? (s/n)");
            jugadorDijoUNO = scanner.next().equalsIgnoreCase("s");
        }
    }

    private void turnoComputadora() {

        for (int i = 0; i < computadora.size(); i++) {
            Card carta = computadora.jugarCarta(i);

            if (carta.esJugableSobre(cartaMesa)) {
                System.out.println("Computadora juega: " + carta);
                cartaMesa = carta;
                aplicarEfecto(carta, false);
                return;
            } else {
                computadora.agregarCarta(carta);
            }
        }

        computadora.agregarCarta(deck.robaCarta());
        System.out.println("Computadora roba.");
    }

    private void aplicarEfecto(Card carta, boolean esJugador) {

        int num = carta.getNumero();

        if (num == 10) { // +2
            System.out.println("Se juega +2");
            for (int i = 0; i < 2; i++) {
                if (esJugador)
                    computadora.agregarCarta(deck.robaCarta());
                else
                    jugador.agregarCarta(deck.robaCarta());
            }
        }

        else if (num == 11) { // SALTO
            System.out.println("Se juega SALTO");
            if (esJugador) turnoJugador();
            else turnoComputadora();
        }

        else if (num == 12) { // REVERSA
            System.out.println("Se juega REVERSA");
            if (esJugador) turnoJugador();
            else turnoComputadora();
        }

        else if (num == 13) { // COMODIN
            cambiarColor(carta, esJugador);
        }

        else if (num == 14) { // ROBA 4
            for (int i = 0; i < 4; i++) {
                if (esJugador)
                    computadora.agregarCarta(deck.robaCarta());
                else
                    jugador.agregarCarta(deck.robaCarta());
            }
            cambiarColor(carta, esJugador);
        }
    }

    private void cambiarColor(Card carta, boolean esJugador) {
        String nuevoColor;

        if (esJugador) {
            System.out.println("Elige color:");
            nuevoColor = scanner.next();
        } else {
            String[] colores = {"rojo", "azul", "verde", "amarillo"};
            nuevoColor = colores[(int)(Math.random()*4)];
            System.out.println("Computadora cambia a " + nuevoColor);
        }

        carta.setColor(nuevoColor);
    }

    private void verificarPenalizacionUNO() {
        if (jugador.size() == 1 && !jugadorDijoUNO) {
            System.out.println("No dijiste UNO. Robas 2.");
            jugador.agregarCarta(deck.robaCarta());
            jugador.agregarCarta(deck.robaCarta());
        }
        jugadorDijoUNO = false;
    }
}
