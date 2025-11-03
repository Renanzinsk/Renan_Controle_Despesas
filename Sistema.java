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
    }

    public static void main(String[] args) {
        Sistema app = new Sistema();
        app.run();
    }

    public void run() {
        if (_menuLogin()) {
            _menuPrincipal();
        }
        System.out.println("Sistema finalizado.");
        scanner.close();
    }

    private boolean _menuLogin() {
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
                gerUsuarios.cadastrar(login, senha);
            } else if (escolha.equals("3")) {
                return false;
            }
        }
    }

    private void _menuPrincipal() {
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
                case "3": gerDespesas.listarPorStatus("Pendente"); break;
                case "4": gerDespesas.listarPorStatus("Paga"); break;
                case "5": return;
                default: System.out.println("Opção inválida.");
            }
        }
    }

    private void _uiEntrarDespesa() {
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
            } else {
                novaDespesa = new DespesaEventual(desc, valor);
            }
            
            gerDespesas.adicionar(novaDespesa);

        } catch (NumberFormatException e) {
            System.out.println("Erro: Valor inválido.");
        }
    }

    private void _uiAnotarPagamento() {
        try {
            System.out.println("\n--- Pagar Despesa ---");
            gerDespesas.listarPorStatus("Pendente");
            System.out.print("Digite o ID da despesa a pagar: ");
            int id = Integer.parseInt(scanner.nextLine());
            
            DespesaBase despesa = gerDespesas.encontrarPorId(id);
            if (despesa != null) {
                if (despesa.getStatus().equals("Pendente")) {
                    despesa.pagar();
                } else {
                    System.out.println("Esta despesa já foi paga.");
                }
            } else {
                System.out.println("ID não encontrado.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Erro: ID inválido.");
        }
    }
}