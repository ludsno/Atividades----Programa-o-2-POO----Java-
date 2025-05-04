package br.ufal.ic.p2.jackut.exceptions;

public class UsuarioJaInimigoException extends RuntimeException {
  public UsuarioJaInimigoException() {
    super("Usuário já está adicionado como inimigo.");
  }
}
