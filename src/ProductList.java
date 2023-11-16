import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class ProductList extends AbstractListModel<Product> implements Serializable {
    private ArrayList<Product> products;
    private String filename;
    private static final long serialVersionUID = 1L;

    public ProductList(){
        products = new ArrayList<Product>();
        filename = "productListData.txt";
    }

    public void addProduct(Product product){
        products.add(product);
        int index = products.size() - 1;
        fireIntervalAdded(this, index, index);
    }
    public void removeProduct(Product product){
        products.remove(product);
    }

    public Product findProduct(int id) {
        return this.products.stream().filter(product -> product.getId() == id).findFirst().orElse(null);
    }

    public Product getProductAtIndex(int i){
        return this.products.get(i);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (Product product : products) {
            str.append(product.toString()).append("\n");
        }
        return str.toString();
    }
    public void saveToFile(){
        try {
            FileOutputStream fos = new FileOutputStream(filename);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(this.products);

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

            this.products = (ArrayList<Product>) in.readObject();

            in.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getSize() {
        return products.size();
    }

    @Override
    public Product getElementAt(int index) {
        return products.get(index);
    }

    public double getTotalPrice(){
        double totalPrice = 0;
        for (Product product : products) {
            totalPrice += (product.getPrice()*product.getStock());
        }
        totalPrice *= 1.2;
        return totalPrice;
    }
}
