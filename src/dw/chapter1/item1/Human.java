package dw.chapter1.item1;

public class Human {

    private static final Human DEFAUT_HUMAN = new Human();

    private Human(){}

    public static Human getInstance() {
        return DEFAUT_HUMAN;
    }
}
