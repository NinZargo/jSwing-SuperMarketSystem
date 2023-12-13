import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.io.Serializable;

public class IAddress implements Serializable {
        private String name;
        private String house_name;
        private Integer house_no;
        private String street;
        private String area;
        private String post_code;
        private String town;
        private String country;

        public IAddress() {
            name = "";
            house_name = "";
            house_no = 0;
            street = "";
            area = "";
            post_code = "";
            town = "";
            country = "";
        }

        public void Display(JTextArea jAddressTextArea) {
            jAddressTextArea.setLineWrap(true);
            jAddressTextArea.append(toString());
        }

        public void Edit(String strname, String strhouse_name, Integer inthouse_no, String strstreet, String strarea, String strpost_code, String strtown, String strcountry) {
            name = strname;
            house_name = strhouse_name;
            house_no = inthouse_no;
            street = strstreet;
            area = strarea;
            post_code = strpost_code;
            town = strtown;
            country = strcountry;

        }

        @Override
        public String toString() {
            return( name + ", " + house_name + ", " + String.valueOf(house_no) + " " + street + ", \n" + area + ", " + post_code + ", \n" + town + ", " + country);
        }
}
