package cadcliente;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main {
    static ArrayList<Cliente> listaClientes = new ArrayList<>();
    public static void Menu(){
        System.out.println("\n=== MENU ===");
                System.out.println("1 - CADASTRO");
                System.out.println("2 - BUSCAR CONTA");
                System.out.println("3 - ATUALIZAÇÃO DE DADOS");
                System.out.println("0 - Sair");
                System.out.print("Opção: ");
    }
    public static void main(String[] args) {
        //conexão com o mysql
        try(Connection conexao = connectionFactory.getConnection();
            Scanner sc = new Scanner(System.in)){
            if (conexao == null){
                System.out.print("ERRO NA CONEXÃO COM O BANCO DE DADOS");
            }
            IClienteDao dao = new ClienteDao(conexao);
            int id = 0;
        int esc;
        boolean condMenu = true;
        System.out.print("SISTEMA DE CLIENTES");
        while (condMenu == true) {
            Menu();
            esc = sc.nextInt();
            sc.nextLine();
            if (esc == 1){
                id += 1;
                System.out.println("CADASTRO DE CONTAS");
                System.out.println("DIGITE O NOME: ");
                String nome = sc.nextLine();
                System.out.println("DIGITE A IDADE: ");
                int idade = sc.nextInt();
                sc.nextLine();
                System.out.println("DIGITE A SENHA: ");
                String senha = sc.nextLine();
                Cliente cliente = new Cliente(nome, idade, senha, id);
                boolean cadastro = dao.criar(cliente);
                if (cadastro == true){
                    System.out.print("CADASTRO REALIZADO");
                }
                else if (cadastro == false){
                    System.out.print("CADASTRO NÃO REALIZADO");
                }
            }
            if (esc == 2){
                System.out.println("VISUALIZAÇÃO DE CADASTROS");
                System.out.print("DIGITE O ID PARA A BUSCA: ");
                int id_busca = sc.nextInt();
                sc.nextLine();
                try{
                    Cliente busca = dao.buscar(id_busca);
                    if (busca == null){
                        System.out.println("CONTA NÃO ENCONTRADA");
                    }
                    else{
                        System.out.println("CONTA ENCONTRADA!\nID: " + busca.getId() + "\nNOME: " + busca.getNome() + "\nIDADE: " + busca.getIdade());
                    }
                    System.out.println("DIGITE QUALQUER TECLA PARA CONTINUAR: ");
                
                }catch(SQLException e){
                    System.out.print("ERRO NA BUSCA");
                }
                String cont = sc.nextLine();
            }
            if (esc == 3){
                System.out.println("MUDAR A SENHA");
                System.out.print("DIGITE O ID PARA A BUSCA: ");
                int id_busca = sc.nextInt();
                sc.nextLine();
                System.out.print("DIGITE A SENHA ANTIGA: ");
                String senha = sc.nextLine();
                boolean conta_login = dao.login(id_busca, senha);
                if (conta_login){
                    System.out.print("DIGITE A NOVA SENHA: ");
                    String nova_senha = sc.nextLine();
                    dao.atualizar(id_busca, nova_senha);
                    System.out.print("\nDIGITE QUALQUER TECLA PARA CONTINUAR: ");
                    String cont = sc.nextLine();
                }
            }
            if (esc == 0){
                System.out.print("...");
                condMenu = false;
            }
        }
        }catch (SQLException e){
            e.getErrorCode();
        }
    }
}