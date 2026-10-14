package cadcliente;
import java.util.List;
import java.sql.SQLException;
public interface IClienteDao {
    boolean criar(Cliente cliente);
    void leitura();
    Cliente buscar(int id) throws SQLException;
    boolean login(int id, String senha);
    void atualizar(int id, String nova_senha);
    void apagar();
}
