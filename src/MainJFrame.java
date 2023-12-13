import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import javax.swing.*;
import javax.swing.event.*;
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

        userList = new UserList();
        userList.loadFromFile();

        currentUser = Optional.of(new User());

        productList = new ProductList();
        productList.loadFromFile();

        basketList = new ProductList();
        Product product = new Product("Apple", 100.0, ProdCat.Fruit, 1, 100, 0);


        initComponents();

        jTabInMemory = new java.awt.Component[5];

        int nonLoginTabs = tabbedPane1.getTabCount() - 1;

        for(int i = 0; i < nonLoginTabs; i++){
            jTabInMemory[i] = (tabbedPane1.getComponentAt(1));
            tabbedPane1.remove(1);
        }

        tabbedPane1.setSelectedIndex(0);
    }

    private void jLoginPressee(ActionEvent e) {
        String username = jUsernameTextField.getText();
        String password = jPasswordTextField.getText();

        // Checking if the username and password are correct
        Optional<User> potential = Optional.ofNullable(userList.findUser(username)); // Using optional allows handling of nulls
        currentUser = potential.filter(User -> User.checkAccount(username, password)).map(User -> User); // Now we can get the employee object from the Optional object
        
        jStatusLabel.setText(currentUser.map(usr -> ("Welcome " + usr.getName())).orElse("Wrong Details Entered.")); // Check if currentEmployee is NULL

        if(currentUser.get().getRole().equals(Role.Owner)){
            tabbedPane1.addTab("Add Products", jTabInMemory[0]);
        }
        tabbedPane1.addTab("Browse Products", jTabInMemory[1]);
        tabbedPane1.addTab("Basket", jTabInMemory[2]);

    }

    private void jRegisterButtonPressed(ActionEvent e) {
        User user = new User(jUsernameTextField.getText(), jPasswordTextField.getText(), jUsernameTextField.getText(), Role.Owner);
        userList.addUser(user);
        userList.saveToFile();
    }

    private void jAddProductPressed(ActionEvent e) {
        if(jAddProductDropdown.getSelectedIndex() <= 5) {
            FoodProduct foodProduct = new FoodProduct(jNameTextField.getText(), Double.parseDouble(jPriceTextField.getText()), ProdCat.valueOf(jAddProductDropdown.getSelectedItem().toString()), Integer.parseInt(jCurrentStockTextField.getText()), Integer.parseInt(jMaxStockTextField.getText()), Integer.parseInt(jMinStockTextField.getText()), Integer.parseInt(jExpiryDaysTextField.getText()));
            productList.addProduct(foodProduct);
        } else {
            Product product = new Product(jNameTextField.getText(), Double.parseDouble(jPriceTextField.getText()), ProdCat.valueOf(jAddProductDropdown.getSelectedItem().toString()), Integer.parseInt(jCurrentStockTextField.getText()), Integer.parseInt(jMaxStockTextField.getText()), Integer.parseInt(jMinStockTextField.getText()));
            productList.addProduct(product);
        }
        productList.saveToFile();
    }

    private void jFindButtonPressed(ActionEvent e) {
        int id = Objects.hash(jNameTextField.getText(), ProdCat.valueOf(jAddProductDropdown.getSelectedItem().toString()));
        Product product = productList.findProduct(id);

        setEdit(product);
    }

    private void tabbedPane1StateChanged(ChangeEvent e) {
        ProductListCellRenderer.setTab(tabbedPane1.getSelectedIndex());
    }

    private void list1ValueChanged(ListSelectionEvent e) {
        int index = list1.getSelectedIndex();
        Product product = productList.getProductAtIndex(index);

        setEdit(product);
    }

    public void setEdit(Product product){
        jNameTextField.setText(product.getName());
        jPriceTextField.setText(String.valueOf(product.getPrice()));
        jCurrentStockTextField.setText(String.valueOf(product.getStock()));
        jMaxStockTextField.setText(String.valueOf(product.getMaxStock()));
        jMinStockTextField.setText(String.valueOf(product.getMinStock()));

        if(product instanceof FoodProduct foodProduct) jExpiryDaysTextField.setText(String.valueOf(foodProduct.getExpirationDays()));
        else jExpiryDaysTextField.setText("Non Food Item");

        jAddProductDropdown.setSelectedItem(product.getCategory());
    }

    private void jSaveButtonPressed(ActionEvent e) {
        int id = Objects.hash(jNameTextField.getText(), ProdCat.valueOf(jAddProductDropdown.getSelectedItem().toString()));
        Product product = productList.findProduct(id);
        productList.removeProduct(product);

        if(jAddProductDropdown.getSelectedIndex() <= 5) {
            FoodProduct foodProduct = new FoodProduct(jNameTextField.getText(), Double.parseDouble(jPriceTextField.getText()), ProdCat.valueOf(jAddProductDropdown.getSelectedItem().toString()), Integer.parseInt(jCurrentStockTextField.getText()), Integer.parseInt(jExpiryDaysTextField.getText()));
            productList.addProduct(foodProduct);
        } else {
            product = new Product(jNameTextField.getText(), Double.parseDouble(jPriceTextField.getText()), ProdCat.valueOf(jAddProductDropdown.getSelectedItem().toString()), Integer.parseInt(jCurrentStockTextField.getText()));
            productList.addProduct(product);
        }
        productList.saveToFile();
    }

    private void jAddBasketButtonPressed(ActionEvent e) {
        Product selectedProduct = (Product) list2.getSelectedValue();

        Product product = new Product(selectedProduct.getName(), selectedProduct.getPrice(), selectedProduct.getCategory(), selectedProduct.getStock(), selectedProduct.getMaxStock(), selectedProduct.getMinStock());
        product.setStock(Integer.parseInt(jQuantityTextField.getText())); // Uses stock as the quantity wanted

        basketList.addProduct(product);
    }

    private void list3ValueChanged(ListSelectionEvent e) {
        Product product = (Product) list3.getSelectedValue();
        jBasketQuantityTextField.setText(String.valueOf(product.getStock()));
    }

    private void jBasketSaveButtonPressed(ActionEvent e) {
        Product product = (Product) list3.getSelectedValue();
        basketList.removeProduct(product);

        product.setStock(Integer.parseInt(jBasketQuantityTextField.getText()));

        if(product.getStock() > 0)basketList.addProduct(product);

        DecimalFormat df = new DecimalFormat("#.##");
        jTotalCostLabel.setText("£" + df.format(basketList.getTotalPrice()));
    }

    private void jConfirmOrderButtonPressed(ActionEvent e) {
        StringBuilder str = new StringBuilder();
        for( int i=0; i<basketList.getSize(); i++) {
            for( int j=0; j<productList.getSize(); j++) {
                if(productList.getProductAtIndex(j).getName().equals(basketList.getProductAtIndex(i).getName()) && productList.getProductAtIndex(j).getCategory().equals(basketList.getProductAtIndex(i).getCategory())) {
                    productList.getProductAtIndex(j).setStock(productList.getProductAtIndex(j).getStock() - basketList.getProductAtIndex(i).getStock());

                    if(productList.getProductAtIndex(j).getStock() <= productList.getProductAtIndex(j).getMinStock()) {
                        JOptionPane.showMessageDialog(null,
                                (productList.getProductAtIndex(j).getName() + " is below the set Minimum Stock \n Reorder Now!!!"),
                                "Below Minimum Stock",
                                JOptionPane.ERROR_MESSAGE);
                    }

                    basketList.removeProduct(basketList.getProductAtIndex(i));
                }
            }
        }
    }

    private void jRemoveButtonPressed(ActionEvent e) {
        Product product = (Product) list1.getSelectedValue();
        productList.removeProduct(product);

        productList.saveToFile();
    }

    private void button1(ActionEvent e) {
        int nonLoginTabs = tabbedPane1.getTabCount() - 1;

        for(int i = 0; i < nonLoginTabs; i++){
            tabbedPane1.remove(1);
        }

        tabbedPane1.setSelectedIndex(0);
    }
    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Educational license - Ethan Deeley (2282321)
        tabbedPane1 = new JTabbedPane();
        jLoginPanel = new JPanel();
        jUsernameLabel = new JLabel();
        jUsernameTextField = new JTextField();
        jPasswordLabel = new JLabel();
        jPasswordTextField = new JTextField();
        jLoginButton = new JButton();
        jRegisterButton = new JButton();
        panel1 = new JPanel();
        scrollPane1 = new JScrollPane();
        list1 = new JList<>(productList);
        list1.setCellRenderer(new ProductListCellRenderer(productList));
        jNameLabel = new JLabel();
        label4 = new JLabel();
        jNameTextField = new JTextField();
        jAddProductDropdown = new JComboBox(ProdCat.values());
        jPriceLabel = new JLabel();
        jMaxStockLabel = new JLabel();
        jPriceTextField = new JTextField();
        jMaxStockTextField = new JTextField();
        jMinStockLabel = new JLabel();
        jCurrentStockLabel = new JLabel();
        jMinStockTextField = new JTextField();
        jCurrentStockTextField = new JTextField();
        jExpiryDaysLabel = new JLabel();
        jExpiryDaysTextField = new JTextField();
        jRemoveButton = new JButton();
        jAddProductButton = new JButton();
        jSaveButton = new JButton();
        jFindButton = new JButton();
        panel2 = new JPanel();
        scrollPane2 = new JScrollPane();
        list2 = new JList<>(productList);
        list2.setCellRenderer(new ProductListCellRenderer(productList));
        jQuantityLabel = new JLabel();
        jQuantityTextField = new JTextField();
        jAddBasketButton = new JButton();
        panel3 = new JPanel();
        scrollPane3 = new JScrollPane();
        list3 = new JList<>(basketList);
        list3.setCellRenderer(new ProductListCellRenderer(basketList));
        jBasketQuantityLabel = new JLabel();
        jBasketQuantityTextField = new JTextField();
        jBasketSaveButton = new JButton();
        jTotalLabel = new JLabel();
        jTotalCostLabel = new JLabel();
        jConfirmOrderButton = new JButton();
        jStatusPanel = new JPanel();
        jStatusLabel = new JLabel();
        button1 = new JButton();

        //======== this ========
        setLayout(new FormLayout(
            "30dlu, 404dlu",
            "24dlu, $lgap, 21dlu, $lgap, 215dlu, $lgap, 38dlu"));

        //======== tabbedPane1 ========
        {
            tabbedPane1.addChangeListener(e -> tabbedPane1StateChanged(e));

            //======== jLoginPanel ========
            {
                jLoginPanel.setLayout(new FormLayout(
                    "148dlu, [45dlu,default], 21dlu, $lcgap, 19dlu, 45dlu, 153dlu",
                    "63dlu, 4*($lgap, default), $lgap, 25dlu"));

                //---- jUsernameLabel ----
                jUsernameLabel.setText("Username");
                jLoginPanel.add(jUsernameLabel, CC.xywh(3, 3, 3, 1, CC.CENTER, CC.DEFAULT));
                jLoginPanel.add(jUsernameTextField, CC.xywh(2, 5, 5, 1));

                //---- jPasswordLabel ----
                jPasswordLabel.setText("Password");
                jLoginPanel.add(jPasswordLabel, CC.xywh(3, 7, 3, 1, CC.CENTER, CC.DEFAULT));
                jLoginPanel.add(jPasswordTextField, CC.xywh(2, 9, 5, 1));

                //---- jLoginButton ----
                jLoginButton.setText("Login");
                jLoginButton.addActionListener(e -> jLoginPressee(e));
                jLoginPanel.add(jLoginButton, CC.xywh(2, 11, 2, 1));

                //---- jRegisterButton ----
                jRegisterButton.setText("Register");
                jRegisterButton.addActionListener(e -> jRegisterButtonPressed(e));
                jLoginPanel.add(jRegisterButton, CC.xywh(5, 11, 2, 1));
            }
            tabbedPane1.addTab("Login", jLoginPanel);

            //======== panel1 ========
            {
                panel1.setLayout(new FormLayout(
                    "[122dlu,default], $lcgap, [111dlu,default], $lcgap, [23dlu,default], 2*($lcgap, 30dlu), $lcgap, 13dlu, $lcgap, 28dlu, $lcgap, 33dlu",
                    "6dlu, 15dlu, $lgap, 20dlu, 15dlu, $lgap, 20dlu, $lgap, 15dlu, $lgap, 20dlu, 2*($lgap, 19dlu), $lgap, 65dlu"));

                //======== scrollPane1 ========
                {

                    //---- list1 ----
                    list1.addListSelectionListener(e -> list1ValueChanged(e));
                    scrollPane1.setViewportView(list1);
                }
                panel1.add(scrollPane1, CC.xywh(1, 2, 3, 16));

                //---- jNameLabel ----
                jNameLabel.setText("Name");
                panel1.add(jNameLabel, CC.xywh(7, 2, 3, 1, CC.CENTER, CC.BOTTOM));

                //---- label4 ----
                label4.setText("Category");
                panel1.add(label4, CC.xywh(13, 2, 3, 1, CC.CENTER, CC.BOTTOM));
                panel1.add(jNameTextField, CC.xywh(7, 4, 3, 1));
                panel1.add(jAddProductDropdown, CC.xywh(13, 4, 3, 1));

                //---- jPriceLabel ----
                jPriceLabel.setText("Price");
                panel1.add(jPriceLabel, CC.xywh(7, 5, 3, 1, CC.CENTER, CC.BOTTOM));

                //---- jMaxStockLabel ----
                jMaxStockLabel.setText("Max Stock");
                panel1.add(jMaxStockLabel, CC.xywh(13, 5, 3, 1, CC.CENTER, CC.BOTTOM));
                panel1.add(jPriceTextField, CC.xywh(7, 7, 3, 1));
                panel1.add(jMaxStockTextField, CC.xywh(13, 7, 3, 1));

                //---- jMinStockLabel ----
                jMinStockLabel.setText("Min Stock");
                panel1.add(jMinStockLabel, CC.xywh(7, 9, 3, 1, CC.CENTER, CC.BOTTOM));

                //---- jCurrentStockLabel ----
                jCurrentStockLabel.setText("Current Stock");
                panel1.add(jCurrentStockLabel, CC.xywh(13, 9, 3, 1, CC.CENTER, CC.BOTTOM));
                panel1.add(jMinStockTextField, CC.xywh(7, 11, 3, 1, CC.DEFAULT, CC.TOP));
                panel1.add(jCurrentStockTextField, CC.xywh(13, 11, 3, 1));

                //---- jExpiryDaysLabel ----
                jExpiryDaysLabel.setText("Expiry days:");
                panel1.add(jExpiryDaysLabel, CC.xywh(7, 13, 5, 1, CC.RIGHT, CC.DEFAULT));
                panel1.add(jExpiryDaysTextField, CC.xywh(13, 13, 3, 1));

                //---- jRemoveButton ----
                jRemoveButton.setText("Remove");
                jRemoveButton.addActionListener(e -> jRemoveButtonPressed(e));
                panel1.add(jRemoveButton, CC.xywh(7, 15, 3, 1, CC.DEFAULT, CC.CENTER));

                //---- jAddProductButton ----
                jAddProductButton.setText("Add");
                jAddProductButton.addActionListener(e -> jAddProductPressed(e));
                panel1.add(jAddProductButton, CC.xywh(13, 15, 3, 1, CC.DEFAULT, CC.BOTTOM));

                //---- jSaveButton ----
                jSaveButton.setText("Save");
                jSaveButton.addActionListener(e -> jSaveButtonPressed(e));
                panel1.add(jSaveButton, CC.xywh(7, 17, 3, 1, CC.DEFAULT, CC.TOP));

                //---- jFindButton ----
                jFindButton.setText("Find");
                jFindButton.addActionListener(e -> jFindButtonPressed(e));
                panel1.add(jFindButton, CC.xywh(13, 17, 3, 1, CC.DEFAULT, CC.TOP));
            }
            tabbedPane1.addTab("Add Product", panel1);

            //======== panel2 ========
            {
                panel2.setLayout(new FormLayout(
                    "232dlu, $lcgap, 63dlu, $lcgap, 59dlu, $lcgap, 117dlu",
                    "37dlu, $lgap, 28dlu, $lgap, 23dlu, $lgap, 18dlu, $lgap, 23dlu, $lgap, 95dlu"));

                //======== scrollPane2 ========
                {
                    scrollPane2.setViewportView(list2);
                }
                panel2.add(scrollPane2, CC.xywh(1, 1, 1, 11));

                //---- jQuantityLabel ----
                jQuantityLabel.setText("Quantity");
                panel2.add(jQuantityLabel, CC.xy(3, 3, CC.CENTER, CC.BOTTOM));
                panel2.add(jQuantityTextField, CC.xy(3, 5));

                //---- jAddBasketButton ----
                jAddBasketButton.setText("Add to Basket");
                jAddBasketButton.addActionListener(e -> jAddBasketButtonPressed(e));
                panel2.add(jAddBasketButton, CC.xy(3, 7));
            }
            tabbedPane1.addTab("Browse Products", panel2);

            //======== panel3 ========
            {
                panel3.setLayout(new FormLayout(
                    "232dlu, $lcgap, 63dlu, $lcgap, 59dlu, $lcgap, 117dlu",
                    "37dlu, $lgap, 28dlu, $lgap, 23dlu, $lgap, 18dlu, $lgap, 23dlu, $lgap, 21dlu, $lgap, 87dlu"));

                //======== scrollPane3 ========
                {

                    //---- list3 ----
                    list3.addListSelectionListener(e -> list3ValueChanged(e));
                    scrollPane3.setViewportView(list3);
                }
                panel3.add(scrollPane3, CC.xywh(1, 1, 1, 13));

                //---- jBasketQuantityLabel ----
                jBasketQuantityLabel.setText("Quantity");
                panel3.add(jBasketQuantityLabel, CC.xy(3, 3, CC.CENTER, CC.BOTTOM));
                panel3.add(jBasketQuantityTextField, CC.xy(3, 5));

                //---- jBasketSaveButton ----
                jBasketSaveButton.setText("Save");
                jBasketSaveButton.addActionListener(e -> jBasketSaveButtonPressed(e));
                panel3.add(jBasketSaveButton, CC.xy(3, 7));

                //---- jTotalLabel ----
                jTotalLabel.setText("Total");
                panel3.add(jTotalLabel, CC.xy(3, 9));

                //---- jTotalCostLabel ----
                jTotalCostLabel.setText("\u00a300.00");
                panel3.add(jTotalCostLabel, CC.xy(3, 11, CC.DEFAULT, CC.TOP));

                //---- jConfirmOrderButton ----
                jConfirmOrderButton.setText("Confirm Order");
                jConfirmOrderButton.addActionListener(e -> jConfirmOrderButtonPressed(e));
                panel3.add(jConfirmOrderButton, CC.xy(3, 13));
            }
            tabbedPane1.addTab("Basket", panel3);
        }
        add(tabbedPane1, CC.xywh(1, 1, 2, 5));

        //======== jStatusPanel ========
        {
            jStatusPanel.setLayout(new FormLayout(
                "13dlu, $lcgap, 355dlu, $lcgap, 58dlu",
                "default"));

            //---- jStatusLabel ----
            jStatusLabel.setText("text");
            jStatusPanel.add(jStatusLabel, CC.xy(3, 1));

            //---- button1 ----
            button1.setText("LogOut");
            button1.addActionListener(e -> button1(e));
            jStatusPanel.add(button1, CC.xy(5, 1));
        }
        add(jStatusPanel, CC.xywh(1, 7, 2, 1));
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

    private final UserList userList;
    private Optional<User> currentUser;
    private final ProductList productList;
    private final ProductList basketList;
    private java.awt.Component[] jTabInMemory;
    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Educational license - Ethan Deeley (2282321)
    private JTabbedPane tabbedPane1;
    private JPanel jLoginPanel;
    private JLabel jUsernameLabel;
    private JTextField jUsernameTextField;
    private JLabel jPasswordLabel;
    private JTextField jPasswordTextField;
    private JButton jLoginButton;
    private JButton jRegisterButton;
    private JPanel panel1;
    private JScrollPane scrollPane1;
    private JList list1;
    private JLabel jNameLabel;
    private JLabel label4;
    private JTextField jNameTextField;
    private JComboBox jAddProductDropdown;
    private JLabel jPriceLabel;
    private JLabel jMaxStockLabel;
    private JTextField jPriceTextField;
    private JTextField jMaxStockTextField;
    private JLabel jMinStockLabel;
    private JLabel jCurrentStockLabel;
    private JTextField jMinStockTextField;
    private JTextField jCurrentStockTextField;
    private JLabel jExpiryDaysLabel;
    private JTextField jExpiryDaysTextField;
    private JButton jRemoveButton;
    private JButton jAddProductButton;
    private JButton jSaveButton;
    private JButton jFindButton;
    private JPanel panel2;
    private JScrollPane scrollPane2;
    private JList list2;
    private JLabel jQuantityLabel;
    private JTextField jQuantityTextField;
    private JButton jAddBasketButton;
    private JPanel panel3;
    private JScrollPane scrollPane3;
    private JList list3;
    private JLabel jBasketQuantityLabel;
    private JTextField jBasketQuantityTextField;
    private JButton jBasketSaveButton;
    private JLabel jTotalLabel;
    private JLabel jTotalCostLabel;
    private JButton jConfirmOrderButton;
    private JPanel jStatusPanel;
    private JLabel jStatusLabel;
    private JButton button1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
