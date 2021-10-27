package com.my.attence;

import com.my.attence.utils.PasswordUtils;

import java.util.UUID;

/**
 * Created by abel on 2020/11/25
 * TODO
 */
public class PasswordTest {
    public static void main(String[] args) {

        String salt = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 5);

        String password = PasswordUtils.encode("admin", salt);
        System.out.println("salt : " + salt + "   " + "password : " + password);
    }
}
