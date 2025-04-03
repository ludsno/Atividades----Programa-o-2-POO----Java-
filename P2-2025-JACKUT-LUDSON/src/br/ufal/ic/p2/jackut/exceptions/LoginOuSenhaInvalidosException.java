package br.ufal.ic.p2.jackut.exceptions;

public class LoginOuSenhaInvalidosException extends Exception {
    public LoginOuSenhaInvalidosException() {
        super("Login ou senha inválidos.");
    }
}
