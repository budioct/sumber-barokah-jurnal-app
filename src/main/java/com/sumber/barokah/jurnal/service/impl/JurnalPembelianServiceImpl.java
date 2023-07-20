package com.sumber.barokah.jurnal.service.impl;

import com.sumber.barokah.jurnal.dto.master.CreateProductRequest;
import com.sumber.barokah.jurnal.dto.master.ProductResponse;
import com.sumber.barokah.jurnal.dto.transaksi.CreateJurnalPembelianRequest;
import com.sumber.barokah.jurnal.dto.transaksi.JurnalPembelianResponse;
import com.sumber.barokah.jurnal.entity.master.Product;
import com.sumber.barokah.jurnal.entity.master.Supplier;
import com.sumber.barokah.jurnal.entity.transaksi.JurnalPembelian;
import com.sumber.barokah.jurnal.entity.transaksi.JurnalPenjualan;
import com.sumber.barokah.jurnal.repository.master.ProductRepository;
import com.sumber.barokah.jurnal.repository.master.SupplierRepository;
import com.sumber.barokah.jurnal.repository.transaksi.JurnalPembelianRepository;
import com.sumber.barokah.jurnal.service.JurnalPembelianService;
import com.sumber.barokah.jurnal.service.ValidationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
        //log.info("ID Looping: {}", request.getProducts());
        List<Product> productslist = new LinkedList<>();
        for (CreateProductRequest products : request.getProducts()) {
            //log.info("ID Looping: {}", products.getProductId());
            Product product = productRepository.findFirstByProductId(products.getProductId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product is not found"));
            //log.info("Product: {}", product);
            productslist.add(product);
            jp.setLike_product(productslist);
        }

        jurnalPembelianRepository.save(jp); // save DB

        return toJurnalPembelianRepository(jp);
    }

    @Transactional(readOnly = true)
    public List<JurnalPembelianResponse> listJurnalPembelian() {

        List<JurnalPembelian> list = jurnalPembelianRepository.findAll();

        if (Objects.isNull(list)){
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Jurnal Pembelian content does not exist!");
        }

        return list.stream().map(this::toJurnalPembelianRepository).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public JurnalPembelianResponse get(String id) {

        JurnalPembelian jp = jurnalPembelianRepository.findFirstByJurnalPembelianId(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Jurnal Pembelian not found"));

        return toJurnalPembelianRepository(jp);
    }

    private JurnalPembelianResponse toJurnalPembelianRepository(JurnalPembelian jp) {
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
                .products(jp.getLike_product().stream().map(this::toProductResponse).collect(Collectors.toList()))
                .createAt(jp.getCreateAt())
                .updateModifiedAt(jp.getUpdateModifiedAt())
                .build();
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
