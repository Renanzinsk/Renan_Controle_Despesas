import java.util.ArrayList;
import java.util.List;


public class GerenciadorUsuarios {


    private List<Usuario> usuarios;

    public GerenciadorUsuarios() {
        this.usuarios = new ArrayList<>();
        this.cadastrar("admin", "admin");
    }

    public boolean cadastrar(String login, String senha) {
        for (Usuario u : usuarios) {
            if (u.getLogin().equals(login)) {
                System.out.println("Erro: Login já existe.");
                return false;
            }
        }
        Usuario novo = new Usuario(login, senha);
        this.usuarios.add(novo);
        System.out.println("Usuário " + login + " cadastrado.");
        return true;
    }

    public Usuario autenticar(String login, String senha) {
        for (Usuario u : usuarios) {
            if (u.getLogin().equals(login) && u.autenticar(senha)) {
                return u;
            }
        }
        return null; // Falha na autenticação
    }
}