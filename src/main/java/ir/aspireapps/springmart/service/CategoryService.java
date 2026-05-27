package ir.aspireapps.springmart.service;

import ir.aspireapps.springmart.dto.category.CategoryCreateRequest;
import ir.aspireapps.springmart.dto.category.CategoryFullResponse;
import ir.aspireapps.springmart.dto.category.CategoryResponse;
import ir.aspireapps.springmart.dto.category.CategoryUpdateRequest;
import ir.aspireapps.springmart.error.DuplicateResourceException;
import ir.aspireapps.springmart.error.InvalidInputException;
import ir.aspireapps.springmart.error.ResourceNotFoundException;
import ir.aspireapps.springmart.mapper.CategoryMapper;
import ir.aspireapps.springmart.model.Category;
import ir.aspireapps.springmart.repo.CategoryRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    @Transactional
    public CategoryResponse register(@NotNull @Valid CategoryCreateRequest request) {
        if (categoryRepository.existsByName(request.name()))
            throw new DuplicateResourceException("A category with name [" + request.name() + "] already exists");
        Category category = categoryMapper.toEntity(request);
        categoryRepository.save(category);
        return categoryMapper.toResponse(category);
    }

    @Transactional
    public CategoryResponse update(@NotNull @Positive Long id,
                                   @NotNull @Valid CategoryUpdateRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category with Id: " + id + " not found"));
        category.update(request);
        return categoryMapper.toResponse(category);
    }

    public void delete(@NotNull @Positive Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category with Id: " + id + " not found"));
        categoryRepository.delete(category);
    }

    public CategoryResponse get(@NotNull @Positive Long id) {
        return categoryMapper.toResponse(
                categoryRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Category with Id: " + id + " not found"))
        );
    }

    public Page<CategoryResponse> getAll(Pageable pageable) {
        if (pageable.getPageSize() > 100)
            throw new InvalidInputException("Page size must be between 5 to 100");
        return categoryRepository
                .findAll(pageable)
                .map(categoryMapper::toResponse);
    }

    public CategoryFullResponse getFull(@NotNull @Positive Long id) {
        return categoryMapper.toResponseFull(
                categoryRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Category with Id: " + id + " not found"))
        );
    }
}
