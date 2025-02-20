package com.example.asm2j5.repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpChiTietService {

    @Autowired
    private RepositorySanPhamChiTiet repositorySanPhamChiTiet;

    public int laySoLuongTonKho(Long idSanPham, String mauSac, String kichThuoc) {
        Integer soLuong = repositorySanPhamChiTiet.getSoLuongTonKho(idSanPham, mauSac, kichThuoc);
        return soLuong != null ? soLuong : 0;
    }

}

