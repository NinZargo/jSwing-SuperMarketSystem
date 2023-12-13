import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Setter
public class User implements Serializable {
    @Getter
    private String Username;
    private String Password; // No Getter for password for security purposes
    @Getter
    private String Name;
    @Getter
    private Role Role;
    @Serial
    private static final long serialVersionUID = 1L;

    public User() {}

    public User(String Username, String Password, String Name, Role Role) {
        this.Username = Username;
        this.Password = Password;
        this.Name = Name;
        this.Role = Role;
    }

    @Override
    public String toString() {
        return "User{" + "Username='" + Username + ", Password='" + Password + ", Name='" + Name + ", Role=" + Role + '}';
    }

    public Boolean checkAccount(String username, String password) {
        return (Username.equals(username) && Password.equals(password));
    }
}
