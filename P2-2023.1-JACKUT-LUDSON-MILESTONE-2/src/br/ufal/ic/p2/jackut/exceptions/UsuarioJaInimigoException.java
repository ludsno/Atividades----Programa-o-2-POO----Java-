package br.ufal.ic.p2.jackut.exceptions;

public class UsuarioJaInimigoException extends RuntimeException {
  public UsuarioJaInimigoException() {
    super("Usu�rio j� est� adicionado como inimigo.");
  }
}
