package com.sumber.barokah.jurnal.service.impl;

import com.sumber.barokah.jurnal.dto.master.CreateProductRequest;
import com.sumber.barokah.jurnal.dto.master.PageableRequest;
import com.sumber.barokah.jurnal.dto.master.ProductResponse;
import com.sumber.barokah.jurnal.dto.master.UpdateProductRequest;
import com.sumber.barokah.jurnal.entity.master.Category;
import com.sumber.barokah.jurnal.entity.master.Product;
import com.sumber.barokah.jurnal.repository.master.CategoryRepository;
import com.sumber.barokah.jurnal.repository.master.ProductRepository;
import com.sumber.barokah.jurnal.service.ProductService;
import com.sumber.barokah.jurnal.service.ValidationService;
import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ValidationService validationService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public ProductResponse create(CreateProductRequest request) {

        validationService.validate(request);

        Category category = categoryRepository.findFirstByCategoryId(request.getCategoryId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category is not found"));

        Product pdt = new Product();
        pdt.setProductId(UUID.randomUUID().toString());
        pdt.setCategory(category);
        pdt.setProductCode(request.getProductCode());
        pdt.setName(request.getName());
        pdt.setQuantity(request.getQuantity());
        pdt.setMinimumLimit(request.getMinimumLimit());
        pdt.setUnit(request.getUnit());
        pdt.setAveragePrice(request.getAveragePrice());
        pdt.setLastPurchasePrice(request.getLastPurchasePrice());
        pdt.setPurchasePrice(request.getPurchasePrice());
        pdt.setSellingPrice(request.getSellingPrice());
        pdt.setItemType(request.getItemType());
        pdt.setDescription(request.getDescription());

        productRepository.save(pdt); // save DB

        return toProductResponse(pdt);
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> listProduct() {

        List<Product> list = productRepository.findAll();

        if (Objects.isNull(list)) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Product content does not exist!");
        }

        return list.stream().map(this::toProductResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<ProductResponse> listPagable(PageableRequest request) {

        Specification<Product> specification = (root, query, criteriaBuilder) -> {

            List<Predicate> predicate = new ArrayList<>();

            if (Objects.nonNull(request.getSortField())) {

                predicate.add(criteriaBuilder.or(
                        criteriaBuilder.like(root.get("name"), "%" + request.getSortField() + "%")
                ));

            }
            return query.where(predicate.toArray(new Predicate[]{})).getRestriction();
        };

        PageRequest pageable = PageRequest.of(request.getPage(), request.getSize(), Sort.by(Sort.Order.asc("createAt")));
        Page<Product> product = productRepository.findAll(specification, pageable);

        if (Objects.isNull(product)) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Product content does not exist!");
        }

        List<ProductResponse> productResponses = product.getContent().stream().map(this::toProductResponse).collect(Collectors.toList());

        return new PageImpl<>(productResponses, pageable, product.getTotalElements());

    }

    @Transactional(readOnly = true)
    public ProductResponse get(String categoryId, String productId) {

        Category category = categoryRepository.findFirstByCategoryId(categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category is not found"));

        //log.info("productId: {}", productId);
        Product product = productRepository.findFirstByCategoryAndProductId(category, productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product is not found"));

        //log.info("product: {}", product.getProductId());

        return toProductResponse(product);
    }

    @Transactional
    public ProductResponse update(UpdateProductRequest request) {

        validationService.validate(request);

        Category category = categoryRepository.findFirstByCategoryId(request.getCategoryId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category is not found"));

        Product pdt = productRepository.findFirstByCategoryAndProductId(category, request.getProductId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product is not found"));

        log.info("category id= {}", category.getCategoryId());
        log.info("product  id= {}", pdt.getProductId());

        pdt.setProductCode(request.getProductCode());
        pdt.setName(request.getName());
        pdt.setQuantity(request.getQuantity());
        pdt.setMinimumLimit(request.getMinimumLimit());
        pdt.setUnit(request.getUnit());
        pdt.setAveragePrice(request.getAveragePrice());
        pdt.setLastPurchasePrice(request.getLastPurchasePrice());
        pdt.setPurchasePrice(request.getPurchasePrice());
        pdt.setSellingPrice(request.getSellingPrice());
        pdt.setItemType(request.getItemType());
        pdt.setDescription(request.getDescription());

        productRepository.save(pdt); // save DB

        return toProductResponse(pdt);
    }

    @Transactional
    public void delete(String categoryId, String productId) {

        Category category = categoryRepository.findFirstByCategoryId(categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category is not found"));

        Product pdt = productRepository.findFirstByCategoryAndProductId(category, productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product is not found"));

        log.info("category id= {}", category.getCategoryId());
        log.info("product  id= {}", pdt.getProductId());

        productRepository.delete(pdt); // proses DB

    }

    private ProductResponse toProductResponse(Product product) {
        return ProductResponse.builder()
                .productId(product.getProductId())
                .productCode(product.getProductCode())
                .name(product.getName())
                .quantity(product.getQuantity())
                .minimumLimit(product.getMinimumLimit())
                .unit(product.getUnit())
                .averagePrice(product.getAveragePrice())
                .lastPurchasePrice(product.getLastPurchasePrice())
                .purchasePrice(product.getPurchasePrice())
                .sellingPrice(product.getSellingPrice())
                .itemType(product.getItemType())
                .description(product.getDescription())
                .categoryId(product.getCategory().getCategoryId())
                .createAt(product.getCreateAt())
                .updateModifiedAt(product.getUpdateModifiedAt())
                .build();
    }

}
