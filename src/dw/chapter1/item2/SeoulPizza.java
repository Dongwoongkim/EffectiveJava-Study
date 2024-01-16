package dw.chapter1.item2;

public class SeoulPizza extends Pizza {

    private final boolean sauceInside;

    public static class Builder extends Pizza.Builder<Builder> {

        private boolean sauceInside = false; // 기본값

        public Builder sauceInside() {
            sauceInside = true;
            return this;
        }

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public SeoulPizza build() {
            return new SeoulPizza(this);
        }
    }

    private SeoulPizza(Builder builder) {
        super(builder);
        sauceInside = builder.sauceInside;
    }
}
