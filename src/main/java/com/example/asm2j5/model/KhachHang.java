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
@Table(name = "khach_hang")
public class KhachHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Nationalized
    @Column(name = "ten", nullable = false)
    private String ten;

    @Nationalized
    @Column(name = "sdt", nullable = false, length = 15)
    private String sdt;

    @Nationalized
    @Column(name = "ma_kh", nullable = false, length = 50)
    private String maKh;

    @Column(name = "trang_thai", nullable = false)
    private Boolean trangThai = false;

}