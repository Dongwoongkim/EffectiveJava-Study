package dw.chapter1.item3;

public class Greenday {

    private static final Greenday INSTANCE = new Greenday();

    private Greenday() {
    }

    public static Greenday getInstance() {
        return INSTANCE;
    }

    private Greenday readResolve() {
        // '진짜' Greenday를 리턴하고, 값을 복사한 가짜 Greenday는 가비지 컬렉터에 맡기게 됩니다.
        return INSTANCE;
    }
}
