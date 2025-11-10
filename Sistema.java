import java.util.List;
import java.util.Scanner;

public class Sistema {

    private GerenciadorUsuarios gerUsuarios;
    private GerenciadorDespesas gerDespesas;
    private Scanner scanner;
    private Usuario usuarioLogado;

    public Sistema() {
        this.gerUsuarios = new GerenciadorUsuarios();
        this.gerDespesas = new GerenciadorDespesas();
        this.scanner = new Scanner(System.in);
        this.usuarioLogado = null;

        // Carrega os dados salvos na inicialização
        _carregarDados();
    }

    public static void main(String[] args) {
        Sistema app = new Sistema();
        app.run();
    }

    public void run() {
        if (_menuLogin()) {
            _menuPrincipal();
        }
        
        System.out.println("Sistema finalizado. Salvando dados...");
        _salvarDados();
        scanner.close();
    }


    private void _carregarDados() {
        System.out.println("Carregando dados do sistema...");
        gerUsuarios.carregarDados();
        gerDespesas.carregarDados();
        System.out.println("Carregamento concluído.");
    }

    private void _salvarDados() {
        gerUsuarios.salvarDados();
        gerDespesas.salvarDados();
        System.out.println("Dados salvos com sucesso.");
    }


    private boolean _menuLogin() {
        // ... (código inalterado) ...
        while (true) {
            System.out.println("\n--- LOGIN ---");
            System.out.println("1. Login");
            System.out.println("2. Cadastrar Novo Usuário");
            System.out.println("3. Sair");
            System.out.print("Escolha: ");
            String escolha = scanner.nextLine();

            if (escolha.equals("1")) {
                System.out.print("Login: ");
                String login = scanner.nextLine();
                System.out.print("Senha: ");
                String senha = scanner.nextLine();
                
                this.usuarioLogado = gerUsuarios.autenticar(login, senha);
                if (this.usuarioLogado != null) {
                    System.out.println("Bem-vindo, " + this.usuarioLogado.getLogin());
                    return true;
                } else {
                    System.out.println("Login ou senha inválidos.");
                }
            } else if (escolha.equals("2")) {
                System.out.print("Novo Login: ");
                String login = scanner.nextLine();
                System.out.print("Nova Senha: ");
                String senha = scanner.nextLine();
                
                boolean sucesso = gerUsuarios.cadastrar(login, senha);
                if (sucesso) {
                    System.out.println("Usuário " + login + " cadastrado.");
                } else {
                    System.out.println("Erro: Login já existe.");
                }
            } else if (escolha.equals("3")) {
                return false;
            }
        }
    }

    private void _menuPrincipal() {
        // ... (código inalterado) ...
        while (true) {
            System.out.printf("\n--- MENU PRINCIPAL (%s) ---\n", this.usuarioLogado.getLogin());
            System.out.println("1. Entrar Despesa");
            System.out.println("2. Anotar Pagamento");
            System.out.println("3. Listar Despesas em Aberto");
            System.out.println("4. Listar Despesas Pagas");
            System.out.println("5. Sair (Logout)");
            System.out.print("Escolha: ");
            String escolha = scanner.nextLine();

            switch (escolha) {
                case "1": _uiEntrarDespesa(); break;
                case "2": _uiAnotarPagamento(); break;
                case "3": _uiListarDespesas("Pendente"); break;
                case "4": _uiListarDespesas("Paga"); break;
                case "5": return;
                default: System.out.println("Opção inválida.");
            }
        }
    }

    private void _uiListarDespesas(String status) {
        // ... (código inalterado) ...
        System.out.printf("\n--- Despesas com Status: %S ---\n", status);
        
        List<DespesaBase> despesas = gerDespesas.encontrarPorStatus(status);

        if (despesas.isEmpty()) {
            System.out.println("Nenhuma despesa encontrada.");
        } else {
            for (DespesaBase d : despesas) {
                System.out.println(d.toString());
                System.out.printf("   (Valor com imposto: R$%.2f)\n", d.getValorComImposto());
            }
        }
    }

    private void _uiEntrarDespesa() {
        // ... (código inalterado) ...
        try {
            System.out.println("\n--- Nova Despesa ---");
            System.out.print("Descrição: ");
            String desc = scanner.nextLine();
            System.out.print("Valor (R$): ");
            double valor = Double.parseDouble(scanner.nextLine());

            System.out.println("Qual o tipo da despesa?");
            System.out.println("1. Transporte (5% imposto)");
            System.out.println("2. Eventual (10% imposto)");
            System.out.print("Escolha: ");
            String tipo = scanner.nextLine();
            
            DespesaBase novaDespesa;
            if (tipo.equals("1")) {
                novaDespesa = new DespesaTransporte(desc, valor);
            } else if (tipo.equals("2")) {
                novaDespesa = new DespesaEventual(desc, valor);
            } else {
                System.out.println("Tipo inválido. Despesa cancelada.");
                return;
            }
            
            gerDespesas.adicionar(novaDespesa);
            System.out.println("Despesa adicionada com sucesso.");

        } catch (NumberFormatException e) {
            System.out.println("Erro: Valor inválido.");
        }
    }

    private void _uiAnotarPagamento() {
        // ... (código inalterado) ...
        try {
            System.out.println("\n--- Pagar Despesa ---");
            
            _uiListarDespesas("Pendente"); 
            System.out.print("\nDigite o ID da despesa a pagar (ou 0 para cancelar): ");
            int id = Integer.parseInt(scanner.nextLine());

            if (id == 0) return;
            
            DespesaBase despesa = gerDespesas.encontrarPorId(id);
            if (despesa != null) {
                boolean sucesso = despesa.pagar();
                if (sucesso) {
                    System.out.printf("Despesa ID %d (%s) foi paga.\n", despesa.getId(), despesa.getDescricao());
                } else {
                    System.out.printf("Despesa ID %d (%s) já estava paga.\n", despesa.getId(), despesa.getDescricao());
                }
            } else {
                System.out.println("ID não encontrado.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Erro: ID inválido.");
        }
    }
}
