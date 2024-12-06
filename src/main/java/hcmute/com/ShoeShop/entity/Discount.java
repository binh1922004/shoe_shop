package hcmute.com.ShoeShop.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private double price;

    private double percent;

    @Column(name = "discount_date")
    private Date discountDate;
}
