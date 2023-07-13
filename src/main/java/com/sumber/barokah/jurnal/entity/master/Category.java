package com.sumber.barokah.jurnal.entity.master;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "categories")
@EntityListeners({AuditingEntityListener.class}) // supaya bisa aktif create_data dan last_modified_data saat query
public class Category {

    @Id
    @Column(name = "category_id")
    private String categoryId;

    private String name;

    @CreatedDate
    @Column(name = "create_at")
    private Instant createAt;

    @LastModifiedDate
    @Column(name = "update_modified_at")
    private Instant updateModifiedAt;

    // @JsonIgnore // digunakan untuk menyelesaikan rekursi Tak Terbatas (StackOverflowError)
    @OneToMany(mappedBy = "category")
    private List<Product> products;

}
