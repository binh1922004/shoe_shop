package hcmute.com.ShoeShop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class Users {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

        @Column(name = "fullname", nullable = false, length = 100)
        private String fullname;

        @Column(name = "email", nullable = false, unique = true, length = 255)
        private String email;

        @Column(name = "pass", nullable = false)
        private String pass;

        @Column(name = "address", length = 255)
        private String address;

        @ManyToOne  // Một người dùng chỉ có một vai trò
        @JoinColumn(name = "role_id", nullable = false)  // Khóa ngoại đến bảng 'roles'
        private Role role;  // Vai trò của người dùng
}
