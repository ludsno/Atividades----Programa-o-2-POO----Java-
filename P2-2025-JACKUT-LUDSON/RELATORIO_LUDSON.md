# Relatório de Design do Sistema Jackut -- Milestone 1

## 1. Introdução

O sistema Jackut é uma rede social inspirada no Orkut, desenvolvido para atender aos requisitos funcionais descritos nas User Stories. O objetivo principal é criar um sistema que gerencie usuários, perfis, amizades, recados e persistência de dados, de forma modular e testável através da biblioteca EasyAccept. Este relatório descreve as principais escolhas de design, as responsabilidades das classes e a forma como a persistência e o gerenciamento de sessões foram implementados.

## 2. Arquitetura e Principais Componentes

Para promover uma boa separação de responsabilidades e facilitar a manutenção, o sistema foi dividido em três camadas principais:

- **Interface de Acesso (Facade):**  
  - **Responsabilidade:** Expor os métodos de negócio de forma simplificada para que os testes automatizados (EasyAccept) possam interagir com o sistema sem conhecer os detalhes da implementação.  
  - **Métodos Principais:** `zerarSistema()`, `criarUsuario()`, `abrirSessao()`, `getAtributoUsuario()`, `editarPerfil()`, `adicionarAmigo()`, `ehAmigo()`, `getAmigos()`, `enviarRecado()`, `lerRecado()`, `encerrarSistema()`.

- **Lógica de Negócio (Sistema):**  
  - **Responsabilidade:** Gerenciar a lógica central, como criação e autenticação de usuários, gerenciamento de sessões, amigos, recados e persistência dos dados.  
  - **Persistência:** Utiliza a serialização Java para salvar e carregar os dados do sistema em um arquivo ("dados.dat"), garantindo que as informações persistam entre execuções.  
  - **Gerenciamento de Sessões:** As sessões são armazenadas em um `Map` temporário (não persistente) que associa um ID de sessão (gerado a partir do login e timestamp) ao usuário correspondente.

- **Entidade (Usuario):**  
  - **Responsabilidade:** Representar os dados e comportamentos de um usuário.  
  - **Atributos:**  
    - Dados básicos: `login`, `senha` e `nome`.  
    - Atributos do perfil: armazenados em um `Map` para permitir a edição e adição dinâmica de atributos.  
    - Amizades: um conjunto de logins de amigos confirmados.  
    - Convites: um conjunto de convites pendentes (para gerenciar o fluxo de solicitação/aceitação de amizade).  
    - Recados: uma fila (FIFO) de mensagens recebidas, para garantir a ordem de leitura.

## 3. Persistência e Gerenciamento de Sessões

A persistência é implementada através da serialização dos objetos da classe `Usuario` (armazenados em um `Map` na classe `Sistema`).  
- **Salvar Dados:** O método `encerrarSistema()` serializa o `Map` de usuários e grava em um arquivo ("dados.dat").  
- **Carregar Dados:** No construtor da classe `Sistema`, o método `carregarDados()` verifica se o arquivo existe e, em caso afirmativo, desserializa os usuários.  
- **Sessões:** São gerenciadas por um `Map<String, String>` (chave: ID da sessão; valor: login do usuário), que é descartado quando o sistema é encerrado.

## 4. Diagrama de Classes

A seguir, um diagrama de classes  ilustrando os principais componentes do sistema:


![alt text](<Editor _ Mermaid Chart-2025-03-25-151523.svg>)




## 5. Conclusão

O design do sistema Jackut foi planejado para garantir:
- **Modularidade:** Com a separação entre a interface (Facade), a lógica de negócio (Sistema) e as entidades (Usuario), o sistema permite alterações e expansões futuras sem acoplamento excessivo.
- **Persistência:** Através da serialização, os dados dos usuários, incluindo perfis, amizades e recados, são mantidos entre execuções, atendendo aos requisitos de persistência.
- **Testabilidade:** A implementação dos métodos na Facade possibilita a automação dos testes (usando EasyAccept), garantindo que cada funcionalidade atenda aos requisitos do cliente.

Essa estrutura facilita a manutenção e a evolução do sistema, permitindo a adição de novas funcionalidades (como comunidades e outros tipos de relacionamentos) sem comprometer a integridade do design.
