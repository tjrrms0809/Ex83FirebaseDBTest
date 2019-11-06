package com.ahnsafety.ex83firebasedbtest;

public class PersonVO {

    String name;
    int age;
    String address;

    //주의!!!!!!
    //firebaseDB는 객체단위로 값을 넣을
    //반드시 매개변수가 비어있는 생성자를 요구함
    public PersonVO() {
    }

    public PersonVO(String name, int age, String address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
