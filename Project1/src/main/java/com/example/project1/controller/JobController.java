package com.example.project1.controller;

import com.example.project1.enitty.RegisterRequest;
import com.example.project1.enitty.RegisterRespone;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


@Controller
@CrossOrigin
@RestController
@RequestMapping("/job")

public class JobController {
    RestTemplate restTemplate = new RestTemplate();

    String registerURL = "http://localhost:8800/api/v1/auth/register";
    String getURL = "http://localhost:8800/api/v1/auth/check";

    @GetMapping("/get")
    public String getTest(){
        ResponseEntity<String> response = restTemplate.getForEntity(getURL, String.class);
        String responseBody = response.getBody();
            return responseBody;
        }

    @PostMapping ("/post")
    public RegisterRespone postTest(@RequestBody RegisterRequest registerRequest){
        ResponseEntity<RegisterRespone> response = restTemplate.postForEntity(registerURL,registerRequest, RegisterRespone.class);
        RegisterRespone responseBody = response.getBody();
        return responseBody;
    }

}
