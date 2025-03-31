package br.ufal.ic.p2.jackut.exceptions;

public class UsuarioJaAmigoEsperandoException extends Exception {
    public UsuarioJaAmigoEsperandoException() {
        super("Usuário já está adicionado como amigo, esperando aceitação do convite.");
    }
}
