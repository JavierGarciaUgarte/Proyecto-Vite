package juego;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

class RuleEngineTest {

    private RuleEngine reglas;
    private Deck deck;
    private TurnManager turnManager;
    private Player p1;
    private Player p2;

    @BeforeEach
    void setUp() {
        reglas = new RuleEngine();
        deck = new Deck();

        p1 = new Player("A", false);
        p2 = new Player("B", false);

        ArrayList<Player> jugadores = new ArrayList<>();
        jugadores.add(p1);
        jugadores.add(p2);

        turnManager = new TurnManager(jugadores);
    }

    @Test
    void testCartaJugable() {
        Card mesa = new Card("rojo", 5);
        Card jugada = new Card("rojo", 8);

        assertTrue(reglas.esJugable(jugada, mesa));
    }

    @Test
    void testMasDosRobaCartas() {
        int antes = p2.getMano().size();

        reglas.aplicarEfecto(new Card("rojo", 10), p1, turnManager, deck, null);

        int despues = p2.getMano().size();

        assertEquals(antes + 2, despues);
    }

    @Test
    void testReversaCambiaDireccion() {
        int dirAntes = turnManager.getDireccion();

        reglas.aplicarEfecto(new Card("rojo", 12), p1, turnManager, deck, null);

        int dirDespues = turnManager.getDireccion();

        assertEquals(dirAntes * -1, dirDespues);
    }

    @Test
    void testMasCuatroRobaCartas() {
        int antes = p2.getMano().size();

        reglas.aplicarEfecto(new Card("negro", 14), p1, turnManager, deck, null);

        int despues = p2.getMano().size();

        assertEquals(antes + 4, despues);
    }

    @Test
    void testSalto() {
        int turnoAntes = turnManager.getTurnoActual();

        reglas.aplicarEfecto(new Card("rojo", 11), p1, turnManager, deck, null);

        int turnoDespues = turnManager.getTurnoActual();

        assertNotEquals(turnoAntes, turnoDespues);
    }
}


