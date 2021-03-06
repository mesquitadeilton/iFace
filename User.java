import java.util.*;

public class User implements Identification {
    private String name;
    private String lastName = "";
    private String email;
    private String password;

    private List<User> friends = new ArrayList<User>();
    private List<User> invitations = new ArrayList<User>();

    private HashMap<User, List<Message>> chats = new HashMap<User, List<Message>>();

    private List<Community> myCommunities = new ArrayList<Community>();
    private List<Community> communities = new ArrayList<Community>();

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

    public void setChat(User key, List<Message> chat) {
        chats.put(key, chat);
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

    public List<User> getFriends() {
        return friends;
    }
    public List<User> getInvitations() {
        return invitations;
    }

    public HashMap<User, List<Message>> getChats() {
        return chats;
    }

    public List<Community> getMyCommunities() {
        return myCommunities;
    }
    public List<Community> getCommunities() {
        return communities;
    }

    @Override
    public String getKey() {
        return getEmail().toUpperCase();
    }

    @Override
    public String getFullName() {
        return (lastName.isEmpty()) ? name : name+" "+lastName;
    }
}