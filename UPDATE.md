# Resumo das Alterações (MVP 1)

## 1. Refatoração (Separação de Responsabilidades)

* **Remoção de `System.out.println`:** Todas as classes de lógica (`GerenciadorUsuarios`, `GerenciadorDespesas`, `DespesaBase`) não imprimem mais nada no console.
* **Retorno de Métodos:**
    * `DespesaBase.pagar()` agora retorna `boolean` (sucesso ou falha).
    * `GerenciadorDespesas.listarPorStatus()` foi renomeado para `encontrarPorStatus()` e agora retorna uma `List<DespesaBase>`.
* **Centralização no `Sistema`:** A classe `Sistema` tornou-se a única responsável por imprimir mensagens e tratar os retornos dos métodos.

---

## 2. Persistência de Dados (PoC - Prova de Conceito)

* **Novos Métodos (`Gerenciadores`):**
    * `GerenciadorUsuarios` e `GerenciadorDespesas` receberam métodos `carregarDados()` e `salvarDados()`.
    * Estes métodos leem e escrevem em arquivos (`usuarios.dat`, `despesas.dat`).
* **Novos Métodos (Modelos):**
    * `Usuario` ganhou um `getSenha()` para permitir o salvamento.
    * `DespesaBase` ganhou `getValor()`, `setStatus(String status)`, `setId(int id)` e `setContadorId(int id)` para carregar dados salvos.
* **Orquestração no `Sistema`:**
    * O construtor do `Sistema` agora chama `_carregarDados()`.
    * O método `run()` do `Sistema` agora chama `_salvarDados()` antes de finalizar.
* **Lógica de Inicialização:** O cadastro do `admin` foi movido para o `carregarDados()` (executado apenas se o arquivo `usuarios.dat` não existir).