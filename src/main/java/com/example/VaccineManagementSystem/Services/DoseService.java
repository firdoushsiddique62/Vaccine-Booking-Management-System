package com.example.VaccineManagementSystem.Services;

import com.example.VaccineManagementSystem.Models.Dose;
import com.example.VaccineManagementSystem.Models.User;
import com.example.VaccineManagementSystem.Repository.DoseRepository;
import com.example.VaccineManagementSystem.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoseService {

    @Autowired
    DoseRepository doseRepository;

    @Autowired
    UserRepository userRepository;

    public String giveDose(String doseId, Integer userId) {
        User user = userRepository.findById(userId).get();

        // An entity of that model has been created
        // This entity will be saved in the database
        Dose dose = new Dose();

        // setting its attributes
        dose.setDoseId(doseId);
        dose.setUser(user);

        //Setting the child object in that corresponding
        user.setDose(dose);

        userRepository.save(user);

        // Child will automatically get saved because of cascading effect.

        return "Dose given to user " + user.getName() + " is successful";
    }
}
