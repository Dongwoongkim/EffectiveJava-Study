# Item 1

## Item 1의 main theme

생성자 대신 정적 팩토리 메소드를 고려하라.

---

## 클래스의 인스턴스를 생성하는 방법

- 어떤 클래스의 인스턴스를 생성해야 하는 경우 일반적으로 아래 1번 방법과 같이 public 생성자를 사용해서 인스턴스를 사용하곤 합니다.

### 1. 생성자

```
public class Person {

    private String name;

    */**
     * 1. public 생성자
     */// name을 arugment로 주입받아 Person 인스턴스 생성*public Person(String name) {
        this.name = name;
    }

    *// arugment를 주입받지 않고 Person 인스턴스 생성*public Person() {
        this.name = "Anonymous";
    }
}
```

사실은 public 생성자를 사용하는 방법 말고도 또 다른 방법이 하나 더 있습니다. 바로 이 Item에서 추천하는 정적 팩토리 메소드를 사용하는 방법입니다. 아래 처럼요.

### 2. 정적 팩토리 메소드

```
public class Person {

    private static Person PERSON = new Person();
    
    */**
     * 2. 정적 팩토리 메소드 사용
     */*private static Person getInstance() {
        return PERSON;
    }
}
```

static 영역에 기본 생성자를 만들어 두고, 필요할 때 마다 메소드를 호출하여 인스턴스를 생성하는 방법입니다.

이 방식에는 장점과 단점 모두 존재하는데 먼저 장점부터 알아봅시다.

---

## 정적 팩토리 메소드 사용 시 장점

### 1. 생성자에 이름을 부여할 수 있다.

한 클래스에 대한 생성자가 여러 개 필요하다면, 생성자에 넘기는 매개변수와 생성자 자체만으로는 반환될 객체의 특성을 제대로 이해하기에는 어려움이 있습니다.

```
package chapter1.item1;

public class Person {

    private String name;
    private Integer age;
    private String sex;

    public Person(String name) {
        this.name = name;
        this.age = 20;
        this.sex = "남자";
    }

    public Person(Integer age) {
        this.name = "anonynmous";
        this.age = age;
        this.sex = "남자";
    }

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
        this.sex = "남자";
    }

    public Person(Integer age, String sex) {
        this.name = "anonynmous";
        this.age = age;
        this.sex = sex;
    }

    public Person(String name, Integer age, String sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
    }
}
```

생성자를 이렇게 무분별하게 만들어 놓으면 클라이언트 입장에서는 각 생성자가 인스턴스의 attribute에 어떤 값을 대입할 지 혼란을 일으킬 수 있습니다.

!https://blog.kakaocdn.net/dn/cAvQ0B/btsDs4QfnOR/2Nk9YZv2x3eKK7kwp9puN0/img.png

이렇게 생성자가 여러 개 존재하게 되면, 각 생성자가 만들어내는 인스턴스의 특성을 단번에 이해하기 어렵습니다.

반면 정적 팩토리 메소드는 메소드 자체에 이름을 부여할 수 있기 때문에, 메소드 작명만 잘 한다면 반환될 인스턴스의 특성을 쉽게 묘사할 수 있습니다.

```
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
```

즉, 하나의 createByName 이라는 시그니처로는 생성자를 하나만 만들도록 함으로써 개발자나 코드를 읽는 사람으로 하여금 혼란을 방지할 수 있습니다.

### 2. 호출할 때 마다 인스턴스를 새로 생성하지 않아도 된다.

```
public class Human {

    private static final Human DEFAUT_HUMAN = new Human();

    private static Human getInstance() {
        return DEFAUT_HUMAN;
    }
}
```

```
public class Main {

    public static void main(String[] args) {
        Human human = Human.getInstance();
    }
}
```

이 덕분에 불변 클래스는 인스턴스를 미리 만들어 놓거나 새로 생성한 인스턴스를 캐싱하여 재활용하는 방식으로 불필요한 객체 생성을 피할 수 있습니다.

```
public class Human {

    private static final Human DEFAUT_HUMAN = new Human();
    
    private Human(){}

    public static Human getInstance() {
        return DEFAUT_HUMAN;
    }
}
```

- 이렇게 정적 팩토리 메소드를 사용할 때는 기본 생성자의 제어자를 private으로 막아줌으로써 new 키워드를 통한 객체 생성을 막아줍니다.

```
@jdk.internal.ValueBased
public final class Boolean implements java.io.Serializable,
Comparable<Boolean>, Constable
{
*/**
* The {@code Boolean} object corresponding to the primitive
* value {@code true}.
*/*public static final Boolean TRUE = new Boolean(true);

    */**
     * The {@code Boolean} object corresponding to the primitive
     * value {@code false}.
     */*public static final Boolean FALSE = new Boolean(false);`

`@IntrinsicCandidate
public static Boolean valueOf(boolean b) {
return (b ? TRUE : FALSE);
}
```

대표적인 예시로, Boolean.valueOf(boolean) 메소드는 이렇게 객체를 아예 생성하지 않고, 내부에서 static 영역에 올려둔 객체를 꺼내다 써서 재활용하는 방식으로 사용되는 것을 볼 수 있습니다.

***객체가 자주 요청되는 상황에서 이 패턴을 적용한다면 성능을 끌어 올려줄 수 있겠습니다.***

여기서 말하는 불변 클래스란?
이와 비슷한 기법으로는 **플라이웨이트 패턴 (Flyweight pattern)**이 있습니다.

- 공유를 통해 다양한 객체들을 효과적으로 지원하는 방법으로,
    - 대표적으로는 String 타입의 객체가 String Pool을 사용해 이런 패턴이 적용되어 있는 것을 볼 수 있습니다.
    - 어차피 값 자체는 불변객체라 같은 참조를 사용해도 되기 때문에, 두 String 이 같은 값이라면 같은 참조를 사용하도록 하여 불필요한 동일 객체 생성을 막고 같은 참조를 공유하여 사용하는 것이죠.

### 3. 반환 타입의 하위 타입 객체를 반환할 수도 있다.

"반환할 객체의 클래스를 자유롭게 선택할 수 있게 하는 유연성을 줍니다."

정적 메소드로부터 인터페이스 자체를 반환하게 하여, 구현 클래스를 공개하지 않으면서 그 인스턴스를 반환할 수 있게 한다는 의미로 정리할 수 있는데, 사실 읽어도 의미파악하기가 너무 어려워서 코드예시로 정리하신 분이 있어서 참고해보았습니다.

```
public interface Grade {
	String toText();
}

public static Grade of(int score) {
    if(score >= 90) {
        return new A();
    }

    if(score >=80) {
        return new B();
    }

    return new F();
}

public class A implements Grade {

    @Override
    public String toText()
    {
        return "A";
    }
}

public class B implements Grade {

	@Override
    public String toText()
    {
        return "B";
    }
}

public class F implements Grade {
	@Override
	public String toText()
	{
    	return "F";
	}
}

public class Application {
	public static void main(String[] args) {
        Grade grade = Grade.of(95);
        System.out.println(grade);
    }
}
```

- Grade 인터페이스에 정적 팩토리 메소드 형태의 of()메소드로 조건에 따라 객체를 선택하여 반환합니다.

메인함수를 주목해보겠습니다.

구현체 생성 시점에 다형성을 활용하여 인터페이스로 구현체를 생성하는데, Grade 인터페이스는 실제 구현체를 연결해주는 역할로 구현체의 생성 구현 로직은 숨기면서 API는 매우 가벼워지는 것을 볼 수 있습니다.

- *자바 8 이전에는 인터페이스가 정적 메소드를 가질 수 없다는 제한이 있었지만 8 이후에는 제한이 풀렸음.*

### 4. 입력 Argument에 따라 매번 다른 클래스의 객체를 반환할 수 있다.

3번과 비슷한 맥락입니다. 장점 3번에 작성된 코드를 보면 score 값에 따라 객체를 선택적으로 반환하는 것을 볼 수 있습니다.

### 5. 정적 팩토리 메소드를 작성하는 시점에는 반환할 객체의 클래스가 존재하지 않아도 된다.

읽고 또 읽어도, 자료를 찾아봐도 이건 무슨 말인지 정말 모르겠네요. PASS 하겠습니다.

---

## 정적 팩토리 메소드 사용 시 단점

물론 장점만 있지는 않습니다. 단점을 알아봅시다.

### 1. 상속을 하려면 public이나 protected 생성자가 필요하므로 하위 클래스를 만들 수 없다.

```
public class Person {

    private String name;

    private Person(){}

    private static Person fromName(String name) {
        Person person = new Person();
        person.name = name;
        return person;
    }
}
```

```
public class Animal extends Person { *// X, Compile Error*
}
```

- 위와 같이 정적 팩토리 생성자 메소드를 사용했을 때는 private으로 기본 생성자를 막아두는 것이 일반적이기 때문에, 상속을 통한 확장이 어렵습니다.
- 하지만 어찌보면 이 제약은 상속보다 컴포지션을 사용하도록 유도함과, 동시에 불변타입으로 만들려면 이 제약을 지켜야 한다는 점에서 장점으로 볼 수도 있겠습니다.
- *추후 Item 17, Item 18에서 이 내용이 언급*

### 2. 프로그래머 입장에서는 정적 팩토리 메소드를 찾기가 어렵다.

정적 팩토리 메소드는 일반적인 생성자처럼 API 설명에 명확히 드러나지 않아, 사용자 입장에서는 정적 팩토리 메소드 방식 클래스를 인스턴스화할 방법을 직접 알아내야 한다는 어려움이 있다는 점을 말하고 있습니다.

---

## 정적 팩토리 메소드의 네이밍 컨벤션

| keyword | 설명 | 예시 |
| --- | --- | --- |
| from | 매개변수를 하나 받아서 해당 타입의 인스턴스를 반환하는 형변환 메소드 | People p = People.from(name); |
| of | 여러 매개변수를 받아 인스턴스를 반환 | People p = People.of(name, age, sex); |
| valueOf | from과 of의 더 자세한 버전 | BigInteger prime =BigInteger.valueOf(Integer.MAX_VALUE); |
| instance, getInstance | 매개변수로 명시한 인스턴스를 반환하지만, 같은  인스턴스임을 보장하지는 않음. | StackWalker luke = StackWalker.getInstance(options); |
| create, newInstance | instance, getInstance와 같지만 매번 새로운 인스턴스를 생성해 반환함을 보장. | Object newArray = Array.newInstance(classObject, arrayLen); |
| getType | getInstance와 같으나 생성할 클래스가 아닌 다른 클래스에 팩토리 메소드를 정의할 때 사용 | FileStore fs = Files.getFileStoere(path); |
| newType | newInstance와 같으나 생성할 클래스가 아닌 다른 클래스에 팩토리 메소드를 정의할 때 사용 | BufferedReader br = Files.newBufferedReader(path); |
| type | getType, newType의 간결한 버전 | List<Compaint> litany = Collections.list(legacyLitany); |

---

## 핵심 정리

> 정적 팩토리 메소드, public 생성자는 각자의 쓰임새가 있다. 상대적인 장단점을 잘 이해하고 사용하는 것이 좋다. 하지만 대체로 정적 팩토리 메소드를 사용하는 것이 유리한 점이 많기 때문에 무작정 public 생성자를 쓰는 습관이 있다면 고치자.
>

이렇게 Item1 을 한 번 정리해봤는데요.

내용이 생각보다 깊고 설명이 난해할 뿐만 아니라, 아무래도 번역본이다 보니 확 와닿게 이해하는데는 어려움이 있는 것 같습니다. 서술방식이 냅다 본론을 던져버리고, 설명하는 방식이라 책의 텍스트로만 온전히 이해함에도 어려움이 있어, 느리더라도 자료를 찾아가면서 꼼꼼하게 공부해야겠습니다.

---

## <참고자료>

[Java - 불변객체(Immutable Object)에 대해
좋은 코드를 작성하기 위한 공부를 하다보면 불변객체에 대해 듣게 되는데, 오늘은 이 불변객체란 무엇인지에 대해 알아보도록 하겠습니다.
min103ju.github.io](https://min103ju.github.io/effective%20java/immutable/)

[[Java] String 객체의 불변성(Immutablity)에 대해
Java에서 String은 불변 객체라고 불립니다. 불변 객체란, 객체가 생성된 이후 상태가 변하지 않고 계속 유지되는 객체를 말합니다. 즉 String 인스턴스는 한 번 생성되면 그 값을 읽기만 할 수 있고,
rlaehddnd0422.tistory.com](https://rlaehddnd0422.tistory.com/174)

[[JAVA] 메모리 누수(Memory Leak)와 GC 성능 튜닝
메모리 누수(memory leak)란? CS 의미로 살펴볼 때, 컴퓨터 프로그램이 필요하지 않은 메모리를 계속 점유하고 있는 현상이다. 할당된 메모리를 사용한 다음 반환하지 않는 것이 누적되면 메모리가
junghyungil.tistory.com](https://junghyungil.tistory.com/133)

[[이펙티브 자바] 객체의 생성과 파괴 Item1 - 생성자 대신 정적 팩터리 메서드를 고려하라
이펙티브 자바 2장. 객체의 생성과 파괴 Item1 - "생성자 대신 정적 팩터리 메서드를 고려하라"
velog.io](https://velog.io/@holidenty/%EC%9D%B4%ED%8E%99%ED%8B%B0%EB%B8%8C-%EC%9E%90%EB%B0%94-%EA%B0%9D%EC%B2%B4%EC%9D%98-%EC%83%9D%EC%84%B1%EA%B3%BC-%ED%8C%8C%EA%B4%B4-Item1-nul1xqx9#span-stylecolord683654-%EC%9E%85%EB%A0%A5-%EB%A7%A4%EA%B0%9C%EB%B3%80%EC%88%98%EC%97%90-%EB%94%B0%EB%9D%BC-%EB%A7%A4%EB%B2%88-%EB%8B%A4%EB%A5%B8-%ED%81%B4%EB%9E%99%EC%8A%A4%EC%9D%98-%EA%B0%9D%EC%B2%B4%EB%A5%BC-%EB%B0%98%ED%99%98%ED%95%A0-%EC%88%98-%EC%9E%88%EB%8B%A4span)
