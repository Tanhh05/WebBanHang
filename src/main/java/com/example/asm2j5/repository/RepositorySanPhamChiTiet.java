package com.example.asm2j5.repository;

import com.example.asm2j5.model.SpChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RepositorySanPhamChiTiet extends JpaRepository<SpChiTiet, Integer> {
    @Query("SELECT s.soLuong FROM SpChiTiet s WHERE s.idSanPham.id = :idSanPham AND s.idMauSac.ten = :mauSac AND s.idKichThuoc.ten = :kichThuoc")
    Integer getSoLuongTonKho(@Param("idSanPham") Long idSanPham,
                             @Param("mauSac") String mauSac,
                             @Param("kichThuoc") String kichThuoc);
}
