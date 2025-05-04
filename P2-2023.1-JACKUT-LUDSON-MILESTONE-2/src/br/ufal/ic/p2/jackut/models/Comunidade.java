package br.ufal.ic.p2.jackut.models;

import br.ufal.ic.p2.jackut.exceptions.NaoHaMensagensException;

import java.io.Serializable;
import java.util.*;

/**
 * A classe Comunidade representa uma comunidade dentro da rede social Jackut.
 *
 * <p>Uma comunidade é composta por um nome, uma descrição, um dono (criador) e uma lista de membros.
 * Além disso, permite o envio e leitura de mensagens pelos membros.
 *
 * <p>Principais funcionalidades:
 * <ul>
 *   <li>Gerenciamento de membros (adição e remoção).</li>
 *   <li>Envio de mensagens para todos os membros da comunidade.</li>
 *   <li>Leitura de mensagens específicas para cada membro.</li>
 * </ul>
 *
 * <p>Esta classe implementa a interface {@link Serializable}, permitindo que objetos desta classe sejam serializados.
 */
public class Comunidade implements Serializable {
    private String nome;
    private String descricao;
    private String dono;
    private Set<String> membros;
    private Queue<String> mensagens = new LinkedList<>();
    private Map<String, Queue<String>> mensagensPorUsuario = new HashMap<>();

    /**
     * Construtor da classe Comunidade.
     *
     * @param nome Nome da comunidade.
     * @param descricao Descrição da comunidade.
     * @param dono Login do dono da comunidade.
     */
    public Comunidade(String nome, String descricao, String dono) {
        this.nome = nome;
        this.descricao = descricao;
        this.dono = dono;
        this.membros = new LinkedHashSet<>();
        this.membros.add(dono);
    }

    /**
     * Obtém o nome da comunidade.
     *
     * @return Nome da comunidade.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Obtém a descrição da comunidade.
     *
     * @return Descrição da comunidade.
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Obtém o login do dono da comunidade.
     *
     * @return Login do dono da comunidade.
     */
    public String getDono() {
        return dono;
    }

    /**
     * Obtém o conjunto de membros da comunidade.
     *
     * @return Conjunto de logins dos membros.
     */
    public Set<String> getMembros() {
        return membros;
    }

    /**
     * Obtém o mapa de mensagens organizadas por usuário.
     *
     * @return Mapa de mensagens por usuário.
     */
    public Map<String, Queue<String>> getMensagensPorUsuario() {
        return mensagensPorUsuario;
    }

    /**
     * Define um novo nome para a comunidade.
     *
     * @param nome Novo nome da comunidade.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Define uma nova descrição para a comunidade.
     *
     * @param descricao Nova descrição da comunidade.
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Define um novo dono para a comunidade.
     *
     * @param dono Login do novo dono da comunidade.
     */
    public void setDono(String dono) {
        this.dono = dono;
    }

    /**
     * Adiciona um novo membro à comunidade.
     *
     * @param login Login do usuário a ser adicionado.
     */
    public void adicionarMembro(String login) {
        membros.add(login);
    }

    /**
     * Remove um membro da comunidade, exceto o dono.
     *
     * @param login Login do usuário a ser removido.
     */
    public void removerMembro(String login) {
        if (!login.equals(dono)) {
            this.membros.remove(login);
        }
    }

    /**
     * Envia uma mensagem para todos os membros da comunidade.
     *
     * @param mensagem Conteúdo da mensagem.
     */
    public void enviarMensagem(String mensagem) {
        mensagens.add(mensagem);
        for (String membro : membros) {
            mensagensPorUsuario.putIfAbsent(membro, new LinkedList<>());
            mensagensPorUsuario.get(membro).add(mensagem);
        }
    }

    /**
     * Lê a próxima mensagem disponível para um membro específico.
     *
     * @param login Login do membro que deseja ler a mensagem.
     * @return Mensagem lida.
     * @throws NaoHaMensagensException Se não houver mensagens disponíveis.
     */
    public String lerMensagem(String login) throws NaoHaMensagensException {
        Queue<String> mensagensUsuario = mensagensPorUsuario.get(login);
        if (mensagensUsuario == null || mensagensUsuario.isEmpty()) {
            throw new NaoHaMensagensException();
        }
        return mensagensUsuario.poll();
    }

    /**
     * Retorna uma representação textual da comunidade.
     *
     * @return String representando a comunidade.
     */
    @Override
    public String toString() {
        return "Comunidade{" +
                "nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", dono='" + dono + '\'' +
                ", membros=" + membros +
                '}';
    }

}
