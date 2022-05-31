package com.example.agrisupportandtorism.repository.address;

import com.example.agrisupportandtorism.entity.address.Address;
import com.example.agrisupportandtorism.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepo extends JpaRepository<Address, Integer> {
    List<Address> findAllByUser(User user);
}
