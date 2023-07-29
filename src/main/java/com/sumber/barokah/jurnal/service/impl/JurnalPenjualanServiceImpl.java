package com.sumber.barokah.jurnal.service.impl;

import com.sumber.barokah.jurnal.dto.master.ProductResponse;
import com.sumber.barokah.jurnal.dto.transaksi.CreateJurnalPenjualanRequest;
import com.sumber.barokah.jurnal.dto.transaksi.JurnalPenjualanResponse;
import com.sumber.barokah.jurnal.dto.transaksi.jurnalpembelian.CreateProductJurnalPembelianRequest;
import com.sumber.barokah.jurnal.entity.master.Customer;
import com.sumber.barokah.jurnal.entity.master.Product;
import com.sumber.barokah.jurnal.entity.transaksi.JurnalPembelian;
import com.sumber.barokah.jurnal.entity.transaksi.JurnalPenjualan;
import com.sumber.barokah.jurnal.repository.master.CustomerRepository;
import com.sumber.barokah.jurnal.repository.master.ProductRepository;
import com.sumber.barokah.jurnal.repository.transaksi.JurnalPenjualanRepository;
import com.sumber.barokah.jurnal.service.JurnalPenjualanService;
import com.sumber.barokah.jurnal.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
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
        for (CreateProductJurnalPembelianRequest products : request.getCreateProducts()){

            validationService.validate(products);

            Product pdt = productRepository.findFirstByProductId(products.getProductId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product is not found"));

            productList.add(pdt);
            jp.setLike_product1(productList);

        }

        jurnalPenjualanRepository.save(jp);

        return toJurnalPenjualanResponse(jp);

    }

    private JurnalPenjualanResponse toJurnalPenjualanResponse(JurnalPenjualan jp){
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
                .createAt(jp.getCreateAt())
                .updateModifiedAt(jp.getUpdateModifiedAt())
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
                .createAt(product.getCreateAt())
                .updateModifiedAt(product.getUpdateModifiedAt())
                .build();

    }


}
