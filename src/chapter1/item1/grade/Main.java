package chapter1.item1.grade;

public class Main {
    public static void main(String[] args) {
        Grade grade = Grade.of(80);
        System.out.println(grade.toText()); // B
    }
}
