package hcmute.com.ShoeShop.entity;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.persistence.*;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Set;

@Data
@Entity
@Table(name = "Product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    protected Long id;

    @NotNull(message = "Title cannot be null")
    @Basic
    @Column(length = 255,name = "title", nullable = true,  columnDefinition = "nvarchar(255)")
    private String title;

    @Size(max = 255, message = "Description cannot exceed 255 characters")
    @Basic
    @Column(name = "description", nullable = true, length = 255, columnDefinition = "nvarchar(255)")
    private String description;

    private Long voucher;

    @Basic
    @Column(name = "price", nullable = false)
    private double price;

    @Basic
    @Column(nullable = true)
    private String image;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @EqualsAndHashCode.Exclude
    private Category category;

    @Column(name = "is_delete")
    private boolean isdelete;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<OrderDetail> orderDetailSet;

}
