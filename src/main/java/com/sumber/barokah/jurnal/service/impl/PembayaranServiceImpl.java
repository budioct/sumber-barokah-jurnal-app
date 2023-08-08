package com.sumber.barokah.jurnal.service.impl;

import com.sumber.barokah.jurnal.dto.master.CustomerResponse;
import com.sumber.barokah.jurnal.dto.master.PageableRequest;
import com.sumber.barokah.jurnal.dto.master.ProductResponse;
import com.sumber.barokah.jurnal.dto.master.SupplierResponse;
import com.sumber.barokah.jurnal.dto.transaksi.*;
import com.sumber.barokah.jurnal.dto.transaksi.pembayaran.CreatePembayaranJurnalPembelianRequest;
import com.sumber.barokah.jurnal.dto.transaksi.pembayaran.CreatePembayaranJurnalPenjualanRequest;
import com.sumber.barokah.jurnal.entity.master.Customer;
import com.sumber.barokah.jurnal.entity.master.Product;
import com.sumber.barokah.jurnal.entity.master.Supplier;
import com.sumber.barokah.jurnal.entity.transaksi.JurnalPembelian;
import com.sumber.barokah.jurnal.entity.transaksi.JurnalPenjualan;
import com.sumber.barokah.jurnal.entity.transaksi.Pembayaran;
import com.sumber.barokah.jurnal.repository.master.CustomerRepository;
import com.sumber.barokah.jurnal.repository.master.ProductRepository;
import com.sumber.barokah.jurnal.repository.master.SupplierRepository;
import com.sumber.barokah.jurnal.repository.transaksi.JurnalPembelianRepository;
import com.sumber.barokah.jurnal.repository.transaksi.JurnalPenjualanRepository;
import com.sumber.barokah.jurnal.repository.transaksi.PembayaranRepository;
import com.sumber.barokah.jurnal.service.PembayaranService;
import com.sumber.barokah.jurnal.service.ValidationService;
import com.sumber.barokah.jurnal.utilities.ConvertDate;
import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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
public class PembayaranServiceImpl implements PembayaranService {

    @Autowired
    ValidationService validationService;

    @Autowired
    SupplierRepository supplierRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    JurnalPembelianRepository jurnalPembelianRepository;

    @Autowired
    JurnalPenjualanRepository jurnalPenjualanRepository;

    @Autowired
    PembayaranRepository pembayaranRepository;

    @Transactional
    public PembayaranResponse create(CreatePembayaranRequest request) {

        validationService.validate(request);

        //JurnalPembelian jurnalPembelian = jurnalPembelianRepository.findFirstByJurnalPembelianId(request.getJurnalPembelianId())
        //        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Jurnal Pembelian Not found"));

        Pembayaran byr = new Pembayaran();
        byr.setPembayaranId(UUID.randomUUID().toString());
        byr.setTanggalPembayaran(request.getTanggalPembayaran());
        byr.setTotalPembayaran(request.getTotalPembayaran());
        byr.setStatus(request.getStatus());
        byr.setKeterangan(request.getKeterangan());
//        byr.setJurnalPembeliansLikeBy(jurnalPembelian);

        pembayaranRepository.save(byr); // save DB

        return toPembayaranResponse(byr);
    }

    @Transactional
    public PembayaranJurnalPembelianResponse createPembayaranJurnalPembelian(CreatePembayaranJurnalPembelianRequest request) {

        validationService.validate(request);

        Supplier supplier = supplierRepository.findFirstBySupplierId(request.getSupplierId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Supplier not found"));

        JurnalPembelian jp = jurnalPembelianRepository.findFirstBySupplierAndJurnalPembelianId(supplier, request.getJurnalPembelianId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Jurnal Pembelian not found"));

        List<JurnalPembelian> jurnalPembelianList = new LinkedList<>();

        Pembayaran byr = new Pembayaran();
        byr.setPembayaranId(UUID.randomUUID().toString());
        byr.setTanggalPembayaran(request.getTanggalPembayaran());
        byr.setTotalPembayaran(request.getTotalPembayaran());
        byr.setStatus(request.getStatus());
        byr.setKeterangan(request.getKeterangan());

        if (jp.getSisaTagihan() == 0) {

        }

        Long sisaTagihan = jp.getSisaTagihan() - request.getTotalPembayaran();
        Long jumlahTotal = jp.getJumlahTotal() + request.getTotalPembayaran();

        jp.setSisaTagihan(sisaTagihan); // update jurnal
        jp.setJumlahTotal(jumlahTotal);
        //log.info("{} - {} = {}", jp.getSisaTagihan(), request.getTotalPembayaran(), sisaTagihan);
        //log.info("{} + {} = {}", jp.getJumlahTotal(), request.getTotalPembayaran(), jumlahTotal);

        supplier.setSaldo(sisaTagihan);
        jp.setSupplier(supplier);

        jurnalPembelianList.add(jp); // List<JurnalPembelian> jurnalPembelianList
        byr.setLike_jurnal_pembelian(jurnalPembelianList); // List<JurnalPembelian> like_jurnal_pembelian

        pembayaranRepository.save(byr);

        return toPembayaranJurnalPembelianResponse(byr);

    }

    @Transactional
    public PembayaranJurnalPenjualanResponse createPembayaranJurnalPenjualan(CreatePembayaranJurnalPenjualanRequest request) {

        validationService.validate(request);

        Customer customer = customerRepository.findFirstByCustomerId(request.getCustomerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));

        JurnalPenjualan jp = jurnalPenjualanRepository.findFirstByCustomerAndJurnalPenjualanId(customer, request.getJurnalPenjualanId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Jurnal Penjualan not found"));

        List<JurnalPenjualan> jurnalPenjualanList = new LinkedList<>();

        Pembayaran byr = new Pembayaran();
        byr.setPembayaranId(UUID.randomUUID().toString());
        byr.setTanggalPembayaran(request.getTanggalPembayaran());
        byr.setTotalPembayaran(request.getTotalPembayaran());
        byr.setStatus(request.getStatus());
        byr.setKeterangan(request.getKeterangan());

        Long sisaTagihan = jp.getSisaTagihan() - request.getTotalPembayaran();
        Long jumlahTotal = jp.getJumlahTotal() + request.getTotalPembayaran();

        jp.setSisaTagihan(sisaTagihan); // update jurnal
        jp.setJumlahTotal(jumlahTotal);
        //log.info("{} - {} = {}", jp.getSisaTagihan(), request.getTotalPembayaran(), sisaTagihan);
        //log.info("{} + {} = {}", jp.getJumlahTotal(), request.getTotalPembayaran(), jumlahTotal);

        customer.setSaldo(sisaTagihan);
        jp.setCustomer(customer);

        jurnalPenjualanList.add(jp); // List<JurnalPembelian> jurnalPembelianList
        byr.setLike_jurnal_penjualan(jurnalPenjualanList); // List<JurnalPembelian> like_jurnal_pembelian

        pembayaranRepository.save(byr);

        return toPembayaranJurnalPenjualanResponse(byr);
    }

    @Transactional(readOnly = true)
    public List<PembayaranResponse> list() {

        List<Pembayaran> list = pembayaranRepository.findAll();

        if (Objects.isNull(list)) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Pembayaran content does not exist!");
        }

        return list.stream().map(this::toPembayaranResponse).collect(Collectors.toList());

    }

    @Transactional(readOnly = true)
    public Page<PembayaranResponse> listPageable(PageableRequest request) {

        Specification<Pembayaran> specification = (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (Objects.nonNull(request.getSortField())) {
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.like(root.get("status"), "%" + request.getSortField() + "%"),
                        criteriaBuilder.like(root.get("keterangan"), "%" + request.getSortField() + "%")
                        //criteriaBuilder.like(root.get("nominalPembayaran"), "%" + request.getSortField() + "%")
                ));
            }
            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };

        PageRequest pageable = PageRequest.of(request.getPage(), request.getSize(), Sort.by(Sort.Order.desc("createAt")));
        Page<Pembayaran> pembayaran = pembayaranRepository.findAll(specification, pageable);

        if (Objects.isNull(pembayaran)) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Pembayaran content does not exist!");
        }

        List<PembayaranResponse> pembayaranResponses = pembayaran.getContent().stream().map(this::toPembayaranResponse).collect(Collectors.toList());

        return new PageImpl<>(pembayaranResponses, pageable, pembayaran.getTotalElements());

    }

    @Transactional(readOnly = true)
    public PembayaranResponse get(String id) {

        Pembayaran pembayaran = pembayaranRepository.findFirstByPembayaranId(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pembayaran not found"));

        return toPembayaranResponse(pembayaran);
    }

    @Transactional
    public PembayaranResponse update(UpdatePembayaranRequest request) {

        validationService.validate(request);

//        JurnalPembelian jurnalPembelian = jurnalPembelianRepository.findFirstByJurnalPembelianId(request.getJurnalPembelianId())
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Jurnal Pembelian not found"));

        Pembayaran byr = pembayaranRepository.findFirstByPembayaranId(request.getPembayaranId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pembayaran not found"));

        byr.setTanggalPembayaran(request.getTanggalPembayaran());
        byr.setTotalPembayaran(request.getTotalPembayaran());
        byr.setStatus(request.getStatus());
        byr.setKeterangan(request.getKeterangan());
//        byr.setJurnalPembeliansLikeBy(jurnalPembelian);

        pembayaranRepository.save(byr);

        return toPembayaranResponse(byr);
    }

    @Transactional
    public void delete(String id) {

        Pembayaran pembayaran = pembayaranRepository.findFirstByPembayaranId(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pembayaran not found"));

        pembayaranRepository.delete(pembayaran);

    }

    private PembayaranResponse toPembayaranResponse(Pembayaran pembayaran){
        return PembayaranResponse.builder()
                .pembayaranId(pembayaran.getPembayaranId())
                .tanggalPembayaran(pembayaran.getTanggalPembayaran())
                .totalPembayaran(pembayaran.getTotalPembayaran())
                .status(pembayaran.getStatus())
                .keterangan(pembayaran.getKeterangan())
                .createAt(ConvertDate.convertToLocalDateTime(pembayaran.getCreateAt()))
                .updateModifiedAt(ConvertDate.convertToLocalDateTime(pembayaran.getUpdateModifiedAt()))
                .build();
    }

    private PembayaranJurnalPembelianResponse toPembayaranJurnalPembelianResponse(Pembayaran pembayaran) {
            return PembayaranJurnalPembelianResponse.builder()
                    .pembayaranId(pembayaran.getPembayaranId())
                    .tanggalPembayaran(pembayaran.getTanggalPembayaran())
                    .totalPembayaran(pembayaran.getTotalPembayaran())
                    .status(pembayaran.getStatus())
                    .keterangan(pembayaran.getKeterangan())
//                .jurnalPembeliansLikeBy(toJurnalPembelianResponse(pembayaran.getLike_jurnal_pembelian().get(0)))
                    .jurnalPembeliansLikeBy(pembayaran.getLike_jurnal_pembelian().stream().map(this::toJurnalPembelianResponse).collect(Collectors.toList()))
                    .createAt(ConvertDate.convertToLocalDateTime(pembayaran.getCreateAt()))
                    .updateModifiedAt(ConvertDate.convertToLocalDateTime(pembayaran.getUpdateModifiedAt()))
                    .build();

    }

    private PembayaranJurnalPenjualanResponse toPembayaranJurnalPenjualanResponse(Pembayaran pembayaran){
        return PembayaranJurnalPenjualanResponse.builder()
                .pembayaranId(pembayaran.getPembayaranId())
                .tanggalPembayaran(pembayaran.getTanggalPembayaran())
                .totalPembayaran(pembayaran.getTotalPembayaran())
                .status(pembayaran.getStatus())
                .keterangan(pembayaran.getKeterangan())
                .jurnalPenjualanLikeBy(pembayaran.getLike_jurnal_penjualan().stream().map(this::toJurnalPenjualanResponse).collect(Collectors.toList()))
                .createAt(ConvertDate.convertToLocalDateTime(pembayaran.getCreateAt()))
                .updateModifiedAt(ConvertDate.convertToLocalDateTime(pembayaran.getUpdateModifiedAt()))
                .build();
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
                .supplier(toSupplierResponse(jp.getSupplier()))
                .products(jp.getLike_product0().stream().map(this::toProductResponse).collect(Collectors.toList()))
                .createAt(ConvertDate.convertToLocalDateTime(jp.getCreateAt()))
                .updateModifiedAt(ConvertDate.convertToLocalDateTime(jp.getUpdateModifiedAt()))
                .build();
    }

    private SupplierResponse toSupplierResponse(Supplier supplier) {
        return SupplierResponse.builder()
                .supplierId(supplier.getSupplierId())
                .name(supplier.getName())
                .company(supplier.getCompany())
                .saldo(supplier.getSaldo())
                .company(supplier.getCompany())
                .email(supplier.getEmail())
                .noHPhone(supplier.getNoHPhone())
                .address(supplier.getAddress())
                .createAt(ConvertDate.convertToLocalDateTime(supplier.getCreateAt()))
                .updateModifiedAt(ConvertDate.convertToLocalDateTime(supplier.getUpdateModifiedAt()))
                .build();
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
                .customer(toCustomerResponse(jp.getCustomer()))
                .products(jp.getLike_product1().stream().map(this::toProductResponse).collect(Collectors.toList()))
                .createAt(ConvertDate.convertToLocalDateTime(jp.getCreateAt()))
                .updateModifiedAt(ConvertDate.convertToLocalDateTime(jp.getUpdateModifiedAt()))
                .build();

    }

    private CustomerResponse toCustomerResponse(Customer customer) {
        return CustomerResponse.builder()
                .customerId(customer.getCustomerId())
                .name(customer.getName())
                .company(customer.getCompany())
                .saldo(customer.getSaldo())
                .noHPhone(customer.getNoHPhone())
                .email(customer.getEmail())
                .address(customer.getAddress())
                .createAt(ConvertDate.convertToLocalDateTime(customer.getCreateAt()))
                .updateModifiedAt(ConvertDate.convertToLocalDateTime(customer.getUpdateModifiedAt()))
                .build(); // response
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
