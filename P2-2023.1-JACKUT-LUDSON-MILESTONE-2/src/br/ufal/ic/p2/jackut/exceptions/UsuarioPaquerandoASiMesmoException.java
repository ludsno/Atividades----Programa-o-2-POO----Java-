package br.ufal.ic.p2.jackut.exceptions;

public class UsuarioPaquerandoASiMesmoException extends RuntimeException {
    public UsuarioPaquerandoASiMesmoException() {
        super("Usu�rio n�o pode ser paquera de si mesmo.");
    }
}
