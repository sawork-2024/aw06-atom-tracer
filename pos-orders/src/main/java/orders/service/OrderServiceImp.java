package orders.service;

import orders.db.OrderDB;
import orders.model.Order;
import orders.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImp implements OrderService {
    private OrderDB orderDB;

    @Autowired
    public void setPosDB(OrderDB orderDB) {
        this.orderDB = orderDB;
    }

    @Override
    public void checkoutOrder() {
        orderDB.saveOrder();
    }

    @Override
    public void addItem(String productId) {
        orderDB.changeItem(productId, 1);
    }

    @Override
    public void deleteItem(String productId) {
        orderDB.changeItem(productId, -1 * orderDB.getItem(productId).getAmount());
    }

    @Override
    public void updateItem(String productId, int deltaAmount) {
        orderDB.changeItem(productId, deltaAmount);
    }

    @Override
    public Order getOrder(int orderId) {
        return orderDB.getOrder(orderId);
    }

    @Override
    public Product getProduct(String productId) {
        return orderDB.getProduct(productId);
    }
}
