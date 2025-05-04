# Relatório de Design do Sistema Jackut -- Milestone 2

## 1. Introdução

O sistema Jackut continua sua evolução, agora com a implementação de novas funcionalidades descritas nas User Stories 5.1 a 9.2. Este relatório descreve as principais mudanças realizadas desde o Milestone 1, destacando as melhorias no design, novas funcionalidades e ajustes feitos com base no feedback recebido.

## 2. Arquitetura e Principais Componentes

### Estrutura Geral 

- **Organização Geral**:  
  O projeto é bem estruturado, com uma separação clara entre as camadas de lógica de negócio (`Sistema`), entidades (`Usuario` e `Comunidade`) e a interface de acesso (`Facade`).  
  A documentação interna (JavaDoc) está presente e bem escrita, facilitando o entendimento do código.

- **Uso de Design Patterns**:  
  O padrão **Facade** foi utilizado para simplificar a interação com o sistema, expondo apenas os métodos necessários para os testes e encapsulando a lógica de negócio.  
  A modularidade foi mantida, com as funcionalidades de comunidades e relacionamentos isoladas em classes específicas.

### Melhorias na Arquitetur
A arquitetura do sistema foi mantida, mas com melhorias significativas para suportar as novas funcionalidades:

- **Interface de Acesso (Facade):**  
  - **Novidades:** Foram adicionados métodos para gerenciar comunidades, relacionamentos (ídolos, paqueras, inimigos) e envio de mensagens em comunidades.  
  - **Novos Métodos:** `criarComunidade()`, `adicionarComunidade()`, `enviarMensagem()`, `lerMensagem()`, `adicionarIdolo()`, `adicionarPaquera()`, `adicionarInimigo()`.

- **Lógica de Negócio (Sistema):**  
  - **Novidades:**  
    - Gerenciamento de comunidades, incluindo criação, adição de membros e envio de mensagens.  
    - Relacionamentos avançados, como ídolos, paqueras e inimigos.  
    - Melhorias no tratamento de recados, com identificação do remetente.  
  - **Persistência:** Agora inclui comunidades e relacionamentos, garantindo que todas as informações sejam mantidas entre execuções.

- **Entidades (Usuario e Comunidade):**  
  - **Usuario:**  
    - **Novidades:** Adição de listas para gerenciar ídolos, paqueras e inimigos.  
    - **Métodos Novos:** `adicionarIdolo()`, `adicionarPaquera()`, `adicionarInimigo()`.  
  - **Comunidade:**  
    - **Novidades:** Implementação completa para gerenciar membros e mensagens.  
    - **Métodos Novos:** `adicionarMembro()`, `enviarMensagem()`, `lerMensagem()`.__

## 3. Novas Funcionalidades

### 3.1 Gerenciamento de Comunidades
- **Descrição:** Agora é possível criar comunidades, adicionar membros e enviar mensagens.  
- **Impacto no Design:**  
  - Adição da classe `Comunidade` para encapsular os dados e comportamentos relacionados.  
  - Métodos na classe `Sistema` para gerenciar comunidades e integrar com os usuários.

### 3.2 Relacionamentos Avançados
- **Descrição:** Foram adicionados novos tipos de relacionamentos: ídolos, paqueras e inimigos.  
- **Impacto no Design:**  
  - Novos atributos e métodos na classe `Usuario` para gerenciar esses relacionamentos.  
  - Validações adicionais para evitar conflitos entre relacionamentos (ex.: inimigos não podem ser adicionados como amigos).

### 3.3 Melhorias no Gerenciamento de Recados
- **Descrição:** Agora os recados incluem o remetente, facilitando a identificação de quem enviou a mensagem.  
- **Impacto no Design:**  
  - Alteração no método `enviarRecado()` para incluir o login do remetente no conteúdo do recado.

## 4. Padrões de Design Utilizados

- **Facade**:  
  A classe `Facade` continua sendo a interface principal para os testes EasyAccept, encapsulando a lógica de negócio e expondo apenas os métodos necessários.

- **Encapsulamento**:  
  As funcionalidades de comunidades e relacionamentos foram encapsuladas em classes específicas, seguindo o princípio de alta coesão.


## 5. Persistência e Gerenciamento de Sessões

A persistência foi ampliada para incluir comunidades e relacionamentos.  
- **Salvar Dados:** Agora, além dos usuários, as comunidades também são serializadas.  
- **Carregar Dados:** O sistema carrega automaticamente as comunidades e seus membros ao iniciar.  
- **Sessões:** Mantidas como transitórias, sem alterações significativas.

## 6. Diagrama de Classes

O diagrama de classes foi atualizado para refletir as mudanças no design, incluindo as novas funcionalidades e classes adicionadas.

![Editor _ Mermaid Chart-2025-05-03-034209.svg](Editor%20_%20Mermaid%20Chart-2025-05-03-034209.svg)

## 7. Conclusão

O Milestone 2 trouxe melhorias significativas ao sistema Jackut, com a adição de funcionalidades avançadas e ajustes no design para suportar a evolução do projeto. As mudanças realizadas reforçam a modularidade e a extensibilidade do sistema, preparando-o para futuras expansões.

- **Destaques:**  
  - Implementação de comunidades e relacionamentos avançados.  
  - Melhorias na persistência e no gerenciamento de recados.  
  - Uso de boas práticas de design para reduzir acoplamento e melhorar a coesão.
