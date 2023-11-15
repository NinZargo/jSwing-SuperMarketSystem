import java.io.Serializable;
import java.time.LocalDate;

public class FoodProduct extends Product implements Serializable {
    private LocalDate expirationDate;
    private int expirationDays;

    FoodProduct(int id, String name, double price, ProdCat category, int stock, int maxStock, String filename, String expirationDate, int expirationDays) {
        super(id, name, price, category, stock, maxStock, filename);

        this.expirationDate = LocalDate.parse(expirationDate);
        this.expirationDays = expirationDays;
    }

    FoodProduct(String name, double price, ProdCat category, int stock, int maxStock, int expirationDays) {
        super(name, price, category, stock, maxStock);

        this.expirationDate = LocalDate.now().plusDays(expirationDays);
    }

    @Override
    public void restockProduct(){
        super.restockProduct();

        this.expirationDate = LocalDate.now().plusDays(expirationDays);
    }
}
