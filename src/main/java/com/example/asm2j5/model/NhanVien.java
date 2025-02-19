package com.example.asm2j5.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

@Getter
@Setter
@Entity
@Table(name = "nhan_vien")
public class NhanVien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Nationalized
    @Column(name = "ten", nullable = false)
    private String ten;

    @Nationalized
    @Column(name = "ma_nv", nullable = false, length = 50)
    private String maNv;

    @Nationalized
    @Column(name = "ten_dang_nhap", nullable = false)
    private String tenDangNhap;

    @Nationalized
    @Column(name = "mat_khau", nullable = false)
    private String matKhau;

    @Column(name = "trang_thai", nullable = false)
    private Boolean trangThai = false;

}