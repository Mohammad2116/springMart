package ir.aspireapps.springmart.model;

import ir.aspireapps.springmart.dto.user.UserUpdateDetailsRequest;
import ir.aspireapps.springmart.error.RedundantActionException;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
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
    private UUID id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @ToString.Include
    private String firstName;
    @Column(nullable = false)
    @ToString.Include
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private Boolean enabled = true;

    @Column(nullable = false)
    private Boolean locked = false;
    @Column(name = "locked_by")
    private UUID lockedBy;
    @Column(name = "locked_at")
    private Instant lockedAt;
    @Column(name = "lock_reason")
    private String lockReason;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @Column(name = "password_updated_at")
    private LocalDateTime passwordUpdatedAt;
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

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
