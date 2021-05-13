package com.vaccine.tracker.VaccineSlotBooker.resource;

import com.vaccine.tracker.VaccineSlotBooker.service.EmailService;
import com.vaccine.tracker.VaccineSlotBooker.service.VaccineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/vaccine")
public class VaccineController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    private VaccineService vaccineService = new VaccineService();
    @Autowired
    private EmailService emailService;

    @GetMapping("/{districtId}")
    public String getVaccineSlotDetails(@PathVariable String districtId) throws InterruptedException {
        {

            int count = 0;
            int count_time = 0;
            int count18_d = 0;
            int count18_l = 0;
            String res_d = "";
            String res_l = "";
            String district = "";
            while (true) {
                Thread.sleep(10000);
                res_d = vaccineService.getSlotAvailability("143",count_time);
                //emailService.sendPreConfiguredMail("Slot Available for 45+");

                res_l = vaccineService.getSlotAvailability("670",count_time);
                //emailService.sendPreConfiguredMail("Slot Available for 45+");

                if(res_l.split("~")[0].equals("1")) {
                    emailService.sendPreConfiguredMail("Slot Available for 18+ in "+ "Lucknow");
                    count18_l++;

                }


                if(res_d.split("~")[0].equals("1")) {
                    emailService.sendPreConfiguredMail("Slot Available for 18+ in "+ "North West Delhi");
                    count18_d++;

                }
                if(count18_d > 1 && count18_l>1)
                    break;



            }

            return "success";
        }
    }
}



