import java.io.*;
import java.text.*;
import java.util.*;

public class Profile {
    private User connected;

    public Profile(User connected) {
        this.connected = connected;
    }

    private void title(String section) {
        System.out.println("--------------------------------------------------");
        System.out.println("iFace | "+section);
        System.out.println("--------------------------------------------------");
    }

    private void endOfMenu() {
        System.out.println("|0| Voltar");
        System.out.println();
        System.out.print("> ");
    }

    private <T extends Message> void printText(T object) {
        System.out.print(new SimpleDateFormat("[dd MMMM yyyy]|HH:mm|").format(object.getDate()).toUpperCase());
        System.out.println(" "+object.getSender().getNameLastName()+" disse: "+object.getText());
    }

    private void editProfile() throws IOException, InterruptedException {
        int option = -1;
        do {
            title("Perfil > Editar perfil");
            System.out.println();
            System.out.println("|1| Nome");
            System.out.println("|2| Sobrenome");
            System.out.println("|3| Email");
            System.out.println("|4| Senha");
            endOfMenu();
    
            try {
                option = Integer.parseInt(Main.input.next());
                switch(option) {
                    case 1:
                        System.out.println();
                        System.out.print("Nome: ");
                        String name = Main.input.next();
                        if(!name.matches("[A-z]+")) throw new Exception("NOME NÃO PODE TER ACENTOS OU NÚMEROS");
                        name = name.substring(0,1).toUpperCase().concat(name.substring(1));

                        connected.setName(name);
                        Main.clear();
                        System.out.println("Nome alterado com sucesso");
                        break;
                    case 2:
                        System.out.println();
                        System.out.print("Sobrenome: ");
                        String lastName = Main.input.next();
                        if(!lastName.matches("[A-z]+")) throw new Exception("NOME NÃO PODE TER ACENTOS OU NÚMEROS");
                        lastName = lastName.substring(0,1).toUpperCase().concat(lastName.substring(1));

                        connected.setLastName(lastName);
                        Main.clear();
                        System.out.println("Sobrenome alterado com sucesso");
                        break;
                    case 3:
                        System.out.println();
                        System.out.print("Email: ");
                        String email = Main.input.next();
                        if(!email.matches("^([\\w\\-]+.)*[\\w\\-]+@([\\w\\-]+.)+([\\w\\-]{2,3})")) throw new Exception("EMAIL INVALIDO");
                        if(Main.search(Main.users, email.toUpperCase()) != null)  throw new Exception("EMAIL JÁ CADASTRADO");
                        
                        connected.setEmail(email);
                        Main.clear();
                        System.out.println("Email alterado com sucesso");
                        break;
                    case 4:
                        System.out.println();
                        System.out.print("Senha: ");
                        String password = Main.input.next();
                        connected.setPassowrd(password);
                        Main.clear();
                        System.out.println("Senha alterada com sucesso");
                        break;
                    case 0:
                        Main.clear();
                        return;
                    default:
                        Main.clear();
                        System.out.println("OPCAO INVALIDA");
                }
            } catch(NumberFormatException e) {
                Main.clear();
                System.out.println("OPCAO INVALIDA");
            } catch(Exception e) {
                Main.clear();
                System.out.println(e.getMessage());
            }
        } while(option != 0);
    }

    private boolean removeAccount() throws IOException, InterruptedException {
        int option = -1;
        do {
            title("Perfil > Encerrar conta");
            System.out.println();
            System.out.println("|1| Encerrar minha conta");
            endOfMenu();
                
            try {
                option = Integer.parseInt(Main.input.next());
                switch(option) {
                    case 1:
                        for(User friend : connected.getFriends())
                            if(friend.getChats().containsKey(connected)) friend.getChats().remove(connected);

                        for(User friend : connected.getFriends())  friend.getFriends().remove(connected);

                        for(Community community : connected.getCommunities()) community.getMembers().remove(connected);

                        for(Community myCommunity : connected.getMyCommunities()) {
                            for(User member : myCommunity.getMembers())
                                member.getCommunities().remove(myCommunity);
                        }
                        
                        for(Post p : Main.feed)
                            if(p.getSender().equals(connected)) Main.feed.remove(p);

                        Main.users.remove(connected);

                        Main.clear();
                        System.out.println("Conta encerrada com sucesso");
                        return false;
                    case 0:
                        Main.clear();
                        return true;
                    default:
                        Main.clear();
                        System.out.println("OPCAO INVALIDA");
                }
            } catch(Exception e) {
                Main.clear();
                System.out.println("OPCAO INVALIDA");
            }
        } while(option != 0);
        return true;
    }

    private boolean profile() throws IOException, InterruptedException {
        Main.clear();
        int option = -1;
        do {
            title("Perfil");
            System.out.println("Nome: "+connected.getName());
            System.out.println("Sobrenome: " +connected.getLastName());
            System.out.println("Email: "+connected.getEmail());
            System.out.println();

            System.out.println("|1| Editar perfil");
            System.out.println("|2| Encerrar conta do iFace");
            endOfMenu();

            try {
                option = Integer.parseInt(Main.input.next());
                
                Main.clear();
                switch(option) {
                    case 1:
                        editProfile();
                        break;
                    case 2:
                        if(!removeAccount()) return false;
                        break;
                    case 0:
                        return true;
                    default:
                        System.out.println("OPCAO INVALIDA");
                }
            } catch(Exception e) {
                Main.clear();
                System.out.println("OPCAO INVALIDA");
            }
        } while(option != 0);
        return true;
    }

    private void invitationFriend() throws IOException, InterruptedException {
        int option = -1;
        do {
            title("Amigos > Solicitações de amizade");
            int i = 0;
            for(User user : connected.getInvitations()) {
                System.out.println("|"+(i+1)+"| "+user.getNameLastName());
                i++;
            }
            System.out.println();
            endOfMenu();

            try {
                option = Integer.parseInt(Main.input.next());

                if(option != 0) {
                    System.out.println();
                    System.out.println("Solicitação de "+connected.getInvitations().get(option-1).getNameLastName()+":");
                    System.out.println();
                    System.out.println("|1| Aceitar");
                    System.out.println("|2| Recusar");
                    endOfMenu();

                    int action = Integer.parseInt(Main.input.next());
                    Main.clear();
                    switch(action) {
                        case 1:
                            connected.setFriend(connected.getInvitations().get(option-1));
                            connected.getInvitations().get(option-1).setFriend(connected);
                            connected.getInvitations().remove(option-1);
                            return;
                        case 2:
                            connected.getInvitations().remove(option-1);
                            return;
                        case 0:
                            Main.clear();
                            return;
                        default:
                            Main.clear();
                            System.out.println("OPCAO INVALIDA");
                    }
                }
            } catch(Exception e) {
                Main.clear();
                System.out.println("OPCAO INVALIDA");
            }
        } while (option != 0);
    }

    private void friends() throws IOException, InterruptedException {
        Main.clear();
        int option = -1;
        do {
            title("Amigos");
            if(!connected.getFriends().isEmpty()) {
                for(User u : connected.getFriends())
                    System.out.println(u.getNameLastName());
            }
            else System.out.println("Sem amigos");

            System.out.println();
            System.out.println("|1| Buscar novo amigo");
            if(!connected.getInvitations().isEmpty()) System.out.println("|2| Solicitações de amizade");
            endOfMenu();

            try {
                option = Integer.parseInt(Main.input.next());
                switch(option) {
                    case 1:
                        System.out.println();
                        System.out.print("Buscar novo amigo pelo email: ");
                        String email = Main.input.next();
                        if(!email.matches("^([\\w\\-]+.)*[\\w\\-]+@([\\w\\-]+.)+([\\w\\-]{2,3})")) throw new Exception("EMAIL INVALIDO");
                        if(email.equals(connected.getEmail())) throw new Exception("USUÁRIO NÃO ENCONTRADO");

                        User user = Main.search(Main.users, email.toUpperCase());
                        if(user == null) throw new Exception("USUÁRIO NÃO ENCONTRADO");
                        if(user.getInvitations().contains(connected) || connected.getInvitations().contains(user)) throw new Exception("CONVITE PARA "+user.getNameLastName().toUpperCase()+" JÁ ENVIADO");
                        if(connected.getFriends().contains(user)) throw new Exception(user.getNameLastName().toUpperCase()+" JÁ FAZ PARTE DA SUA LISTA DE AMIGOS");

                        user.setInvitation(connected);
                        Main.clear();
                        System.out.println("Convite enviado para "+user.getNameLastName());
                        break;
                    case 2:
                        Main.clear();
                        if(!connected.getInvitations().isEmpty())
                            invitationFriend();
                        else
                            System.out.println("OPCAO INVALIDA");
                        break;
                    case 0:
                        Main.clear();
                        return;
                    default:
                        Main.clear();
                        System.out.println("OPCAO INVALIDA");
                }
            } catch(NumberFormatException e) {
                Main.clear();
                System.out.println("OPCAO INVALIDA");
            } catch(Exception e) {
                Main.clear();
                System.out.println(e.getMessage());
            }
        } while(option != 0);
    }

    private void chats() throws IOException, InterruptedException {
        Main.clear();
        int option = -1;
        do {
            title("Recados");
            if(!connected.getFriends().isEmpty()) {
                int i = 0;
                for(User user : connected.getFriends()) {
                    System.out.println("|"+(i+1)+"| "+user.getNameLastName());
                    i++;
                }
            }
            else System.out.println("Sem amigos");

            System.out.println();
            endOfMenu();
            
            try {
                option = Integer.parseInt(Main.input.next());
                Main.clear();

                User friend = null;
                if(option !=0) friend = connected.getFriends().get(option-1);

                if((option != 0) && (friend != null)) {
                    String text;
                    do {
                        Main.clear();
                        title("Recados > "+connected.getFriends().get(option-1).getNameLastName());
                        
                        if(connected.getChats().get(friend) != null && !connected.getChats().get(friend).isEmpty())
                            for(Message message : connected.getChats().get(friend))
                                printText(message);
                        else {
                            connected.setChat(friend);
                            friend.setChat(connected);
                        }

                        System.out.println();
                        endOfMenu();
                        text = Main.input.nextLine();

                        if(!text.equals("0") && !text.isEmpty()) {
                            Message message = new Message(connected, text, Calendar.getInstance().getTime());

                            connected.setChatMessage(friend, message);
                            friend.setChatMessage(connected, message);
                        }
                    } while(!text.equals("0"));
                    Main.clear();
                }
            } catch(Exception e) {
                Main.clear();
                System.out.println("OPCAO INVALIDA");
            }
        } while(option != 0);
    }

    private void addComunity() throws IOException, InterruptedException {
        Main.input.nextLine();
        System.out.println();
        System.out.print("Buscar comunidade pelo nome: ");
        String name = Main.input.nextLine();
        Main.clear();
        Community community = Main.search(Main.communities, name.toUpperCase());
        try {
            if(community == null) throw new Exception("COMUNIDADE NÃO ENCONTRADA");
            if(community.getCreator().equals(connected) || connected.getCommunities().contains(community)) throw new Exception("VOCÊ JÁ FAZ PARTE DE "+community.getName().toUpperCase());
            
            community.setInvitation(connected);
            System.out.println("Convite enviado para fazer parte de "+community.getName());
        } catch(Exception e) {
            Main.clear();
            System.out.println(e.getMessage());
        }
    }

    private void createComunity() throws IOException, InterruptedException {
        Main.clear();
        Main.input.nextLine();
        title("Comunidades > Criar comunidade");
        System.out.print("Nome: ");
        String name = Main.input.nextLine();
    
        Community community = Main.search(Main.communities, name);
        if(community == null) {
            System.out.print("Descrição: ");
            String description = Main.input.nextLine();

            Community newCommunity = new Community(connected, name, description);
            Main.communities.add(newCommunity);
            connected.setMyComunity(newCommunity);
            connected.setComunity(newCommunity);

            Main.clear();
            System.out.println("Comunidade criada");
        }
        else {
            Main.clear();
            System.out.println("COMUNIDADE JÁ EXISTE");
        }
    }

    private boolean selectInvitationComunity(Community community) throws IOException, InterruptedException {
        Main.clear();
        int option = -1;
        do {
            title("Comunidades > Convites para "+community.getName());
            int j = 0;
            for(User u : community.getInvitations()) {
                System.out.println("|"+(j+1)+"| "+u.getNameLastName());
                j++;
            }
            System.out.println();
            endOfMenu();

            try {
                option = Integer.parseInt(Main.input.next());
                if(option != 0) {
                    int action;
                    System.out.println();
                    System.out.println("Convite de "+community.getInvitations().get(option-1).getNameLastName()+" para fazer parte de "+community.getName()+":");
                    System.out.println();
                    System.out.println("|1| Aceitar");
                    System.out.println("|2| Recusar");
                    endOfMenu();

                    action = Integer.parseInt(Main.input.next());
                    Main.clear();
                    switch(action) {
                        case 1:
                            community.setMember(community.getInvitations().get(option-1));
                            community.getInvitations().get(option-1).setComunity(community);
                            community.getInvitations().remove(option-1);
                
                            System.out.println("Novo membro adicionada na comunidade "+community.getName());
                            return false;
                        case 2:
                            community.getInvitations().remove(option-1);

                            System.out.println("Membro recusado na comunidade "+community.getName());
                            return false;
                        case 0:
                            Main.clear();
                            return false;
                        default:
                            Main.clear();
                            System.out.println("OPCAO INVALIDA");
                    }
                }
            } catch(Exception e) {
                Main.clear();
                System.out.println("OPCAO INVALIDA");
            }
        } while(option != 0);

        return true;
    }

    private void invitationComunity() throws IOException, InterruptedException {
        Main.clear();
        int option = -1;
        do {
            int i = 0;
            title("Comunidades > Minhas comunidades > Convites");
            for(Community c : connected.getMyCommunities()) {
                if(!c.getInvitations().isEmpty()) {
                    System.out.println("|"+(i+1)+"| "+c.getName());
                }
                i++;
            }
            System.out.println();
            endOfMenu();

            try {
                option = Integer.parseInt(Main.input.next());
                if(option != 0) {
                    boolean find = selectInvitationComunity(connected.getMyCommunities().get(option-1));
                    if(find == false) return;
                }
            } catch(Exception e) {
                Main.clear();
                System.out.println("OPCAO INVALIDA");
            }
        } while (option != 0);
        Main.clear();
    }

    private void myCommunities() throws IOException, InterruptedException {
        Main.clear();
        int option = -1;
        do {
            title("Comunidades > Minhas comunidades");
            if(!connected.getMyCommunities().isEmpty()) {
                for(Community c : connected.getMyCommunities())
                        System.out.println(c.getName());
            }
            else System.out.println("Sem comunidades");
            
            System.out.println();
            System.out.println("|1| Criar comunidade");
            boolean invitation = false;
            for(Community c : connected.getMyCommunities()) {
                if(!c.getInvitations().isEmpty()) {
                    invitation = true;
                    System.out.println("|2| Convites das minhas comunidades");
                    break;
                }
            }
            endOfMenu();
    
            try {
                option = Integer.parseInt(Main.input.next());
                switch(option) {
                    case 1:
                        createComunity();
                        break;
                    case 2:
                        if(invitation)
                            invitationComunity();
                        else {
                            Main.clear();
                            System.out.println("OPCAO INVALIDA");
                        }
                        break;
                    case 0:
                        Main.clear();
                        return;
                    default:
                        Main.clear();
                        System.out.println("OPCAO INVALIDA");
                }
            } catch(Exception e) {
                Main.clear();
                System.out.println("OPCAO INVALIDA");
            }
        } while(option != 0);
    }

    private void chatCommunity() throws IOException, InterruptedException {
        Main.clear();
        int option = -1;
        do {
            title("Comunidades > Recados");
            int i = 0;
            for(Community community : connected.getCommunities()) {
                System.out.println("|"+(i+1)+"| "+community.getName());
                i++;
            }

            System.out.println();
            endOfMenu();
            
            try {
                option = Integer.parseInt(Main.input.next());
                Main.clear();

                if((option != 0)) {
                    Community community = connected.getCommunities().get(option-1);
                    String text;
                    do {
                        Main.clear();
                        title("Comunidades > Recados > "+community.getName());
                        if(!connected.getCommunities().isEmpty())
                            for(Message message : community.getChat())
                                printText(message);

                        System.out.println();
                        endOfMenu();
                        text = Main.input.nextLine();

                        if(!text.equals("0") && !text.isEmpty()) {
                            Message message = new Message(connected, text, Calendar.getInstance().getTime());

                            community.setMessage(message);
                        }
                    } while(!text.equals("0"));
                    Main.clear();
                }
            } catch(Exception e) {
                Main.clear();
                System.out.println("OPCAO INVALIDA");
            }
        } while(option != 0);
    }

    private void communities() throws IOException, InterruptedException {
        Main.clear();
        int option = -1;
        do {
            title("Comunidades");
            if(!connected.getCommunities().isEmpty()) {
                for(Community c : connected.getCommunities())
                    System.out.println(c.getName());
            }
            else System.out.println("Sem comunidades");

            System.out.println();
            System.out.println("|1| Buscar comunidade");
            System.out.println("|2| Minhas comunidades");
            if(!connected.getCommunities().isEmpty()) System.out.println("|3| Recados");
            endOfMenu();

            try {
                option = Integer.parseInt(Main.input.next());
                switch(option) {
                    case 1:
                        addComunity();
                        break;
                    case 2:
                        myCommunities();
                        break;
                    case 3:
                        if(!connected.getCommunities().isEmpty())
                            chatCommunity();
                        else
                            throw new Exception();
                        break;
                    case 0:
                        Main.clear();
                        return;
                    default:
                        Main.clear();
                        System.out.println("OPCAO INVALIDA");
                }
            } catch(Exception e) {
                Main.clear();
                System.out.println("OPCAO INVALIDA");
            }
        } while(option != 0);
    }

    private void feed() throws IOException, InterruptedException {
        String text;
        do {
            Main.input.nextLine();
            title("Feed de Notícias");
            for(Post post : Main.feed)
                if(post.getSender().equals(connected) || post.getVisibility() || connected.getFriends().contains(post.getSender()))
                    printText(post);
            System.out.println();
            endOfMenu();
            text = Main.input.nextLine();

            if(!text.equals("0") && !text.isEmpty()) {
                int option;
                System.out.println();
                System.out.println("Quem pode ver esta publicação:");
                System.out.println();
                System.out.println("|1| Amigos");
                System.out.println("|2| Todos");
                System.out.println();
                System.out.print("> ");

                try {
                    option = Integer.parseInt(Main.input.next());
                    Main.clear();

                    if(option == 1 || option == 2) {
                        Post post = new Post(connected, text, Calendar.getInstance().getTime());
                        if(option == 1) 
                            post.setVisibility(false);
                        else
                            post.setVisibility(true);

                        Main.feed.add(post);
                    }
                    else System.out.println("OPCAO INVALIDA");
                } catch(Exception e) {
                    Main.clear();
                    System.out.println("OPCAO INVALIDA");
                }
            }
            Main.clear();
        } while(!text.equals("0"));
    }

    public void menu() throws IOException, InterruptedException {
        Main.clear();
        int option = -1;
        do {
            title(connected.getNameLastName());
            System.out.println("Amigos: "+connected.getFriends().size());
            System.out.println("Comunidades: "+connected.getCommunities().size());
            System.out.println();
            System.out.println("|1| Perfil");
            System.out.println("|2| Amigos");
            System.out.println("|3| Recados");
            System.out.println("|4| Comunidades");
            System.out.println("|5| Feed");
            System.out.println("|0| Sair");
            System.out.println();
            System.out.print("> ");

            try {
                option = Integer.parseInt(Main.input.next());

                Main.clear();
                switch(option) {
                    case 1:
                        if(!profile()) return;
                        break;
                    case 2:
                        friends();
                        break;
                    case 3:
                        chats();
                        break;
                    case 4:
                        communities();
                        break;
                    case 5:
                        feed();
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