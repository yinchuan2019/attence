package com.my.attence.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author abel
 * @since 2020-11-24
 */
public class Student extends Model<Student> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String studentName;

    private String email;

    private String gender;

    private Integer age;


    public Integer getId() {
        return id;
    }

    public Student setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getStudentName() {
        return studentName;
    }

    public Student setStudentName(String studentName) {
        this.studentName = studentName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Student setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getGender() {
        return gender;
    }

    public Student setGender(String gender) {
        this.gender = gender;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public Student setAge(Integer age) {
        this.age = age;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Student{" +
        "id=" + id +
        ", studentName=" + studentName +
        ", email=" + email +
        ", gender=" + gender +
        ", age=" + age +
        "}";
    }
}
