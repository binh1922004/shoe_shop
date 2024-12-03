package hcmute.com.ShoeShop.services.imp;

import hcmute.com.ShoeShop.entity.Users;
import hcmute.com.ShoeShop.repository.UserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService{
    @Autowired
    private UserRepository userRepository;

    public long count() {
        return userRepository.count();
    }

    public void delete(Users user) {
        userRepository.delete(user);
    }

    public List<Users> findAll() {
        return userRepository.findAll();
    }

    public Page<Users> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public void saveUser(Users user) {
        userRepository.save(user);
    }

    public Users findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<Users> findByFullnameAndRole(String name, int roleid){
        return userRepository.findByRoleIdAndContainName(name, roleid);
    }

    public Users findUserByUserID(int userId){
        return userRepository.findUsersById(userId);
    }
}
