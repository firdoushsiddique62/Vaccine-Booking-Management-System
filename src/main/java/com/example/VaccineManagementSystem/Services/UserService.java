package com.example.VaccineManagementSystem.Services;

import com.example.VaccineManagementSystem.Dtos.RequestDtos.UpdateEmailDto;
import com.example.VaccineManagementSystem.Models.User;
import com.example.VaccineManagementSystem.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User addUser(User user) {
        user = userRepository.save(user);
        return user;
    }

    public Date getVaccinationDate(Integer id) {
        return userRepository.findById(id).get().getDose().getVaccinationDate();
    }

    public String updateEmail(UpdateEmailDto updateEmailDto) {
        int userId = updateEmailDto.getUserId();

        User user = userRepository.findById(userId).get();
        user.setEmailId(updateEmailDto.getEmailId());
        userRepository.save(user);

        return "Old Email has been modified with new one " + updateEmailDto.getEmailId();
    }

    public User getUserByEmail(String emailId) {
        return userRepository.findByEmailId(emailId);
    }
}
