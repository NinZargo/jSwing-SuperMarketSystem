import java.io.*;
import java.util.ArrayList;

public class UserList {
    ArrayList<User> users;
    String filename;
    private static final long serialVersionUID = 1L;

    public UserList(){
        users = new ArrayList<User>();
        filename = "employees.txt";
    }

    public void addUser(User user){
        users.add(user);
    }

    public User findUser(String username) {
        return this.users.stream().filter(user -> user.getUsername().equals(username)).findFirst().orElse(null);
    }

    public void saveToFile(){
        try {
            FileOutputStream fos = new FileOutputStream(filename);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(this.users);

            oos.flush();
            oos.close();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public void loadFromFile(){
        try {
            FileInputStream fileIn = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(fileIn);

            users = (ArrayList<User>) in.readObject();

            in.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
