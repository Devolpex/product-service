package com.eshop.productservice.dto.product;

import jakarta.validation.constraints.*;


    public class ProductCreateDto {

        // Name should contain characters and optionally numbers
        @NotBlank(message = "Product Name cannot be empty")
        @Pattern(regexp = "[a-zA-Z]+[0-9]*", message = "Product name must contain alphabetic characters and optionally numbers")
        private String name;

        // Description should not be empty
        @NotBlank(message = "Description cannot be empty")
        private String description;

        // Price should be a positive double
        @Positive(message = "Price must be positive")
        private double price;

        // Image should be a string but there's no need for specific validation unless you require something specific like format checking
        private String image;

        // Getters and setters for all fields are essential for data encapsulation and manipulation

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }


