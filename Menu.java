import java.io.*;

public class Menu {
    private static User login() throws IOException, InterruptedException {
        System.out.println("--------------------------------------------------");
        System.out.println("         Bem-vindo ao iFace | Fazer login         ");
        System.out.println("--------------------------------------------------");
        try {
            System.out.print("Email: ");
            String email = Main.input.next();
            if(!email.matches("^([\\w\\-]+.)*[\\w\\-]+@([\\w\\-]+.)+([\\w\\-]{2,3})")) throw new Exception("EMAIL INVALIDO");
            User user = Main.search(Main.users, email.toUpperCase());
            if(user == null) throw new Exception("USUÁRIO NÃO ENCONTRADO");
            
            System.out.print("Senha: ");
            String password = Main.input.next();
            Main.clear();
            if(!user.getPassword().equals(password)) throw new Exception("USUÁRIO NÃO ENCONTRADO");

            return user;
        }
        catch (Exception e) {
            Main.clear();
            System.out.println(e.getMessage());
        }

        return null;
    }

    private static void createAccount() throws IOException, InterruptedException {
        System.out.println("--------------------------------------------------");
        System.out.println("         Bem-vindo ao iFace | Criar conta         ");
        System.out.println("--------------------------------------------------");
        System.out.print("Nome: ");

        try {
            String name = Main.input.next();
            if(!name.matches("[A-z]+")) throw new Exception("NOME NÃO PODE TER ACENTOS OU NÚMEROS");
            name = name.substring(0,1).toUpperCase().concat(name.substring(1));

            Main.clear();
            System.out.println("--------------------------------------------------");
            System.out.println("         Bem-vindo ao iFace | Criar conta         ");
            System.out.println("--------------------------------------------------");
            System.out.println("Olá "+name);
            System.out.println();

            System.out.print("Email: ");
            String email = Main.input.next();
            if(!email.matches("^([\\w\\-]+.)*[\\w\\-]+@([\\w\\-]+.)+([\\w\\-]{2,3})")) throw new Exception("EMAIL INVALIDO");
            if(Main.search(Main.users, email.toUpperCase()) != null)  throw new Exception("EMAIlL JÁ CADASTRADO");

            System.out.print("Senha: ");
            String password = Main.input.next();

            User user = new User(name, email, password);
            Main.users.add(user);

            Main.clear();
            System.out.println("Conta criada com sucesso");
        } catch(Exception e) {
            Main.clear();
            System.out.println(e.getMessage());
        }
    }

    public void print() throws IOException, InterruptedException {
        int option = -1;
        do {
            System.out.println("--------------------------------------------------");
            System.out.println("                Bem-vindo ao iFace                ");
            System.out.println("--------------------------------------------------");
            System.out.println("|1| Fazer login");
            System.out.println("|2| Criar conta");
            System.out.println("|0| Sair");
            System.out.println();
            System.out.print("> ");

            try {
                option = Integer.parseInt(Main.input.next());

                Main.clear();
                switch(option) {
                    case 1:
                        User user = login();
                        if(user != null) {
                            Profile profile = new Profile(user);
                            profile.menu();
                        }
                        break;
                    case 2:
                        createAccount();
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("OPCAO INVALIDA");
                }
            } catch(Exception e) {
                Main.clear();
                System.out.println("OPCAO INVALIDA");
            }
        } while(option != 0);
    }
}