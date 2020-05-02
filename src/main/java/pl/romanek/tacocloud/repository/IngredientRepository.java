
package pl.romanek.tacocloud.repository;

import pl.romanek.tacocloud.domain.Ingredient;


public interface IngredientRepository {
    
    Iterable<Ingredient> findAll();
    Ingredient findById(String id);
    Ingredient save(Ingredient ingredient);
    
}
