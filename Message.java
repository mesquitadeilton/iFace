import java.util.*;

public class Message {
    private User sender;
    private String text;
    private Date date;

    public Message(User sender, String text, Date date) {
        this.sender = sender;
        this.text = text;
        this.date = date;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getSender() {
        return sender;
    }

    public String getText() {
        return text;
    }

    public Date getDate() {
        return date;
    }
}