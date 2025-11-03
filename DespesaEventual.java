public class DespesaEventual extends DespesaBase {

    public DespesaEventual(String descricao, double valor) {
        super(descricao, valor);
    }
    @Override
    public double getValorComImposto() {
        return this.valor * 1.10;
    }

    @Override
    public String toString() {
        return "[EVENTUAL] " + super.toString();
    }
}