package br.ufal.ic.p2.jackut.exceptions;

public class UsuarioInimigoException extends RuntimeException {
    public UsuarioInimigoException(String message) {
        super("Fun��o inv�lida: " + message + " � seu inimigo.");
    }
}
