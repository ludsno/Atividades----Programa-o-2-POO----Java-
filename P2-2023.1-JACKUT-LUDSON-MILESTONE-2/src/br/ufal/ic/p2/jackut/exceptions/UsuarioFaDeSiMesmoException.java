package br.ufal.ic.p2.jackut.exceptions;

public class UsuarioFaDeSiMesmoException extends RuntimeException {
    public UsuarioFaDeSiMesmoException() {
        super("Usuário não pode ser fã de si mesmo.");
    }
}
