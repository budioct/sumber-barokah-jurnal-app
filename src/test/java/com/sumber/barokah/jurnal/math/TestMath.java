package com.sumber.barokah.jurnal.math;

import com.sumber.barokah.jurnal.dto.transaksi.CreateJurnalPembelianRequest;
import com.sumber.barokah.jurnal.dto.transaksi.jurnalpembelian.CreateProductJurnalPembelianRequest;
import com.sumber.barokah.jurnal.entity.master.Product;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;

@Slf4j
@SpringBootTest
public class TestMath {

    @Test
    void testBro() {

        Long jumlah_total;
        List<Long> jumlahTotalList = new LinkedList<>();
        List<CreateProductJurnalPembelianRequest> create = new LinkedList<>();

        CreateProductJurnalPembelianRequest product1 = new CreateProductJurnalPembelianRequest();
        product1.setQuantity(10);
        product1.setSellingPrice(1000L);

        CreateProductJurnalPembelianRequest product2 = new CreateProductJurnalPembelianRequest();
        product2.setQuantity(10);
        product2.setSellingPrice(2000L);

        create.add(product1);
        create.add(product2);

        CreateJurnalPembelianRequest request = new CreateJurnalPembelianRequest();
        request.setCreateProducts(create);

        if (Objects.nonNull(request.getCreateProducts())) {
            for (CreateProductJurnalPembelianRequest products : request.getCreateProducts()) {

                products.getQuantity();
                products.getSellingPrice();
                log.info("products.getQuantity=  {}", products.getQuantity());
                log.info("products.getSellingPrice()=  {}", products.getSellingPrice());

                jumlah_total = products.getQuantity() * products.getSellingPrice();
                jumlahTotalList.add(jumlah_total);

                log.info("jumlah_total= {}", jumlah_total);
            }

        }

        log.info("jumlahTotalList= {}", jumlahTotalList);

        Long reduce = jumlahTotalList.stream().reduce(0L, Long::sum);
        log.info("reduce= {}", reduce);


    }

}
