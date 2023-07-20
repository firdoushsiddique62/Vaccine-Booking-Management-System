package com.example.VaccineManagementSystem.Controllers;

import com.example.VaccineManagementSystem.Services.DoseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/dose")
public class DoseController {

    @Autowired
    DoseService doseService;

    @PostMapping("/giveDose")
    public String giveDose(@RequestParam String doseId, @RequestParam Integer userId) {
        return doseService.giveDose(doseId, userId);
    }

}
