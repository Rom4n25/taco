package pl.romanek.tacocloud.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.romanek.tacocloud.domain.Ingredient;
import pl.romanek.tacocloud.domain.Ingredient.Type;
import pl.romanek.tacocloud.domain.Taco;

@Slf4j
@Controller
@RequestMapping("/design")
public class DesignTacoController {

    @GetMapping
    public String showDesignForm(Model model) {
        List<Ingredient> ingredients = Arrays.asList(
                new Ingredient("GRBF", "mielona wołowina ", Type.PROTEIN),
                new Ingredient("CARN", "kawałki mięsa", Type.PROTEIN),
                new Ingredient("TMTO", "pomidory pokrojone w kostkę", Type.VEGGIES),
                new Ingredient("LETC", "sałata", Type.VEGGIES),
                new Ingredient("CHED", "cheddar", Type.CHEESE),
                new Ingredient("JACK", "Monterey Jack", Type.CHEESE),
                new Ingredient("SLSA", "pikantny sos pomidorowy", Type.SAUCE),
                new Ingredient("SRCR", "śmietana", Type.SAUCE),
                new Ingredient("FLTO", "pszenna", Type.WRAP),
                new Ingredient("COTO", "kukurydziana", Type.WRAP)
        );

        Type[] types = Ingredient.Type.values();                                                //tworzę szyk (array) z typami składkiników (PROTEIN, VEGGIES itp..)
        for (Type type : types) {

            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type)); //przekazuję do modelu składniki pofiltrowane po typie
        }

        model.addAttribute("taco", new Taco());                                                  //dodatkowo przekazuję do modelu obiekt Taco
        return "design";
    }

    private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {              //metoda wykorzystująca Stream
        return ingredients.stream()                                                               //metoda stream() - moge jej użyć wyłącznie na jakiejś kolekcji 
                .filter(x -> x.getType().equals(type))                                            //metoda filter -> wykorzystuję Lambda Expressions (biorę wszystkie te elementy, które mają taki sam typ jaki podałem w parametrzee)
                .collect(Collectors.toList());                                                    //potem wszystkie zbieram i zaposuje jako listę

    }

    @PostMapping
    public String processDesign(@Valid Taco design, Errors errors, Model model) { //po wysłaniu formularza jego pola zostają dołączone do właściwości obiektu Taco
                                                                                  //do modelu dodałem obiekt newTaco() pod kluczem taco, jeżeli chce żeby to się wszystko bindowało
                                                                                  //to nazwa klucza musi być taka sama jak nazwa klasy z małej litery, ponieważ domyślna wartość @ModelAttribute to nazwa klasy z małej litery
                                                                                  //jeżeli nadaje inną nazwę klucza np "myTaco" to musze wtedy przy Taco dodać adnotacje @ModelAttribute("myTaco")
        if (errors.hasErrors()) {                                                 //The default model attribute name is inferred from the declared attribute type (i.e. the method parameter type or method return type), based on the non-qualified class name

            List<Ingredient> ingredients = Arrays.asList(
                    new Ingredient("GRBF", "mielona wołowina ", Type.PROTEIN),
                    new Ingredient("CARN", "kawałki mięsa", Type.PROTEIN),
                    new Ingredient("TMTO", "pomidory pokrojone w kostkę", Type.VEGGIES),
                    new Ingredient("LETC", "sałata", Type.VEGGIES),
                    new Ingredient("CHED", "cheddar", Type.CHEESE),
                    new Ingredient("JACK", "Monterey Jack", Type.CHEESE),
                    new Ingredient("SLSA", "pikantny sos pomidorowy", Type.SAUCE),
                    new Ingredient("SRCR", "śmietana", Type.SAUCE),
                    new Ingredient("FLTO", "pszenna", Type.WRAP),
                    new Ingredient("COTO", "kukurydziana", Type.WRAP)
            );
            Type[] types = Ingredient.Type.values(); //tworzę szyk (array) z typami składkiników (PROTEIN, VEGGIES itp..)
            for (Type type : types) {

                model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type)); //przekazuję do modelu składniki pofiltrowane po typie
            }

            return "design";
        }
        log.info("Przetwarzanie projektu taco: " + design);//wyświetlam komunikat w aplikacji o nazwie i składnikach Taco (w konsoli)

        return "redirect:/orders/current";
    }

}
