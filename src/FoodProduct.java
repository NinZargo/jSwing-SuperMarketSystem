import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Getter @Setter
public class FoodProduct extends Product implements Serializable {
    private LocalDate expirationDate;
    private int expirationDays;

    // File Constructor
    FoodProduct(int id, String name, double price, ProdCat category, int stock, int maxStock, String filename, String expirationDate, int expirationDays, int minStock) {
        super(id, name, price, category, stock, maxStock, minStock, filename);

        this.expirationDate = LocalDate.parse(expirationDate);
        this.expirationDays = expirationDays;
    }

    // New Item Constructor
    FoodProduct(String name, double price, ProdCat category, int stock, int maxStock, int minStock, int expirationDays) {
        super(name, price, category, stock, maxStock, minStock);

        this.expirationDate = LocalDate.now().plusDays(expirationDays);
        this.expirationDays = expirationDays;
    }

    FoodProduct(String name, double price, ProdCat category, int stock, int expirationDays) {
        super(name, price, category, stock);

        this.expirationDate = LocalDate.now().plusDays(expirationDays);
        this.expirationDays = expirationDays;
    }

    @Override
    public void restockProduct(){
        super.restockProduct();

        this.expirationDate = LocalDate.now().plusDays(expirationDays);
    }

    @Override
    public String toString() {
        return "FoodProduct{" +
                "id=" + super.id +
                ", name='" + super.name +
                ", price=" + super.price +
                ", category=" + super.category +
                ", stock=" + super.stock +
                ", maxStock=" + super.maxStock +
                ", imageFile='" + super.imageFile +
                ", expirationDate=" + expirationDate +
                ", expirationDays=" + expirationDays +
                "} ";
    }
    public String toAddFoodProduct(){
        return name +
                " £" + price +
                ", " + category +
                ", inStock " + stock +
                ", minStock " + minStock +
                ", maxStock " + maxStock +
                ", expirationDays " + expirationDays;

    }
}
