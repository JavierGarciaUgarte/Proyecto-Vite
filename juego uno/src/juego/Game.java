package juego;

import java.util.Scanner;

public class Game {

    private Deck deck;
    private Hand jugador;
    private Hand computadora;
    private Card cartaMesa;
    private Scanner scanner;

    public Game() {
        deck = new Deck();
        jugador = new Hand();
        computadora = new Hand();
        scanner = new Scanner(System.in);
    }

    public void iniciar() {
        repartirCartas();

        cartaMesa = deck.robaCarta();
        System.out.println("Carta inicial en mesa: " + cartaMesa);
        System.out.println("----------");

        while (true) {

            turnoJugador();
            if (jugador.estaVacia()) {
                System.out.println("¡Ganaste!");
                break;
            }

            turnoComputadora();
            if (computadora.estaVacia()) {
                System.out.println("La computadora ganó");
                break;
            }
        }
    }

    private void repartirCartas() {
        for (int i = 0; i < 7; i++) {
            jugador.agregarCarta(deck.robaCarta());
            computadora.agregarCarta(deck.robaCarta());
        }
    }

    private void turnoJugador() {
        System.out.println("\n=== TU TURNO ===");
        System.out.println("Carta en mesa: " + cartaMesa);
        jugador.mostrarMano();

        if (!jugador.tieneJugadaValida(cartaMesa)) {
            System.out.println("No tienes jugada válida, robas carta");
            jugador.agregarCarta(deck.robaCarta());
            return;
        }

        while (true) {
            System.out.print("Elige carta (número) o -1 para robar: ");
            int opcion = scanner.nextInt();

            if (opcion == -1) {
                jugador.agregarCarta(deck.robaCarta());
                System.out.println("Robaste carta");
                return;
            }

            Card seleccionada = jugador.jugarCarta(opcion);

            if (seleccionada == null) {
                System.out.println("Índice inválido");
                continue;
            }

            if (seleccionada.esJugableSobre(cartaMesa)) {
                cartaMesa = seleccionada;
                System.out.println("Jugaste: " + cartaMesa);
                return;
            } else {
                System.out.println("Carta no válida, regresa a tu mano");
                jugador.agregarCarta(seleccionada);
            }
        }
    }

    private void turnoComputadora() {
        System.out.println("\n=== TURNO COMPUTADORA ===");
        System.out.println("Carta en mesa: " + cartaMesa);

        for (int i = 0; i < computadora.size(); i++) {
            Card c = computadora.jugarCarta(i);

            if (c.esJugableSobre(cartaMesa)) {
                cartaMesa = c;
                System.out.println("Computadora juega: " + cartaMesa);
                return;
            } else {
                computadora.agregarCarta(c);
            }
        }

        System.out.println("Computadora roba carta");
        computadora.agregarCarta(deck.robaCarta());
    }
}
