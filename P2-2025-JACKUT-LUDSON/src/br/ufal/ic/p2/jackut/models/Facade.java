package br.ufal.ic.p2.jackut.models;

/**
 * Classe de fachada para o sistema Jackut.
 * Fornece uma interface simplificada para os testes EasyAccept.
 */
/**
 * A classe Facade atua como uma interface simplificada para interagir com o sistema.
 * Ela encapsula a l�gica do sistema e fornece m�todos para gerenciar usu�rios, sess�es,
 * amigos e recados, al�m de permitir a inicializa��o, reset e encerramento do sistema.
 *
 * M�todos dispon�veis:
 * - zerarSistema(): Reseta o sistema, apagando todos os dados e sess�es.
 * - criarUsuario(String login, String senha, String nome): Cria um novo usu�rio no sistema.
 * - abrirSessao(String login, String senha): Abre uma sess�o para um usu�rio autenticado.
 * - getAtributoUsuario(String login, String atributo): Obt�m um atributo de um usu�rio.
 * - editarPerfil(String id, String atributo, String valor): Edita o perfil de um usu�rio.
 * - adicionarAmigo(String id, String amigo): Adiciona um amigo a um usu�rio.
 * - ehAmigo(String id, String amigo): Verifica se dois usu�rios s�o amigos.
 * - getAmigos(String login): Obt�m a lista de amigos de um usu�rio.
 * - enviarRecado(String id, String destinatario, String recado): Envia um recado para um usu�rio.
 * - lerRecado(String id): L� os recados de um usu�rio.
 * - encerrarSistema(): Encerra o sistema, salvando os dados e fechando as sess�es.
 *
 * Exce��es:
 * - Os m�todos podem lan�ar exce��es caso os par�metros fornecidos sejam inv�lidos
 *   ou caso ocorram erros durante a execu��o das opera��es.
 *
 * Observa��o:
 * - A classe Facade depende de uma inst�ncia da classe Sistema para realizar as opera��es.
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
     * Reseta o sistema, apagando todos os dados e sess�es.
     */
    public void zerarSistema() {
        sistema.zerarSistema();
    }

    /**
     * Cria um novo usu�rio no sistema.
     * @param login Login do usu�rio.
     * @param senha Senha do usu�rio.
     * @param nome Nome do usu�rio.
     * @throws Exception Se o login ou senha forem inv�lidos ou se o login j� existir.
     */
    public void criarUsuario(String login, String senha, String nome) throws Exception {
        sistema.criarUsuario(login, senha, nome);
    }

    /**
     * Abre uma sess�o para um usu�rio autenticado.
     * @param login Login do usu�rio.
     * @param senha Senha do usu�rio.
     * @return ID da sess�o criada.
     * @throws Exception Se o login ou senha forem inv�lidos.
     */
    public String abrirSessao(String login, String senha) throws Exception {
        return sistema.abrirSessao(login, senha);
    }

    /**
     * Obt�m um atributo de um usu�rio.
     * @param login Login do usu�rio.
     * @param atributo Nome do atributo.
     * @return Valor do atributo.
     * @throws Exception Se o login ou atributo forem inv�lidos.
     */
    public String getAtributoUsuario(String login, String atributo) throws Exception {
        return sistema.getAtributoUsuario(login, atributo);
    }

    /**
     * Edita o perfil de um usu�rio.
     * @param id ID da sess�o do usu�rio.
     * @param atributo Nome do atributo a ser editado.
     * @param valor Novo valor do atributo.
     * @throws Exception Se o id, atributo ou valor forem inv�lidos.
     */
    public void editarPerfil(String id, String atributo, String valor) throws Exception {
        sistema.editarPerfil(id, atributo, valor);

    }

    /**
     * Adiciona um amigo a um usu�rio.
     * @param id ID da sess�o do usu�rio.
     * @param amigo Login do amigo a ser adicionado.
     * @throws Exception Se o id ou amigo forem inv�lidos.
     */
    public void adicionarAmigo(String id, String amigo) throws Exception {
        sistema.adicionarAmigo(id, amigo);
    }

    /**
     * Verifica se dois usu�rios s�o amigos.
     * @param id ID da sess�o do usu�rio.
     * @param amigo Login do amigo.
     * @return true se forem amigos, false caso contr�rio.
     * @throws Exception Se o id ou amigo forem inv�lidos.
     */
    public boolean ehAmigo(String id, String amigo) throws Exception {
        return sistema.ehAmigo(id, amigo);
    }

    /**
     * Obt�m a lista de amigos de um usu�rio.
     * @param login Login do usu�rio.
     * @return Lista de amigos.
     * @throws Exception Se o login for inv�lido.
     */
    public String getAmigos(String login) throws Exception {
        return sistema.getAmigos(login);
    }

    /**
     * Envia um recado para um usu�rio.
     * @param id ID da sess�o do usu�rio.
     * @param destinatario Login do destinat�rio.
     * @param recado Conte�do do recado.
     * @throws Exception Se o id, destinatario ou recado forem inv�lidos.
     */
    public void enviarRecado(String id, String destinatario, String recado) throws Exception {
        sistema.enviarRecado(id, destinatario, recado);
    }

    /**
     * L� os recados de um usu�rio.
     * @param id ID da sess�o do usu�rio.
     * @return Recados do usu�rio.
     * @throws Exception Se o id for inv�lido.
     */
    public String lerRecado(String id) throws Exception {
        return sistema.lerRecado(id);
    }

    /**
     * Encerra o sistema, salvando os dados e fechando as sess�es.
     */
    public void encerrarSistema() {
        sistema.encerrarSistema();
    }
    
}
