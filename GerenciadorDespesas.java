import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

public class GerenciadorDespesas {

    private List<DespesaBase> despesas;
    private static final String ARQUIVO_DESPESAS = "despesas.dat";

    public GerenciadorDespesas() {
        this.despesas = new ArrayList<>();
    }

    public void adicionar(DespesaBase despesa) {
        this.despesas.add(despesa);
    }

    public DespesaBase encontrarPorId(int id) {
        for (DespesaBase d : despesas) {
            if (d.getId() == id) {
                return d;
            }
        }
        return null;
    }

    public List<DespesaBase> encontrarPorStatus(String status) {
        List<DespesaBase> encontradas = new ArrayList<>();
        for (DespesaBase d : despesas) {
            if (d.getStatus().equalsIgnoreCase(status)) {
                encontradas.add(d);
            }
        }
        return encontradas;
    }

    // --- MÉTODOS ADICIONADOS PARA PERSISTÊNCIA ---

    public void carregarDados() {
        int maxId = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO_DESPESAS))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(",");
                if (partes.length == 5) {
                    String tipo = partes[0];
                    int id = Integer.parseInt(partes[1]);
                    String desc = partes[2];
                    double valor = Double.parseDouble(partes[3]);
                    String status = partes[4];

                    DespesaBase despesa;
                    if (tipo.equals("TRANSPORTE")) {
                        despesa = new DespesaTransporte(desc, valor);
                    } else {
                        despesa = new DespesaEventual(desc, valor);
                    }
                    
                    despesa.setId(id);
                    despesa.setStatus(status);
                    
                    this.despesas.add(despesa);

                    if (id > maxId) {
                        maxId = id;
                    }
                }
            }
            DespesaBase.setContadorId(maxId);
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo de despesas não encontrado. Começando do zero.");
        } catch (IOException | NumberFormatException e) {
            System.out.println("Erro ao carregar despesas: " + e.getMessage());
        }
    }

    public void salvarDados() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARQUIVO_DESPESAS))) {
            for (DespesaBase d : despesas) {
                String tipo = (d instanceof DespesaTransporte) ? "TRANSPORTE" : "EVENTUAL";
                
                pw.println(String.format("%s,%d,%s,%.2f,%s",
                        tipo,
                        d.getId(),
                        d.getDescricao(),
                        d.getValor(),
                        d.getStatus()
                ));
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar despesas: " + e.getMessage());
        }
    }
}
