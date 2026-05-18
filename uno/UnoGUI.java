package com.mycompany.uno;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class UnoGUI extends JFrame {

    private Game game = new Game();
    private CardImageLoader imageLoader = new CardImageLoader();

    private JPanel nortePanel = new JPanel();
    private JPanel estePanel = new JPanel();
    private JPanel surPanel = new JPanel();
    private JPanel oestePanel = new JPanel();

    private JPanel centroPanel = new JPanel();
    private JPanel mesaPanel = new JPanel();

    private JTextArea logArea = new JTextArea(8, 30);

    private JLabel cartaCentral = new JLabel("Carta Central");
    private JLabel turnoLabel = new JLabel("Turno:");

    private JButton robarBtn = new JButton("Robar");
    private JButton unoBtn = new JButton("UNO");
    private JButton pasarBtn = new JButton("Pasar");

    public UnoGUI() {

        game.iniciarGUI();

        setTitle("UNO - GUI");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        configurarPaneles();
        construirCentro();

        add(nortePanel, BorderLayout.NORTH);
        add(estePanel, BorderLayout.EAST);
        add(surPanel, BorderLayout.SOUTH);
        add(oestePanel, BorderLayout.WEST);
        add(centroPanel, BorderLayout.CENTER);

        actualizarTodo();

        setVisible(true);
    }

    private void configurarPaneles() {

        nortePanel.setLayout(new FlowLayout());
        surPanel.setLayout(new FlowLayout());

        estePanel.setLayout(new BoxLayout(estePanel, BoxLayout.Y_AXIS));
        oestePanel.setLayout(new BoxLayout(oestePanel, BoxLayout.Y_AXIS));

        nortePanel.setBorder(BorderFactory.createTitledBorder("Pepe"));
        estePanel.setBorder(BorderFactory.createTitledBorder("Toño"));
        oestePanel.setBorder(BorderFactory.createTitledBorder("Maria"));
        surPanel.setBorder(BorderFactory.createTitledBorder("Tú"));

        centroPanel.setLayout(new BorderLayout());

        mesaPanel.setLayout(new FlowLayout());
        mesaPanel.setBorder(BorderFactory.createTitledBorder("Mesa"));

        logArea.setEditable(false);
    }

    private void construirCentro() {

        JPanel controles = new JPanel();

        controles.add(robarBtn);
        controles.add(unoBtn);
        controles.add(pasarBtn);

        JPanel infoPanel = new JPanel(new GridLayout(2, 1));

        infoPanel.add(turnoLabel);
        infoPanel.add(cartaCentral);

        JScrollPane scroll = new JScrollPane(logArea);

        centroPanel.add(infoPanel, BorderLayout.NORTH);
        centroPanel.add(mesaPanel, BorderLayout.CENTER);
        centroPanel.add(controles, BorderLayout.SOUTH);
        centroPanel.add(scroll, BorderLayout.EAST);

        configurarEventos();
    }

    private void configurarEventos() {

        robarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (!game.esTurnoHumano()) {
                    logArea.append("No es tu turno.\n");
                    return;
                }

                game.robarCartaJugadorActual();

                logArea.append(
                        game.getJugadorActual().getNombre()
                        + " roba una carta\n"
                );

                game.siguienteTurno();

                jugarTurnosIA();
            }
        });

        unoBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                game.decirUNO();

                logArea.append("Jugador gritó UNO!\n");
            }
        });

        pasarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (!game.esTurnoHumano()) {
                    logArea.append("No es tu turno.\n");
                    return;
                }

                logArea.append(
                        game.getJugadorActual().getNombre()
                        + " pasa turno\n"
                );

                game.verificarPenalizacionJugadorActual();

                game.siguienteTurno();

                jugarTurnosIA();
            }
        });
    }

    private void jugarTurnosIA() {

        while (!game.esTurnoHumano()) {

            String resultado = game.jugarTurnoComputadoraActual();

            logArea.append(resultado + "\n");

            if (game.hayGanador()) {

                JOptionPane.showMessageDialog(
                        UnoGUI.this,
                        game.getNombreGanador() + " gana!"
                );

                actualizarTodo();
                return;
            }

            game.verificarPenalizacionJugadorActual();

            if (game.getUltimaCartaJugada() != null) {
                game.finalizarTurnoDespuesDeJugada();
            } else {
                game.siguienteTurno();
            }
        }

        actualizarTodo();
    }

    private void actualizarTodo() {

        actualizarJugadores();
        actualizarMesa();
        actualizarTurno();

        revalidate();
        repaint();
    }

    private void actualizarJugadores() {

        nortePanel.removeAll();
        surPanel.removeAll();
        estePanel.removeAll();
        oestePanel.removeAll();

        ArrayList<Player> jugadores = game.getJugadores();

        Player jugadorHumano = jugadores.get(0);
        Player jugadorNorte = jugadores.get(1);
        Player jugadorEste = jugadores.get(2);
        Player jugadorOeste = jugadores.get(3);

        agregarCartasOcultas(nortePanel, jugadorNorte);
        agregarCartasOcultas(estePanel, jugadorEste);
        agregarCartasOcultas(oestePanel, jugadorOeste);

        agregarCartasJugador(surPanel, jugadorHumano);
    }

    private void agregarCartasOcultas(JPanel panel, Player jugador) {

        for (int i = 0; i < jugador.getMano().size(); i++) {
            panel.add(crearCartaOculta());
        }
    }

    private void agregarCartasJugador(JPanel panel, Player jugador) {

        for (int i = 0; i < jugador.getMano().size(); i++) {

            Card carta = jugador.getMano().verCarta(i);

            JButton botonCarta = crearCartaVisible(carta, i);

            panel.add(botonCarta);
        }
    }

    private void actualizarMesa() {

        mesaPanel.removeAll();

        Card cartaMesa = game.getCartaMesa();

        JLabel carta = new JLabel(imageLoader.obtenerIcono(cartaMesa));

        carta.setPreferredSize(new Dimension(140, 200));
        carta.setHorizontalAlignment(SwingConstants.CENTER);
        carta.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));

        mesaPanel.add(carta);

        cartaCentral.setText("Carta en mesa: " + cartaMesa);
    }

    private void actualizarTurno() {

        turnoLabel.setText(
                "Turno actual: "
                + game.getJugadorActual().getNombre()
        );
    }

    private JButton crearCartaVisible(Card carta, int indice) {

        JButton boton = new JButton(imageLoader.obtenerIcono(carta));
        boton.setText("");

        boton.setPreferredSize(new Dimension(90, 130));

        boton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Player actual = game.getJugadorActual();

                if (!actual.esHumano()) {
                    logArea.append("No es tu turno.\n");
                    return;
                }

                String colorElegido = null;

                if (carta.getNumero() == 13 || carta.getNumero() == 14) {
                    colorElegido = elegirColor();
                }

                boolean jugadaValida =
                        game.jugarCartaJugadorActual(
                                indice,
                                colorElegido
                        );

                if (jugadaValida) {

                    logArea.append(
                            actual.getNombre()
                            + " juega: "
                            + carta
                            + "\n"
                    );

                    game.verificarPenalizacionJugadorActual();

                    if (game.hayGanador()) {

                        JOptionPane.showMessageDialog(
                                UnoGUI.this,
                                game.getNombreGanador()
                                + " gana!"
                        );

                        actualizarTodo();
                        return;
                    }

                    game.finalizarTurnoDespuesDeJugada();

                    jugarTurnosIA();

                } else {

                    logArea.append(
                            "Carta no válida: "
                            + carta
                            + "\n"
                    );
                }

                actualizarTodo();
            }
        });

        return boton;
    }

    private JLabel crearCartaOculta() {

        JLabel carta = new JLabel(imageLoader.obtenerReverso());

        carta.setHorizontalAlignment(SwingConstants.CENTER);
        carta.setPreferredSize(new Dimension(70, 100));
        carta.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        return carta;
    }

    private String elegirColor() {

        String[] colores = {
            "rojo",
            "azul",
            "verde",
            "amarillo"
        };

        String color = (String) JOptionPane.showInputDialog(
                this,
                "Elige un color:",
                "Cambio de color",
                JOptionPane.QUESTION_MESSAGE,
                null,
                colores,
                colores[0]
        );

        if (color == null) {
            return "rojo";
        }

        return color;
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new UnoGUI();
            }
        });
    }
}