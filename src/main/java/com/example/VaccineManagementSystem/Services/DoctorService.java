package com.example.VaccineManagementSystem.Services;

import com.example.VaccineManagementSystem.Dtos.RequestDtos.AssociateDocDto;
import com.example.VaccineManagementSystem.Exceptions.CenterNotFoundException;
import com.example.VaccineManagementSystem.Exceptions.DoctorNotFound;
import com.example.VaccineManagementSystem.Exceptions.EmailIdEmptyException;
import com.example.VaccineManagementSystem.Exceptions.DoctorAlreadyExistException;
import com.example.VaccineManagementSystem.Models.Doctor;
import com.example.VaccineManagementSystem.Models.VaccinationCenter;
import com.example.VaccineManagementSystem.Repository.DoctorRepository;
import com.example.VaccineManagementSystem.Repository.VaccinationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private VaccinationRepository vaccinationRepository;

    public String addDoctor(Doctor doctor) throws EmailIdEmptyException, DoctorAlreadyExistException {
        if (doctor.getEmailId() == null)
            throw new EmailIdEmptyException("Email Id is mandatory!");

        if (doctorRepository.findByEmailId(doctor.getEmailId()) != null)
            throw new DoctorAlreadyExistException("Doctor with this emailId already exists.");


        doctorRepository.save(doctor);

        return "A new doctor " + doctor.getName() + " has been added to the database.";
    }

    public String associateDoctor(AssociateDocDto associateDocDto) throws DoctorNotFound, CenterNotFoundException {
        Integer docId = associateDocDto.getDocId();

        Optional<Doctor> optionalDoctor = doctorRepository.findById(docId);

        if (!optionalDoctor.isPresent())
            throw new DoctorNotFound();

        Integer centerId = associateDocDto.getCenterId();

        Optional<VaccinationCenter> optionalCenter = vaccinationRepository.findById(centerId);

        if (!optionalCenter.isPresent())
            throw new CenterNotFoundException();

        Doctor doctor = optionalDoctor.get();
        VaccinationCenter vaccinationCenter = optionalCenter.get();

        doctor.setVaccinationCenter(vaccinationCenter); //Setting the foreign key

        // Setting the bidirectional map
        // Adding the doctor to the list of doctors of that vaccination center
        vaccinationCenter.getDoctorList().add(doctor);
        vaccinationRepository.save(vaccinationCenter);

        return "Doctor has been associated to the center.";
    }
}
