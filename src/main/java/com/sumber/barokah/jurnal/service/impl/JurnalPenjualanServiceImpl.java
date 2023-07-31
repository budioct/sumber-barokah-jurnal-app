package com.sumber.barokah.jurnal.service.impl;

import com.sumber.barokah.jurnal.dto.master.PageableRequest;
import com.sumber.barokah.jurnal.dto.master.ProductResponse;
import com.sumber.barokah.jurnal.dto.transaksi.CreateJurnalPenjualanRequest;
import com.sumber.barokah.jurnal.dto.transaksi.JurnalPenjualanResponse;
import com.sumber.barokah.jurnal.dto.transaksi.UpdateJurnalPenjualanRequest;
import com.sumber.barokah.jurnal.dto.transaksi.jurnalpembelian.CreateProductJurnalPembelianRequest;
import com.sumber.barokah.jurnal.dto.transaksi.jurnalpembelian.UpdateProductJurnalPembelianRequest;
import com.sumber.barokah.jurnal.entity.master.Customer;
import com.sumber.barokah.jurnal.entity.master.Product;
import com.sumber.barokah.jurnal.entity.transaksi.JurnalPembelian;
import com.sumber.barokah.jurnal.entity.transaksi.JurnalPenjualan;
import com.sumber.barokah.jurnal.repository.master.CustomerRepository;
import com.sumber.barokah.jurnal.repository.master.ProductRepository;
import com.sumber.barokah.jurnal.repository.transaksi.JurnalPenjualanRepository;
import com.sumber.barokah.jurnal.service.JurnalPenjualanService;
import com.sumber.barokah.jurnal.service.ValidationService;
import com.sumber.barokah.jurnal.utilities.ConvertDate;
import jakarta.persistence.criteria.Predicate;
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

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JurnalPenjualanServiceImpl implements JurnalPenjualanService {


    @Autowired
    ValidationService validationService;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    JurnalPenjualanRepository jurnalPenjualanRepository;

    @Transactional
    public JurnalPenjualanResponse create(CreateJurnalPenjualanRequest request) {

        validationService.validate(request);

        Customer customer = customerRepository.findFirstByCustomerId(request.getCustomerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));

        JurnalPenjualan jp = new JurnalPenjualan();
        jp.setJurnalPenjualanId(UUID.randomUUID().toString());
        jp.setNoFaktur(request.getNoFaktur());
        jp.setTanggalTransaksi(request.getTanggalTransaksi());
        jp.setTanggalJatuhTempo(request.getTanggalJatuhTempo());
        jp.setStatus(request.getStatus());
        jp.setSisaTagihan(request.getSisaTagihan());
        jp.setJumlahTotal(request.getJumlahTotal());
        jp.setNoTransaksi(request.getNoTransaksi());
        jp.setTags(request.getTags());
        jp.setCustomer(customer);

        List<Product> productList = new LinkedList<>();
        for (CreateProductJurnalPembelianRequest products : request.getCreateProducts()) {

            validationService.validate(products);

            Product pdt = productRepository.findFirstByProductId(products.getProductId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product is not found"));

            productList.add(pdt);
            jp.setLike_product1(productList);

        }

        jurnalPenjualanRepository.save(jp); // save DB

        return toJurnalPenjualanResponse(jp);

    }

    @Transactional(readOnly = true)
    public List<JurnalPenjualanResponse> listJurnalPenjualan() {

        List<JurnalPenjualan> list = jurnalPenjualanRepository.findAll();

        if (Objects.isNull(list)) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Jurnal Penjualan content does not exist!");
        }

        return list.stream().map(this::toJurnalPenjualanResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<JurnalPenjualanResponse> listPageable(PageableRequest request) {

        Specification<JurnalPenjualan> specification = (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (Objects.nonNull(request.getSortField())) {
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.like(root.get("noFaktur"), "%" + request.getSortField() + "%"),
                        criteriaBuilder.like(root.get("noTransaksi"), "%" + request.getSortField() + "%")
                ));
            }
            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };

        PageRequest pageable = PageRequest.of(request.getPage(), request.getSize(), Sort.by(Sort.Order.desc("createAt")));
        Page<JurnalPenjualan> jurnalPenjualan = jurnalPenjualanRepository.findAll(specification, pageable);

        if (Objects.isNull(jurnalPenjualan)) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Jurnal Penjualan content does not exist!");
        }

        List<JurnalPenjualanResponse> jurnalPenjualanResponses = jurnalPenjualan.getContent().stream().map(this::toJurnalPenjualanResponse).collect(Collectors.toList());

        return new PageImpl<>(jurnalPenjualanResponses, pageable, jurnalPenjualan.getTotalElements());

    }

    @Transactional(readOnly = true)
    public JurnalPenjualanResponse get(String id) {

        JurnalPenjualan jp = jurnalPenjualanRepository.findFirstByJurnalPenjualanId(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Jurnal Penjualan not found"));

        return toJurnalPenjualanResponse(jp);
    }

    @Transactional
    public JurnalPenjualanResponse update(UpdateJurnalPenjualanRequest request) {

        validationService.validate(request);

        Customer customer = customerRepository.findFirstByCustomerId(request.getCustomerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));

        JurnalPenjualan jp = jurnalPenjualanRepository.findFirstByCustomerAndJurnalPenjualanId(customer, request.getJurnalPenjualanId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Jurnal Penjualan not found"));

        jp.setNoFaktur(request.getNoFaktur());
        jp.setTanggalTransaksi(request.getTanggalTransaksi());
        jp.setTanggalJatuhTempo(request.getTanggalJatuhTempo());
        jp.setStatus(request.getStatus());
        jp.setSisaTagihan(request.getSisaTagihan());
        jp.setJumlahTotal(request.getJumlahTotal());
        jp.setNoTransaksi(request.getNoTransaksi());
        jp.setTags(request.getTags());
        jp.setCustomer(customer);

        List<Product> productList = new LinkedList<>();

        // delete product
        //        if (Objects.nonNull(request.getDeleteProducts())) {
//            for (UpdateProductJurnalPembelianRequest products : request.getDeleteProducts()) {
//                log.info("ID Looping: {}", products.getProductId());
//
//                validationService.validate(products);
//
//                Product product = productRepository.findFirstByProductId(products.getProductId())
//                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product is not found"));
//                log.info("Product: {}", product);
//
//                productslist.add(product);
//                jp.setLike_product(productslist);
//            }
//        }

        // update product
        if (Objects.nonNull(request.getUpdateProducts())) {
            for (UpdateProductJurnalPembelianRequest products : request.getUpdateProducts()) {
                validationService.validate(products);

                Product pdt = productRepository.findFirstByProductId(products.getProductId())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product is not found"));

                pdt.setDescription(products.getDescription());
                pdt.setQuantity(products.getQuantity());
                pdt.setSellingPrice(products.getSellingPrice());

                productList.add(pdt);
                jp.setLike_product1(productList);
            }
        }
        jurnalPenjualanRepository.save(jp); // save DB

        return toJurnalPenjualanResponse(jp);

    }

    @Transactional
    public void delete(String id) {

        JurnalPenjualan jp = jurnalPenjualanRepository.findFirstByJurnalPenjualanId(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Jurnal Penjualan not found"));

        jurnalPenjualanRepository.deleteJurnalPenjualan(jp.getJurnalPenjualanId()); // delete DB

    }

    private JurnalPenjualanResponse toJurnalPenjualanResponse(JurnalPenjualan jp) {
        return JurnalPenjualanResponse.builder()
                .jurnalPenjualanId(jp.getJurnalPenjualanId())
                .noFaktur(jp.getNoFaktur())
                .tanggalTransaksi(jp.getTanggalTransaksi())
                .tanggalJatuhTempo(jp.getTanggalJatuhTempo())
                .status(jp.getStatus())
                .sisaTagihan(jp.getSisaTagihan())
                .jumlahTotal(jp.getJumlahTotal())
                .noTransaksi(jp.getNoTransaksi())
                .tags(jp.getTags())
                .customer(jp.getCustomer())
                .products(jp.getLike_product1().stream().map(this::toProductResponse).collect(Collectors.toList()))
                .createAt(ConvertDate.convertToLocalDateTime(jp.getCreateAt()))
                .updateModifiedAt(ConvertDate.convertToLocalDateTime(jp.getUpdateModifiedAt()))
                .build();

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
                .createAt(ConvertDate.convertToLocalDateTime(product.getCreateAt()))
                .updateModifiedAt(ConvertDate.convertToLocalDateTime(product.getUpdateModifiedAt()))
                .build();

    }


}
