package com.example.asm2j5.controller;

import com.example.asm2j5.model.HoaDonChiTiet;
import com.example.asm2j5.repository.HoaDonChiTietRepository;
import com.example.asm2j5.repository.HoaDonRepository;
import com.example.asm2j5.repository.RepositorySanPhamChiTiet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/hoa-don")
public class ControllerHoaDon {
    @Autowired
    RepositorySanPhamChiTiet repositorySanPhamChiTiet;

    @Autowired
    HoaDonRepository hoaDonRepository;

    @Autowired
    HoaDonChiTietRepository hoaDonChiTietRepository;

    @GetMapping("/hien-thi")
    public String hienThi(Model model) {
        model.addAttribute("listHoaDon", hoaDonRepository.findAll());
        return "hoadon/hien-thi";
    }

    @GetMapping("/detail/{id}")
    public String detailHdChiTiet(@PathVariable("id") Integer id, Model model) {
        List<HoaDonChiTiet> hoaDonChiTiets = hoaDonChiTietRepository.getByIDHoaDon(id);

        // Kiểm tra nếu hóa đơn chi tiết không tồn tại
        if (hoaDonChiTiets == null) {
            return "redirect:/hoa-don/hien-thi"; // Chuyển hướng nếu không tìm thấy hóa đơn
        }

        model.addAttribute("hdChiTiet", hoaDonChiTiets);
        model.addAttribute("listHdChiTiet", hoaDonChiTietRepository.findAll());

        return "hoadon/detail";
    }

}
