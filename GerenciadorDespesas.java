import java.util.ArrayList;
import java.util.List;

/**
 * Gerencia o "banco de dados" em memória de despesas.
 * É o principal ponto de demonstração de Polimorfismo.
 */
public class GerenciadorDespesas {

    // REQ: Polimorfismo
    // A lista é do tipo Abstrato (DespesaBase), mas aceita
    // objetos dos tipos Concretos (Transporte, Eventual).
    private List<DespesaBase> despesas;

    public GerenciadorDespesas() {
        this.despesas = new ArrayList<>();
    }

    public void adicionar(DespesaBase despesa) {
        this.despesas.add(despesa);
        System.out.println("Despesa adicionada com sucesso.");
    }

    public DespesaBase encontrarPorId(int id) {
        for (DespesaBase d : despesas) {
            if (d.getId() == id) {
                return d;
            }
        }
        return null;
    }

    public void listarPorStatus(String status) {
        System.out.printf("\n--- Despesas com Status: %S ---\n", status);
        boolean encontrou = false;
        
        for (DespesaBase d : despesas) {
            if (d.getStatus().equalsIgnoreCase(status)) {
                
                System.out.println(d.toString());
                System.out.printf("   (Valor com imposto: R$%.2f)\n", d.getValorComImposto());
                encontrou = true;
            }
        }

        if (!encontrou) {
            System.out.println("Nenhuma despesa encontrada.");
        }
    }
}