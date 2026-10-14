package cadcliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.cj.protocol.Resultset;

public class ClienteDao implements IClienteDao {
    private PreparedStatement pstmCreate;
    private PreparedStatement pstmRead;
    private PreparedStatement pstmReadByNumero;
    private PreparedStatement pstmUpdate;
    private PreparedStatement pstmDelete;
    private final Connection conexao;

    public ClienteDao(Connection connection){
        this.conexao = connection;
        try {
            String sqlCreate = "INSERT INTO clientes (nome, idade, senha) VALUES (?, ?, ?)";
            String sqlUpdate = "SELECT senha FROM clientes WHERE id = ?";
            this.pstmCreate = connection.prepareStatement(sqlCreate);
            this.pstmUpdate = connection.prepareStatement(sqlUpdate);
        }catch(SQLException e){
            System.out.print("ERRO AO PREPARAR COMANDO SQL: " + e);
        }
    }
    @Override
    public boolean criar(Cliente cliente){
        try {
            pstmCreate.setString(1, cliente.getNome());
            pstmCreate.setInt(2, cliente.getIdade());
            pstmCreate.setString(3, cliente.getSenha());
            pstmCreate.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.print("ERRO NA EXECUÇÃO da criação da conta");
        }
        return true;
    }
    @Override
    public Cliente buscar(int id){
        String sql = "SELECT nome, idade, senha FROM clientes WHERE id = ?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)){
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()){
                    String nome = rs.getString("nome");
                    int idade = rs.getInt("idade");
                    String senha = rs.getString("senha");
                    return new Cliente(nome, idade, senha, id);
                }
            }
        } catch (SQLException e) {
            System.out.print("ERRO NA BUSCA DE SQL");
        }
        return null;
    }
    @Override
    public boolean login(int id, String dig_senha){
        Boolean acesso = false;
        String  sql = "SELECT senha FROM clientes WHERE id = ?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()){
                    String senha = rs.getString("senha");
                    if (dig_senha.equals(senha)){
                        System.out.println("ACESSO CONCEDIDO");
                        acesso = true;
                        return true;

                    }
                    else{
                        acesso = false;
                        return false;
                    }
                }
            } catch (SQLException e) {
                System.out.print(e.getErrorCode());
            }
        } catch (SQLException e) {
            System.out.print(e.getErrorCode());
        }
        if (acesso){
            return true;
        }
        else{
            System.out.print("ERRO, SENHA INCORRETA");
            return false;
        }
    }
    @Override
    public void atualizar(int id, String nova_senha) {
        String sql = "UPDATE clientes SET senha = ? WHERE id = ?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)){
            ps.setString(1, nova_senha);
            ps.setInt(2, id);
            ps.executeUpdate();
            System.out.print("SENHA ALTERADA!");
            } catch (SQLException e) {
            System.out.print("ERRO NA BUSCA DE SQL\n" + e.getMessage());
        }
    }
    @Override
    public void apagar(){
    }
    @Override
    public void leitura(){
    }
}
