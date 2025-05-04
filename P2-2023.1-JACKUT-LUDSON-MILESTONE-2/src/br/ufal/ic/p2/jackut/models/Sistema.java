package br.ufal.ic.p2.jackut.models;

import br.ufal.ic.p2.jackut.exceptions.*;

import java.io.*;
import java.util.*;

/**
 * Classe principal do sistema Jackut.
 * Gerencia usu�rios, sess�es, amigos, recados, comunidades, relacionamentos e persist�ncia de dados.
 *
 * <p>Principais funcionalidades:
 * <ul>
 *   <li>Gerenciamento de usu�rios (cria��o, edi��o e recupera��o de atributos).</li>
 *   <li>Gerenciamento de sess�es de usu�rios autenticados.</li>
 *   <li>Gerenciamento de amizades entre usu�rios.</li>
 *   <li>Envio e leitura de recados entre usu�rios.</li>
 *   <li>Gerenciamento de comunidades (cria��o, adi��o de membros, envio de mensagens).</li>
 *   <li>Gerenciamento de relacionamentos (�dolos, paqueras, inimigos).</li>
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
 *   <li>{@link #criarComunidade(String, String, String)}: Cria uma nova comunidade.</li>
 *   <li>{@link #adicionarComunidade(String, String)}: Adiciona um usu�rio a uma comunidade.</li>
 *   <li>{@link #enviarMensagem(String, String, String)}: Envia uma mensagem para uma comunidade.</li>
 *   <li>{@link #lerMensagem(String)}: L� mensagens de uma comunidade para um usu�rio.</li>
 *   <li>{@link #adicionarIdolo(String, String)}: Adiciona um �dolo para um usu�rio.</li>
 *   <li>{@link #ehFa(String, String)}: Verifica se um usu�rio � f� de outro.</li>
 *   <li>{@link #getFas(String)}: Retorna os f�s de um usu�rio.</li>
 *   <li>{@link #adicionarPaquera(String, String)}: Adiciona uma paquera para um usu�rio.</li>
 *   <li>{@link #ehPaquera(String, String)}: Verifica se um usu�rio � paquera de outro.</li>
 *   <li>{@link #getPaqueras(String)}: Retorna as paqueras de um usu�rio.</li>
 *   <li>{@link #adicionarInimigo(String, String)}: Adiciona um inimigo para um usu�rio.</li>
 *   <li>{@link #removerUsuario(String)}: Remove um usu�rio do sistema, apagando todas as suas informa��es.</li>
 *   <li>{@link #encerrarSistema()}: Salva os dados e encerra o sistema.</li>
 *   <li>{@link #zerarSistema()}: Reseta o sistema, apagando todos os dados e sess�es.</li>
 * </ul>
 *
 * <p>O sistema tamb�m carrega automaticamente os dados persistidos ao ser inicializado.
 */
public class Sistema implements Serializable {
    private static final long serialVersionUID = 1L;

    // Mapa de usu�rios cadastrados no sistema
    private Map<String, Usuario> usuarios = new HashMap<>();
    // Mapa de sess�es ativas (n�o persistente)
    private transient Map<String, String> sessoes = new HashMap<>();
    private Map<String, Comunidade> comunidades = new LinkedHashMap<>();
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
     *
     * @param login Login do usu�rio.
     * @param senha Senha do usu�rio.
     * @param nome  Nome do usu�rio.
     * @throws LoginInvalidoException    Se o login for inv�lido.
     * @throws SenhaInvalidaException    Se a senha for inv�lida.
     * @throws ContaJaExistenteException Se o login j� existir.
     */
    public void criarUsuario(String login, String senha, String nome) throws Exception {
        if (login == null || login.trim().isEmpty())
            throw new LoginInvalidoException();
        if (senha == null || senha.trim().isEmpty())
            throw new SenhaInvalidaException();
        if (usuarios.containsKey(login))
            throw new ContaJaExistenteException(); // Alterado para lan�ar a exce��o correta
        usuarios.put(login, new Usuario(login, senha, nome));
    }

    /**
     * Abre uma sess�o para um usu�rio autenticado.
     *
     * @param login Login do usu�rio.
     * @param senha Senha do usu�rio.
     * @return ID da sess�o criada.
     * @throws LoginOuSenhaInvalidosException Se o login ou senha forem inv�lidos.
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
     * Recupera um atributo espec�fico de um usu�rio com base no login.
     *
     * @param login    O identificador de login do usu�rio.
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
     * @param id       O identificador da sess�o do usu�rio.
     * @param atributo O atributo do perfil que ser� editado.
     * @param valor    O novo valor para o atributo.
     * @throws Exception Se o usu�rio n�o estiver cadastrado ou a sess�o n�o for v�lida.
     */
    public void editarPerfil(String id, String atributo, String valor) throws Exception {
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
     * @param id    O identificador da sess�o do usu�rio que est� enviando o pedido de amizade.
     * @param amigo O login do usu�rio que ser� adicionado como amigo.
     * @throws UsuarioNaoCadastradoException       Se o ID da sess�o n�o est� associado a nenhum usu�rio cadastrado.
     * @throws UsuarioAdicionandoASiMesmoException Se o usu�rio tentar adicionar a si mesmo como amigo.
     * @throws UsuarioJaAmigoException             Se o usu�rio alvo j� est� adicionado como amigo.
     * @throws UsuarioJaAmigoEsperandoException    Se o usu�rio alvo j� recebeu um convite de amizade do remetente.
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

        // Verificar se j� s�o amigos
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
     * Envia um recado de um usu�rio para outro.
     *
     * @param id           O identificador da sess�o do remetente.
     * @param destinatario O login do usu�rio destinat�rio.
     * @param recado       O conte�do do recado a ser enviado.
     * @throws UsuarioNaoCadastradoException          Se o usu�rio remetente n�o estiver cadastrado.
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
     * L� o recado de um usu�rio identificado pelo ID da sess�o.
     *
     * @param id O identificador da sess�o do usu�rio.
     * @return O recado do usu�rio.
     * @throws UsuarioNaoCadastradoException Se o usu�rio n�o estiver cadastrado.
     * @throws NaoHaRecadosException         Se n�o houver recados para o usu�rio.
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
     * @param id        ID da sess�o do usu�rio que est� criando a comunidade.
     * @param nome      Nome da comunidade.
     * @param descricao Descri��o da comunidade.
     * @throws UsuarioNaoCadastradoException Se o ID da sess�o n�o estiver associado a um usu�rio v�lido.
     * @throws ComunidadeNomeJaExiste        Se j� existir uma comunidade com o mesmo nome.
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
     * Obt�m as comunidades de um usu�rio.
     *
     * @param login Login do usu�rio.
     * @return Uma string contendo as comunidades do usu�rio no formato "{comunidade1,comunidade2,...}".
     * @throws UsuarioNaoCadastradoException Se o login n�o estiver associado a um usu�rio v�lido.
     */
    public String getComunidades(String login) throws Exception {
        if (!usuarios.containsKey(login)) {
            throw new UsuarioNaoCadastradoException();
        }

        List<String> comunidadesUsuario = usuarios.get(login).getComunidades();

        return "{" + String.join(",", comunidadesUsuario) + "}";
    }

    /**
     * Obt�m a descri��o de uma comunidade.
     *
     * @param nome Nome da comunidade.
     * @return A descri��o da comunidade.
     * @throws ComunidadeNaoExisteException Se a comunidade n�o existir.
     */
    public String getDescricaoComunidade(String nome) throws Exception {
        Comunidade comunidade = comunidades.get(nome);
        if (comunidade == null) {
            throw new ComunidadeNaoExisteException();
        }
        return comunidade.getDescricao();
    }

    /**
     * Obt�m o dono de uma comunidade.
     *
     * @param nome Nome da comunidade.
     * @return O login do dono da comunidade.
     * @throws ComunidadeNaoExisteException Se a comunidade n�o existir.
     */
    public String getDonoComunidade(String nome) throws Exception {
        Comunidade comunidade = comunidades.get(nome);
        if (comunidade == null) {
            throw new ComunidadeNaoExisteException();
        }
        return comunidade.getDono();
    }

    /**
     * Obt�m os membros de uma comunidade.
     *
     * @param nome Nome da comunidade.
     * @return Uma string contendo os membros da comunidade no formato "{membro1,membro2,...}".
     * @throws ComunidadeNaoExisteException Se a comunidade n�o existir.
     */
    public String getMembrosComunidade(String nome) throws Exception {
        Comunidade comunidade = comunidades.get(nome);
        if (comunidade == null) {
            throw new ComunidadeNaoExisteException();
        }
        return "{" + String.join(",", comunidade.getMembros()) + "}";
    }

    /**
     * Adiciona um usu�rio a uma comunidade.
     *
     * @param id             ID da sess�o do usu�rio que deseja entrar na comunidade.
     * @param nomeComunidade Nome da comunidade.
     * @throws UsuarioNaoCadastradoException Se o ID da sess�o n�o estiver associado a um usu�rio v�lido.
     * @throws ComunidadeNaoExisteException  Se a comunidade n�o existir.
     * @throws UsuarioJaMembroException      Se o usu�rio j� for membro da comunidade.
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
     * @param id             ID da sess�o do usu�rio que est� enviando a mensagem.
     * @param nomeComunidade Nome da comunidade.
     * @param mensagem       Conte�do da mensagem.
     * @throws UsuarioNaoCadastradoException Se o ID da sess�o n�o estiver associado a um usu�rio v�lido.
     * @throws ComunidadeNaoExisteException  Se a comunidade n�o existir.
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
     * L� a pr�xima mensagem dispon�vel para o usu�rio em uma comunidade.
     *
     * @param id ID da sess�o do usu�rio.
     * @return A mensagem lida.
     * @throws UsuarioNaoCadastradoException Se o ID da sess�o n�o estiver associado a um usu�rio v�lido.
     * @throws NaoHaMensagensException       Se n�o houver mensagens dispon�veis para o usu�rio.
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
     * Adiciona um �dolo para o usu�rio.
     *
     * @param id    ID da sess�o do usu�rio.
     * @param idolo Login do �dolo a ser adicionado.
     * @throws UsuarioNaoCadastradoException Se o ID da sess�o ou o login do �dolo n�o estiverem associados a usu�rios v�lidos.
     * @throws UsuarioFaDeSiMesmoException   Se o usu�rio tentar adicionar a si mesmo como �dolo.
     * @throws UsuarioInimigoException       Se o �dolo for inimigo do usu�rio.
     * @throws UsuarioJaIdoloException       Se o �dolo j� estiver na lista de �dolos do usu�rio.
     */
    public void adicionarIdolo(String id, String idolo) throws Exception {
        if (!sessoes.containsKey(id)) {
            throw new UsuarioNaoCadastradoException();
        }

        String remetenteLogin = sessoes.get(id);
        if (remetenteLogin.equals(idolo)) {
            // Usu�rio n�o pode ser f� de si mesmo.
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
     * Verifica se um usu�rio � f� de outro.
     *
     * @param login Login do usu�rio.
     * @param idolo Login do �dolo.
     * @return true se o usu�rio for f� do �dolo, false caso contr�rio.
     * @throws UsuarioNaoCadastradoException Se o login do usu�rio n�o estiver associado a um usu�rio v�lido.
     */
    public boolean ehFa(String login, String idolo) throws Exception {
        Usuario usuario = usuarios.get(login);
        if (usuario == null) {
            throw new UsuarioNaoCadastradoException();
        }
        return usuario.getIdolos().contains(idolo);
    }

    /**
     * Obt�m os f�s de um usu�rio.
     *
     * @param login Login do usu�rio.
     * @return Uma string contendo os f�s do usu�rio no formato "{fa1,fa2,...}".
     * @throws UsuarioNaoCadastradoException Se o login do usu�rio n�o estiver associado a um usu�rio v�lido.
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
     * Adiciona uma paquera para o usu�rio.
     *
     * @param id      ID da sess�o do usu�rio.
     * @param paquera Login da paquera a ser adicionada.
     * @throws UsuarioNaoCadastradoException       Se o ID da sess�o ou o login da paquera n�o estiverem associados a usu�rios v�lidos.
     * @throws UsuarioPaquerandoASiMesmoException  Se o usu�rio tentar adicionar a si mesmo como paquera.
     * @throws UsuarioInimigoException             Se a paquera for inimiga do usu�rio.
     * @throws UsuarioJaPaqueraException           Se a paquera j� estiver na lista de paqueras do usu�rio.
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
            String recadoParaRemetente = alvo.getNome() + " � seu paquera - Recado do Jackut.";
            String recadoParaAlvo = remetente.getNome() + " � seu paquera - Recado do Jackut.";

            usuarios.get(remetenteLogin).adicionarRecado(recadoParaRemetente);
            usuarios.get(paquera).adicionarRecado(recadoParaAlvo);
        }
    }

    /**
     * Verifica se um usu�rio � paquera de outro.
     *
     * @param id      ID da sess�o do usu�rio.
     * @param paquera Login da paquera.
     * @return true se o usu�rio for paquera, false caso contr�rio.
     * @throws UsuarioNaoCadastradoException Se o ID da sess�o n�o estiver associado a um usu�rio v�lido.
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
     * Obt�m as paqueras de um usu�rio.
     *
     * @param id ID da sess�o do usu�rio.
     * @return Uma string contendo as paqueras do usu�rio no formato "{paquera1,paquera2,...}".
     * @throws UsuarioNaoCadastradoException Se o ID da sess�o n�o estiver associado a um usu�rio v�lido.
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
     * Adiciona um inimigo para o usu�rio.
     *
     * @param id      ID da sess�o do usu�rio.
     * @param inimigo Login do inimigo a ser adicionado.
     * @throws UsuarioNaoCadastradoException    Se o ID da sess�o ou o login do inimigo n�o estiverem associados a usu�rios v�lidos.
     * @throws UsuarioInimigoDeSiMesmoException Se o usu�rio tentar adicionar a si mesmo como inimigo.
     * @throws UsuarioJaInimigoException        Se o inimigo j� estiver na lista de inimigos do usu�rio.
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
     * Remove um usu�rio do sistema.
     *
     * @param id ID da sess�o do usu�rio a ser removido.
     * @throws UsuarioNaoCadastradoException Se o ID da sess�o n�o estiver associado a um usu�rio v�lido.
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
     * Reseta o sistema, apagando todos os dados e sess�es.
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