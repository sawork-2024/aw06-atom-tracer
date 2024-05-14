package orders.service;

import orders.model.Order;
import orders.model.Product;

public interface OrderService {
    void checkoutOrder();

    void addItem(String productId);

    void deleteItem(String productId);

    void updateItem(String productId, int amount);

    Order getOrder(int orderId);
    Product getProduct(String productId);
}
