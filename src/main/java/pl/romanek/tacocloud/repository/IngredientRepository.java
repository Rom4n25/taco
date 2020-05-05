
package pl.romanek.tacocloud.repository;

import pl.romanek.tacocloud.domain.Ingredient;

public interface IngredientRepository {
    
    Iterable<Ingredient> findAll(); //jest to interfejs, którego implementują wszystkie kolekcje. Używam iterable bo potem mogę sobie zwrocony obiekt przerobić na co chcę np Listę albo Set itp...
    Ingredient findById(String id);
    Ingredient save(Ingredient ingredient);
    
}
