package br.ufal.ic.p2.jackut.exceptions;

public class UsuarioJaAmigoEsperandoException extends Exception {
    public UsuarioJaAmigoEsperandoException() {
        super("Usu�rio j� est� adicionado como amigo, esperando aceita��o do convite.");
    }
}
