package com.example.demo.Model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Promotion")
@NoArgsConstructor
@Getter @Setter
@AllArgsConstructor
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "promotion_id")
    private int promotionId;

    @Column(name = "promotion_name")
    private String promotionName;

    @Column(name = "description")
    private String description;

    @Column(name = "start_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;

    @Column(name = "end_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;

    @Column(name = "discount")
    private int discount;

    @Column(name = "code")
    private String code;

    @Column(name = "url_image")
    private String urlImage;
}


