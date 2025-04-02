package br.ufal.ic.p2.jackut.models;

import br.ufal.ic.p2.jackut.exceptions.*;
import java.io.*;
import java.util.*;

/**
 * Classe principal do sistema Jackut.
 * Gerencia usuários, sessões, amigos, recados e persistência de dados.
 */
/**
 * Classe que representa o sistema principal de gerenciamento de usuários, sessões,
 * amizades e recados. Implementa a interface Serializable para persistência de dados.
 * 
 * <p>Principais funcionalidades:
 * <ul>
 *   <li>Gerenciamento de usuários (criação, edição e recuperação de atributos).</li>
 *   <li>Gerenciamento de sessões de usuários autenticados.</li>
 *   <li>Gerenciamento de amizades entre usuários.</li>
 *   <li>Envio e leitura de recados entre usuários.</li>
 *   <li>Persistência de dados em arquivo.</li>
 * </ul>
 * 
 * <p>Exceções específicas são lançadas para tratar erros como login inválido, 
 * usuário não cadastrado, tentativa de adicionar a si mesmo como amigo, entre outros.
 * 
 * <p>O sistema utiliza um mapa de usuários persistente e um mapa de sessões transitório.
 * 
 * <p>Principais métodos:
 * <ul>
 *   <li>{@link #criarUsuario(String, String, String)}: Cria um novo usuário no sistema.</li>
 *   <li>{@link #abrirSessao(String, String)}: Abre uma sessão para um usuário autenticado.</li>
 *   <li>{@link #getAtributoUsuario(String, String)}: Recupera um atributo específico de um usuário.</li>
 *   <li>{@link #editarPerfil(String, String, String)}: Edita o perfil de um usuário.</li>
 *   <li>{@link #getAmigos(String)}: Retorna os amigos de um usuário.</li>
 *   <li>{@link #ehAmigo(String, String)}: Verifica se dois usuários são amigos.</li>
 *   <li>{@link #adicionarAmigo(String, String)}: Adiciona um amigo ao usuário.</li>
 *   <li>{@link #enviarRecado(String, String, String)}: Envia um recado de um usuário para outro.</li>
 *   <li>{@link #lerRecado(String)}: Lê o recado de um usuário.</li>
 *   <li>{@link #encerrarSistema()}: Salva os dados e encerra o sistema.</li>
 *   <li>{@link #zerarSistema()}: Reseta o sistema, apagando todos os dados e sessões.</li>
 * </ul>
 * 
 * <p>O sistema também carrega automaticamente os dados persistidos ao ser inicializado.
 * 
 *
 */
public class Sistema implements Serializable {
    private static final long serialVersionUID = 1L;

    // Mapa de usuários cadastrados no sistema
    private Map<String, Usuario> usuarios = new HashMap<>();
    // Mapa de sessões ativas (não persistente)
    private transient Map<String, String> sessoes = new HashMap<>();
    // Nome do arquivo de persistência
    private static final String ARQUIVO = "dados.dat";

    /**
     * Construtor da classe Sistema.
     * Carrega os dados persistidos, se existirem.
     */
    public Sistema() {
        carregarDados();
    }

    /**
     * Cria um novo usuário no sistema.
     * @param login Login do usuário.
     * @param senha Senha do usuário.
     * @param nome Nome do usuário.
     * @throws LoginInvalidoException Se o login for inválido.
     * @throws SenhaInvalidaException Se a senha for inválida.
     * @throws UsuarioNaoCadastradoException Se o login já existir.
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
     * Abre uma sessão para um usuário autenticado.
     * @param login Login do usuário.
     * @param senha Senha do usuário.
     * @return ID da sessão criada.
     * @throws LoginOuSenhaInvalidosException Se o login ou senha forem inválidos.
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
     * Recupera um atributo específico de um usuário com base no login.
     *
     * @param login O identificador de login do usuário.
     * @param atributo O nome do atributo a ser recuperado.
     * @return O valor do atributo especificado para o usuário.
     * @throws Exception Se o usuário não estiver cadastrado ou o atributo não puder ser recuperado.
     */
    public String getAtributoUsuario(String login, String atributo) throws Exception {
        Usuario usuario = usuarios.get(login);
        if (usuario == null)
            throw new Exception("Usuário não cadastrado.");
        return usuario.getAtributo(atributo);
    }

    /**
     * Edita o perfil de um usuário com base no atributo e valor fornecidos.
     *
     * @param id O identificador da sessão do usuário.
     * @param atributo O atributo do perfil que será editado.
     * @param valor O novo valor para o atributo.
     * @throws Exception Se o usuário não estiver cadastrado ou a sessão não for válida.
     */
    public void editarPerfil (String id, String atributo, String valor) throws Exception {
        if (!sessoes.containsKey(id)) {
            throw new Exception("Usuário não cadastrado.");
        }
        String login = sessoes.get(id);
        Usuario usuario = usuarios.get(login);
        if (usuario == null) {
            throw new Exception("Usuário não cadastrado.");
        }
        usuario.editarPerfil(atributo, valor);
    }

    /**
     * Retorna uma representação em formato de string dos amigos de um usuário.
     *
     * @param login O login do usuário cujo amigos serão listados.
     * @return Uma string contendo os amigos do usuário no formato "{amigo1,amigo2,...}".
     * @throws Exception Se o usuário com o login fornecido não estiver cadastrado.
     */
    public String getAmigos(String login) throws Exception {
        Usuario usuario = usuarios.get(login);
        if (usuario == null) {
            throw new Exception("Usuário não cadastrado.");
        }
        String amigosStr = String.join(",", usuario.getAmigos());
        return "{" + amigosStr + "}";
    }

    /**
     * Verifica se um determinado usuário é amigo de outro.
     *
     * @param login O login do usuário que será verificado.
     * @param amigo O login do possível amigo a ser verificado.
     * @return true se o usuário especificado for amigo, false caso contrário.
     * @throws Exception Se o usuário com o login fornecido não estiver cadastrado.
     */
    public boolean ehAmigo(String login, String amigo) throws Exception {
        Usuario usuario = usuarios.get(login);
        if (usuario == null) {
            throw new Exception("Usuário não cadastrado.");
        }
        return usuario.getAmigos().contains(amigo);
    }

    /**
     * Adiciona um amigo ao usuário identificado pelo ID da sessão.
     *
     * @param id O identificador da sessão do usuário que está enviando o pedido de amizade.
     * @param amigo O login do usuário que será adicionado como amigo.
     * @throws UsuarioNaoCadastradoException Se o ID da sessão não está associado a nenhum usuário cadastrado.
     * @throws UsuarioAdicionandoASiMesmoException Se o usuário tentar adicionar a si mesmo como amigo.
     * @throws UsuarioJaAmigoException Se o usuário alvo já está adicionado como amigo.
     * @throws UsuarioJaAmigoEsperandoException Se o usuário alvo já recebeu um convite de amizade do remetente.
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
     * Envia um recado de um usuário para outro.
     *
     * @param id           O identificador da sessão do remetente.
     * @param destinatario O login do usuário destinatário.
     * @param recado       O conteúdo do recado a ser enviado.
     * @throws UsuarioNaoCadastradoException Se o usuário remetente não estiver cadastrado.
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
     * Lê o recado de um usuário identificado pelo ID da sessão.
     *
     * @param id O identificador da sessão do usuário.
     * @return O recado do usuário.
     * @throws UsuarioNaoCadastradoException Se o usuário não estiver cadastrado.
     * @throws NaoHaRecadosException Se não houver recados para o usuário.
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
     * Reseta o sistema, apagando todos os dados e sessões.
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
