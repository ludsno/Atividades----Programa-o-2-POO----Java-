package br.ufal.ic.p2.jackut.exceptions;

public class UsuarioJaPaqueraException extends RuntimeException {
    public UsuarioJaPaqueraException() {
        super("Usu�rio j� est� adicionado como paquera.");
    }
}
