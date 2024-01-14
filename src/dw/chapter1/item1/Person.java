package dw.chapter1.item1;

public class Person {

    private String name;

    private Person(){}

    public static Person fromName(String name) {
        Person person = new Person();
        person.name = name;
        return person;
    }
}
