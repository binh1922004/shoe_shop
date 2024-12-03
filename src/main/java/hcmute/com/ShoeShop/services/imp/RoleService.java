package hcmute.com.ShoeShop.services.imp;

import hcmute.com.ShoeShop.entity.Role;
import hcmute.com.ShoeShop.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    // Tìm Role theo ID
    public Role findRoleById(int id) {
        return roleRepository.findById(id).orElse(null);
    }
}
