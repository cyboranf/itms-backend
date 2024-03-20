package com.pink.itms.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Table(name = "product")
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String code;
    private double width;
    private double height;
    private double length;
    private double weight;
    @ManyToMany(mappedBy = "products")
    private Set<Task> tasks;
}
