package ir.aspireapps.springmart.model;

import ir.aspireapps.springmart.dto.user.UserUpdateDetailsRequest;
import ir.aspireapps.springmart.error.RedundantActionException;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users", indexes = {
        @Index(name = "idx_user_email", columnList = "email")
})
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE USERS SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    @ToString.Include
    UUID id;

    @Column(unique = true, nullable = false)
    String email;

    @Column(nullable = false)
    String password;

    @Column(nullable = false)
    @ToString.Include
    String firstName;
    @Column(nullable = false)
    @ToString.Include
    String lastName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    Role role;

    @Column(nullable = false)
    Boolean enabled = true;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    LocalDateTime updatedAt;
    @Column(name = "password_updated_at")
    LocalDateTime passwordUpdatedAt;
    @Column(name = "deleted_at")
    LocalDateTime deletedAt;

    @OneToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Product> products = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();

    public void update(UserUpdateDetailsRequest request) {
        if (firstName.equals(request.firstName()) &&
                lastName.equals(request.lastName()))
            throw new RedundantActionException("New details for user are already the same");
        this.lastName = request.lastName();
        this.firstName = request.firstName();
    }

    public void addProduct(Product product) {
        this.products.add(product);
        product.setUser(this);
    }

    public void removeProduct(Product product) {
        this.products.remove(product);
        product.setUser(null);
    }

    public void addOrder(Order order) {
        orders.add(order);
        order.setUser(this);
    }
}
