package com.sumber.barokah.jurnal.dto.master;

import com.sumber.barokah.jurnal.entity.master.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryResponse {

    private String categoryId;

    private String name;

    private List<Product> products;

    private Instant createAt;

    private Instant updateModifiedAt;

}