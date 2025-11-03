
public abstract class DespesaBase implements Pagavel {


    private static int contadorId = 0;

    protected int id;
    protected String descricao;
    protected double valor;
    protected String status;


    public DespesaBase(String descricao, double valor) {
        this.id = ++contadorId;
        this.descricao = descricao;
        this.valor = valor;
        // Inicialmente pendente
        this.status = "Pendente";
    }

    @Override
    public void pagar() {
        if (!"Paga".equals(this.status)) {
            this.status = "Paga";
            System.out.printf("Despesa ID %d (%s) foi paga.\n", this.id, this.descricao);
        } else {
            System.out.printf("Despesa ID %d (%s) já está paga.\n", this.id, this.descricao);
        }
    }

    @Override
    public String getStatus() {
        return this.status;
    }

    public int getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return String.format("ID: %d | Desc: %s | Valor: R$%.2f | Status: %s",
                id, descricao, valor, status);
    }

    public abstract double getValorComImposto();
}