import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class Supplier {
    private IAddress address = new IAddress();
    private ProductList products = new ProductList();
    private int DaysToDeliver = 7;

    public Supplier(IAddress address, ProductList products, int DaysToDeliver) {
        this.address = address;
        this.products = products;
        this.DaysToDeliver = DaysToDeliver;
    }

    public LocalDate getDeliveryDate() {
        return LocalDate.now().plusDays(DaysToDeliver);
    }

    public double placeOrder(ProductList orderProducts) {
        for( int i=0; i<orderProducts.getSize(); i++) {
            for (int j = 0; j < products.getSize(); j++) {
                if (products.getProductAtIndex(j).getName().equals(orderProducts.getProductAtIndex(i).getName()) && products.getProductAtIndex(j).getCategory().equals(orderProducts.getProductAtIndex(i).getCategory())) {
                    products.getProductAtIndex(j).setStock(products.getProductAtIndex(j).getStock() - orderProducts.getProductAtIndex(i).getStock());

                }
            }
        }
        return orderProducts.getTotalPrice();
    }
}
