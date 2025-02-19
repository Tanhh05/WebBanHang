package com.example.asm2j5.controller;

import com.example.asm2j5.model.KichThuoc;
import com.example.asm2j5.model.MauSac;
import com.example.asm2j5.model.SanPham;
import com.example.asm2j5.model.SpChiTiet;
import com.example.asm2j5.repository.RepositoryKichThuoc;
import com.example.asm2j5.repository.RepositoryMauSac;
import com.example.asm2j5.repository.RepositorySanPham;
import com.example.asm2j5.repository.RepositorySanPhamChiTiet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/ban-hang")
public class ControllerBanHang {
    @Autowired
    RepositoryMauSac repositoryMauSac;

    @Autowired
    RepositoryKichThuoc repositoryKichThuoc;

    @Autowired
    RepositorySanPham repositorySanPham;

    @Autowired
    RepositorySanPhamChiTiet repositorySanPhamChiTiet;

    @ModelAttribute("listMauSac")
    List<MauSac> getListMauSac() {
        return repositoryMauSac.findAll();
    }

    @ModelAttribute("listKichThuoc")
    List<KichThuoc> getKichThuoc() {
        return repositoryKichThuoc.findAll();
    }

    @ModelAttribute("listSanPham")
    List<SanPham> getListSanPham() {
        return repositorySanPham.findAll();
    }

    @GetMapping("/hien-thi")
    public String hienThi(Model model) {
        model.addAttribute("ListSpChiTiet", repositorySanPhamChiTiet.findAll());
        return "banhang/hien-thi";
    }

    @GetMapping("/detail/{id}")
    public String detailSpChiTiet(@PathVariable("id") Integer id, Model model) {
        SpChiTiet spChiTiet = repositorySanPhamChiTiet.findById(id).orElse(null);
        model.addAttribute("spChiTiet", spChiTiet);
        model.addAttribute("listSanPham", repositorySanPham.findAll());
        model.addAttribute("listMauSac", repositoryMauSac.findAll());
        model.addAttribute("listKichThuoc", repositoryKichThuoc.findAll());
        return "banhang/detail";
    }

}
