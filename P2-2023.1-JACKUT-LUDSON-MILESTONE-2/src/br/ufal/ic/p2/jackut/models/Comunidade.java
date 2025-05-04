package br.ufal.ic.p2.jackut.models;

import br.ufal.ic.p2.jackut.exceptions.NaoHaMensagensException;

import java.io.Serializable;
import java.util.*;

/**
 * A classe Comunidade representa uma comunidade dentro da rede social Jackut.
 *
 * <p>Uma comunidade � composta por um nome, uma descri��o, um dono (criador) e uma lista de membros.
 * Al�m disso, permite o envio e leitura de mensagens pelos membros.
 *
 * <p>Principais funcionalidades:
 * <ul>
 *   <li>Gerenciamento de membros (adi��o e remo��o).</li>
 *   <li>Envio de mensagens para todos os membros da comunidade.</li>
 *   <li>Leitura de mensagens espec�ficas para cada membro.</li>
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
     * @param descricao Descri��o da comunidade.
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
     * Obt�m o nome da comunidade.
     *
     * @return Nome da comunidade.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Obt�m a descri��o da comunidade.
     *
     * @return Descri��o da comunidade.
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Obt�m o login do dono da comunidade.
     *
     * @return Login do dono da comunidade.
     */
    public String getDono() {
        return dono;
    }

    /**
     * Obt�m o conjunto de membros da comunidade.
     *
     * @return Conjunto de logins dos membros.
     */
    public Set<String> getMembros() {
        return membros;
    }

    /**
     * Obt�m o mapa de mensagens organizadas por usu�rio.
     *
     * @return Mapa de mensagens por usu�rio.
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
     * Define uma nova descri��o para a comunidade.
     *
     * @param descricao Nova descri��o da comunidade.
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
     * Adiciona um novo membro � comunidade.
     *
     * @param login Login do usu�rio a ser adicionado.
     */
    public void adicionarMembro(String login) {
        membros.add(login);
    }

    /**
     * Remove um membro da comunidade, exceto o dono.
     *
     * @param login Login do usu�rio a ser removido.
     */
    public void removerMembro(String login) {
        if (!login.equals(dono)) {
            this.membros.remove(login);
        }
    }

    /**
     * Envia uma mensagem para todos os membros da comunidade.
     *
     * @param mensagem Conte�do da mensagem.
     */
    public void enviarMensagem(String mensagem) {
        mensagens.add(mensagem);
        for (String membro : membros) {
            mensagensPorUsuario.putIfAbsent(membro, new LinkedList<>());
            mensagensPorUsuario.get(membro).add(mensagem);
        }
    }

    /**
     * L� a pr�xima mensagem dispon�vel para um membro espec�fico.
     *
     * @param login Login do membro que deseja ler a mensagem.
     * @return Mensagem lida.
     * @throws NaoHaMensagensException Se n�o houver mensagens dispon�veis.
     */
    public String lerMensagem(String login) throws NaoHaMensagensException {
        Queue<String> mensagensUsuario = mensagensPorUsuario.get(login);
        if (mensagensUsuario == null || mensagensUsuario.isEmpty()) {
            throw new NaoHaMensagensException();
        }
        return mensagensUsuario.poll();
    }

    /**
     * Retorna uma representa��o textual da comunidade.
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
