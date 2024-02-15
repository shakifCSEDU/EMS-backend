package com.example.todo.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderImpl {
    public static void main(String[] args) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        System.out.println(passwordEncoder.encode("shakif"));
        System.out.println(passwordEncoder.encode("admin"));
    }
}
//$2a$10$LVNhwQ2CJYd.8fW4Zif6yOX2qqgyET/WLPC8qo/jBlpb.xopcf9qO
//$2a$10$k/tqWUc1jYPJCnByZaVbTukSK.0RswVRL6yc.MZ2t96IjfenwKyKS