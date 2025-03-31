package br.ufal.ic.p2.jackut.exceptions;

public class UsuarioAdicionandoASiMesmoException extends Exception {
    public UsuarioAdicionandoASiMesmoException() {
        super("Usuário não pode adicionar a si mesmo como amigo.");
    }
}
