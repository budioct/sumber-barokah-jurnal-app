package com.sumber.barokah.jurnal.service.impl;

import com.sumber.barokah.jurnal.dto.master.CreateProductRequest;
import com.sumber.barokah.jurnal.dto.master.ProductResponse;
import com.sumber.barokah.jurnal.entity.master.Category;
import com.sumber.barokah.jurnal.entity.master.Product;
import com.sumber.barokah.jurnal.repository.master.CategoryRepository;
import com.sumber.barokah.jurnal.repository.master.ProductRepository;
import com.sumber.barokah.jurnal.service.ProductService;
import com.sumber.barokah.jurnal.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

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
        pdt.setCategory(category);

        productRepository.save(pdt);

        return toProductResponse(pdt);
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> listProduct() {

        List<Product> list = productRepository.findAll();

        if (Objects.isNull(list)){
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Customer content does not exist!");
        }

        return list.stream().map(this::toProductResponse).collect(Collectors.toList());
    }

    private ProductResponse toProductResponse(Product product){
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
