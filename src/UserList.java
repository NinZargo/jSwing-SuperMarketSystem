import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class UserList {
    ArrayList<User> users;
    String filename;

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
