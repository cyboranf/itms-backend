package com.pink.itms.model;

import jakarta.persistence.*;
import lombok.Data;

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
}
