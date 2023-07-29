package com.sumber.barokah.jurnal.service.impl;

import com.sumber.barokah.jurnal.dto.master.PageableRequest;
import com.sumber.barokah.jurnal.dto.master.ProductResponse;
import com.sumber.barokah.jurnal.dto.transaksi.CreatePembayaranRequest;
import com.sumber.barokah.jurnal.dto.transaksi.JurnalPembelianResponse;
import com.sumber.barokah.jurnal.dto.transaksi.PembayaranResponse;
import com.sumber.barokah.jurnal.entity.master.Product;
import com.sumber.barokah.jurnal.entity.transaksi.JurnalPembelian;
import com.sumber.barokah.jurnal.entity.transaksi.Pembayaran;
import com.sumber.barokah.jurnal.repository.master.ProductRepository;
import com.sumber.barokah.jurnal.repository.master.SupplierRepository;
import com.sumber.barokah.jurnal.repository.transaksi.JurnalPembelianRepository;
import com.sumber.barokah.jurnal.repository.transaksi.PembayaranRepository;
import com.sumber.barokah.jurnal.service.PembayaranService;
import com.sumber.barokah.jurnal.service.ValidationService;
import jakarta.persistence.criteria.Predicate;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
public class PembayaranServiceImpl implements PembayaranService {

    @Autowired
    ValidationService validationService;

    @Autowired
    SupplierRepository supplierRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    JurnalPembelianRepository jurnalPembelianRepository;

    @Autowired
    PembayaranRepository pembayaranRepository;

    @Transactional
    public PembayaranResponse create(CreatePembayaranRequest request) {

        validationService.validate(request);

        JurnalPembelian jurnalPembelian = jurnalPembelianRepository.findFirstByJurnalPembelianId(request.getJurnalPembelianId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Jurnal Pembelian Not found"));

        Pembayaran byr = new Pembayaran();
        byr.setPembayaranId(UUID.randomUUID().toString());
        byr.setTanggalPembayaran(request.getTanggalPembayaran());
        byr.setNominalPembayaran(request.getNominalPembayaran());
        byr.setStatus(request.getStatus());
        byr.setKeterangan(request.getKeterangan());
        byr.setJurnalPembeliansLikeBy(jurnalPembelian);

        pembayaranRepository.save(byr); // save DB

        return toPembayaranResponse(byr);
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

    private PembayaranResponse toPembayaranResponse(Pembayaran pembayaran) {

        return PembayaranResponse.builder()
                .pembayaranId(pembayaran.getPembayaranId())
                .tanggalPembayaran(pembayaran.getTanggalPembayaran())
                .nominalPembayaran(pembayaran.getNominalPembayaran())
                .status(pembayaran.getStatus())
                .keterangan(pembayaran.getKeterangan())
                .jurnalPembeliansLikeBy(toJurnalPembelianResponse(pembayaran.getJurnalPembeliansLikeBy()))
                .createAt(pembayaran.getCreateAt())
                .updateModifiedAt(pembayaran.getUpdateModifiedAt())
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
                .supplier(jp.getSupplier())
                .products(jp.getLike_product().stream().map(this::toProductResponse).collect(Collectors.toList()))
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
