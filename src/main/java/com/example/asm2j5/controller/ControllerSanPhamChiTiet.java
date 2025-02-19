package com.example.asm2j5.controller;

import com.example.asm2j5.model.KichThuoc;
import com.example.asm2j5.model.MauSac;
import com.example.asm2j5.model.SanPham;
import com.example.asm2j5.model.SpChiTiet;
import com.example.asm2j5.repository.RepositoryKichThuoc;
import com.example.asm2j5.repository.RepositoryMauSac;
import com.example.asm2j5.repository.RepositorySanPham;
import com.example.asm2j5.repository.RepositorySanPhamChiTiet;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/san-pham-chi-tiet")
public class ControllerSanPhamChiTiet {
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
        return "spchitiet/hien-thi";
    }

    @PostMapping("/add")
    @Transactional
    public String addSpChiTiet(
            @RequestParam("idSanPham") Integer idSanPham,
            @RequestParam("idMauSac") Integer idMauSac,
            @RequestParam("idKichThuoc") Integer idKichThuoc,
            @RequestParam("soLuong") Integer soLuong,
            @RequestParam("donGia") Long donGia,
            @RequestParam(value = "maSpct", required = false) String maSpct,
            @RequestParam(value = "trangThai", defaultValue = "false") Boolean trangThai,
            RedirectAttributes redirectAttributes) {

        SpChiTiet spChiTiet = new SpChiTiet();
        spChiTiet.setMaSpct(maSpct != null && !maSpct.trim().isEmpty() ? maSpct : "SPCT-" + System.currentTimeMillis());
        spChiTiet.setIdSanPham(repositorySanPham.findById(idSanPham).orElse(null));
        spChiTiet.setIdMauSac(repositoryMauSac.findById(idMauSac).orElse(null));
        spChiTiet.setIdKichThuoc(repositoryKichThuoc.findById(idKichThuoc).orElse(null));
        spChiTiet.setSoLuong(soLuong);
        spChiTiet.setDonGia(donGia);
        spChiTiet.setTrangThai(trangThai);

        repositorySanPhamChiTiet.save(spChiTiet);
        redirectAttributes.addFlashAttribute("success", "Thêm sản phẩm chi tiết thành công!");

        return "redirect:/san-pham-chi-tiet/hien-thi";
    }

    @GetMapping("/detail/{id}")
    public String detailSpChiTiet(@PathVariable("id") Integer id, Model model) {
        SpChiTiet spChiTiet = repositorySanPhamChiTiet.findById(id).orElse(null);
        model.addAttribute("spChiTiet", spChiTiet);
        model.addAttribute("listSanPham", repositorySanPham.findAll());
        model.addAttribute("listMauSac", repositoryMauSac.findAll());
        model.addAttribute("listKichThuoc", repositoryKichThuoc.findAll());
        return hienThi(model);
    }

    @GetMapping("/delete/{id}")
    public String deleteSpChiTiet(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        if (repositorySanPhamChiTiet.existsById(id)) {
            repositorySanPhamChiTiet.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Xóa sản phẩm chi tiết thành công!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Sản phẩm chi tiết không tồn tại!");
        }
        return "redirect:/san-pham-chi-tiet/hien-thi";
    }

    @GetMapping("/update/{id}")
    public String viewUpdate(@PathVariable("id") Integer id, Model model) {
        SpChiTiet spChiTiet = repositorySanPhamChiTiet.findById(id).orElse(null);
        model.addAttribute("spChiTiet", spChiTiet);
        model.addAttribute("listSanPham", repositorySanPham.findAll());
        model.addAttribute("listMauSac", repositoryMauSac.findAll());
        model.addAttribute("listKichThuoc", repositoryKichThuoc.findAll());
        return "/spchitiet/view-update";
    }

    @PostMapping("/update")
    @Transactional
    public String updateSpChiTiet(
            @RequestParam("id") Integer id, // Nhận ID từ form
            @RequestParam("idSanPham") Integer idSanPham,
            @RequestParam("idMauSac") Integer idMauSac,
            @RequestParam("idKichThuoc") Integer idKichThuoc,
            @RequestParam("soLuong") Integer soLuong,
            @RequestParam("donGia") Long donGia,
            @RequestParam("maSpct") String maSpct,
            @RequestParam(value = "trangThai", defaultValue = "false") Boolean trangThai,
            RedirectAttributes redirectAttributes) {

        // Kiểm tra ID có tồn tại trong database không
        SpChiTiet spChiTiet = repositorySanPhamChiTiet.findById(id).orElse(null);
        if (spChiTiet == null) {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy sản phẩm chi tiết để cập nhật!");
            return "redirect:/san-pham-chi-tiet/hien-thi";
        }

        // Cập nhật dữ liệu
        spChiTiet.setMaSpct(maSpct);
        spChiTiet.setIdSanPham(repositorySanPham.findById(idSanPham).orElse(null));
        spChiTiet.setIdMauSac(repositoryMauSac.findById(idMauSac).orElse(null));
        spChiTiet.setIdKichThuoc(repositoryKichThuoc.findById(idKichThuoc).orElse(null));
        spChiTiet.setSoLuong(soLuong);
        spChiTiet.setDonGia(donGia);
        spChiTiet.setTrangThai(trangThai);

        // Lưu vào database
        repositorySanPhamChiTiet.save(spChiTiet);

        redirectAttributes.addFlashAttribute("success", "Cập nhật sản phẩm chi tiết thành công!");
        return "redirect:/san-pham-chi-tiet/hien-thi";
    }

}
