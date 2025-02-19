package com.example.asm2j5.controller;

import com.example.asm2j5.model.MauSac;
import com.example.asm2j5.repository.RepositoryMauSac;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.Optional;

@Controller
@RequestMapping("/mau-sac")
public class ControllerMauSac {
    @Autowired
    RepositoryMauSac repositoryMauSac;

    @GetMapping("/hien-thi")
    public String hienThi(Model model){
        model.addAttribute("listMauSac", repositoryMauSac.findAll());
        return "mausac/hien-thi";
    }

    @PostMapping("/add")
    public String addMauSac(MauSac mauSac){
        repositoryMauSac.save(mauSac);
        return "redirect:/mau-sac/hien-thi";
    }

    @GetMapping("/detail/{id}")
    public String showDetail(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("listMauSac", repositoryMauSac.findAll());
        model.addAttribute("ms", repositoryMauSac.findById(id).get());
        return "mausac/hien-thi";
    }

    @GetMapping("/update/{id}")
    public String viewUpdate(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("ms", repositoryMauSac.findById(id).get());
        return "mausac/view-update";
    }

    @PostMapping("/update")
    public String updateMauSac(MauSac mauSac){
        repositoryMauSac.save(mauSac);
        return "redirect:/mau-sac/hien-thi";
    }

    @GetMapping("/delete")
    public String updateMauSac(@RequestParam("id") Integer id){
        repositoryMauSac.deleteById(id);
        return "redirect:/mau-sac/hien-thi";
    }

}
