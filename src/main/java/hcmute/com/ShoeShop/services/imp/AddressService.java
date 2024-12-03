package hcmute.com.ShoeShop.services.imp;

import hcmute.com.ShoeShop.entity.Address;
import hcmute.com.ShoeShop.entity.Users;
import hcmute.com.ShoeShop.repository.AddressRepository;
import hcmute.com.ShoeShop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private UserRepository userRepository;

    public void save(Address address) {
        addressRepository.save(address);
    }

    public List<Address> findAll() {
        return addressRepository.findAll();
    }

    public void delete(Address address) {
        addressRepository.delete(address);
    }

    public List<Address> getAddressesByID(int id) {
        return addressRepository.findByUserId(id);
    }

    //Mục đích là chỉ có 1 cái default
    public void setDefaultAddress(Address newDefaultAddress) {
        List<Address> addresses = addressRepository.findAll();

        // Duyệt qua các địa chỉ và đặt isDefault = false cho tất cả
        for (Address address : addresses) {
            address.setIsDefault(false);
            addressRepository.save(address);
        }

        newDefaultAddress.setIsDefault(true);
        addressRepository.save(newDefaultAddress);
    }

}
