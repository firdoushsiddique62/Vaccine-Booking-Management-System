package com.example.VaccineManagementSystem.Repository;

import com.example.VaccineManagementSystem.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    // just define the functions
    User findByEmailId(String emailId);
    //prebuilt functions : and you can use it directly...
}
