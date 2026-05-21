package juego;

import java.util.ArrayList;
import java.util.Scanner;

public class Game {

    private Deck deck;
    private Card cartaMesa;
    private Scanner scanner;

    private ArrayList<Player> jugadores;
    private int turnoActual;
    private int direccion;




    private boolean jugadorDijoUNO = false;

    public Game() {
        deck = new Deck();
        scanner = new Scanner(System.in);

        jugadores = new ArrayList<>();
        jugadores.add(new Player("Tú", true));
        jugadores.add(new Player("Pepe", false));
        jugadores.add(new Player("Toño", false));
        jugadores.add(new Player("Maria", false));

        turnoActual = 0;
        direccion = 1;

    }
    private void siguienteTurno() {
        turnoActual += direccion;

        if (turnoActual >= jugadores.size()) {
            turnoActual = 0;
        } else if (turnoActual < 0) {
            turnoActual = jugadores.size() - 1;
        }
    }
    
    private Player obtenerSiguienteJugador() {
        int siguiente = turnoActual + direccion;

        if (siguiente >= jugadores.size()) siguiente = 0;
        if (siguiente < 0) siguiente = jugadores.size() - 1;

        return jugadores.get(siguiente);
    }



    public void iniciar() {

        repartirCartas();

        cartaMesa = deck.robaCarta();
        System.out.println("Carta inicial en mesa: " + cartaMesa);
        System.out.println("----------");

        while (true) {

            Player actual = jugadores.get(turnoActual);

            System.out.println("\nTurno de: " + actual.getNombre());

            if (actual.esHumano()) {
                turnoJugador(actual);
                verificarPenalizacionUNO(actual);
            } else {
                turnoComputadora(actual);
            }
            if (actual.getMano().estaVacia()) {
                System.out.println(actual.getNombre() + " gana!");
                break;
            }


            siguienteTurno(); 
        }
        }
    private void repartirCartas() {
        for (int i = 0; i < 7; i++) {
            for (Player p : jugadores) {
                p.getMano().agregarCarta(deck.robaCarta());
            }
        }
    }

    
    private void turnoJugador(Player actual) {

    	System.out.println("\n=== TURNO DE " + actual.getNombre() + " ===");
        System.out.println("Carta en mesa: " + cartaMesa);

        actual.getMano().mostrarMano(); 

        if (!actual.getMano().tieneJugadaValida(cartaMesa)) { 
            System.out.println("No tienes jugada válida. Robas carta.");
            actual.getMano().agregarCarta(deck.robaCarta()); 
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
                actual.getMano().agregarCarta(deck.robaCarta()); 
                return;
            }

            Card seleccionada = actual.getMano().jugarCarta(opcion); 

            if (seleccionada == null) {
                System.out.println("Índice inválido.");
                continue;
            }

            if (seleccionada.esJugableSobre(cartaMesa)) {
                cartaMesa = seleccionada;
                System.out.println("Jugaste: " + cartaMesa);
                aplicarEfecto(seleccionada, actual);
                break;
            } else {
                System.out.println("Carta no válida. Regresa a tu mano.");
                actual.getMano().agregarCarta(seleccionada); 
            }
        }

        if (actual.getMano().size() == 1) { 
            System.out.println("¿Deseas decir UNO? (s/n)");
            jugadorDijoUNO = scanner.next().equalsIgnoreCase("s");
        }
    }

    
    private void turnoComputadora(Player actual) {

        System.out.println("\n=== TURNO DE " + actual.getNombre() + " ===");
        System.out.println("Carta en mesa: " + cartaMesa);

        for (int i = 0; i < actual.getMano().size(); i++) {
            Card carta = actual.getMano().jugarCarta(i);

            if (carta.esJugableSobre(cartaMesa)) {
                System.out.println(actual.getNombre() + " juega: " + carta);
                cartaMesa = carta;
                aplicarEfecto(carta, actual);
                return;
            } else {
                actual.getMano().agregarCarta(carta);
            }
        }

        System.out.println(actual.getNombre() + " roba carta.");
        actual.getMano().agregarCarta(deck.robaCarta());
    }

    
    private void aplicarEfecto(Card carta, Player actual) {

        int num = carta.getNumero();

        if (num == 10) { 
            System.out.println("Se juega +2");
            
            Player objetivo = obtenerSiguienteJugador();

            for (int i = 0; i < 2; i++) {
                objetivo.getMano().agregarCarta(deck.robaCarta());
            }

            System.out.println(objetivo.getNombre() + " roba 2 y pierde turno");

            siguienteTurno(); 
        }

        else if (num == 11) { 
            System.out.println("Se juega SALTO");
            siguienteTurno();
        }

        else if (num == 12) { 
            System.out.println("Se juega REVERSA");
            direccion *= -1;
        }

        else if (num == 13) { 
            cambiarColor(carta, actual.esHumano());
        }

        else if (num == 14) { 
            System.out.println("Se juega ROBA 4");
            
            Player objetivo = obtenerSiguienteJugador();


            for (int i = 0; i < 4; i++) {
                objetivo.getMano().agregarCarta(deck.robaCarta());
            }

            System.out.println(objetivo.getNombre() + " roba 4 y pierde turno");

            cambiarColor(carta, actual.esHumano());

            siguienteTurno(); 
        }
    }

    private void cambiarColor(Card carta, boolean esJugador) {

        String nuevoColor;

        if (esJugador) {
            System.out.println("Elige color:");
            nuevoColor = scanner.next();
        } else {
            String[] colores = {"rojo", "azul", "verde", "amarillo"};
            nuevoColor = colores[(int)(Math.random() * 4)];
            System.out.println("Computadora cambia a " + nuevoColor);
        }

        carta.setColor(nuevoColor);
    }
    private void verificarPenalizacionUNO(Player actual) {

        if (actual.getMano().size() == 1) {

            if (actual.esHumano()) {
                if (!jugadorDijoUNO) {
                    System.out.println(actual.getNombre() + " no dijo UNO. Roba 2.");
                    actual.getMano().agregarCarta(deck.robaCarta());
                    actual.getMano().agregarCarta(deck.robaCarta());
                }
            } else {
                if (Math.random() < 0.7) {
                    System.out.println(actual.getNombre() + " dice UNO!");
                } else {
                    System.out.println(actual.getNombre() + " olvidó decir UNO. Roba 2.");
                    actual.getMano().agregarCarta(deck.robaCarta());
                    actual.getMano().agregarCarta(deck.robaCarta());
                }
            }
        }

        jugadorDijoUNO = false;
    }
}