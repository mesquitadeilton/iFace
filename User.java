import java.util.*;

public class User implements Key {
    private String name;
    private String lastName = "";
    private String email;
    private String password;

    private ArrayList<User> friends = new ArrayList<User>();
    private ArrayList<User> invitations = new ArrayList<User>();

    private HashMap<User, ArrayList<Message>> chats = new HashMap<User, ArrayList<Message>>();

    private ArrayList<Community> myCommunities = new ArrayList<Community>();
    private ArrayList<Community> communities = new ArrayList<Community>();

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassowrd(String password) {
        this.password = password;
    }

    public void setFriend(User friend) {
        friends.add(friend);
    }
    public void setInvitation(User invitation) {
        invitations.add(invitation);
    }

    public void setChat(User key) {
        chats.put(key, new ArrayList<Message>());
    }

    public void setChatMessage(User key, Message message) {
        chats.get(key).add(message);
    }

    public void setMyComunity(Community community) {
        myCommunities.add(community);
    }
    public void setComunity(Community community) {
        communities.add(community);
    }

    public String getNameLastName() {
        return (lastName.isEmpty()) ? name : name+" "+lastName;
    }

    public String getName() {
        return name;
    }
    public String getLastName() {
        return lastName;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }

    public ArrayList<User> getFriends() {
        return friends;
    }
    public ArrayList<User> getInvitations() {
        return invitations;
    }

    public HashMap<User, ArrayList<Message>> getChats() {
        return chats;
    }

    public ArrayList<Community> getMyCommunities() {
        return myCommunities;
    }
    public ArrayList<Community> getCommunities() {
        return communities;
    }

    @Override
    public String getKey() {
        return email.toUpperCase();
    }
}