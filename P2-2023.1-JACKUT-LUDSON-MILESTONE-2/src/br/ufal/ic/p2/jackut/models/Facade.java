package br.ufal.ic.p2.jackut.models;

/**
 * Classe de fachada para o sistema Jackut.
 * Fornece uma interface simplificada para os testes EasyAccept.
 */
/**
 * A classe Facade atua como uma interface simplificada para interagir com o sistema.
 * Ela encapsula a lógica do sistema e fornece métodos para gerenciar usuários, sessões,
 * amigos e recados, além de permitir a inicialização, reset e encerramento do sistema.
 *
 * Métodos disponíveis:
 * - zerarSistema(): Reseta o sistema, apagando todos os dados e sessões.
 * - criarUsuario(String login, String senha, String nome): Cria um novo usuário no sistema.
 * - abrirSessao(String login, String senha): Abre uma sessão para um usuário autenticado.
 * - getAtributoUsuario(String login, String atributo): Obtém um atributo de um usuário.
 * - editarPerfil(String id, String atributo, String valor): Edita o perfil de um usuário.
 * - adicionarAmigo(String id, String amigo): Adiciona um amigo a um usuário.
 * - ehAmigo(String id, String amigo): Verifica se dois usuários são amigos.
 * - getAmigos(String login): Obtém a lista de amigos de um usuário.
 * - enviarRecado(String id, String destinatario, String recado): Envia um recado para um usuário.
 * - lerRecado(String id): Lê os recados de um usuário.
 * - encerrarSistema(): Encerra o sistema, salvando os dados e fechando as sessões.
 *
 * Exceções:
 * - Os métodos podem lançar exceções caso os parâmetros fornecidos sejam inválidos
 *   ou caso ocorram erros durante a execução das operações.
 *
 * Observação:
 * - A classe Facade depende de uma instância da classe Sistema para realizar as operações.
 */
public class Facade {
    private Sistema sistema;

    /**
     * Construtor da classe Facade.
     * Inicializa o sistema.
     */
    public Facade() {
        sistema = new Sistema();
    }

    /**
     * Reseta o sistema, apagando todos os dados e sessões.
     */
    public void zerarSistema() {
        sistema.zerarSistema();
    }

    /**
     * Cria um novo usuário no sistema.
     * @param login Login do usuário.
     * @param senha Senha do usuário.
     * @param nome Nome do usuário.
     * @throws Exception Se o login ou senha forem inválidos ou se o login já existir.
     */
    public void criarUsuario(String login, String senha, String nome) throws Exception {
        sistema.criarUsuario(login, senha, nome);
    }

    /**
     * Abre uma sessão para um usuário autenticado.
     * @param login Login do usuário.
     * @param senha Senha do usuário.
     * @return ID da sessão criada.
     * @throws Exception Se o login ou senha forem inválidos.
     */
    public String abrirSessao(String login, String senha) throws Exception {
        return sistema.abrirSessao(login, senha);
    }

    /**
     * Obtém um atributo de um usuário.
     * @param login Login do usuário.
     * @param atributo Nome do atributo.
     * @return Valor do atributo.
     * @throws Exception Se o login ou atributo forem inválidos.
     */
    public String getAtributoUsuario(String login, String atributo) throws Exception {
        return sistema.getAtributoUsuario(login, atributo);
    }

    /**
     * Edita o perfil de um usuário.
     * @param id ID da sessão do usuário.
     * @param atributo Nome do atributo a ser editado.
     * @param valor Novo valor do atributo.
     * @throws Exception Se o id, atributo ou valor forem inválidos.
     */
    public void editarPerfil(String id, String atributo, String valor) throws Exception {
        sistema.editarPerfil(id, atributo, valor);

    }

    /**
     * Adiciona um amigo a um usuário.
     * @param id ID da sessão do usuário.
     * @param amigo Login do amigo a ser adicionado.
     * @throws Exception Se o id ou amigo forem inválidos.
     */
    public void adicionarAmigo(String id, String amigo) throws Exception {
        sistema.adicionarAmigo(id, amigo);
    }

    /**
     * Verifica se dois usuários são amigos.
     * @param id ID da sessão do usuário.
     * @param amigo Login do amigo.
     * @return true se forem amigos, false caso contrário.
     * @throws Exception Se o id ou amigo forem inválidos.
     */
    public boolean ehAmigo(String id, String amigo) throws Exception {
        return sistema.ehAmigo(id, amigo);
    }

    /**
     * Obtém a lista de amigos de um usuário.
     * @param login Login do usuário.
     * @return Lista de amigos.
     * @throws Exception Se o login for inválido.
     */
    public String getAmigos(String login) throws Exception {
        return sistema.getAmigos(login);
    }

    /**
     * Envia um recado para um usuário.
     * @param id ID da sessão do usuário.
     * @param destinatario Login do destinatário.
     * @param recado Conteúdo do recado.
     * @throws Exception Se o id, destinatario ou recado forem inválidos.
     */
    public void enviarRecado(String id, String destinatario, String recado) throws Exception {
        sistema.enviarRecado(id, destinatario, recado);
    }



    /**
     * Lê os recados de um usuário.
     * @param id ID da sessão do usuário.
     * @return Recados do usuário.
     * @throws Exception Se o id for inválido.
     */
    public String lerRecado(String id) throws Exception {
        return sistema.lerRecado(id);
    }


    public void criarComunidade(String id, String nome, String descricao) throws Exception {
        sistema.criarComunidade(id, nome, descricao);
    }

    public String getComunidades(String login) throws Exception {
        return sistema.getComunidades(login);
    }

    public String getDescricaoComunidade(String nome) throws Exception {
        return sistema.getDescricaoComunidade(nome);
    }

    public String getDonoComunidade(String nome) throws Exception {
        return sistema.getDonoComunidade(nome);
    }

    public String getMembrosComunidade(String nome) throws Exception {
        return sistema.getMembrosComunidade(nome);
    }



    public void adicionarComunidade(String id, String nomeComunidade) throws Exception {
        sistema.adicionarComunidade(id, nomeComunidade);
    }



    public void enviarMensagem(String id, String nomeComunidade, String mensagem) throws Exception {
        sistema.enviarMensagem(id, nomeComunidade, mensagem);
    }

    public String lerMensagem(String id) throws Exception {
        return sistema.lerMensagem(id);
    }




    public void adicionarIdolo(String id, String idolo) throws Exception {
        sistema.adicionarIdolo(id, idolo);
    }

    public boolean ehFa(String login, String idolo) throws Exception {
        return sistema.ehFa(login, idolo);
    }

    public String getFas(String login) throws Exception {
        return sistema.getFas(login);
    }

    public void adicionarPaquera(String id, String paquera) throws Exception {
        sistema.adicionarPaquera(id, paquera);
    }

    public boolean ehPaquera(String id, String paquera) throws Exception {
        return sistema.ehPaquera(id, paquera);
    }

    public String getPaqueras(String id) throws Exception {
        return sistema.getPaqueras(id);
    }

    public void adicionarInimigo(String id, String inimigo) throws Exception {
        sistema.adicionarInimigo(id, inimigo);
    }



    public void removerUsuario(String id) throws Exception {
        sistema.removerUsuario(id);
    }



    /**
     * Encerra o sistema, salvando os dados e fechando as sessões.
     */
    public void encerrarSistema() {
        sistema.encerrarSistema();
    }
    
}
