
public class DespesaTransporte extends DespesaBase {

	public DespesaTransporte(String descricao, double valor) {
		super(descricao, valor);
	}

	@Override
	public double getValorComImposto() {
		// 5% de imposto
		return this.valor * 1.05;
	}

	@Override
	public String toString() {
		return "[TRANSPORTE] " + super.toString();
	}
}
