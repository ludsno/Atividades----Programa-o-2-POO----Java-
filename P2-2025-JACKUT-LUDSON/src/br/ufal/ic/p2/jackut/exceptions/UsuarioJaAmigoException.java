package br.ufal.ic.p2.jackut.exceptions;

public class UsuarioJaAmigoException extends Exception {
    public UsuarioJaAmigoException() {
        super("Usu�rio j� est� adicionado como amigo.");
    }
}
