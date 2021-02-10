package com.my.attence.constant;

/**
 * Created by abel on 2021/1/24
 * TODO
 */
public enum ClassTypeEnum {
    CLASS_MAKE(1,"课题制作"),
    CLASS_ORDER(2,"一对多课程预约"),

    CLASS_VIP(3,"VIP作品集研究计划"),

    CLASS_WORK(4,"事务工作"),
    CLASS_OTHER(5,"其他");




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
        return name;
    }
}
