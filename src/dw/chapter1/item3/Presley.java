package dw.chapter1.item3;

public class Presley {

    public static final Presley INSTANCE = new Presley();

    private Presley() {
        if (INSTANCE != null) {
            throw new RuntimeException("생성자를 호출할 수 없습니다.");
        }
    }
}
