import javax.swing.*;
import java.awt.*;

public class ProductListCellRenderer extends DefaultListCellRenderer {
    private ProductList listModel;
    private static int selectedTab;

    public ProductListCellRenderer(ProductList listModel) {
        this.listModel = listModel;
    }

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (value != null) {

            Product product = listModel.getProductAtIndex(index);
            ImageIcon icon = getProductImage(product);
            label.setIcon(icon);

            switch (selectedTab) {
                case 2:
                    label.setText(product.toOrder());
                    break;
                case 3:
                    label.setText(product.toBasket());
                    break;
                default:

                    label.setText(product.toAddProduct());

                    if (!(isProductInStock(product))) {
                        label.setForeground(Color.RED);
                    } else if(!(isProductAboveMinStock(product))) {
                        label.setForeground(Color.YELLOW);
                    } else {
                        label.setForeground(Color.GREEN);
                    }
                    break;
            }

        }
        return label;
    }

    private ImageIcon getProductImage(Product product) {
        return new ImageIcon(new ImageIcon(product.getImageFile()).getImage()
                .getScaledInstance(50, 50, Image.SCALE_SMOOTH));
    }

    private boolean isProductAboveMinStock(Product product) {
        return product.getStock() >= product.getMinStock();
    }

    private boolean isProductInStock(Product product) {
        return product.getStock() >= 1;
    }

    public static void setTab(int src){selectedTab = src;}
}