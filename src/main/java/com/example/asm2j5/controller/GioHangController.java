package com.example.asm2j5.controller;

import com.example.asm2j5.model.HoaDon;
import com.example.asm2j5.model.HoaDonChiTiet;
import com.example.asm2j5.model.KhachHang;
import com.example.asm2j5.model.NhanVien;
import com.example.asm2j5.model.SanPhamTrongGio;
import com.example.asm2j5.model.SpChiTiet;
import com.example.asm2j5.repository.GioHangService;
import com.example.asm2j5.repository.HoaDonChiTietRepository;
import com.example.asm2j5.repository.HoaDonRepository;
import com.example.asm2j5.repository.KhachHangRepository;
import com.example.asm2j5.repository.RepositoryNhanVien;
import com.example.asm2j5.repository.RepositorySanPhamChiTiet;
import com.example.asm2j5.repository.SpChiTietService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/gio-hang")
public class GioHangController {

    private final GioHangService gioHangService;
    private final KhachHangRepository khachHangRepository;
    private final HoaDonRepository hoaDonRepository;
    private final HoaDonChiTietRepository hoaDonChiTietRepository;
    private final RepositorySanPhamChiTiet repositorySanPhamChiTiet;
    private final RepositoryNhanVien repositoryNhanVien;

    private final SpChiTietService spChiTietService;


    public GioHangController(GioHangService gioHangService,
                             KhachHangRepository khachHangRepository,
                             HoaDonRepository hoaDonRepository,
                             HoaDonChiTietRepository hoaDonChiTietRepository,
                             RepositorySanPhamChiTiet repositorySanPhamChiTiet,
                             RepositoryNhanVien repositoryNhanVien, SpChiTietService spChiTietService) {
        this.gioHangService = gioHangService;
        this.khachHangRepository = khachHangRepository;
        this.hoaDonRepository = hoaDonRepository;
        this.hoaDonChiTietRepository = hoaDonChiTietRepository;
        this.repositorySanPhamChiTiet = repositorySanPhamChiTiet;
        this.repositoryNhanVien = repositoryNhanVien;
        this.spChiTietService = spChiTietService;
    }

    // Tạo giỏ hàng nếu chưa có
    private List<SanPhamTrongGio> layGioHang(HttpSession session) {
        List<SanPhamTrongGio> gioHang = (List<SanPhamTrongGio>) session.getAttribute("gioHang");
        if (gioHang == null) {
            gioHang = new ArrayList<>();
            session.setAttribute("gioHang", gioHang);
        }
        return gioHang;
    }

    @PostMapping("/them")
    public ResponseEntity<?> themVaoGio(@RequestParam Long idSanPham,
                                        @RequestParam String tenSanPham,
                                        @RequestParam long donGia,
                                        @RequestParam String mauSac,
                                        @RequestParam String kichThuoc,
                                        @RequestParam int soLuong,
                                        HttpSession session) {

        // Kiểm tra giá trị null hoặc trống
        if (idSanPham == null || tenSanPham.isEmpty() || mauSac.isEmpty() || kichThuoc.isEmpty() || soLuong <= 0) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Dữ liệu không hợp lệ!"));
        }

        List<SanPhamTrongGio> gioHang = layGioHang(session);

        // Đảm bảo dữ liệu đúng kiểu
        int soLuongTonKho = spChiTietService.laySoLuongTonKho(idSanPham, mauSac, kichThuoc);

        Optional<SanPhamTrongGio> sanPhamTrongGio = gioHang.stream()
                .filter(sp -> sp.getIdSanPham().equals(idSanPham)
                        && sp.getMauSac().equals(mauSac)
                        && sp.getKichThuoc().equals(kichThuoc))
                .findFirst();

        int soLuongHienTai = sanPhamTrongGio.map(SanPhamTrongGio::getSoLuong).orElse(0);
        int tongSoLuong = soLuongHienTai + soLuong;

        if (tongSoLuong > soLuongTonKho) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Số lượng sản phẩm vượt quá tồn kho! Chỉ còn " + soLuongTonKho + " sản phẩm."));
        }

        if (sanPhamTrongGio.isPresent()) {
            sanPhamTrongGio.get().setSoLuong(tongSoLuong);
        } else {
            gioHang.add(new SanPhamTrongGio(idSanPham, tenSanPham, soLuong, donGia, mauSac, kichThuoc));
        }

        return ResponseEntity.ok(gioHang);
    }

    @GetMapping("/xem")
    public ResponseEntity<List<SanPhamTrongGio>> xemGioHang(HttpSession session) {
        return ResponseEntity.ok(layGioHang(session));
    }

    @GetMapping("/hien-thi")
    public String hienThiGioHang(Model model, HttpSession session) {
        model.addAttribute("gioHang", layGioHang(session));
        return "banhang/gio-hang"; // Trả về file Thymeleaf
    }

    @DeleteMapping("/xoa-tat-ca")
    public ResponseEntity<Void> xoaTatCa(HttpSession session) {
        session.removeAttribute("gioHang"); // Xóa giỏ hàng khỏi session
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/xoa")
    public ResponseEntity<List<SanPhamTrongGio>> xoaSanPham(@RequestParam String tenSanPham, HttpSession session) {
        List<SanPhamTrongGio> gioHang = layGioHang(session);
        gioHang.removeIf(sp -> sp.getTenSanPham().equals(tenSanPham));
        return ResponseEntity.ok(gioHang);
    }

    @PostMapping("/thanh-toan")
    public ResponseEntity<String> thanhToan(@RequestBody KhachHang khachHang, HttpSession session) {
        List<SanPhamTrongGio> gioHang = layGioHang(session);

        if (gioHang.isEmpty()) {
            return ResponseEntity.badRequest().body("Giỏ hàng trống!");
        }

        // Tạo khách hàng
        String maKhachHang = "KH" + System.currentTimeMillis();
        khachHang.setMaKh(maKhachHang);
        khachHang.setTrangThai(true);
        KhachHang kh = khachHangRepository.save(khachHang);

        // Lấy nhân viên có ID = 1
        NhanVien nhanVien = repositoryNhanVien.findById(1)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên với ID 1"));

        // Tạo hóa đơn
        HoaDon hoaDon = new HoaDon();
        hoaDon.setIdKhachHang(kh);
        hoaDon.setIdNhanVien(nhanVien);
        hoaDon.setNgayMuaHang(Instant.now());
        hoaDon.setTrangThai(true);
        HoaDon hoaDonMoi = hoaDonRepository.save(hoaDon);

        // Lưu hóa đơn chi tiết
        for (SanPhamTrongGio sp : gioHang) {
            HoaDonChiTiet chiTiet = new HoaDonChiTiet();
            chiTiet.setIdHoaDon(hoaDonMoi);

            SpChiTiet sanPham = repositorySanPhamChiTiet.findById(Math.toIntExact(sp.getIdSanPham()))
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));

            chiTiet.setIdSpct(sanPham);
            chiTiet.setSoLuong(sp.getSoLuong());
            chiTiet.setDonGia(sp.getDonGia());
            chiTiet.setTrangThai(true);
            hoaDonChiTietRepository.save(chiTiet);

            // Cập nhật số lượng sản phẩm
            sanPham.setSoLuong(sanPham.getSoLuong() - sp.getSoLuong());
            repositorySanPhamChiTiet.save(sanPham);
        }

        // Xóa giỏ hàng sau khi thanh toán
        session.removeAttribute("gioHang");

        return ResponseEntity.ok("Thanh toán thành công!");
    }
}


