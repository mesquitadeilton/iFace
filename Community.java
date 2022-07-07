import java.util.*;

public class Community implements Idetification {
    private User creator;
    private String name;
    private String description;
    
    private List<User> members = new ArrayList<User>();
    private List<User> invitations = new ArrayList<User>();

    private List<Message> chat = new ArrayList<Message>();

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

    public List<User> getMembers() {
        return members;
    }

    public List<User> getInvitations() {
        return invitations;
    }

    public List<Message> getChat() {
        return chat;
    }

    @Override
    public String getKey() {
        return getName().toUpperCase();
    }

    @Override
    public String getFullName() {
        return getName();
    }
}