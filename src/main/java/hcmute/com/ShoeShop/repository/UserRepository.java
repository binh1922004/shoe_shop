package hcmute.com.ShoeShop.repository;

import hcmute.com.ShoeShop.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;


@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {

    @Query("SELECT u FROM Users u WHERE u.email = :email")
    Users findByEmail(@Param("email") String email);

}
