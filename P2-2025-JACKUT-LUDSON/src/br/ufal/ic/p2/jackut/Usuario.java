package br.ufal.ic.p2.jackut;

import java.io.Serializable;
import java.util.*;

/**
 * Representa um usuário do sistema Jackut.
 * Contém informações de perfil, amigos, convites e recados.
 */
public class Usuario implements Serializable {
    private String login; // Login do usuário
    private String senha; // Senha do usuário
    private String nome;  // Nome do usuário

    // Conjunto de amigos do usuário
    private Set<String> amigos = new LinkedHashSet<>();
    // Convites de amizade recebidos pelo usuário
    private Set<String> convitesRecebidos = new LinkedHashSet<>();
    // Atributos personalizados do perfil do usuário
    private Map<String, String> atributos = new HashMap<>();
    // Fila de recados recebidos pelo usuário
    private Queue<String> recados = new LinkedList<>();

    /**
     * Construtor da classe Usuario.
     * @param login Login do usuário.
     * @param senha Senha do usuário.
     * @param nome Nome do usuário.
     */
    public Usuario(String login, String senha, String nome) {
        this.login = login;
        this.senha = senha;
        this.nome = nome;
    }

    /**
     * Verifica se a senha fornecida é válida.
     * @param senha Senha a ser verificada.
     * @return true se a senha for válida, false caso contrário.
     */
    public boolean verificarSenha(String senha) {
        return this.senha.equals(senha);
    }

    /**
     * Obtém o valor de um atributo do perfil do usuário.
     * @param atributo Nome do atributo.
     * @return Valor do atributo.
     * @throws Exception Se o atributo não estiver preenchido.
     */
    public String getAtributo(String atributo) throws Exception {
        if ("nome".equals(atributo)) {
            return nome;
        }
        if (atributos.containsKey(atributo)) {
            return atributos.get(atributo);
        }
        throw new Exception("Atributo não preenchido.");
    }

    /**
     * Obtém o conjunto de amigos do usuário.
     * @return Conjunto de amigos.
     */
    public Set<String> getAmigos() {
        return amigos;
    }

    /**
     * Obtém o conjunto de convites recebidos pelo usuário.
     * @return Conjunto de convites recebidos.
     */
    public Set<String> getConvitesRecebidos() {
        return convitesRecebidos;
    }

    /**
     * Adiciona um convite ao conjunto de convites recebidos.
     * @param login Login do usuário que enviou o convite.
     */
    public void adicionarConvite(String login) {
        convitesRecebidos.add(login);
    }

    /**
     * Remove um convite do conjunto de convites recebidos.
     * @param login Login do usuário que enviou o convite.
     */
    public void removerConvite(String login) {
        convitesRecebidos.remove(login);
    }

    /**
     * Adiciona um amigo ao conjunto de amigos do usuário.
     * @param login Login do amigo a ser adicionado.
     */
    public void adicionarAmigo(String login) {
        amigos.add(login);
    }

    /**
     * Adiciona um recado à fila de recados do usuário.
     * @param recado Conteúdo do recado.
     */
    public void adicionarRecado(String recado) {
        recados.add(recado);
    }

    /**
     * Lê e remove o próximo recado da fila.
     * @return O recado lido.
     * @throws Exception Se não houver recados.
     */
    public String lerRecado() throws Exception {
        if (recados.isEmpty()) {
            throw new Exception("Não há recados.");
        }
        return recados.poll();
    }

    /**
     * Edita ou adiciona um atributo ao perfil do usuário.
     * @param atributo Nome do atributo.
     * @param valor Valor do atributo.
     */
    public void editarPerfil(String atributo, String valor) {
        atributos.put(atributo, valor);
    }
}
