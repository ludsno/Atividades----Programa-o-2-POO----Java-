package br.ufal.ic.p2.jackut.exceptions;

public class UsuarioEnviandoRecadoASiMesmoException extends Exception {
    public UsuarioEnviandoRecadoASiMesmoException() {
        super("Usu�rio n�o pode enviar recado para si mesmo.");
    }
}
