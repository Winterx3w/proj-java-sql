package cadcliente;
public class Cliente {
    private String nome;
    private int idade;
    private String senha;
    private int id;
    public Cliente(String nome, int idade, String senha, int id){
        this.nome = nome;
        this.idade = idade;
        this.senha = senha;
        this.id = id;
    }
    public int getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public int getIdade() {
        return idade;
    }
    public void setIdade(int idade) {
        this.idade = idade;
    }
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
}
