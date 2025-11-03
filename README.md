
# Sistema de Controle de Despesas

Este é um sistema simplificado de controle de despesas. O objetivo principal é demonstrar como as classes e módulos se conectam usando os conceitos de Programação Orientada a Objetos (OOP).

O sistema **roda 100% em memória**, o que significa que os dados (usuários e despesas) são perdidos quando o programa é fechado. O foco é a **arquitetura** das classes.

## Os Módulos Principais

O sistema é dividido em 4 módulos (grupos de arquivos) principais:

### 1. O "Contrato" (A Interface)

Este módulo define as "regras" que as outras classes devem seguir.

* **Arquivo:** `Pagavel.java`
* **O que faz?** É um "contrato" que obriga qualquer classe de despesa a ter, no mínimo, os métodos `pagar()` e `getStatus()`.

### 2. Os "Moldes" de Despesa (Herança e Polimorfismo)

Este módulo define o que é uma despesa.

* **Arquivo "Mãe":** `DespesaBase.java`
    * **O que faz?** É a classe "Mãe" de todas as despesas.
    * Define o que toda despesa tem em comum: `id`, `descricao`, `valor`, `status` e a lógica padrão de `pagar()`.
    * Ela é **Abstrata**: não podemos criar uma "DespesaBase", apenas suas filhas.

* **Arquivos "Filhas":** `DespesaTransporte.java` e `DespesaEventual.java`
    * **O que fazem?** Elas **Herdam** tudo da `DespesaBase` (não precisam repetir código).
    * Elas se especializam: cada uma implementa sua própria regra de negócio no método `getValorComImposto()` (uma cobra 5% e a outra 10%).

### 3. Os "Gerenciadores" (A Lógica e a Memória)

Estes módulos funcionam como o "banco de dados em memória" e contêm a lógica de negócio.

* **Arquivos:** `Usuario.java` e `GerenciadorUsuarios.java`
    * **O que fazem?** `Usuario.java` é apenas um "molde" para guardar os dados.
    * `GerenciadorUsuarios.java` controla a lista de usuários, sabendo como `cadastrar()` e `autenticar()`.

* **Arquivo:** `GerenciadorDespesas.java`
    * **O que faz?** Controla a lista de despesas.
    * É aqui que o **Polimorfismo** acontece: ele guarda `DespesaTransporte` e `DespesaEventual` na *mesma lista* de `DespesaBase`, tratando ambas de forma igual, mesmo que elas tenham regras de imposto diferentes.

### 4. A Aplicação (O "Motor")

Este é o arquivo que executa o programa e "amarra" todos os outros módulos.

* **Arquivo:** `Sistema.java`
    * **O que faz?** Contém o método `main()` e controla os menus que o usuário vê.
    * Ele **usa** os outros módulos: pede ao `GerenciadorUsuarios` para logar e ao `GerenciadorDespesas` para adicionar ou listar despesas.