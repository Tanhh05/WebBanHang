package com.example.asm2j5.controller;

import com.example.asm2j5.model.KichThuoc;
import com.example.asm2j5.repository.RepositoryKichThuoc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/kich-thuoc")
public class ControllerKichThuoc {
    @Autowired
    RepositoryKichThuoc repositoryKichThuoc;

    @GetMapping("/hien-thi")
    public String hienThi(Model model){
        model.addAttribute("listKichThuoc", repositoryKichThuoc.findAll());
        return "kichthuoc/hien-thi";
    }

    @PostMapping("/add")
    public String addKichThuoc(KichThuoc kichThuoc){
        repositoryKichThuoc.save(kichThuoc);
        return "redirect:/kich-thuoc/hien-thi";
    }

    @GetMapping("/detail/{id}")
    public String showDetail(Model model, @PathVariable("id") Integer id){
        model.addAttribute("kt", repositoryKichThuoc.findById(id).get());
        model.addAttribute("listKichThuoc", repositoryKichThuoc.findAll());
        return "kichthuoc/hien-thi";
    }

    @GetMapping("/update/{id}")
    public String viewUpdate(Model model, @PathVariable("id") Integer id){
        model.addAttribute("kt", repositoryKichThuoc.findById(id).get());
        return "kichthuoc/view-update";
    }

    @PostMapping("/update")
    public String update(KichThuoc kichThuoc){
        repositoryKichThuoc.save(kichThuoc);
        return "redirect:/kich-thuoc/hien-thi";
    }

    @GetMapping("/delete")
    public String deleteKichThuoc(Integer id){
        repositoryKichThuoc.deleteById(id);
        return "redirect:/kich-thuoc/hien-thi";
    }
}
