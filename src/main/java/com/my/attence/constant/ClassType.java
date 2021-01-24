package com.my.attence.constant;

/**
 * Created by abel on 2021/1/24
 * TODO
 */
public enum ClassType {
    CLASS_MAKE(1,"课题制作"),
    CLASS_ORDER(2,"一对多课程预约"),
    CLASS_VIP(2,"VIP作品集研究计划");


    private final int id;
    private final String name;

    private ClassType(int id, String name) {
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
