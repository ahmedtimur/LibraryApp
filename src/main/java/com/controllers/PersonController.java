package com.controllers;

import com.models.Person;
import com.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/library")
public class PersonController {

    private PersonRepository personRepository;

    @Autowired
    public PersonController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @GetMapping("/save")
    public String savePerson(@ModelAttribute("person")Person person) {
        return "/library/savePerson";
    }

    @PostMapping("/save")
    public String savePersonPost(@ModelAttribute("person")@Valid Person person, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "/library/savePerson";

        personRepository.savePerson(person);
        return "redirect:/library";
    }

    @GetMapping
    public String getAllPeople(Model model) {
        model.addAttribute("people", personRepository.getAllPeople());
        return "/library/getAllPeople";
    }

    @GetMapping("/{id}/update")
    public String updatePerson(@PathVariable("id") Long id, Model model) {
        model.addAttribute("person", personRepository.findById(id));
        return "/library/updatePerson";
    }

    @PatchMapping("/{id}/update")
    public String updatePersonPost(@ModelAttribute("person") @Valid Person person, @PathVariable("id") Long id, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return "/library/updatePerson";
        personRepository.updatePerson(id, person);
        return "redirect:/library";
    }

    @GetMapping("/{id}")
    public String showById(Model model, @PathVariable("id") Long id) {
        model.addAttribute("person", personRepository.findById(id));
        model.addAttribute("books", personRepository.findBookByPersonId(id));
        return "/library/showByID";
    }

    @DeleteMapping("/{id}/delete")
    public String deletePerson(@PathVariable("id") Long id) {
        personRepository.deletePerson(id);
        return "redirect:/library";
    }


}
