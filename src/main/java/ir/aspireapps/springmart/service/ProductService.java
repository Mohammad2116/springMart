package ir.aspireapps.springmart.service;

import ir.aspireapps.springmart.dto.product.ProductRegisterRequest;
import ir.aspireapps.springmart.dto.product.ProductResponse;
import ir.aspireapps.springmart.dto.product.ProductUpdateRequest;
import ir.aspireapps.springmart.error.PermissionDeniedException;
import ir.aspireapps.springmart.error.ResourceNotFoundException;
import ir.aspireapps.springmart.mapper.ProductMapper;
import ir.aspireapps.springmart.model.Category;
import ir.aspireapps.springmart.model.Product;
import ir.aspireapps.springmart.model.User;
import ir.aspireapps.springmart.repo.CategoryRepository;
import ir.aspireapps.springmart.repo.ProductRepository;
import ir.aspireapps.springmart.repo.UserRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Transactional
    public ProductResponse register(ProductRegisterRequest request, String email) {
        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category Id: " + request.categoryId() + " not found"));
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User with email [" + email + "] not found"));
        Product product = productMapper.toEntity(request);
        product.setCategory(category);
        product.setUser(user);
        category.addProduct(product);
        user.addProduct(product);
        Product savedProduct = productRepository.save(product);
        return productMapper.toResponse(savedProduct);
    }

    @Transactional
    public ProductResponse update(@Positive Long id, UUID userId, @Valid ProductUpdateRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product Id: " + id + " not found"));
        if (product.getUser().getId().equals(userId))
            throw new PermissionDeniedException("Product Id: " + id + " can't be updated by user Id " + userId);

        product.update(request);
        Category oldCategory = product.getCategory();
        if (!oldCategory.getId().equals(request.categoryId())) {
            Category newCategory = categoryRepository.findById(request.categoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category Id: " + request.categoryId() + " not found"));
            oldCategory.removeProduct(product);
            newCategory.addProduct(product);
        }

        return productMapper.toResponse(product);
    }

    public void delete(@Positive Long id, UUID userId) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product Id: " + id + "] not found"));
        if (product.getUser().getId().compareTo(userId) != 0)
            throw new PermissionDeniedException("Product Id: " + id + " not authorized to be deleted by user Id: " + userId);

        product.getCategory().removeProduct(product);
        product.getUser().removeProduct(product);
        productRepository.delete(product);
    }

    public Page<ProductResponse> findAll(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(productMapper::toResponse);
    }

    public Page<ProductResponse> findAllByCategoryId(Long categoryId, Pageable pageable) {
        return productRepository.findAllByCategoryIdAndDeletedAtIsNull(categoryId, pageable)
                .map(productMapper::toResponse);
    }

    public Page<ProductResponse> findAllByName(String productName, Pageable pageable) {
        return productRepository.findAllByNameAndDeletedAtIsNull(productName, pageable)
                .map(productMapper::toResponse);
    }

    public Page<ProductResponse> findAllByNameAndCategory(String name, Long categoryId, Pageable pageable) {
        return productRepository.findAllByNameAndCategoryIdAndDeletedAtIsNull(name, categoryId, pageable)
                .map(productMapper::toResponse);
    }

    public ProductResponse find(Long id) {
        return productMapper.toResponse(
                productRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Product with Id: " + id + " not found")));
    }
}
