package ir.aspireapps.springmart.repo;

import ir.aspireapps.springmart.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findAllByCategoryIdAndDeletedAtIsNull(Long categoryId, Pageable pageable);

    Page<Product> findAllByNameAndDeletedAtIsNull(String productName, Pageable pageable);

    Page<Product> findAllByNameAndCategoryIdAndDeletedAtIsNull(String name, Long categoryId, Pageable pageable);
}
