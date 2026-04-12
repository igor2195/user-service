package ru.test_app.user_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.test_app.user_service.model.Address;
import ru.test_app.user_service.repository.AddressRepository;
import ru.test_app.user_service.repository.UserRepository;
import ru.test_app.user_service.service.AddressService;

@Controller
@RequestMapping("/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    @GetMapping
    public String list(@RequestParam(required = false) String search, Model model) {
        model.addAttribute("addresses", addressService.findAll(search));
        return "addresses/list";
    }

    @GetMapping("/form")
    public String form(Model model) {
        model.addAttribute("address", new Address());
        model.addAttribute("users", userRepository.findAll());
        return "addresses/form";
    }

    @GetMapping("/form/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("address", addressService.findById(id));
        model.addAttribute("users", userRepository.findAll());
        return "addresses/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Address address) {
        addressService.save(address);
        return "redirect:/addresses";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        addressService.delete(id);
        return "redirect:/addresses";
    }
}
