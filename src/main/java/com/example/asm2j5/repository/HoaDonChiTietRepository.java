package com.example.asm2j5.repository;

import com.example.asm2j5.model.HoaDonChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface HoaDonChiTietRepository extends JpaRepository<HoaDonChiTiet, Integer> {
    @Query("select hdct from HoaDonChiTiet  hdct where hdct.idHoaDon.id = :idhdct")
    List<HoaDonChiTiet> getByIDHoaDon(@RequestParam("idhdct") Integer idhdct);
}
