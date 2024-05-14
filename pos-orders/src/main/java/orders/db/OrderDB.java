package orders.db;

import orders.model.Item;
import orders.model.Order;
import orders.model.Product;

import java.util.List;

public interface OrderDB {
    List<Product> getProducts();

    Product getProduct(String productId);

    void saveOrder();

    Order getOrder(int orderId);

    void changeItem(String productId, int amount);

    Item getItem(String productId);
}
