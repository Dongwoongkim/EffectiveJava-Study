package dw.chapter1.item2;

import static dw.chapter1.item2.NewYorkPizza.Size.SMALL;
import static dw.chapter1.item2.Pizza.Topping.ONION;
import static dw.chapter1.item2.Pizza.Topping.SAUSAGE;

import dw.chapter1.item2.Pizza.Topping;

public class PizzaMain {

    public static void main(String[] args) {
        NewYorkPizza newYorkPizza = new NewYorkPizza
                .Builder(SMALL)
                .addTopping(SAUSAGE)
                .addTopping(ONION)
                .build();
        SeoulPizza seoulPizza = new SeoulPizza
                .Builder()
                .addTopping(ONION)
                .sauceInside()
                .build();
    }
}
