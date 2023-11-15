import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter @Setter @RequiredArgsConstructor
public class Product implements Serializable {
    private int id;
    private String name;
    private double price;
    private ProdCat category;
    private int stock;
    private final int maxStock;
    private String imageFile;

    // Constructor used for loading from file
    public Product(int id, String name, double price, ProdCat category, int stock, int maxStock, String imageFile) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.stock = stock;
        this.maxStock = maxStock;
        this.imageFile = imageFile;
    }

    // Constructor used for new product
    public Product(String name, double price, ProdCat category, int stock, int maxStock){
        this.name = name;
        this.price = price;
        this.category = category;
        this.stock = stock;
        this.maxStock = maxStock;

        this.id = this.hashCode(); // Sets id to uniquely generated code based on name and category

        this.imageFile = (this.name.substring(0,1).toUpperCase()+this.name.substring(1)+".jpg"); // Set naming convention for image files
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(name, product.name) && category == product.category;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, category);
    }

    public void restockProduct(){
        this.stock = this.maxStock;
    }
}
