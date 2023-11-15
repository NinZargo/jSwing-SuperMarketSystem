import java.awt.event.*;
import java.util.Arrays;
import java.util.Optional;
import javax.swing.*;
import javax.swing.text.html.Option;

import com.jgoodies.forms.factories.*;
import com.jgoodies.forms.layout.*;
/*
 * Created by JFormDesigner on Sat Nov 04 16:13:51 GMT 2023
 */



/**
 * @author ethan
 */
public class MainJFrame extends JPanel {
    public MainJFrame() {
        initComponents();

        employeeList = new EmployeeList();
        currentEmployee = Optional.of(new Employee());
        
        
    }

    private void jLoginPressee(ActionEvent e) {
        String username = jUsernameTextField.getText();
        String password = jPasswordTextField.getText();

        // Checking if the username and password are correct
        Optional<Employee> potential = Optional.ofNullable(employeeList.findEmployee(username)); // Using optional allows handling of nulls
        currentEmployee = potential.filter(emp -> emp.checkAccount(username, password)).map(emp -> emp); // Now we can get the employee object from the Optional object
        
        jStatusLabel.setText(currentEmployee.map(employee -> ("Welcome " + employee.getName())).orElse("Wrong Details Entered.")); // Check if currentEmployee is NULL
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - Ethan Deeley
        tabbedPane1 = new JTabbedPane();
        jLoginPanel = new JPanel();
        jUsernameLabel = new JLabel();
        jUsernameTextField = new JTextField();
        jPasswordLabel = new JLabel();
        jPasswordTextField = new JTextField();
        jLoginButton = new JButton();
        panel1 = new JPanel();
        jAddProductDropdown = new JComboBox();
        jStatusPanel = new JPanel();
        jStatusLabel = new JLabel();
        button1 = new JButton();

        //======== this ========
        setBorder (new javax. swing. border. CompoundBorder( new javax .swing .border .TitledBorder (new javax. swing. border.
        EmptyBorder( 0, 0, 0, 0) , "JFor\u006dDesi\u0067ner \u0045valu\u0061tion", javax. swing. border. TitledBorder. CENTER, javax. swing
        . border. TitledBorder. BOTTOM, new java .awt .Font ("Dia\u006cog" ,java .awt .Font .BOLD ,12 ),
        java. awt. Color. red) , getBorder( )) );  addPropertyChangeListener (new java. beans. PropertyChangeListener( )
        { @Override public void propertyChange (java .beans .PropertyChangeEvent e) {if ("bord\u0065r" .equals (e .getPropertyName () ))
        throw new RuntimeException( ); }} );
        setLayout(new FormLayout(
            "default, $lcgap, 51dlu, $lcgap, 38dlu, $lcgap, 89dlu, $lcgap, 57dlu, $lcgap, 28dlu",
            "24dlu, $lgap, 21dlu, $lgap, 19dlu, $lgap, 44dlu, $lgap, 42dlu, $lgap, 28dlu, $lgap, 47dlu"));

        //======== tabbedPane1 ========
        {

            //======== jLoginPanel ========
            {
                jLoginPanel.setLayout(new FormLayout(
                    "37dlu, 33dlu, [39dlu,default], 44dlu, 36dlu, 2*(35dlu)",
                    "20dlu, 4*($lgap, default), $lgap, 18dlu"));

                //---- jUsernameLabel ----
                jUsernameLabel.setText("Username");
                jLoginPanel.add(jUsernameLabel, CC.xy(4, 3, CC.CENTER, CC.DEFAULT));
                jLoginPanel.add(jUsernameTextField, CC.xywh(3, 5, 3, 1));

                //---- jPasswordLabel ----
                jPasswordLabel.setText("Password");
                jLoginPanel.add(jPasswordLabel, CC.xy(4, 7, CC.CENTER, CC.DEFAULT));
                jLoginPanel.add(jPasswordTextField, CC.xywh(3, 9, 3, 1));

                //---- jLoginButton ----
                jLoginButton.setText("Login");
                jLoginButton.addActionListener(e -> jLoginPressee(e));
                jLoginPanel.add(jLoginButton, CC.xy(4, 11));
            }
            tabbedPane1.addTab("Login", jLoginPanel);

            //======== panel1 ========
            {
                panel1.setLayout(new FormLayout(
                    "29dlu, 7*($lcgap, 30dlu)",
                    "5*(20dlu, $lgap), 21dlu"));
                panel1.add(jAddProductDropdown, CC.xywh(13, 3, 3, 1));
            }
            tabbedPane1.addTab("Add Product", panel1);
        }
        add(tabbedPane1, CC.xywh(1, 1, 11, 11));

        //======== jStatusPanel ========
        {
            jStatusPanel.setLayout(new FormLayout(
                "13dlu, $lcgap, 203dlu, $lcgap, 58dlu",
                "default"));

            //---- jStatusLabel ----
            jStatusLabel.setText("text");
            jStatusPanel.add(jStatusLabel, CC.xy(3, 1));

            //---- button1 ----
            button1.setText("LogOut");
            jStatusPanel.add(button1, CC.xy(5, 1));
        }
        add(jStatusPanel, CC.xywh(1, 13, 11, 1));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Super Market App");
        frame.add(new MainJFrame());
        frame.setLocationByPlatform(true);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.pack(); // Sets the window to fit the natural size of its content pane
        frame.setVisible(true);
    }


    private EmployeeList employeeList;
    private Optional<Employee> currentEmployee;
    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - Ethan Deeley
    private JTabbedPane tabbedPane1;
    private JPanel jLoginPanel;
    private JLabel jUsernameLabel;
    private JTextField jUsernameTextField;
    private JLabel jPasswordLabel;
    private JTextField jPasswordTextField;
    private JButton jLoginButton;
    private JPanel panel1;
    private JComboBox jAddProductDropdown;
    private JPanel jStatusPanel;
    private JLabel jStatusLabel;
    private JButton button1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
