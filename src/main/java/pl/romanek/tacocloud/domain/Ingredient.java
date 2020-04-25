package pl.romanek.tacocloud.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data //Adnotacja z biblioteki Lombok - nakazuje klasie wygenerowanie wszystkich brakujących metod (getterów i setterów) w trakcie działania aplikacji
      //oraz konstruktora akceptującego argumenty w postaci właściwości zdefiniowanych jako final
@RequiredArgsConstructor
public class Ingredient {

    private final String id;
    private final String name;
    private final Type type;

    public static enum Type { //Typ wyliczeniowy - enum
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }
}
