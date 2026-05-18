package com.mycompany.uno;

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
    private Card ultimaCartaJugada;

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

    public void iniciarGUI() {
        prepararJuego();
    }

    private void prepararJuego() {
        repartirCartas();

        Card inicial = obtenerCartaInicialValida();

        discardPile.ponerCarta(inicial);
        ultimaCartaJugada = inicial;
    }

    public ArrayList<Player> getJugadores() {
        return jugadores;
    }

    public Player getJugadorActual() {
        return turnManager.getJugadorActual();
    }

    public Card getCartaMesa() {
        return discardPile.getCartaActual();
    }

    public boolean esTurnoHumano() {
        return getJugadorActual().esHumano();
    }

    public void siguienteTurno() {
        turnManager.siguienteTurno();
    }

    public void robarCartaJugadorActual() {
        getJugadorActual().robarCarta(deck);
        ultimaCartaJugada = null;
    }

    public void decirUNO() {
        jugadorDijoUNO = true;
    }

    public void verificarPenalizacionJugadorActual() {
        verificarPenalizacionUNO(getJugadorActual());
    }

    public Card getUltimaCartaJugada() {
        return ultimaCartaJugada;
    }

    public boolean jugarCartaJugadorActual(int indice, String colorElegido) {

        Player actual = getJugadorActual();
        Card cartaMesa = getCartaMesa();

        Card seleccionada = actual.getMano().verCarta(indice);

        if (seleccionada == null) {
            return false;
        }

        if (!ruleEngine.esJugable(seleccionada, cartaMesa)) {
            return false;
        }

        seleccionada = actual.getMano().jugarCarta(indice);

        discardPile.ponerCarta(seleccionada);
        ultimaCartaJugada = seleccionada;

        ruleEngine.aplicarEfecto(
                seleccionada,
                actual,
                turnManager,
                deck,
                colorElegido
        );

        return true;
    }

    public String jugarTurnoComputadoraActual() {

        Player actual = getJugadorActual();

        if (actual.esHumano()) {
            return "Es turno del jugador humano.";
        }

        Card cartaMesa = getCartaMesa();

        int tamanoInicial = actual.getMano().size();

        for (int i = 0; i < tamanoInicial; i++) {

            Card carta = actual.getMano().verCarta(i);

            if (ruleEngine.esJugable(carta, cartaMesa)) {

                carta = actual.getMano().jugarCarta(i);

                String colorElegido = null;

                if (carta.getNumero() == 13 || carta.getNumero() == 14) {
                    colorElegido = elegirColor(actual);
                }

                discardPile.ponerCarta(carta);
                ultimaCartaJugada = carta;

                ruleEngine.aplicarEfecto(
                        carta,
                        actual,
                        turnManager,
                        deck,
                        colorElegido
                );

                if (carta.getNumero() == 13 || carta.getNumero() == 14) {
                    return actual.getNombre()
                            + " juega: "
                            + carta
                            + " y cambia el color a "
                            + colorElegido;
                }

                return actual.getNombre() + " juega: " + carta;
            }
        }

        actual.robarCarta(deck);
        ultimaCartaJugada = null;

        return actual.getNombre() + " roba una carta.";
    }

    public void finalizarTurnoDespuesDeJugada() {

        if (ultimaCartaJugada == null) {
            turnManager.siguienteTurno();
            return;
        }

        int num = ultimaCartaJugada.getNumero();

        if (num == 10 || num == 11 || num == 14) {
            turnManager.siguienteTurno();
            turnManager.siguienteTurno();
            return;
        }

        turnManager.siguienteTurno();
    }

    public boolean hayGanador() {
        return getJugadorActual().getMano().estaVacia();
    }

    public String getNombreGanador() {
        return getJugadorActual().getNombre();
    }

    private void repartirCartas() {

        for (int i = 0; i < 7; i++) {

            for (Player p : jugadores) {
                p.robarCarta(deck);
            }
        }
    }

    private Card obtenerCartaInicialValida() {

        Card carta;

        do {
            carta = deck.robaCarta();
        } while (!discardPile.esCartaInicialValida(carta));

        return carta;
    }

    private void verificarPenalizacionUNO(Player actual) {

        if (actual.getMano().size() == 1) {

            if (actual.esHumano()) {

                if (!jugadorDijoUNO) {
                    actual.robarCarta(deck);
                    actual.robarCarta(deck);
                }

            } else {

                if (Math.random() >= 0.7) {
                    actual.robarCarta(deck);
                    actual.robarCarta(deck);
                }
            }
        }

        jugadorDijoUNO = false;
    }

    private String elegirColor(Player actual) {

        String[] colores = {
            "rojo",
            "azul",
            "verde",
            "amarillo"
        };

        return colores[(int) (Math.random() * 4)];
    }

    public void iniciar() {
        iniciarGUI();
    }
}