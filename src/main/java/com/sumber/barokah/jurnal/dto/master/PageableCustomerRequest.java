package com.sumber.barokah.jurnal.dto.master;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageableCustomerRequest {

    private Integer page;

    private Integer size;

    private String sortField;

    private String order;

}
