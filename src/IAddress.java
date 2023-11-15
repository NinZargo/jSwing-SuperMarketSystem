import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.io.Serializable;

public record IAddress(String name, String house_name, Integer house_no, String street, String post_code, String town, String country) implements Serializable {
    public void Display(@NotNull JTextArea src){
        src.append("""
\
IAddress{
    name='$name',\s
    house_name='$house_name',\s
    house_no=$house_no,\s
    street='$street',\s
    post_code='$post_code',\s
    town='$town',\s
    country='$country'
}""");
    }
}
