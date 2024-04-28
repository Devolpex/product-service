package com.eshop.product.DTO;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CategoryCreateDto {
    private int id;
    private String name;
    private String description;
    private Date createDate;

}
