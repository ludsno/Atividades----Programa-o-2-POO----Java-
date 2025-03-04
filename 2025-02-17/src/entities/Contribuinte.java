package entities;

public abstract class Contribuinte {
    private String nome;
    private double rendaAnual;

    public Contribuinte (String nome, double rendaAnual) {
        this.nome = nome;
        this.rendaAnual = rendaAnual;
    }

    public String getNome() {
        return nome;
    }

    public double getRendaAnual() {
        return rendaAnual;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setRendaAnual(double rendaAnual) {
        this.rendaAnual = rendaAnual;
    }

    @Override
    public String toString() {
        return nome + ": " + String.format("%.2f", rendaAnual);
    }

    public abstract double calcularImposto();
}
