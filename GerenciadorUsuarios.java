import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

public class GerenciadorUsuarios {

    private List<Usuario> usuarios;
    private static final String ARQUIVO_USUARIOS = "usuarios.dat";

    public GerenciadorUsuarios() {
        this.usuarios = new ArrayList<>();
    }

    public boolean cadastrar(String login, String senha) {
        for (Usuario u : usuarios) {
            if (u.getLogin().equals(login)) {
                return false;
            }
        }
        Usuario novo = new Usuario(login, senha);
        this.usuarios.add(novo);
        return true;
    }

    public Usuario autenticar(String login, String senha) {
        for (Usuario u : usuarios) {
            if (u.getLogin().equals(login) && u.autenticar(senha)) {
                return u;
            }
        }
        return null;
    }

    // --- MÉTODOS ADICIONADOS PARA PERSISTÊNCIA ---

    public void carregarDados() {
        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO_USUARIOS))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(",");
                if (partes.length == 2) {
                    String login = partes[0];
                    String senha = partes[1];
                    this.usuarios.add(new Usuario(login, senha));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo de usuários não encontrado, criando admin padrão.");
            this.cadastrar("admin", "admin");
        } catch (IOException e) {
            System.out.println("Erro ao carregar usuários: " + e.getMessage());
        }
    }

    public void salvarDados() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARQUIVO_USUARIOS))) {
            for (Usuario u : usuarios) {
                pw.println(u.getLogin() + "," + u.getSenha());
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar usuários: " + e.getMessage());
        }
    }
}
