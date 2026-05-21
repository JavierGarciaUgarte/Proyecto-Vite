package juego;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class TurnManagerTest {

    private ArrayList<Player> crearJugadores() {
        ArrayList<Player> jugadores = new ArrayList<>();
        jugadores.add(new Player("A", false));
        jugadores.add(new Player("B", false));
        jugadores.add(new Player("C", false));
        return jugadores;
    }

    @Test
    void testSiguienteTurno() {
        TurnManager tm = new TurnManager(crearJugadores());

        tm.siguienteTurno();

        assertEquals(1, tm.getTurnoActual());
    }

    @Test
    void testCicloTurnos() {
        TurnManager tm = new TurnManager(crearJugadores());

        tm.siguienteTurno(); // 1
        tm.siguienteTurno(); // 2
        tm.siguienteTurno(); // vuelve a 0

        assertEquals(0, tm.getTurnoActual());
    }

    @Test
    void testReversaDireccion() {
        TurnManager tm = new TurnManager(crearJugadores());

        tm.invertirDireccion();

        assertEquals(-1, tm.getDireccion());
    }

    @Test
    void testTurnoConReversa() {
        TurnManager tm = new TurnManager(crearJugadores());

        tm.invertirDireccion();
        tm.siguienteTurno();

        assertEquals(2, tm.getTurnoActual()); // va hacia atrás
    }

    @Test
    void testSiguienteJugador() {
        TurnManager tm = new TurnManager(crearJugadores());

        Player siguiente = tm.getSiguienteJugador();

        assertEquals("B", siguiente.getNombre());
    }
}
