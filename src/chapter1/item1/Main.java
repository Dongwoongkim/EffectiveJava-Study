package chapter1.item1;

public class Main {

    public static void main(String[] args) {
        Human human = Human.getInstance();
        Human human2 = Human.getInstance();

        System.out.println(human == human2); // true
    }
}
