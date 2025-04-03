package br.ufal.ic.p2.jackut.models;

import br.ufal.ic.p2.jackut.exceptions.*;
import java.io.Serializable;
import java.util.*;

/**
 * Representa um usu�rio do sistema Jackut.
 * Cont�m informa��es de perfil, amigos, convites e recados.
 */
/**
 * A classe Usuario representa um usu�rio de uma rede social, contendo informa��es
 * como login, senha, nome, amigos, convites recebidos, atributos personalizados
 * e recados. Ela fornece m�todos para gerenciar essas informa��es e interagir
 * com outros usu�rios.
 *
 * <p>Principais funcionalidades:
 * <ul>
 *   <li>Verificar a senha do usu�rio.</li>
 *   <li>Gerenciar atributos personalizados do perfil.</li>
 *   <li>Adicionar e remover amigos.</li>
 *   <li>Gerenciar convites de amizade recebidos.</li>
 *   <li>Adicionar e ler recados.</li>
 * </ul>
 *
 * <p>Exce��es personalizadas:
 * <ul>
 *   <li>{@link AtributoNaoPreenchidoException} - Lan�ada quando um atributo solicitado n�o est� preenchido.</li>
 *   <li>{@link UsuarioJaAmigoEsperandoException} - Lan�ada ao tentar adicionar um convite j� existente.</li>
 *   <li>{@link UsuarioJaAmigoException} - Lan�ada ao tentar adicionar um amigo j� existente.</li>
 *   <li>{@link NaoHaRecadosException} - Lan�ada ao tentar ler um recado quando n�o h� nenhum dispon�vel.</li>
 * </ul>
 *
 * <p>Esta classe implementa a interface {@link Serializable}, permitindo que
 * objetos desta classe sejam serializados.
 */
public class Usuario implements Serializable {
    private String login; // Login do usu�rio
    private String senha; // Senha do usu�rio
    private String nome;  // Nome do usu�rio

    // Conjunto de amigos do usu�rio
    private Set<String> amigos = new LinkedHashSet<>();
    // Convites de amizade recebidos pelo usu�rio
    private Set<String> convitesRecebidos = new LinkedHashSet<>();
    // Atributos personalizados do perfil do usu�rio
    private Map<String, String> atributos = new HashMap<>();
    // Fila de recados recebidos pelo usu�rio
    private Queue<String> recados = new LinkedList<>();

    /**
     * Construtor da classe Usuario.
     * @param login Login do usu�rio.
     * @param senha Senha do usu�rio.
     * @param nome Nome do usu�rio.
     */
    public Usuario(String login, String senha, String nome) {
        this.login = login;
        this.senha = senha;
        this.nome = nome;
    }

    /**
     * Verifica se a senha fornecida � v�lida.
     * @param senha Senha a ser verificada.
     * @return true se a senha for v�lida, false caso contr�rio.
     */
    public boolean verificarSenha(String senha) {
        return this.senha.equals(senha);
    }

    /**
     * Obt�m o valor de um atributo do perfil do usu�rio.
     * @param atributo Nome do atributo.
     * @return Valor do atributo.
     * @throws AtributoNaoPreenchidoException Se o atributo n�o estiver preenchido.
     */
    public String getAtributo(String atributo) throws AtributoNaoPreenchidoException {
        if ("nome".equals(atributo)) {
            return nome;
        }
        if (atributos.containsKey(atributo)) {
            return atributos.get(atributo);
        }
        throw new AtributoNaoPreenchidoException();
    }

    /**
     * Obt�m o conjunto de amigos do usu�rio.
     * @return Conjunto de amigos.
     */
    public Set<String> getAmigos() {
        return amigos;
    }

    /**
     * Obt�m o conjunto de convites recebidos pelo usu�rio.
     * @return Conjunto de convites recebidos.
     */
    public Set<String> getConvitesRecebidos() {
        return convitesRecebidos;
    }

    /**
     * Adiciona um convite ao conjunto de convites recebidos.
     * @param login Login do usu�rio que enviou o convite.
     * @throws UsuarioJaAmigoEsperandoException Se o convite j� foi enviado anteriormente.
     */
    public void adicionarConvite(String login) throws UsuarioJaAmigoEsperandoException {
        if (convitesRecebidos.contains(login)) {
            throw new UsuarioJaAmigoEsperandoException();
        }
        convitesRecebidos.add(login);
    }

    /**
     * Remove um convite do conjunto de convites recebidos.
     * @param login Login do usu�rio que enviou o convite.
     */
    public void removerConvite(String login) {
        convitesRecebidos.remove(login);
    }

    /**
     * Adiciona um amigo ao conjunto de amigos do usu�rio.
     * @param login Login do amigo a ser adicionado.
     * @throws UsuarioJaAmigoException Se o usu�rio j� for amigo.
     */
    public void adicionarAmigo(String login) throws UsuarioJaAmigoException {
        if (amigos.contains(login)) {
            throw new UsuarioJaAmigoException();
        }
        amigos.add(login);
    }

    /**
     * Adiciona um recado � fila de recados do usu�rio.
     * @param recado Conte�do do recado.
     */
    public void adicionarRecado(String recado) {
        recados.add(recado);
    }

    /**
     * L� e remove o pr�ximo recado da fila.
     * @return O recado lido.
     * @throws NaoHaRecadosException Se n�o houver recados.
     */
    public String lerRecado() throws NaoHaRecadosException {
        if (recados.isEmpty()) {
            throw new NaoHaRecadosException();
        }
        return recados.poll();
    }

    /**
     * Edita ou adiciona um atributo ao perfil do usu�rio.
     * @param atributo Nome do atributo.
     * @param valor Valor do atributo.
     */
    public void editarPerfil(String atributo, String valor) {
        atributos.put(atributo, valor);
    }
}
