package dw.chapter1.item1.grade;

public interface Grade
{
    String toText();

    public static Grade of(int score)
    {
        if (score >= 90) {
            return new A();
        }
        if (score >= 80) {
            return new B();
        }
        if (score >= 70) {
            return new C();
        }
        if (score >= 60) {
            return new D();
        }

        return new F();
    }
}
