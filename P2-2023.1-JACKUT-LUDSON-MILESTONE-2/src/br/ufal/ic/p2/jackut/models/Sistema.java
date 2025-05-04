package br.ufal.ic.p2.jackut.models;

import br.ufal.ic.p2.jackut.exceptions.*;

import java.io.*;
import java.util.*;

/**
 * Classe principal do sistema Jackut.
 * Gerencia usuários, sessões, amigos, recados, comunidades, relacionamentos e persistência de dados.
 *
 * <p>Principais funcionalidades:
 * <ul>
 *   <li>Gerenciamento de usuários (criação, edição e recuperação de atributos).</li>
 *   <li>Gerenciamento de sessões de usuários autenticados.</li>
 *   <li>Gerenciamento de amizades entre usuários.</li>
 *   <li>Envio e leitura de recados entre usuários.</li>
 *   <li>Gerenciamento de comunidades (criação, adição de membros, envio de mensagens).</li>
 *   <li>Gerenciamento de relacionamentos (ídolos, paqueras, inimigos).</li>
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
 *   <li>{@link #criarComunidade(String, String, String)}: Cria uma nova comunidade.</li>
 *   <li>{@link #adicionarComunidade(String, String)}: Adiciona um usuário a uma comunidade.</li>
 *   <li>{@link #enviarMensagem(String, String, String)}: Envia uma mensagem para uma comunidade.</li>
 *   <li>{@link #lerMensagem(String)}: Lê mensagens de uma comunidade para um usuário.</li>
 *   <li>{@link #adicionarIdolo(String, String)}: Adiciona um ídolo para um usuário.</li>
 *   <li>{@link #ehFa(String, String)}: Verifica se um usuário é fã de outro.</li>
 *   <li>{@link #getFas(String)}: Retorna os fãs de um usuário.</li>
 *   <li>{@link #adicionarPaquera(String, String)}: Adiciona uma paquera para um usuário.</li>
 *   <li>{@link #ehPaquera(String, String)}: Verifica se um usuário é paquera de outro.</li>
 *   <li>{@link #getPaqueras(String)}: Retorna as paqueras de um usuário.</li>
 *   <li>{@link #adicionarInimigo(String, String)}: Adiciona um inimigo para um usuário.</li>
 *   <li>{@link #removerUsuario(String)}: Remove um usuário do sistema, apagando todas as suas informações.</li>
 *   <li>{@link #encerrarSistema()}: Salva os dados e encerra o sistema.</li>
 *   <li>{@link #zerarSistema()}: Reseta o sistema, apagando todos os dados e sessões.</li>
 * </ul>
 *
 * <p>O sistema também carrega automaticamente os dados persistidos ao ser inicializado.
 */
public class Sistema implements Serializable {
    private static final long serialVersionUID = 1L;

    // Mapa de usuários cadastrados no sistema
    private Map<String, Usuario> usuarios = new HashMap<>();
    // Mapa de sessões ativas (não persistente)
    private transient Map<String, String> sessoes = new HashMap<>();
    private Map<String, Comunidade> comunidades = new LinkedHashMap<>();
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
     *
     * @param login Login do usuário.
     * @param senha Senha do usuário.
     * @param nome  Nome do usuário.
     * @throws LoginInvalidoException    Se o login for inválido.
     * @throws SenhaInvalidaException    Se a senha for inválida.
     * @throws ContaJaExistenteException Se o login já existir.
     */
    public void criarUsuario(String login, String senha, String nome) throws Exception {
        if (login == null || login.trim().isEmpty())
            throw new LoginInvalidoException();
        if (senha == null || senha.trim().isEmpty())
            throw new SenhaInvalidaException();
        if (usuarios.containsKey(login))
            throw new ContaJaExistenteException(); // Alterado para lançar a exceção correta
        usuarios.put(login, new Usuario(login, senha, nome));
    }

    /**
     * Abre uma sessão para um usuário autenticado.
     *
     * @param login Login do usuário.
     * @param senha Senha do usuário.
     * @return ID da sessão criada.
     * @throws LoginOuSenhaInvalidosException Se o login ou senha forem inválidos.
     */
    public String abrirSessao(String login, String senha) throws Exception {
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
     * @param login    O identificador de login do usuário.
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
     * @param id       O identificador da sessão do usuário.
     * @param atributo O atributo do perfil que será editado.
     * @param valor    O novo valor para o atributo.
     * @throws Exception Se o usuário não estiver cadastrado ou a sessão não for válida.
     */
    public void editarPerfil(String id, String atributo, String valor) throws Exception {
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
     * @param id    O identificador da sessão do usuário que está enviando o pedido de amizade.
     * @param amigo O login do usuário que será adicionado como amigo.
     * @throws UsuarioNaoCadastradoException       Se o ID da sessão não está associado a nenhum usuário cadastrado.
     * @throws UsuarioAdicionandoASiMesmoException Se o usuário tentar adicionar a si mesmo como amigo.
     * @throws UsuarioJaAmigoException             Se o usuário alvo já está adicionado como amigo.
     * @throws UsuarioJaAmigoEsperandoException    Se o usuário alvo já recebeu um convite de amizade do remetente.
     */
    public void adicionarAmigo(String id, String amigo) throws Exception {
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
        if (remetente.getInimigos().contains(amigo) || alvo.getInimigos().contains(remetenteLogin)) {
            throw new UsuarioInimigoException(alvo.getNome());
        }

        // Verificar se já são amigos
        if (remetente.getAmigos().contains(amigo)) {
            throw new UsuarioJaAmigoException();
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
     * @throws UsuarioNaoCadastradoException          Se o usuário remetente não estiver cadastrado.
     * @throws UsuarioEnviandoRecadoASiMesmoException Se o remetente tentar enviar um recado para si mesmo.
     */
    public void enviarRecado(String id, String destinatario, String recado) throws Exception {
        if (!sessoes.containsKey(id)) {
            throw new UsuarioNaoCadastradoException();
        }
        String remetenteLogin = sessoes.get(id);
        if (remetenteLogin.equals(destinatario)) {
            throw new UsuarioEnviandoRecadoASiMesmoException();
        }
        Usuario remetente = usuarios.get(remetenteLogin);
        Usuario destino = usuarios.get(destinatario);
        if (destino == null) {
            throw new UsuarioNaoCadastradoException();
        }
        if (remetente.getInimigos().contains(destinatario) || destino.getInimigos().contains(remetenteLogin)) {
            throw new UsuarioInimigoException(destino.getNome());
        }
//        destino.adicionarRecado(recado);
        destino.adicionarRecado(remetenteLogin + ":" + recado);
    }

    /**
     * Lê o recado de um usuário identificado pelo ID da sessão.
     *
     * @param id O identificador da sessão do usuário.
     * @return O recado do usuário.
     * @throws UsuarioNaoCadastradoException Se o usuário não estiver cadastrado.
     * @throws NaoHaRecadosException         Se não houver recados para o usuário.
     */
    public String lerRecado(String id) throws Exception {
        if (!sessoes.containsKey(id)) {
            throw new UsuarioNaoCadastradoException();
        }
        return usuarios.get(sessoes.get(id)).lerRecado();
    }

    /**
     * Cria uma nova comunidade no sistema.
     *
     * @param id        ID da sessão do usuário que está criando a comunidade.
     * @param nome      Nome da comunidade.
     * @param descricao Descrição da comunidade.
     * @throws UsuarioNaoCadastradoException Se o ID da sessão não estiver associado a um usuário válido.
     * @throws ComunidadeNomeJaExiste        Se já existir uma comunidade com o mesmo nome.
     */
    public void criarComunidade(String id, String nome, String descricao) throws Exception {
        if (!sessoes.containsKey(id)) {
            throw new UsuarioNaoCadastradoException();
        }
        if (comunidades.containsKey(nome)) {
            throw new ComunidadeNomeJaExiste();
        }
        String dono = sessoes.get(id);
        Comunidade comunidade = new Comunidade(nome, descricao, dono);
        comunidades.put(nome, comunidade);

        usuarios.get(dono).adicionarComunidade(nome);
    }

    /**
     * Obtém as comunidades de um usuário.
     *
     * @param login Login do usuário.
     * @return Uma string contendo as comunidades do usuário no formato "{comunidade1,comunidade2,...}".
     * @throws UsuarioNaoCadastradoException Se o login não estiver associado a um usuário válido.
     */
    public String getComunidades(String login) throws Exception {
        if (!usuarios.containsKey(login)) {
            throw new UsuarioNaoCadastradoException();
        }

        List<String> comunidadesUsuario = usuarios.get(login).getComunidades();

        return "{" + String.join(",", comunidadesUsuario) + "}";
    }

    /**
     * Obtém a descrição de uma comunidade.
     *
     * @param nome Nome da comunidade.
     * @return A descrição da comunidade.
     * @throws ComunidadeNaoExisteException Se a comunidade não existir.
     */
    public String getDescricaoComunidade(String nome) throws Exception {
        Comunidade comunidade = comunidades.get(nome);
        if (comunidade == null) {
            throw new ComunidadeNaoExisteException();
        }
        return comunidade.getDescricao();
    }

    /**
     * Obtém o dono de uma comunidade.
     *
     * @param nome Nome da comunidade.
     * @return O login do dono da comunidade.
     * @throws ComunidadeNaoExisteException Se a comunidade não existir.
     */
    public String getDonoComunidade(String nome) throws Exception {
        Comunidade comunidade = comunidades.get(nome);
        if (comunidade == null) {
            throw new ComunidadeNaoExisteException();
        }
        return comunidade.getDono();
    }

    /**
     * Obtém os membros de uma comunidade.
     *
     * @param nome Nome da comunidade.
     * @return Uma string contendo os membros da comunidade no formato "{membro1,membro2,...}".
     * @throws ComunidadeNaoExisteException Se a comunidade não existir.
     */
    public String getMembrosComunidade(String nome) throws Exception {
        Comunidade comunidade = comunidades.get(nome);
        if (comunidade == null) {
            throw new ComunidadeNaoExisteException();
        }
        return "{" + String.join(",", comunidade.getMembros()) + "}";
    }

    /**
     * Adiciona um usuário a uma comunidade.
     *
     * @param id             ID da sessão do usuário que deseja entrar na comunidade.
     * @param nomeComunidade Nome da comunidade.
     * @throws UsuarioNaoCadastradoException Se o ID da sessão não estiver associado a um usuário válido.
     * @throws ComunidadeNaoExisteException  Se a comunidade não existir.
     * @throws UsuarioJaMembroException      Se o usuário já for membro da comunidade.
     */
    public void adicionarComunidade(String id, String nomeComunidade) throws Exception {
        if (!sessoes.containsKey(id)) {
            throw new UsuarioNaoCadastradoException();
        }

        String loginUsuario = sessoes.get(id);
        Comunidade comunidade = comunidades.get(nomeComunidade);
        if (comunidade == null) {
            throw new ComunidadeNaoExisteException();
        }
        if (comunidade.getMembros().contains(loginUsuario)) {
            throw new UsuarioJaMembroException();
        }

        comunidade.adicionarMembro(loginUsuario);

        usuarios.get(loginUsuario).adicionarComunidade(nomeComunidade);
    }

    /**
     * Envia uma mensagem para uma comunidade.
     *
     * @param id             ID da sessão do usuário que está enviando a mensagem.
     * @param nomeComunidade Nome da comunidade.
     * @param mensagem       Conteúdo da mensagem.
     * @throws UsuarioNaoCadastradoException Se o ID da sessão não estiver associado a um usuário válido.
     * @throws ComunidadeNaoExisteException  Se a comunidade não existir.
     */
    public void enviarMensagem(String id, String nomeComunidade, String mensagem) throws Exception {
        if (!sessoes.containsKey(id)) {
            throw new UsuarioNaoCadastradoException();
        }
        Comunidade comunidade = comunidades.get(nomeComunidade);
        if (comunidade == null) {
            throw new ComunidadeNaoExisteException();
        }

        String loginUsuario = sessoes.get(id);

        comunidade.enviarMensagem(mensagem);
    }

    /**
     * Lê a próxima mensagem disponível para o usuário em uma comunidade.
     *
     * @param id ID da sessão do usuário.
     * @return A mensagem lida.
     * @throws UsuarioNaoCadastradoException Se o ID da sessão não estiver associado a um usuário válido.
     * @throws NaoHaMensagensException       Se não houver mensagens disponíveis para o usuário.
     */
    public String lerMensagem(String id) throws Exception {
        if (!sessoes.containsKey(id)) {
            throw new UsuarioNaoCadastradoException();
        }

        String loginUsuario = sessoes.get(id);
        for (Comunidade comunidade : comunidades.values()) {
            if (comunidade.getMembros().contains(loginUsuario)) {
                Queue<String> mensagensUsuario = comunidade.getMensagensPorUsuario().get(loginUsuario);
                if (mensagensUsuario != null && !mensagensUsuario.isEmpty()) {
                    return comunidade.lerMensagem(loginUsuario);
                }
            }
        }

        throw new NaoHaMensagensException();
    }

    /**
     * Adiciona um ídolo para o usuário.
     *
     * @param id    ID da sessão do usuário.
     * @param idolo Login do ídolo a ser adicionado.
     * @throws UsuarioNaoCadastradoException Se o ID da sessão ou o login do ídolo não estiverem associados a usuários válidos.
     * @throws UsuarioFaDeSiMesmoException   Se o usuário tentar adicionar a si mesmo como ídolo.
     * @throws UsuarioInimigoException       Se o ídolo for inimigo do usuário.
     * @throws UsuarioJaIdoloException       Se o ídolo já estiver na lista de ídolos do usuário.
     */
    public void adicionarIdolo(String id, String idolo) throws Exception {
        if (!sessoes.containsKey(id)) {
            throw new UsuarioNaoCadastradoException();
        }

        String remetenteLogin = sessoes.get(id);
        if (remetenteLogin.equals(idolo)) {
            // Usuário não pode ser fã de si mesmo.
            throw new UsuarioFaDeSiMesmoException();
        }
        if (!usuarios.containsKey(idolo)) {
            throw new UsuarioNaoCadastradoException();
        }

        Usuario remetente = usuarios.get(remetenteLogin);
        Usuario alvo = usuarios.get(idolo);
        if (remetente.getInimigos().contains(idolo) || alvo.getInimigos().contains(remetenteLogin)) {
            throw new UsuarioInimigoException(alvo.getNome());
        }
        if (remetente.getIdolos().contains(idolo)) {
            throw new UsuarioJaIdoloException();
        }

        remetente.adicionarIdolo(idolo);
    }

    /**
     * Verifica se um usuário é fã de outro.
     *
     * @param login Login do usuário.
     * @param idolo Login do ídolo.
     * @return true se o usuário for fã do ídolo, false caso contrário.
     * @throws UsuarioNaoCadastradoException Se o login do usuário não estiver associado a um usuário válido.
     */
    public boolean ehFa(String login, String idolo) throws Exception {
        Usuario usuario = usuarios.get(login);
        if (usuario == null) {
            throw new UsuarioNaoCadastradoException();
        }
        return usuario.getIdolos().contains(idolo);
    }

    /**
     * Obtém os fãs de um usuário.
     *
     * @param login Login do usuário.
     * @return Uma string contendo os fãs do usuário no formato "{fa1,fa2,...}".
     * @throws UsuarioNaoCadastradoException Se o login do usuário não estiver associado a um usuário válido.
     */
    public String getFas(String login) throws Exception {
        if (!usuarios.containsKey(login)) {
            throw new UsuarioNaoCadastradoException();
        }

        Set<String> fas = new LinkedHashSet<>();
        for (Usuario u : usuarios.values()) {
            if (u.getIdolos().contains(login)) {
                fas.add(u.getLogin());
            }
        }
        return "{" + String.join(",", fas) + "}";
    }

    /**
     * Adiciona uma paquera para o usuário.
     *
     * @param id      ID da sessão do usuário.
     * @param paquera Login da paquera a ser adicionada.
     * @throws UsuarioNaoCadastradoException       Se o ID da sessão ou o login da paquera não estiverem associados a usuários válidos.
     * @throws UsuarioPaquerandoASiMesmoException  Se o usuário tentar adicionar a si mesmo como paquera.
     * @throws UsuarioInimigoException             Se a paquera for inimiga do usuário.
     * @throws UsuarioJaPaqueraException           Se a paquera já estiver na lista de paqueras do usuário.
     */
    public void adicionarPaquera(String id, String paquera) throws Exception {
        if (!sessoes.containsKey(id)) {
            throw new UsuarioNaoCadastradoException();
        }

        String remetenteLogin = sessoes.get(id);
        if (remetenteLogin.equals(paquera)) {
            throw new UsuarioPaquerandoASiMesmoException();
        }
        if (!usuarios.containsKey(paquera)) {
            throw new UsuarioNaoCadastradoException();
        }

        Usuario remetente = usuarios.get(remetenteLogin);
        Usuario alvo = usuarios.get(paquera);
        if (remetente.getInimigos().contains(paquera) || alvo.getInimigos().contains(remetenteLogin)) {
            throw new UsuarioInimigoException(alvo.getNome());
        }
        if (remetente.getPaqueras().contains(paquera)) {
            throw new UsuarioJaPaqueraException();
        }

        remetente.adicionarPaquera(paquera);

        if (alvo.getPaqueras().contains(remetenteLogin)) {
            String recadoParaRemetente = alvo.getNome() + " é seu paquera - Recado do Jackut.";
            String recadoParaAlvo = remetente.getNome() + " é seu paquera - Recado do Jackut.";

            usuarios.get(remetenteLogin).adicionarRecado(recadoParaRemetente);
            usuarios.get(paquera).adicionarRecado(recadoParaAlvo);
        }
    }

    /**
     * Verifica se um usuário é paquera de outro.
     *
     * @param id      ID da sessão do usuário.
     * @param paquera Login da paquera.
     * @return true se o usuário for paquera, false caso contrário.
     * @throws UsuarioNaoCadastradoException Se o ID da sessão não estiver associado a um usuário válido.
     */
    public boolean ehPaquera(String id, String paquera) throws Exception {
        if (!sessoes.containsKey(id)) {
            throw new UsuarioNaoCadastradoException();
        }

        String login = sessoes.get(id);
        Usuario usuario = usuarios.get(login);
        return usuario.getPaqueras().contains(paquera);
    }

    /**
     * Obtém as paqueras de um usuário.
     *
     * @param id ID da sessão do usuário.
     * @return Uma string contendo as paqueras do usuário no formato "{paquera1,paquera2,...}".
     * @throws UsuarioNaoCadastradoException Se o ID da sessão não estiver associado a um usuário válido.
     */
    public String getPaqueras(String id) throws Exception {
        if (!sessoes.containsKey(id)) {
            throw new UsuarioNaoCadastradoException();
        }
        String login = sessoes.get(id);
        Usuario usuario = usuarios.get(login);
        return "{" + String.join(",", usuario.getPaqueras()) + "}";
    }

    /**
     * Adiciona um inimigo para o usuário.
     *
     * @param id      ID da sessão do usuário.
     * @param inimigo Login do inimigo a ser adicionado.
     * @throws UsuarioNaoCadastradoException    Se o ID da sessão ou o login do inimigo não estiverem associados a usuários válidos.
     * @throws UsuarioInimigoDeSiMesmoException Se o usuário tentar adicionar a si mesmo como inimigo.
     * @throws UsuarioJaInimigoException        Se o inimigo já estiver na lista de inimigos do usuário.
     */
    public void adicionarInimigo(String id, String inimigo) throws Exception {
        if (!sessoes.containsKey(id)) {
            throw new UsuarioNaoCadastradoException();
        }

        String remetenteLogin = sessoes.get(id);
        if (remetenteLogin.equals(inimigo)) {
            throw new UsuarioInimigoDeSiMesmoException();
        }
        if (!usuarios.containsKey(inimigo)) {
            throw new UsuarioNaoCadastradoException();
        }

        Usuario remetente = usuarios.get(remetenteLogin);
        if (remetente.getInimigos().contains(inimigo)) {
            throw new UsuarioJaInimigoException();
        }

        remetente.adicionarInimigo(inimigo);
    }

    /**
     * Remove um usuário do sistema.
     *
     * @param id ID da sessão do usuário a ser removido.
     * @throws UsuarioNaoCadastradoException Se o ID da sessão não estiver associado a um usuário válido.
     */
    public void removerUsuario(String id) throws Exception {
        if (!sessoes.containsKey(id)) {
            throw new UsuarioNaoCadastradoException();
        }

        String login = sessoes.get(id);
        Usuario usuario = usuarios.get(login);

        Iterator<Map.Entry<String, Comunidade>> it = comunidades.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry<String, Comunidade> entry = it.next();
            Comunidade comunidade = entry.getValue();

            if (comunidade.getDono().equals(login)) {
                it.remove();
            } else {
                comunidade.removerMembro(login);
            }
        }

        for (Usuario u : usuarios.values()) {
            u.getAmigos().remove(login);
            u.getConvitesRecebidos().remove(login);
            u.getIdolos().remove(login);
            u.getPaqueras().remove(login);
            u.getInimigos().remove(login);
//            u.getComunidades().removeIf(comunidade ->
//                    comunidades.containsKey(comunidade) &&
//                            comunidades.get(comunidade).getMembros().contains(login)
//            );
            u.getComunidades().removeIf(nomeComunidade -> !comunidades.containsKey(nomeComunidade));
        }



        for (Usuario u : usuarios.values()) {
            Queue<String> recados = u.getRecados();
            recados.removeIf(recado -> recado.startsWith(login + ":"));
        }



        usuarios.remove(login);
        sessoes.remove(id);
    }


    /**
     * Encerra o sistema e salva os dados persistidos.
     */
    public void encerrarSistema() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(ARQUIVO))) {
            out.writeObject(usuarios);
            out.writeObject(comunidades);
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
            comunidades = (Map<String, Comunidade>) in.readObject();

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
        comunidades.clear();
        File file = new File(ARQUIVO);
        if (file.exists()) {
            file.delete();
        }
    }

    }