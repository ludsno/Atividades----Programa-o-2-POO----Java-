package br.ufal.ic.p2.jackut.exceptions;

public class UsuarioEnviandoRecadoASiMesmoException extends Exception {
    public UsuarioEnviandoRecadoASiMesmoException() {
        super("Usuário não pode enviar recado para si mesmo.");
    }
}
