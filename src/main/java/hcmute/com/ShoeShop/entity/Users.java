package hcmute.com.ShoeShop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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

        @NotNull(message = "Fullname cannot be null")
        @Size(min = 1, max = 100, message = "Fullname must be between 1 and 100 characters")
        @Column(name = "fullname", nullable = false, length = 100)
        private String fullname;

        @NotNull(message = "Email cannot be null")
        @Email(message = "Email should be valid")
        @Size(max = 100, message = "Email cannot exceed 100 characters")
        @Column(name = "email", nullable = false, unique = true, length = 100)
        private String email;

        @NotNull(message = "Password cannot be null")
        @Size(min = 6, message = "Password must have at least 6 characters")
        @Column(name = "pass", nullable = false)
        private String pass;

        @Size(max = 255, message = "Address cannot exceed 255 characters")
        @Column(name = "address", length = 255)
        private String address;

        @ManyToOne  // Một người dùng chỉ có một vai trò
        @JoinColumn(name = "role_id", nullable = false)
        private Role role;
}
