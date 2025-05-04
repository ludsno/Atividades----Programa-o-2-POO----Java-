package br.ufal.ic.p2.jackut.exceptions;

public class UsuarioInimigoDeSiMesmoException extends RuntimeException {
    public UsuarioInimigoDeSiMesmoException() {
        super("Usuário não pode ser inimigo de si mesmo.");
    }
}
