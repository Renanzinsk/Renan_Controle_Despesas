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
            this.status = "Pendente";
    }

    @Override
    public boolean pagar() {
        if (!"Paga".equals(this.status)) {
            this.status = "Paga";
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String getStatus() {
        return this.status;
    }

    public int getId() {
        return this.id;
    }

    public String getDescricao() {
        return this.descricao;
    }
    
    public double getValor() {
        return this.valor;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static void setContadorId(int id) {
        contadorId = id;
    }
    

    @Override
    public String toString() {
        return String.format("ID: %d | Desc: %s | Valor: R$%.2f | Status: %s",
                id, descricao, valor, status);
    }

    public abstract double getValorComImposto();
}
