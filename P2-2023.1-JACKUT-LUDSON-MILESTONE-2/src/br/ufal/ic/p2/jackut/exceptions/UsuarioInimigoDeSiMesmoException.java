package br.ufal.ic.p2.jackut.exceptions;

public class UsuarioInimigoDeSiMesmoException extends RuntimeException {
    public UsuarioInimigoDeSiMesmoException() {
        super("Usu�rio n�o pode ser inimigo de si mesmo.");
    }
}
