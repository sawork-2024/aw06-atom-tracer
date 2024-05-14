package orders.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product implements Serializable {

    @NotEmpty
    private String pid;

    @NotEmpty
    private String name;

    @NotNull
    private double price;

    @NotEmpty
    private String category;

    private String img;

    @Min(0)
    private int stock;

    @Min(0)
    private int quantity;

    public Product(String pid, String name, double price, String img, String category) {
        this.pid = pid;
        this.name = name;
        this.price = price;
        this.category = category;
        this.img = img;
        this.stock = 1;
        this.quantity = 500;
    }
}
