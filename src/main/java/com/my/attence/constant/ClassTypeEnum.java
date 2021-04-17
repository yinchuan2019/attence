package com.my.attence.constant;

import com.my.attence.common.R;

/**
 * Created by abel on 2021/1/24
 * TODO
 */
public enum ClassTypeEnum {

    CLASS_MAKE(1,"stu2.projectProduction"),
    //CLASS_ORDER(2,"一对多课程预约"),

    CLASS_VIP(3,"stu_course2"),

    CLASS_WORK(4,"tea2.someThing"),
    CLASS_OTHER(5,"tea2.other"),
    CLASS_COURSE0(7,"stu_course0"),
    CLASS_COURSE1(8,"tu_course1");




    private final int id;
    private final String name;

    private ClassTypeEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return R.fail(name).getMsg();
    }
}
