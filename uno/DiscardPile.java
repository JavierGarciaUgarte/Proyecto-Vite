/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.uno;

/**
 *
 * @author PC
 */
public class DiscardPile {
     private Card cartaActual;

	    public void ponerCarta(Card carta) {
	        this.cartaActual = carta;
	    }

	    public Card getCartaActual() {
	        return cartaActual;
	    }

	    public boolean esCartaInicialValida(Card carta) {
	        return carta.getNumero() != 13 && carta.getNumero() != 14;
	    }
	}

