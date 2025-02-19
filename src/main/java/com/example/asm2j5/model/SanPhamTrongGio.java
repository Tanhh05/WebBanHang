package com.example.asm2j5.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SanPhamTrongGio {  // Không cần @Entity
    private Long idSanPham;
    private String tenSanPham;
    private int soLuong;
    private long donGia;
    private String mauSac;
    private String kichThuoc;

    public SanPhamTrongGio(String tenSanPham, int soLuong, long donGia) {
        this.tenSanPham = tenSanPham;
        this.soLuong = soLuong;
        this.donGia = donGia;
    }
}
