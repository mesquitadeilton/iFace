import java.io.*;
import java.util.*;

public class Main {
    static Scanner input = new Scanner(System.in);

    static List<User> users = new ArrayList<User>();
    static List<Community> communities = new ArrayList<Community>();
    static List<Post> feed = new ArrayList<Post>();

    public static <T extends Key>T search(List<T> list, String key) {
        for(T element : list) 
            if(element.getKey().equals(key)) return element;

        return null;
    }

    public static void clear() throws IOException, InterruptedException {
        if (System.getProperty("os.name").contains("Windows"))
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        else
            new ProcessBuilder("clear").inheritIO().start().waitFor();
    }
    public static void main(String[] args) throws IOException, InterruptedException {
        clear();

        Menu menu = new Menu();
        menu.print();
    }
}