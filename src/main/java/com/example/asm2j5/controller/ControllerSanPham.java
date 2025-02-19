package com.example.asm2j5.controller;

import com.example.asm2j5.model.SanPham;
import com.example.asm2j5.repository.RepositorySanPham;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/san-pham")
public class ControllerSanPham {
    @Autowired
    RepositorySanPham repositorySanPham;

    @GetMapping("/hien-thi")
    public String hienThi(Model model){
        model.addAttribute("listSanPham", repositorySanPham.findAll());
        return "sanpham/hien-thi";
    }

    @GetMapping("/detail/{id}")
    public String showDetail(Model model, @PathVariable("id") Integer id){
        model.addAttribute("sp", repositorySanPham.findById(id).get());
        model.addAttribute("listSanPham", repositorySanPham.findAll());
        return "sanpham/hien-thi";
    }

    @PostMapping("/add")
    public String addSanPham(SanPham sanPham){
        repositorySanPham.save(sanPham);
        return "redirect:/san-pham/hien-thi";
    }

    @GetMapping("/update/{id}")
    public String viewUpdate(Model model, @PathVariable("id") Integer id){
        model.addAttribute("sp", repositorySanPham.findById(id).get());
        return "sanpham/view-update";
    }

    @PostMapping("/update")
    public String updateSanPham(SanPham sanPham){
        repositorySanPham.save(sanPham);
        return "redirect:/san-pham/hien-thi";
    }

    @GetMapping("/delete")
    public String deleteSanPham(Integer id){
        repositorySanPham.deleteById(id);
        return "redirect:/san-pham/hien-thi";
    }
}
