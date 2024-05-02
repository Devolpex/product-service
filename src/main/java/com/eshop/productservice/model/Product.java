package com.eshop.productservice.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "products")
@Builder
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank(message = "Name is mandatory")
    private String name;
    private String description;

    @Transient
    public String getShortDescription() {
        if (description.length() > 100) {
            return description.substring(0, 100);
        }
        return description;
    }
    private double price;
    private int quantity;
    private String image;

    private Date created_at;
    @Nullable
    private Date update_at;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;
}
