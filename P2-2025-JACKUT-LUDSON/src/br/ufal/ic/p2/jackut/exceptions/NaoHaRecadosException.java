package br.ufal.ic.p2.jackut.exceptions;

public class NaoHaRecadosException extends Exception {
    public NaoHaRecadosException() {
        super("Não há recados.");
    }
}
