package com.example.VaccineManagementSystem.Dtos.RequestDtos;

import lombok.Data;

import java.time.LocalTime;
import java.util.Date;


@Data
public class AppointmentReqDto {

    private int docId;

    private int userId;

    private Date appintmentDate;

    private LocalTime appointmentTime;
}
