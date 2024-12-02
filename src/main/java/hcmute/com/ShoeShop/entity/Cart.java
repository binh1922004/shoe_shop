package hcmute.com.ShoeShop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;
import java.util.Set;

@Entity
@Data
public class Cart {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

        @ManyToOne
        @NotNull(message = "User cannot be null")
        @JoinColumn(name = "user_id", nullable = false) // Khóa ngoại đến User
        private Users userId;

        @Column(name = "total_price", nullable = false)
        @NotNull(message = "Total price cannot be null")
        @PositiveOrZero(message = "Total price must be greater than or equal to 0")
        private Double totalPrice;

        @Column(name = "created_date", nullable = false)
        @Temporal(TemporalType.TIMESTAMP) // Định dạng DateTime
        @NotNull(message = "Created date cannot be null")
        private Date createdDate;

        @OneToMany(mappedBy = "cart",cascade = CascadeType.ALL)
        @EqualsAndHashCode.Exclude
        @ToString.Exclude
        private Set<CartDetail> orderDetailSet;
}
