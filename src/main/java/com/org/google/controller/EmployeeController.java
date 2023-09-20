package com.org.google.controller;

import com.org.google.controller.dto.EmployeeDTO;
import com.org.google.entities.EmployeeEntity;
import com.org.google.service.EmployeeService;
import com.org.google.service.RecaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private RecaptchaService recaptchaService;

    @GetMapping(path = {"/", "/all"})
    public String showAll(Model model){

        List<EmployeeEntity> employeeEntityList = employeeService.findAll();

        model.addAttribute("employees", employeeEntityList);

        return "index";
    }

    @GetMapping("/create/form")
    public String createForm(Model model){
        model.addAttribute("employee", new EmployeeEntity());
        return "form";
    }

    @PostMapping("/create/process")
    public String createProcess(@ModelAttribute(name = "employee") EmployeeDTO employeeDTO, @RequestParam(name = "g-recaptcha-response") String captcha, Model model){

        boolean captchaValid = recaptchaService.validateRecaptcha(captcha);

        if(captchaValid){
            EmployeeEntity employeeEntity = EmployeeEntity.builder()
                    .name(employeeDTO.getName())
                    .lastName(employeeDTO.getLastName())
                    .dateOfBirth(employeeDTO.getDateOfBirth())
                    .build();

            employeeService.createEmployee(employeeEntity);
            return "redirect:/all";
        } else {

            model.addAttribute("message","captcha invalido");
            return "error";
        }
    }

}
