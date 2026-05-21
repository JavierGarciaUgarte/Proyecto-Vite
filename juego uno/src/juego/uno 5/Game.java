package juego;

import java.util.ArrayList;
import java.util.Scanner;

public class Game {

    private Deck deck;
    private DiscardPile discardPile;
    private Scanner scanner;

    private ArrayList<Player> jugadores;
    private TurnManager turnManager;
    private RuleEngine ruleEngine;

    private boolean jugadorDijoUNO = false;

    public Game() {
        deck = new Deck();
        discardPile = new DiscardPile();
        scanner = new Scanner(System.in);

        jugadores = new ArrayList<>();
        jugadores.add(new Player("Tú", true));
        jugadores.add(new Player("Pepe", false));
        jugadores.add(new Player("Toño", false));
        jugadores.add(new Player("Maria", false));

        turnManager = new TurnManager(jugadores);
        ruleEngine = new RuleEngine();
    }

    public void iniciar() {

        repartirCartas();

        //  Carta inicial válida (no comodín ni +4)
        Card inicial = obtenerCartaInicialValida();
        discardPile.ponerCarta(inicial);

        System.out.println("Carta inicial en mesa: " + inicial);
        System.out.println("----------");

        while (true) {

            Player actual = turnManager.getJugadorActual();

            System.out.println("\nTurno de: " + actual.getNombre());

            if (actual.esHumano()) {
                turnoJugador(actual);
            } else {
                turnoComputadora(actual);
            }

            verificarPenalizacionUNO(actual);

            if (actual.getMano().estaVacia()) {
                System.out.println(actual.getNombre() + " gana!");
                break;
            }

            turnManager.siguienteTurno();
        }
    }

    private Card obtenerCartaInicialValida() {
        Card carta;

        do {
            carta = deck.robaCarta();
        } while (!discardPile.esCartaInicialValida(carta));

        return carta;
    }

    private void repartirCartas() {
        for (int i = 0; i < 7; i++) {
            for (Player p : jugadores) {
                p.robarCarta(deck);
            }
        }
    }

    private void turnoJugador(Player actual) {

        Card cartaMesa = discardPile.getCartaActual();

        System.out.println("Carta en mesa: " + cartaMesa);
        actual.getMano().mostrarMano();

        if (!actual.tieneJugadaValida(cartaMesa, ruleEngine)) {
            System.out.println("No tienes jugada válida. Robas carta.");
            actual.robarCarta(deck);
            return;
        }

        while (true) {

            System.out.print("Elige carta o -1 para robar: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Entrada inválida.");
                scanner.next();
                continue;
            }

            int opcion = scanner.nextInt();

            if (opcion == -1) {
                actual.robarCarta(deck);
                return;
            }

            Card seleccionada = actual.getMano().jugarCarta(opcion);

            if (seleccionada == null) {
                System.out.println("Índice inválido.");
                continue;
            }

            if (ruleEngine.esJugable(seleccionada, cartaMesa)) {

                String colorElegido = null;

                if (seleccionada.getNumero() == 13 || seleccionada.getNumero() == 14) {
                    colorElegido = elegirColor(actual);
                }

                discardPile.ponerCarta(seleccionada);
                System.out.println("Jugaste: " + seleccionada);

                ruleEngine.aplicarEfecto(seleccionada, actual, turnManager, deck, colorElegido);
                break;

            } else {
                System.out.println("Carta no válida.");
                actual.getMano().agregarCarta(seleccionada);
            }
        }

        if (actual.getMano().size() == 1) {
            System.out.println("¿Deseas decir UNO? (s/n)");
            jugadorDijoUNO = scanner.next().equalsIgnoreCase("s");
        }
    }

    private void turnoComputadora(Player actual) {

        Card cartaMesa = discardPile.getCartaActual();

        System.out.println("Carta en mesa: " + cartaMesa);

        for (int i = 0; i < actual.getMano().size(); i++) {

            Card carta = actual.getMano().jugarCarta(i);

            if (ruleEngine.esJugable(carta, cartaMesa)) {

                String colorElegido = null;

                if (carta.getNumero() == 13 || carta.getNumero() == 14) {
                    colorElegido = elegirColor(actual);
                }

                System.out.println(actual.getNombre() + " juega: " + carta);

                discardPile.ponerCarta(carta);

                ruleEngine.aplicarEfecto(carta, actual, turnManager, deck, colorElegido);
                return;

            } else {
                actual.getMano().agregarCarta(carta);
            }
        }

        System.out.println(actual.getNombre() + " roba carta.");
        actual.robarCarta(deck);
    }

    private String elegirColor(Player actual) {

        if (actual.esHumano()) {
            System.out.println("Elige color (rojo, azul, verde, amarillo):");
            return scanner.next();
        } else {
            String[] colores = {"rojo", "azul", "verde", "amarillo"};
            String color = colores[(int)(Math.random() * 4)];
            System.out.println(actual.getNombre() + " cambia a " + color);
            return color;
        }
    }

    private void verificarPenalizacionUNO(Player actual) {

        if (actual.getMano().size() == 1) {

            if (actual.esHumano()) {
                if (!jugadorDijoUNO) {
                    System.out.println("No dijiste UNO. Robas 2.");
                    actual.robarCarta(deck);
                    actual.robarCarta(deck);
                }
            } else {
                if (Math.random() < 0.7) {
                    System.out.println(actual.getNombre() + " dice UNO!");
                } else {
                    System.out.println(actual.getNombre() + " olvidó decir UNO. Roba 2.");
                    actual.robarCarta(deck);
                    actual.robarCarta(deck);
                }
            }
        }

        jugadorDijoUNO = false;
    }
}
