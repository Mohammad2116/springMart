package ir.aspireapps.springmart.model;

import ir.aspireapps.springmart.error.InvalidInputException;
import ir.aspireapps.springmart.error.ResourceNotFoundException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "carts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "cart")
    private User user;

    @ElementCollection
    @CollectionTable(
            name = "cart_items",
            joinColumns = @JoinColumn(name = "cart_id")
    )
    @MapKeyJoinColumn(name = "product_id")
    @Column(name = "quantity", nullable = false)
    private Map<Product, Long> products = new HashMap<>();

    public void clear() {
        products.forEach((product, quantity) -> {
            product.setStock(product.getStock() + quantity);
        });
        products.clear();
    }

    public void add(Product Product, long quantity) {
        products.merge(Product, quantity, Long::sum);
    }

    public void remove(Product product, long quantity) {
        if (!products.containsKey(product))
            throw new ResourceNotFoundException("Product Id: " + product.getId() + " not found in cart");

        long cartQuantity = products.get(product);
        if (cartQuantity > quantity) {
            products.merge(product, -quantity, Long::sum);
        } else if (cartQuantity == quantity) {
            products.remove(product);
        } else throw new InvalidInputException("There is not enough items in chart to remove");
    }
}
