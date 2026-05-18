/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.uno;
import java.util.ArrayList;

/**
 *
 * @author PC
 */
public class TurnManager {

    private ArrayList<Player> jugadores;
    private int turnoActual;
    private int direccion;

    public TurnManager(ArrayList<Player> jugadores) {
        this.jugadores = jugadores;
        this.turnoActual = 0;
        this.direccion = 1;
    }

    public Player getJugadorActual() {
        return jugadores.get(turnoActual);
    }

    public void siguienteTurno() {
        turnoActual += direccion;

        if (turnoActual >= jugadores.size()) turnoActual = 0;
        if (turnoActual < 0) turnoActual = jugadores.size() - 1;
    }

    public Player getSiguienteJugador() {
        int siguiente = turnoActual + direccion;

        if (siguiente >= jugadores.size()) siguiente = 0;
        if (siguiente < 0) siguiente = jugadores.size() - 1;

        return jugadores.get(siguiente);
    }

    public void invertirDireccion() {
        direccion *= -1;
    }

    public int getTurnoActual() {
        return turnoActual;
    }

    public int getDireccion() {
        return direccion;
    }    
}
