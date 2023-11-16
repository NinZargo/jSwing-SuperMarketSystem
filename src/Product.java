import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Objects;

@Getter @Setter
public class Product implements Serializable,Cloneable {
    protected int id;
    protected String name;
    protected double price;
    protected ProdCat category;
    protected int stock;
    protected int maxStock;
    protected int minStock;
    protected String imageFile;
    protected static double VAT = 1.2;
    @Serial
    protected static final long serialVersionUID = 1L;

    public Product(){}
    // Constructor used for loading from file
    public Product(int id, String name, double price, ProdCat category, int stock, int maxStock, int minStock, String imageFile) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.stock = stock;
        this.maxStock = maxStock;
        this.minStock = minStock;
        this.imageFile = imageFile;
    }

    // Constructor used for new product
    public Product(String name, double price, ProdCat category, int stock, int maxStock, int minStock){
        this.name = name;
        this.price = price;
        this.category = category;
        this.stock = stock;
        this.maxStock = maxStock;
        this.minStock = minStock;

        this.id = this.hashCode(); // Sets id to uniquely generated code based on name and category

        this.imageFile = (this.name.substring(0,1).toUpperCase()+this.name.substring(1)+".jpg"); // Set naming convention for image files
    }

    public Product(String name, double price, ProdCat category, int stock){
        this.name = name;
        this.price = price;
        this.category = category;
        this.stock = stock;

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

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name +
                ", price=" + price +
                ", category=" + category +
                ", stock=" + stock +
                ", maxStock=" + maxStock +
                ", imageFile='" + imageFile +
                '}';
    }

    public String toAddProduct(){

        return name +
            " £" + price +
            ", " + category +
            ", inStock " + stock +
            ", minStock " + minStock +
            ", maxStock " + maxStock;
    }



    public String toOrder(){ // For the order page
        DecimalFormat df = new DecimalFormat("#.##");
        return this.name + " £" + df.format(this.price * VAT) + ", " + this.category.toString();
    }
    public String toBasket(){ // For the Basket page
        DecimalFormat df = new DecimalFormat("#.##");
        return this.name + " £" + df.format(this.price * VAT) + ", " + this.category.toString() + ", " + this.stock;
    }

    @Override
    public Product clone() {
        try {
            Product clone = (Product) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
