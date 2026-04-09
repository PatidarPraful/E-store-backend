package com.prafull.ecom_project.model;

import java.math.BigDecimal;
import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Column(name = "description")
    private String description;
    private String brand;
    private BigDecimal price;
    private String category;

    
    @Column(name = "release_date")
    private Date releaseDate;
    private Boolean productAvailable;
    private Integer stockQuantity;

    private String imageName;
    private String imageType;
    @Lob
    private byte[] imageData;


}
