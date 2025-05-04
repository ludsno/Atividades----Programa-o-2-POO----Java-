package br.ufal.ic.p2.jackut.exceptions;

public class UsuarioJaPaqueraException extends RuntimeException {
    public UsuarioJaPaqueraException() {
        super("Usuário já está adicionado como paquera.");
    }
}
