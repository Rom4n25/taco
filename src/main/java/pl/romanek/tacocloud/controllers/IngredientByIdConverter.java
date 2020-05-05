package pl.romanek.tacocloud.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.romanek.tacocloud.domain.Ingredient;
import pl.romanek.tacocloud.repository.IngredientRepository;

@Component
public class IngredientByIdConverter implements Converter<String, Ingredient> { //implementuje Interfejs Conveter aby móc konwertować Stringa na Ingredient
                                                                                //przydatny do kowerowania stringów na obiekt (z widoku coś do modelu)
  private IngredientRepository ingredientRepo;

  @Autowired
  public IngredientByIdConverter(IngredientRepository ingredientRepo) {
    this.ingredientRepo = ingredientRepo;
  }
  
  @Override
  public Ingredient convert(String id) { //konwertuje Stringa ingredient id z widoku do obiektu Ingredient
    return ingredientRepo.findById(id);
  }

}