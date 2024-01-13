package chapter1.item1;

public class Person {

    private String name;
    private Integer age;
    private String sex;

    private static Person createByName(String name) {
        Person person = new Person();
        person.name = name;
        person.age = 20;
        person.sex = "남자";

        return person;
    }
}
