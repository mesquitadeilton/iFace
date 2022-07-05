import java.util.*;

public class Community implements Key {
    private User creator;
    private String name;
    private String description;
    
    private ArrayList<User> members = new ArrayList<User>();
    private ArrayList<User> invitations = new ArrayList<User>();

    private ArrayList<Message> chat = new ArrayList<Message>();

    public Community(User creator, String name, String description) {
        this.creator = creator;
        this.name = name;
        this.description = description;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setMember(User member) {
        members.add(member);
    }

    public void setInvitation(User user) {
        invitations.add(user);
    }

    public void setMessage(Message message) {
        chat.add(message);
    }

    public User getCreator() {
        return creator;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<User> getMembers() {
        return members;
    }

    public ArrayList<User> getInvitations() {
        return invitations;
    }

    public ArrayList<Message> getChat() {
        return chat;
    }

    @Override
    public String getKey() {
        return name.toUpperCase();
    }
}