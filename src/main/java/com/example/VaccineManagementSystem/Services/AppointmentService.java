package com.example.VaccineManagementSystem.Services;

import com.example.VaccineManagementSystem.Dtos.RequestDtos.AppointmentReqDto;
import com.example.VaccineManagementSystem.Exceptions.DoctorNotFound;
import com.example.VaccineManagementSystem.Exceptions.UserNotFound;
import com.example.VaccineManagementSystem.Models.Appointment;
import com.example.VaccineManagementSystem.Models.Doctor;
import com.example.VaccineManagementSystem.Models.User;
import com.example.VaccineManagementSystem.Repository.AppointmentRepository;
import com.example.VaccineManagementSystem.Repository.DoctorRepository;
import com.example.VaccineManagementSystem.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender emailSender;

    public String bookAppointment(AppointmentReqDto appointmentReqDto) throws DoctorNotFound, UserNotFound {
        Optional<Doctor> doctorOptional = doctorRepository.findById(appointmentReqDto.getDocId());

        if (!doctorOptional.isPresent())
            throw new DoctorNotFound();

        Optional<User> userOptional = userRepository.findById(appointmentReqDto.getUserId());

        if (!userOptional.isPresent())
            throw new UserNotFound("User id not found");

        Doctor doctor = doctorOptional.get();
        User user = userOptional.get();

        Appointment appointment = new Appointment();

        // Creating the object and setting its attribute
        appointment.setAppointmentDate(appointmentReqDto.getAppintmentDate());
        appointment.setAppointmentTime(appointmentReqDto.getAppointmentTime());

        // Setting the foreign key attribute
        appointment.setDoctor(doctor);
        appointment.setUser(user);

        // Meth 1 and It's used to get appointment id in first space
        appointment = appointmentRepository.save(appointment);


        doctor.getAppointmentList().add(appointment);

//        // Method 2
//        // Get the last Appointment from list of appointments
//        List<Appointment> appointmentList = doctor.getAppointmentList();
//        Appointment latestAppointment = appointmentList.get(appointmentList.size() - 1);
//        int id = latestAppointment.getId();
//
//        appointment.setId(id);

        user.getAppointmentList().add(appointment);

        doctorRepository.save(doctor);
        userRepository.save(user);

        // Send an Email to the sender
        String body = "Hi " + user.getName()  + "! \n" +
                "You Have Successfully booked an appointment on " + appointment.getAppointmentDate() + " at " + appointment.getAppointmentTime() + "\n" +
                "Your doctor is " + doctor.getName() + "\n" +
                "Please reach at " + doctor.getVaccinationCenter().getAddress() + "\n"
                + "Mask is mandatory";

        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom("TestingEmailAmresh@gmail.com");
        mailMessage.setTo(user.getEmailId());
        mailMessage.setSubject("Appointment Confirmed");
        mailMessage.setText(body);

        emailSender.send(mailMessage);

        return "Appointment booked successfully";
    }
}
