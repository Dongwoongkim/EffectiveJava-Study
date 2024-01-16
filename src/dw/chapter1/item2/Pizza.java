package dw.chapter1.item2;

import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;

public abstract class Pizza {

    public enum Topping {HAM, MUSHROOM, ONION, PEPPER, SAUSAGE}

    final Set<Topping> toppings;

    abstract static class Builder<T extends Builder<T>> {
        EnumSet<Topping> toppings = EnumSet.noneOf(Topping.class);

        public T addTopping(Topping topping) {
            toppings.add(Objects.requireNonNull(topping));
            return self();
        }

        // 하위 클래스에서 이 메소드를 오버라이딩하여 this를 리턴하도록 해야함.
        protected abstract T self();

        abstract Pizza build();
    }

    Pizza(Builder<?> builder) {
        toppings = builder.toppings.clone();
    }
}
