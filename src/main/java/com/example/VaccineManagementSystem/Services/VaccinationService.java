package com.example.VaccineManagementSystem.Services;

import com.example.VaccineManagementSystem.Exceptions.VaccinationAddressNotFound;
import com.example.VaccineManagementSystem.Models.VaccinationCenter;
import com.example.VaccineManagementSystem.Repository.VaccinationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class VaccinationService {

    @Autowired
    private VaccinationRepository vaccinationRepository;

    public String addVaccinationCenter(VaccinationCenter vaccinationCenter) throws VaccinationAddressNotFound {

        if (vaccinationCenter.getAddress() == null)
            throw new VaccinationAddressNotFound("Vaccination Address is Empty!");

        vaccinationRepository.save(vaccinationCenter);
        return "Vaccination center added at a location " + vaccinationCenter.getAddress()+ ".";
    }

}
