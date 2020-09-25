package com.baidu.demo.entity;

/**
 * @ClassName TestEntity
 * @Description: TODO
 * @Author liuchongyang
 * @Date 2020/9/15
 * @Version V1.0
 **/
public class TestEntity {

    private String code;

    private String pass;

    private int age;

    private String likeColor;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getLikeColor() {
        return likeColor;
    }

    public void setLikeColor(String likeColor) {
        this.likeColor = likeColor;
    }

    @Override
    public String toString() {
        return "TestEntity{" +
                "code='" + code + '\'' +
                ", pass='" + pass + '\'' +
                ", age=" + age +
                ", likeColor='" + likeColor + '\'' +
                '}';
    }

    public TestEntity(String code, String pass, int age, String likeColor) {
        this.code = code;
        this.pass = pass;
        this.age = age;
        this.likeColor = likeColor;
    }
}
