package com.medicalcare.medicalcareconsumer.controller;

import com.medicalcare.entity.Register;
import com.medicalcare.medicalcareconsumer.service.RegisterVisiService;
import com.medicalcare.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cashier")
@CrossOrigin
public class RegisterVisiController {
    @Autowired
    private RegisterVisiService registerVisiService;
    @RequestMapping(value = "addRegister",method = RequestMethod.POST)
    public boolean addRegister(@RequestBody Register register){
        return registerVisiService.addRegister(register);
    }
}
