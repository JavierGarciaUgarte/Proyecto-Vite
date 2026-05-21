package juego;

public class Card {
    
    private String color;
    private int numero;

    public Card(String color, int numero) {
        this.color = color;
        this.numero = numero;
    }

    public String getColor() {
        return color;
    }

    public int getNumero() {
        return numero;
    }
    
    public void setColor(String color) {
        this.color = color;
    }

    
    public boolean esJugableSobre(Card otra){
        if (otra == null) return true;

        
        if (numero == 13 || numero == 14) return true;

        return this.color.equals(otra.color) || this.numero == otra.numero;
    }
    
    @Override
    public String toString() {

        // mantener formato simple tipo original
        if (numero == 10) return color + " +2";
        if (numero == 11) return color + " salto";
        if (numero == 12) return color + " reversa";
        if (numero == 13) return "comodin";
        if (numero == 14) return "roba4";

        return color + " " + numero;
    }
}