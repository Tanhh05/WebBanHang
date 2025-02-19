package com.example.asm2j5.repository;

import com.example.asm2j5.model.SanPhamTrongGio;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class GioHangService {
    private final List<SanPhamTrongGio> danhSachSanPham = new ArrayList<>();

    // 🛒 Thêm sản phẩm vào giỏ hàng
    public void themSanPham(String tenSanPham, int soLuong, long donGia) {
        for (SanPhamTrongGio sp : danhSachSanPham) {
            if (sp.getTenSanPham().equals(tenSanPham)) {
                sp.setSoLuong(sp.getSoLuong() + soLuong);
                return;
            }
        }
        danhSachSanPham.add(new SanPhamTrongGio(tenSanPham, soLuong, donGia));
    }

    // 📋 Lấy danh sách sản phẩm trong giỏ hàng
    public List<SanPhamTrongGio> layDanhSachGioHang() {
        return danhSachSanPham;
    }

    // 💰 Tính tổng tiền giỏ hàng
    public long tinhTongTien() {
        return danhSachSanPham.stream()
                .mapToLong(sp -> sp.getSoLuong() * sp.getDonGia())
                .sum();
    }

    // 🗑 Xóa giỏ hàng
    public void xoaGioHang() {
        danhSachSanPham.clear();
    }
}

