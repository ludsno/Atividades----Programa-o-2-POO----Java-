package br.ufal.ic.p2.jackut.exceptions;

public class UsuarioJaIdoloException extends RuntimeException {
    public UsuarioJaIdoloException() {
        super("Usu�rio j� est� adicionado como �dolo.");
    }
}
