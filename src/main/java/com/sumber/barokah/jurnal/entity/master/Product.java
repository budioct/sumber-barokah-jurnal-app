package com.sumber.barokah.jurnal.entity.master;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
@EntityListeners({AuditingEntityListener.class}) // supaya bisa aktif create_data dan last_modified_data saat query
public class Product {

    @Id
    @Column(name = "product_id")
    private String productId;

    @Column(name = "product_code")
    private String productCode;

    private String name;

    private Integer quantity;

    @Column(name = "minimum_limit")
    private Integer minimumLimit;

    private Integer unit;

    @Column(name = "average_price")
    private Long averagePrice;

    @Column(name = "last_purchase_price")
    private Long lastPurchasePrice;

    @Column(name = "purchase_price")
    private Long purchasePrice;

    @Column(name = "selling_price")
    private Long sellingPrice;

    @Column(name = "item_type")
    private String itemType;

    private String description;

    @CreatedDate
    @Column(name = "create_at")
    private Instant createAt;

    @LastModifiedDate
    @Column(name = "update_modified_at")
    private Instant updateModifiedAt;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    private Category category;


}
