package br.ufal.ic.p2.jackut.exceptions;

public class UsuarioFaDeSiMesmoException extends RuntimeException {
    public UsuarioFaDeSiMesmoException() {
        super("Usu�rio n�o pode ser f� de si mesmo.");
    }
}
