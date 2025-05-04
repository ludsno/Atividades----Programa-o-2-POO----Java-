package br.ufal.ic.p2.jackut.models;

import br.ufal.ic.p2.jackut.exceptions.*;

import java.io.Serializable;
import java.util.*;

/**
 * Representa um usuário do sistema Jackut.
 * Contém informações de perfil, amigos, convites e recados.
 */

/**
 * A classe Usuario representa um usuário de uma rede social, contendo informações
 * como login, senha, nome, amigos, convites recebidos, atributos personalizados
 * e recados. Ela fornece métodos para gerenciar essas informações e interagir
 * com outros usuários.
 *
 * <p>Principais funcionalidades:
 * <ul>
 *   <li>Verificar a senha do usuário.</li>
 *   <li>Gerenciar atributos personalizados do perfil.</li>
 *   <li>Adicionar e remover amigos.</li>
 *   <li>Gerenciar convites de amizade recebidos.</li>
 *   <li>Adicionar e ler recados.</li>
 * </ul>
 *
 * <p>Exceções personalizadas:
 * <ul>
 *   <li>{@link AtributoNaoPreenchidoException} - Lançada quando um atributo solicitado não está preenchido.</li>
 *   <li>{@link UsuarioJaAmigoEsperandoException} - Lançada ao tentar adicionar um convite já existente.</li>
 *   <li>{@link UsuarioJaAmigoException} - Lançada ao tentar adicionar um amigo já existente.</li>
 *   <li>{@link NaoHaRecadosException} - Lançada ao tentar ler um recado quando não há nenhum disponível.</li>
 * </ul>
 *
 * <p>Esta classe implementa a interface {@link Serializable}, permitindo que
 * objetos desta classe sejam serializados.
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

    private List<String> comunidades = new ArrayList<>();


    private List<String> idolos = new ArrayList<>();
    private List<String> paqueras = new ArrayList<>();
    private List<String> inimigos = new ArrayList<>();

    /**
     * Construtor da classe Usuario.
     *
     * @param login Login do usuário.
     * @param senha Senha do usuário.
     * @param nome  Nome do usuário.
     */
    public Usuario(String login, String senha, String nome) {
        this.login = login;
        this.senha = senha;
        this.nome = nome;
    }

    /**
     * Verifica se a senha fornecida é válida.
     *
     * @param senha Senha a ser verificada.
     * @return true se a senha for válida, false caso contrário.
     */
    public boolean verificarSenha(String senha) {
        return this.senha.equals(senha);
    }

    /**
     * Obtém o valor de um atributo do perfil do usuário.
     *
     * @param atributo Nome do atributo.
     * @return Valor do atributo.
     * @throws AtributoNaoPreenchidoException Se o atributo não estiver preenchido.
     */
    public String getAtributo(String atributo) throws Exception {
        if ("nome".equals(atributo)) {
            return nome;
        }
        if (atributos.containsKey(atributo)) {
            return atributos.get(atributo);
        }
        throw new AtributoNaoPreenchidoException();
    }

    /**
     * Obtém o conjunto de amigos do usuário.
     *
     * @return Conjunto de amigos.
     */
    public Set<String> getAmigos() {
        return amigos;
    }

    /**
     * Obtém o conjunto de convites recebidos pelo usuário.
     *
     * @return Conjunto de convites recebidos.
     */
    public Set<String> getConvitesRecebidos() {
        return convitesRecebidos;
    }

    public List<String> getComunidades() {
        return comunidades;
    }

    /**
     * Adiciona um convite ao conjunto de convites recebidos.
     *
     * @param login Login do usuário que enviou o convite.
     * @throws UsuarioJaAmigoEsperandoException Se o convite já foi enviado anteriormente.
     */
    public void adicionarConvite(String login) throws Exception {
        if (convitesRecebidos.contains(login)) {
            throw new UsuarioJaAmigoEsperandoException();
        }
        convitesRecebidos.add(login);
    }

    /**
     * Remove um convite do conjunto de convites recebidos.
     *
     * @param login Login do usuário que enviou o convite.
     */
    public void removerConvite(String login) {
        convitesRecebidos.remove(login);
    }

    /**
     * Adiciona um amigo ao conjunto de amigos do usuário.
     *
     * @param login Login do amigo a ser adicionado.
     * @throws UsuarioJaAmigoException Se o usuário já for amigo.
     */
    public void adicionarAmigo(String login) throws UsuarioJaAmigoException {
        if (amigos.contains(login)) {
            throw new UsuarioJaAmigoException();
        }
        amigos.add(login);
    }

    /**
     * Adiciona um recado à fila de recados do usuário.
     *
     * @param recado Conteúdo do recado.
     */
    public void adicionarRecado(String recado) {
        recados.add(recado);
    }

    public void adicionarComunidade(String nomeComunidade) {
        if (!comunidades.contains(nomeComunidade)) {
            comunidades.add(nomeComunidade);
        }
    }

    /**
     * Lê e remove o próximo recado da fila.
     *
     * @return O recado lido.
     * @throws NaoHaRecadosException Se não houver recados.
     */
    public String lerRecado() throws Exception {
        if (recados.isEmpty()) {
            throw new NaoHaRecadosException();
        }
//        return recados.poll();
        String recado = recados.poll();
        return recado.contains(":") ? recado.substring(recado.indexOf(":") + 1) : recado;
    }

    public Queue<String> getRecados() {
        return recados;
    }

    /**
     * Edita ou adiciona um atributo ao perfil do usuário.
     *
     * @param atributo Nome do atributo.
     * @param valor    Valor do atributo.
     */
    public void editarPerfil(String atributo, String valor) {
        atributos.put(atributo, valor);
    }

    public List<String> getIdolos() {
        return idolos;
    }

    public List<String> getPaqueras() {
        return paqueras;
    }

    public List<String> getInimigos() {
        return inimigos;
    }

    public void adicionarIdolo(String idolo) {
        idolos.add(idolo);
    }

    public void adicionarPaquera(String paquera) {
        paqueras.add(paquera);
    }

    public void adicionarInimigo(String inimigo) {
        inimigos.add(inimigo);
    }


    public String getLogin() {
        return login;
    }

    public String getNome() {
        return nome;
    }

}
