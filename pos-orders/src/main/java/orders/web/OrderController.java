package orders.web;

import orders.mapper.OrderMapper;
import orders.model.Order;
import orders.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webpos.rest.api.OrdersServiceApi;
import webpos.rest.dto.OrderDto;

@RestController
@RequestMapping("/ordersService") // 定义API的基础路径
public class OrderController implements OrdersServiceApi {
    private final OrderService orderService;
    private final OrderMapper orderMapper;

    // Constructor injection for service and mapper
    public OrderController(OrderService orderService, OrderMapper orderMapper) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }

    @Override
    @GetMapping("/add/{productId}")
    @CrossOrigin(value = "*", maxAge = 1800, allowedHeaders = "*")
    public ResponseEntity<Boolean> addProduct(@PathVariable String productId) {
        if(orderService.getProduct(productId) == null)
            return ResponseEntity.ok(false);
        orderService.addItem(productId);
        return ResponseEntity.ok(true);
    }

    @Override
    @GetMapping("/delete/{productId}")
    @CrossOrigin(value = "*", maxAge = 1800, allowedHeaders = "*")
    public ResponseEntity<Boolean> deleteProduct(@PathVariable String productId) {
        if(orderService.getProduct(productId) == null)
            return ResponseEntity.ok(false);
        orderService.deleteItem(productId);
        return ResponseEntity.ok(true);
    }


    @Override
    @GetMapping("/checkout")
    @CrossOrigin(value = "*", maxAge = 1800, allowedHeaders = "*")
    public ResponseEntity<Boolean> checkCart() {
        orderService.checkoutOrder();
        return ResponseEntity.ok(true);
    }

    @Override
    @GetMapping("/order/{orderId}")
    @CrossOrigin(value = "*", maxAge = 1800, allowedHeaders = "*")
    public ResponseEntity<OrderDto> getOrder(@PathVariable Integer orderId) {
        Order theOrder = orderService.getOrder(orderId);
        if (theOrder == null)
            // 使用HTTP 404 Not Found，如果没有任何产品可列出
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(orderMapper.toOrderDto(theOrder)); // 使用HTTP 200 OK
    }
}
