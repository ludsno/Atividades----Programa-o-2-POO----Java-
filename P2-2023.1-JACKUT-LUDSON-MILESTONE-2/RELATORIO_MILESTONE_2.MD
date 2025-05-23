# Relat�rio de Design do Sistema Jackut -- Milestone 2

## 1. Introdu��o

O sistema Jackut continua sua evolu��o, agora com a implementa��o de novas funcionalidades descritas nas User Stories 5.1 a 9.2. Este relat�rio descreve as principais mudan�as realizadas desde o Milestone 1, destacando as melhorias no design, novas funcionalidades e ajustes feitos com base no feedback recebido.

## 2. Arquitetura e Principais Componentes

### Estrutura Geral 

- **Organiza��o Geral**:  
  O projeto � bem estruturado, com uma separa��o clara entre as camadas de l�gica de neg�cio (`Sistema`), entidades (`Usuario` e `Comunidade`) e a interface de acesso (`Facade`).  
  A documenta��o interna (JavaDoc) est� presente e bem escrita, facilitando o entendimento do c�digo.

- **Uso de Design Patterns**:  
  O padr�o **Facade** foi utilizado para simplificar a intera��o com o sistema, expondo apenas os m�todos necess�rios para os testes e encapsulando a l�gica de neg�cio.  
  A modularidade foi mantida, com as funcionalidades de comunidades e relacionamentos isoladas em classes espec�ficas.

### Melhorias na Arquitetur
A arquitetura do sistema foi mantida, mas com melhorias significativas para suportar as novas funcionalidades:

- **Interface de Acesso (Facade):**  
  - **Novidades:** Foram adicionados m�todos para gerenciar comunidades, relacionamentos (�dolos, paqueras, inimigos) e envio de mensagens em comunidades.  
  - **Novos M�todos:** `criarComunidade()`, `adicionarComunidade()`, `enviarMensagem()`, `lerMensagem()`, `adicionarIdolo()`, `adicionarPaquera()`, `adicionarInimigo()`.

- **L�gica de Neg�cio (Sistema):**  
  - **Novidades:**  
    - Gerenciamento de comunidades, incluindo cria��o, adi��o de membros e envio de mensagens.  
    - Relacionamentos avan�ados, como �dolos, paqueras e inimigos.  
    - Melhorias no tratamento de recados, com identifica��o do remetente.  
  - **Persist�ncia:** Agora inclui comunidades e relacionamentos, garantindo que todas as informa��es sejam mantidas entre execu��es.

- **Entidades (Usuario e Comunidade):**  
  - **Usuario:**  
    - **Novidades:** Adi��o de listas para gerenciar �dolos, paqueras e inimigos.  
    - **M�todos Novos:** `adicionarIdolo()`, `adicionarPaquera()`, `adicionarInimigo()`.  
  - **Comunidade:**  
    - **Novidades:** Implementa��o completa para gerenciar membros e mensagens.  
    - **M�todos Novos:** `adicionarMembro()`, `enviarMensagem()`, `lerMensagem()`.__

## 3. Novas Funcionalidades

### 3.1 Gerenciamento de Comunidades
- **Descri��o:** Agora � poss�vel criar comunidades, adicionar membros e enviar mensagens.  
- **Impacto no Design:**  
  - Adi��o da classe `Comunidade` para encapsular os dados e comportamentos relacionados.  
  - M�todos na classe `Sistema` para gerenciar comunidades e integrar com os usu�rios.

### 3.2 Relacionamentos Avan�ados
- **Descri��o:** Foram adicionados novos tipos de relacionamentos: �dolos, paqueras e inimigos.  
- **Impacto no Design:**  
  - Novos atributos e m�todos na classe `Usuario` para gerenciar esses relacionamentos.  
  - Valida��es adicionais para evitar conflitos entre relacionamentos (ex.: inimigos n�o podem ser adicionados como amigos).

### 3.3 Melhorias no Gerenciamento de Recados
- **Descri��o:** Agora os recados incluem o remetente, facilitando a identifica��o de quem enviou a mensagem.  
- **Impacto no Design:**  
  - Altera��o no m�todo `enviarRecado()` para incluir o login do remetente no conte�do do recado.

## 4. Padr�es de Design Utilizados

- **Facade**:  
  A classe `Facade` continua sendo a interface principal para os testes EasyAccept, encapsulando a l�gica de neg�cio e expondo apenas os m�todos necess�rios.

- **Encapsulamento**:  
  As funcionalidades de comunidades e relacionamentos foram encapsuladas em classes espec�ficas, seguindo o princ�pio de alta coes�o.


## 5. Persist�ncia e Gerenciamento de Sess�es

A persist�ncia foi ampliada para incluir comunidades e relacionamentos.  
- **Salvar Dados:** Agora, al�m dos usu�rios, as comunidades tamb�m s�o serializadas.  
- **Carregar Dados:** O sistema carrega automaticamente as comunidades e seus membros ao iniciar.  
- **Sess�es:** Mantidas como transit�rias, sem altera��es significativas.

## 6. Diagrama de Classes

O diagrama de classes foi atualizado para refletir as mudan�as no design, incluindo as novas funcionalidades e classes adicionadas.

![Editor _ Mermaid Chart-2025-05-05-235731.svg](Editor%20_%20Mermaid%20Chart-2025-05-05-235731.svg)

## 7. Conclus�o

O Milestone 2 trouxe melhorias significativas ao sistema Jackut, com a adi��o de funcionalidades avan�adas e ajustes no design para suportar a evolu��o do projeto. As mudan�as realizadas refor�am a modularidade e a extensibilidade do sistema, preparando-o para futuras expans�es.

- **Destaques:**  
  - Implementa��o de comunidades e relacionamentos avan�ados.  
  - Melhorias na persist�ncia e no gerenciamento de recados.  
  - Uso de boas pr�ticas de design para reduzir acoplamento e melhorar a coes�o.
