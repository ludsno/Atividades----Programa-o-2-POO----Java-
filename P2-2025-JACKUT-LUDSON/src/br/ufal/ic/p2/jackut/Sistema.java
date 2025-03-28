package br.ufal.ic.p2.jackut;

import java.io.*;
import java.util.*;

/**
 * Classe principal do sistema Jackut.
 * Gerencia usuários, sessões, amigos, recados e persistência de dados.
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
     * @throws Exception Se o login ou senha forem inválidos ou se o login já existir.
     */
    public void criarUsuario(String login, String senha, String nome) throws Exception {
        if (login == null || login.trim().isEmpty())
            throw new Exception("Login inválido.");
        if (senha == null || senha.trim().isEmpty())
            throw new Exception("Senha inválida.");
        if (usuarios.containsKey(login))
            throw new Exception("Conta com esse nome já existe.");
        usuarios.put(login, new Usuario(login, senha, nome));
    }

    /**
     * Abre uma sessão para um usuário autenticado.
     * @param login Login do usuário.
     * @param senha Senha do usuário.
     * @return ID da sessão criada.
     * @throws Exception Se o login ou senha forem inválidos.
     */
    public String abrirSessao(String login, String senha) throws Exception {
        if (!usuarios.containsKey(login)) {  
            throw new Exception("Login ou senha inválidos.");  // Corrigido para a mensagem esperada
        }
        
        Usuario usuario = usuarios.get(login);  
        if (!usuario.verificarSenha(senha)) {  
            throw new Exception("Login ou senha inválidos.");  // Senha errada -> mesma mensagem
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
     * @throws Exception Se ocorrer uma das seguintes condições:
     * <ul>
     *   <li>O ID da sessão não está associado a nenhum usuário cadastrado.</li>
     *   <li>O usuário remetente não está cadastrado no sistema.</li>
     *   <li>O usuário tentar adicionar a si mesmo como amigo.</li>
     *   <li>O usuário alvo não está cadastrado no sistema.</li>
     *   <li>O usuário alvo já está adicionado como amigo.</li>
     *   <li>O usuário alvo já recebeu um convite de amizade do remetente.</li>
     * </ul>
     * 
     * Caso o usuário remetente tenha recebido um convite de amizade do usuário alvo,
     * a amizade será automaticamente estabelecida entre ambos, e o convite será removido.
     * Caso contrário, um convite de amizade será enviado ao usuário alvo.
     */
    public void adicionarAmigo(String id, String amigo) throws Exception {
        if(!sessoes.containsKey(id)) {
            throw new Exception("Usuário não cadastrado.");
        }
        String remetenteLogin = sessoes.get(id);
        if (!usuarios.containsKey(remetenteLogin)) {
            throw new Exception("Usuário não cadastrado.");
        }
        if (remetenteLogin.equals(amigo)) {
            throw new Exception("Usuário não pode adicionar a si mesmo como amigo.");
        }

        Usuario remetente = usuarios.get(remetenteLogin);
        Usuario alvo = usuarios.get(amigo);
        if (alvo == null) {
            throw new Exception("Usuário não cadastrado.");
        }

        if(remetente.getAmigos().contains(amigo)) {
            throw new Exception("Usuário já está adicionado como amigo.");
        }
        if(alvo.getConvitesRecebidos().contains(remetenteLogin)) {
            throw new Exception("Usuário já está adicionado como amigo, esperando aceitação do convite.");
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
     * @throws Exception Se o usuário remetente não estiver cadastrado,
     *                   se o destinatário não estiver cadastrado,
     *                   ou se o remetente tentar enviar um recado para si mesmo.
     */
    public void enviarRecado(String id, String destinatario, String recado) throws Exception {
        if (!sessoes.containsKey(id)) {
            throw new Exception("Usuário não cadastrado.");
        }
        String remetenteLogin = sessoes.get(id);
        if (remetenteLogin.equals(destinatario)) {
            throw new Exception("Usuário não pode enviar recado para si mesmo.");
        }
        Usuario destino = usuarios.get(destinatario);
        if (destino == null) {
            throw new Exception("Usuário não cadastrado.");
        }
        destino.adicionarRecado(recado);
    }

    /**
     * Lê o recado de um usuário identificado pelo ID da sessão.
     *
     * @param id O identificador da sessão do usuário.
     * @return O recado do usuário.
     * @throws Exception Se o usuário não estiver cadastrado.
     */
    public String lerRecado(String id) throws Exception {
        if(!sessoes.containsKey(id)) {
            throw new Exception("Usuário não cadastrado.");
        }
        String login = sessoes.get(id);
        Usuario usuario = usuarios.get(login);
        return usuario.lerRecado();
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
        // Opcional: apagar o arquivo de persistência
        File file = new File(ARQUIVO);
        if (file.exists()) {
            file.delete();
        }
    }
}
