package br.ufal.ic.p2.jackut.models;

import br.ufal.ic.p2.jackut.exceptions.*;
import java.io.*;
import java.util.*;

/**
 * Classe principal do sistema Jackut.
 * Gerencia usu�rios, sess�es, amigos, recados e persist�ncia de dados.
 */
/**
 * Classe que representa o sistema principal de gerenciamento de usu�rios, sess�es,
 * amizades e recados. Implementa a interface Serializable para persist�ncia de dados.
 * 
 * <p>Principais funcionalidades:
 * <ul>
 *   <li>Gerenciamento de usu�rios (cria��o, edi��o e recupera��o de atributos).</li>
 *   <li>Gerenciamento de sess�es de usu�rios autenticados.</li>
 *   <li>Gerenciamento de amizades entre usu�rios.</li>
 *   <li>Envio e leitura de recados entre usu�rios.</li>
 *   <li>Persist�ncia de dados em arquivo.</li>
 * </ul>
 * 
 * <p>Exce��es espec�ficas s�o lan�adas para tratar erros como login inv�lido, 
 * usu�rio n�o cadastrado, tentativa de adicionar a si mesmo como amigo, entre outros.
 * 
 * <p>O sistema utiliza um mapa de usu�rios persistente e um mapa de sess�es transit�rio.
 * 
 * <p>Principais m�todos:
 * <ul>
 *   <li>{@link #criarUsuario(String, String, String)}: Cria um novo usu�rio no sistema.</li>
 *   <li>{@link #abrirSessao(String, String)}: Abre uma sess�o para um usu�rio autenticado.</li>
 *   <li>{@link #getAtributoUsuario(String, String)}: Recupera um atributo espec�fico de um usu�rio.</li>
 *   <li>{@link #editarPerfil(String, String, String)}: Edita o perfil de um usu�rio.</li>
 *   <li>{@link #getAmigos(String)}: Retorna os amigos de um usu�rio.</li>
 *   <li>{@link #ehAmigo(String, String)}: Verifica se dois usu�rios s�o amigos.</li>
 *   <li>{@link #adicionarAmigo(String, String)}: Adiciona um amigo ao usu�rio.</li>
 *   <li>{@link #enviarRecado(String, String, String)}: Envia um recado de um usu�rio para outro.</li>
 *   <li>{@link #lerRecado(String)}: L� o recado de um usu�rio.</li>
 *   <li>{@link #encerrarSistema()}: Salva os dados e encerra o sistema.</li>
 *   <li>{@link #zerarSistema()}: Reseta o sistema, apagando todos os dados e sess�es.</li>
 * </ul>
 * 
 * <p>O sistema tamb�m carrega automaticamente os dados persistidos ao ser inicializado.
 * 
 *
 */
public class Sistema implements Serializable {
    private static final long serialVersionUID = 1L;

    // Mapa de usu�rios cadastrados no sistema
    private Map<String, Usuario> usuarios = new HashMap<>();
    // Mapa de sess�es ativas (n�o persistente)
    private transient Map<String, String> sessoes = new HashMap<>();
    // Nome do arquivo de persist�ncia
    private static final String ARQUIVO = "dados.dat";

    /**
     * Construtor da classe Sistema.
     * Carrega os dados persistidos, se existirem.
     */
    public Sistema() {
        carregarDados();
    }

    /**
     * Cria um novo usu�rio no sistema.
     * @param login Login do usu�rio.
     * @param senha Senha do usu�rio.
     * @param nome Nome do usu�rio.
     * @throws LoginInvalidoException Se o login for inv�lido.
     * @throws SenhaInvalidaException Se a senha for inv�lida.
     * @throws UsuarioNaoCadastradoException Se o login j� existir.
     */
    public void criarUsuario(String login, String senha, String nome) throws LoginInvalidoException, SenhaInvalidaException, UsuarioNaoCadastradoException {
        if (login == null || login.trim().isEmpty())
            throw new LoginInvalidoException();
        if (senha == null || senha.trim().isEmpty())
            throw new SenhaInvalidaException();
        if (usuarios.containsKey(login))
            throw new UsuarioNaoCadastradoException();
        usuarios.put(login, new Usuario(login, senha, nome));
    }

    /**
     * Abre uma sess�o para um usu�rio autenticado.
     * @param login Login do usu�rio.
     * @param senha Senha do usu�rio.
     * @return ID da sess�o criada.
     * @throws LoginOuSenhaInvalidosException Se o login ou senha forem inv�lidos.
     */
    public String abrirSessao(String login, String senha) throws LoginOuSenhaInvalidosException {
        if (!usuarios.containsKey(login)) {
            throw new LoginOuSenhaInvalidosException();
        }
        Usuario usuario = usuarios.get(login);
        if (!usuario.verificarSenha(senha)) {
            throw new LoginOuSenhaInvalidosException();
        }
        String idSessao = login + System.currentTimeMillis();
        sessoes.put(idSessao, login);
        return idSessao;
    }

    /**
     * Recupera um atributo espec�fico de um usu�rio com base no login.
     *
     * @param login O identificador de login do usu�rio.
     * @param atributo O nome do atributo a ser recuperado.
     * @return O valor do atributo especificado para o usu�rio.
     * @throws Exception Se o usu�rio n�o estiver cadastrado ou o atributo n�o puder ser recuperado.
     */
    public String getAtributoUsuario(String login, String atributo) throws Exception {
        Usuario usuario = usuarios.get(login);
        if (usuario == null)
            throw new Exception("Usu�rio n�o cadastrado.");
        return usuario.getAtributo(atributo);
    }

    /**
     * Edita o perfil de um usu�rio com base no atributo e valor fornecidos.
     *
     * @param id O identificador da sess�o do usu�rio.
     * @param atributo O atributo do perfil que ser� editado.
     * @param valor O novo valor para o atributo.
     * @throws Exception Se o usu�rio n�o estiver cadastrado ou a sess�o n�o for v�lida.
     */
    public void editarPerfil (String id, String atributo, String valor) throws Exception {
        if (!sessoes.containsKey(id)) {
            throw new Exception("Usu�rio n�o cadastrado.");
        }
        String login = sessoes.get(id);
        Usuario usuario = usuarios.get(login);
        if (usuario == null) {
            throw new Exception("Usu�rio n�o cadastrado.");
        }
        usuario.editarPerfil(atributo, valor);
    }

    /**
     * Retorna uma representa��o em formato de string dos amigos de um usu�rio.
     *
     * @param login O login do usu�rio cujo amigos ser�o listados.
     * @return Uma string contendo os amigos do usu�rio no formato "{amigo1,amigo2,...}".
     * @throws Exception Se o usu�rio com o login fornecido n�o estiver cadastrado.
     */
    public String getAmigos(String login) throws Exception {
        Usuario usuario = usuarios.get(login);
        if (usuario == null) {
            throw new Exception("Usu�rio n�o cadastrado.");
        }
        String amigosStr = String.join(",", usuario.getAmigos());
        return "{" + amigosStr + "}";
    }

    /**
     * Verifica se um determinado usu�rio � amigo de outro.
     *
     * @param login O login do usu�rio que ser� verificado.
     * @param amigo O login do poss�vel amigo a ser verificado.
     * @return true se o usu�rio especificado for amigo, false caso contr�rio.
     * @throws Exception Se o usu�rio com o login fornecido n�o estiver cadastrado.
     */
    public boolean ehAmigo(String login, String amigo) throws Exception {
        Usuario usuario = usuarios.get(login);
        if (usuario == null) {
            throw new Exception("Usu�rio n�o cadastrado.");
        }
        return usuario.getAmigos().contains(amigo);
    }

    /**
     * Adiciona um amigo ao usu�rio identificado pelo ID da sess�o.
     *
     * @param id O identificador da sess�o do usu�rio que est� enviando o pedido de amizade.
     * @param amigo O login do usu�rio que ser� adicionado como amigo.
     * @throws UsuarioNaoCadastradoException Se o ID da sess�o n�o est� associado a nenhum usu�rio cadastrado.
     * @throws UsuarioAdicionandoASiMesmoException Se o usu�rio tentar adicionar a si mesmo como amigo.
     * @throws UsuarioJaAmigoException Se o usu�rio alvo j� est� adicionado como amigo.
     * @throws UsuarioJaAmigoEsperandoException Se o usu�rio alvo j� recebeu um convite de amizade do remetente.
     */
    public void adicionarAmigo(String id, String amigo) throws UsuarioNaoCadastradoException, UsuarioAdicionandoASiMesmoException, UsuarioJaAmigoException, UsuarioJaAmigoEsperandoException {
        if (!sessoes.containsKey(id)) {
            throw new UsuarioNaoCadastradoException();
        }
        String remetenteLogin = sessoes.get(id);
        if (remetenteLogin.equals(amigo)) {
            throw new UsuarioAdicionandoASiMesmoException();
        }

        Usuario remetente = usuarios.get(remetenteLogin);
        Usuario alvo = usuarios.get(amigo);
        if (alvo == null) {
            throw new UsuarioNaoCadastradoException();
        }

        if (remetente.getConvitesRecebidos().contains(amigo)) {
            remetente.adicionarAmigo(amigo);
            alvo.adicionarAmigo(remetenteLogin);
            remetente.removerConvite(amigo);
            return;
        }

        alvo.adicionarConvite(remetenteLogin);
    }

    /**
     * Envia um recado de um usu�rio para outro.
     *
     * @param id           O identificador da sess�o do remetente.
     * @param destinatario O login do usu�rio destinat�rio.
     * @param recado       O conte�do do recado a ser enviado.
     * @throws UsuarioNaoCadastradoException Se o usu�rio remetente n�o estiver cadastrado.
     * @throws UsuarioEnviandoRecadoASiMesmoException Se o remetente tentar enviar um recado para si mesmo.
     */
    public void enviarRecado(String id, String destinatario, String recado) throws UsuarioNaoCadastradoException, UsuarioEnviandoRecadoASiMesmoException {
        if (!sessoes.containsKey(id)) {
            throw new UsuarioNaoCadastradoException();
        }
        if (sessoes.get(id).equals(destinatario)) {
            throw new UsuarioEnviandoRecadoASiMesmoException();
        }
        Usuario destino = usuarios.get(destinatario);
        if (destino == null) {
            throw new UsuarioNaoCadastradoException();
        }
        destino.adicionarRecado(recado);
    }

    /**
     * L� o recado de um usu�rio identificado pelo ID da sess�o.
     *
     * @param id O identificador da sess�o do usu�rio.
     * @return O recado do usu�rio.
     * @throws UsuarioNaoCadastradoException Se o usu�rio n�o estiver cadastrado.
     * @throws NaoHaRecadosException Se n�o houver recados para o usu�rio.
     */
    public String lerRecado(String id) throws UsuarioNaoCadastradoException, NaoHaRecadosException {
        if (!sessoes.containsKey(id)) {
            throw new UsuarioNaoCadastradoException();
        }
        return usuarios.get(sessoes.get(id)).lerRecado();
    }

    /**
     * Encerra o sistema e salva os dados persistidos.
     */
    public void encerrarSistema() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(ARQUIVO))) {
            out.writeObject(usuarios);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Carrega os dados persistidos do arquivo.
     */
    @SuppressWarnings("unchecked")
    private void carregarDados() {
        File file = new File(ARQUIVO);
        if (!file.exists()) return;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            usuarios = (Map<String, Usuario>) in.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reseta o sistema, apagando todos os dados e sess�es.
     */
    public void zerarSistema() {
        usuarios.clear();
        sessoes.clear();
        File file = new File(ARQUIVO);
        if (file.exists()) {
            file.delete();
        }
    }
}
