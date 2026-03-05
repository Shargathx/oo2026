package ee.msaareva.proovikontrolltoo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Price {
    PREMIUM_PRICE(4.0),
    BASIC_PRICE(3.0);
    private final double price;
}
