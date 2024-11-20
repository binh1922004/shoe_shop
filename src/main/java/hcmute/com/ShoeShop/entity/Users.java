package hcmute.com.ShoeShop.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Users {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

        String fullname;
        String email;
        String pass;
        String address;
        int roleid;
}
