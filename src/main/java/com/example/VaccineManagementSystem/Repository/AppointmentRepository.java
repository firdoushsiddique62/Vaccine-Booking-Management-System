package com.example.VaccineManagementSystem.Repository;

import com.example.VaccineManagementSystem.Models.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
}
