package statistics;

public class Stat {
    private String type;
    private String player;
    private int valor;

    public Stat(String type, String player, int valor) {
        this.type = type;
        this.player = player;
        this.valor = valor;
    }

    public String getType() {
        return type;
    }

    public String getPlayer() {
        return player;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public void incrementScore(int increment) {
        this.valor += increment;
    }

    @Override
    public String toString() {
        return "Statistics: " + type + ": " + valor;
    }
}
