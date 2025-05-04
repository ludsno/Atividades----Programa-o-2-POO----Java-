package br.ufal.ic.p2.jackut.exceptions;

public class UsuarioInimigoException extends RuntimeException {
    public UsuarioInimigoException(String message) {
        super("Função inválida: " + message + " é seu inimigo.");
    }
}
