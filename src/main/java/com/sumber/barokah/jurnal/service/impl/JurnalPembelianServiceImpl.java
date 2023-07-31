package com.sumber.barokah.jurnal.service.impl;

import com.sumber.barokah.jurnal.dto.master.CreateProductRequest;
import com.sumber.barokah.jurnal.dto.master.PageableRequest;
import com.sumber.barokah.jurnal.dto.master.ProductResponse;
import com.sumber.barokah.jurnal.dto.master.UpdateProductRequest;
import com.sumber.barokah.jurnal.dto.transaksi.CreateJurnalPembelianRequest;
import com.sumber.barokah.jurnal.dto.transaksi.JurnalPembelianResponse;
import com.sumber.barokah.jurnal.dto.transaksi.UpdateJurnalPembelianRequest;
import com.sumber.barokah.jurnal.dto.transaksi.jurnalpembelian.CreateProductJurnalPembelianRequest;
import com.sumber.barokah.jurnal.dto.transaksi.jurnalpembelian.UpdateProductJurnalPembelianRequest;
import com.sumber.barokah.jurnal.entity.master.Product;
import com.sumber.barokah.jurnal.entity.master.Supplier;
import com.sumber.barokah.jurnal.entity.transaksi.JurnalPembelian;
import com.sumber.barokah.jurnal.entity.transaksi.JurnalPenjualan;
import com.sumber.barokah.jurnal.repository.master.ProductRepository;
import com.sumber.barokah.jurnal.repository.master.SupplierRepository;
import com.sumber.barokah.jurnal.repository.transaksi.JurnalPembelianRepository;
import com.sumber.barokah.jurnal.service.JurnalPembelianService;
import com.sumber.barokah.jurnal.service.ValidationService;
import com.sumber.barokah.jurnal.utilities.ConvertDate;
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

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class JurnalPembelianServiceImpl implements JurnalPembelianService {

    @Autowired
    ValidationService validationService;

    @Autowired
    SupplierRepository supplierRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    JurnalPembelianRepository jurnalPembelianRepository;

    @Transactional
    public JurnalPembelianResponse create(CreateJurnalPembelianRequest request) {

        validationService.validate(request);

        Supplier supplier = supplierRepository.findFirstBySupplierId(request.getSupplierId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Supplier not found"));

        JurnalPembelian jp = new JurnalPembelian();
        jp.setJurnalPembelianId(UUID.randomUUID().toString());
        jp.setNoFaktur(request.getNoFaktur());
        jp.setTanggalTransaksi(request.getTanggalTransaksi());
        jp.setTanggalJatuhTempo(request.getTanggalJatuhTempo());
        jp.setStatus(request.getStatus());
        jp.setSisaTagihan(request.getSisaTagihan());
        jp.setJumlahTotal(request.getJumlahTotal());
        jp.setNoTransaksi(request.getNoTransaksi());
        jp.setTags(request.getTags());
        jp.setSupplier(supplier);

//        log.info("ID Looping: {}", request.getProducts());
        List<Product> productslist = new LinkedList<>();
        for (CreateProductJurnalPembelianRequest products : request.getCreateProducts()) {

            validationService.validate(products);

            //log.info("ID Looping: {}", products.getProductId());
            Product pdt = productRepository.findFirstByProductId(products.getProductId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product is not found"));
            //log.info("Product: {}", pdt);

            productslist.add(pdt); // List<Product> productslist
            jp.setLike_product0(productslist); // List<Product> like_product
        }

        jurnalPembelianRepository.save(jp); // save DB

        return toJurnalPembelianResponse(jp);
    }

    @Transactional(readOnly = true)
    public List<JurnalPembelianResponse> listJurnalPembelian() {

        List<JurnalPembelian> list = jurnalPembelianRepository.findAll();

        if (Objects.isNull(list)) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Jurnal Pembelian content does not exist!");
        }

        return list.stream().map(this::toJurnalPembelianResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<JurnalPembelianResponse> listPageable(PageableRequest request) {

        Specification<JurnalPembelian> specification = (root, query, criteriaBuilder) -> {

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
        Page<JurnalPembelian> jurnalPembelian = jurnalPembelianRepository.findAll(specification, pageable);

        if (Objects.isNull(jurnalPembelian)) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Jurnal Pembelian content does not exist!");
        }

        List<JurnalPembelianResponse> jurnalPembelianResponses = jurnalPembelian.getContent().stream().map(this::toJurnalPembelianResponse).collect(Collectors.toList());

        return new PageImpl<>(jurnalPembelianResponses, pageable, jurnalPembelian.getTotalElements());
    }

    @Transactional(readOnly = true)
    public JurnalPembelianResponse get(String id) {

        JurnalPembelian jp = jurnalPembelianRepository.findFirstByJurnalPembelianId(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Jurnal Pembelian not found"));

        return toJurnalPembelianResponse(jp);
    }

    @Transactional
    public JurnalPembelianResponse update(UpdateJurnalPembelianRequest request) {
        // note supplier tidak boleh di ubah

        validationService.validate(request);

        Supplier supplier = supplierRepository.findFirstBySupplierId(request.getSupplierId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Supplier not found"));

        JurnalPembelian jp = jurnalPembelianRepository.findFirstBySupplierAndJurnalPembelianId(supplier, request.getJurnalPembelianId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Jurnal Pembelian not found"));

        //log.info("ID Supplier: {}", supplier.getSupplierId());
        //log.info("ID JURNAL PEMBELIAN: {}", jp.getJurnalPembelianId());

        jp.setNoFaktur(request.getNoFaktur());
        jp.setTanggalTransaksi(request.getTanggalTransaksi());
        jp.setTanggalJatuhTempo(request.getTanggalJatuhTempo());
        jp.setStatus(request.getStatus());
        jp.setSisaTagihan(request.getSisaTagihan());
        jp.setJumlahTotal(request.getJumlahTotal());
        jp.setNoTransaksi(request.getNoTransaksi());
        jp.setTags(request.getTags());
        jp.setSupplier(supplier);

        // belum buat opsi delete belum yakin
        // delete and update product // many to many
        //log.info("ID Looping: {}", request.getUpdateProducts());
        List<Product> productslist = new LinkedList<>();
        // delete
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
        // update
        if (Objects.nonNull(request.getUpdateProducts())) {
            for (UpdateProductJurnalPembelianRequest products : request.getUpdateProducts()) {
                validationService.validate(products);

                Product pdt = productRepository.findFirstByProductId(products.getProductId())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product is not found"));

                pdt.setDescription(products.getDescription());
                pdt.setQuantity(products.getQuantity());
                pdt.setSellingPrice(products.getSellingPrice());

                productslist.add(pdt); // List<Product> productslist
                jp.setLike_product0(productslist); // List<Product> like_product
            }
        }
        jurnalPembelianRepository.save(jp); // save DB

        return toJurnalPembelianResponse(jp);
    }

    @Transactional
    public void delete(String id) {

        JurnalPembelian jp = jurnalPembelianRepository.findFirstByJurnalPembelianId(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Jurnal Pembelian not found"));

        jurnalPembelianRepository.deleteJurnalPembelian(jp.getJurnalPembelianId()); // delete DB

    }

    private JurnalPembelianResponse toJurnalPembelianResponse(JurnalPembelian jp) {
        return JurnalPembelianResponse.builder()
                .jurnalPembelianId(jp.getJurnalPembelianId())
                .noFaktur(jp.getNoFaktur())
                .tanggalTransaksi(jp.getTanggalTransaksi())
                .tanggalJatuhTempo(jp.getTanggalJatuhTempo())
                .status(jp.getStatus())
                .sisaTagihan(jp.getSisaTagihan())
                .jumlahTotal(jp.getJumlahTotal())
                .noTransaksi(jp.getNoTransaksi())
                .tags(jp.getTags())
                .supplier(jp.getSupplier())
                .products(jp.getLike_product0().stream().map(this::toProductResponse).collect(Collectors.toList()))
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
