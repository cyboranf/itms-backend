package com.pink.itms.model;

import javax.persistence.*;
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
    @OneToMany(mappedBy = "product")
    private Set<Warehouse> warehouses; // Set of products in the warehouse

}
