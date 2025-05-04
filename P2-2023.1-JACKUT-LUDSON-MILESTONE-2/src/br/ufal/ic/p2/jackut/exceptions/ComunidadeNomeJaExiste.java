package br.ufal.ic.p2.jackut.exceptions;

public class ComunidadeNomeJaExiste extends RuntimeException {
    public ComunidadeNomeJaExiste() {
        super("Comunidade com esse nome já existe.");
    }
}
