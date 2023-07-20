package com.example.VaccineManagementSystem.Exceptions;

public class DoctorNotFound extends Exception {
    public DoctorNotFound() {
        super("Doctor id is wrong");
    }
}
