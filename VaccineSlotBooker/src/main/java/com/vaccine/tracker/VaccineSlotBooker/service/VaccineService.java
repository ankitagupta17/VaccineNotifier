package com.vaccine.tracker.VaccineSlotBooker.service;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class VaccineService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public String getSlotAvailability(String districtId, int count_time) {


        EmailService emailService = new EmailService();
            int count = 0;
            int count2 = 0;

            try {
                RestTemplate restTemplate = new RestTemplate();
                HttpHeaders headers = new HttpHeaders();
                headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
                headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
                HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

                Date date = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                String strDate= formatter.format(date);

                logger.info("URL-"+"https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/calendarByDistrict?district_id="+districtId +"&date="+strDate);
                ResponseEntity<String> response = restTemplate.exchange("https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/calendarByDistrict?district_id="+districtId +"&date="+strDate, HttpMethod.GET, entity, String.class);
                logger.info("----->" + response.getBody());

                String data = response.getBody();
                if (data.indexOf("min_age_limit") > 0) {
                    String arr[] = data.split("min_age_limit");
                    String arr2[] = data.split("available_capacity");
                    for (int i = 0; i < arr.length; i++) {

                        if (arr[i].substring(2, 4).equals("45") && !(arr2[i].substring(2, 3).equals("0"))) {
                            //System.out.println("Slot Available for 45+---------->");

                            count2++;
                            count_time = -1;
                            //emailService.sendPreConfiguredMail("Slot Available for 45+");
                            break;

                        }
                    }
                    for (int i = 0; i < arr.length; i++) {
                        if(arr[i].substring(2, 4).equals("18"))
                        {
                            System.out.println("Data-- "+"districtId-->"+districtId+ "---Age>"+arr[i].substring(2, 4) + "------Availability->" + (arr2[i].substring(2, 3)));
                        }

                        if (arr[i].substring(2, 4).equals("18")&& !(arr2[i].substring(2, 3).equals("0"))) {
                            //System.out.println("Slot Available for 18+---------->");
                            //emailService.sendPreConfiguredMail("Slot Available for 18+");
                            count++;
                            count_time =-1;
                            break;
                        }
                    }
                }


                //System.out.println(response);
            } catch (Exception ex) {
                ex.printStackTrace();

            }


    String res = count + "~" + count2;
    return res;
    }
}
