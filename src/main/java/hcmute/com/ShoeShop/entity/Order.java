package hcmute.com.ShoeShop.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.catalina.User;

import java.util.Date;
import java.util.Set;

@Entity
@Data
@Table(name = "Orders")
public class Order {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;

        @ManyToOne
        @JoinColumn(name = "user_id", nullable = false) // Khóa ngoại đến User
        private Users userId;


        @Column(name = "total_price", nullable = false)
        private Double totalPrice;

        @Column(name = "created_date", nullable = false)
        @Temporal(TemporalType.TIMESTAMP) // Định dạng DateTime
        private Date createdDate;

        @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
        @EqualsAndHashCode.Exclude
        @ToString.Exclude
        private Set<OrderDetail> orderDetailSet;
}
