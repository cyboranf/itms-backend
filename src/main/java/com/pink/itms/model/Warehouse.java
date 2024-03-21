package com.pink.itms.model;

import javax.persistence.*;

import lombok.Data;

import java.util.Set;

@Entity
@Table(name = "warehouse")
@Data
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String building;
    private String zone;
    @Column(name = "space_id")
    private Long spaceId;
    @Column(name = "space_height")
    private String spaceHeight;
    @Column(name = "space_width")
    private String spaceWidth;
    @Column(name = "space_length")
    private String spaceLength;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "product_id")
    private Product product;
}
