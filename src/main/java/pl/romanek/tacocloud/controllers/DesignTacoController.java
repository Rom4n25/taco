package pl.romanek.tacocloud.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import pl.romanek.tacocloud.domain.Ingredient;
import pl.romanek.tacocloud.domain.Ingredient.Type;
import pl.romanek.tacocloud.domain.Order;
import pl.romanek.tacocloud.domain.Taco;
import pl.romanek.tacocloud.repository.IngredientRepository;
import pl.romanek.tacocloud.repository.TacoRepository;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")//order musi być dostępny w wielu żądaniach aby można było dodać wiele Taco do jednego zamówienia
public class DesignTacoController {

    private final IngredientRepository ingredientRepo;
    private final TacoRepository designRepo;

    @Autowired //wstrzykuję interfejs komunikujący się z bazą danych
    public DesignTacoController(IngredientRepository ingredientRepo, TacoRepository designRepo) {
        this.ingredientRepo = ingredientRepo;
        this.designRepo = designRepo;
    }

    @ModelAttribute(name="order")
    public Order order(){
        return new Order();
    }
    
    @ModelAttribute(name="taco")
    public Taco taco(){
        return new Taco();
    }
    
    
    @GetMapping
    public String showDesignForm(Model model) {
        
        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepo.findAll().forEach(i->ingredients.add(i));
        
        
        //Poniżej dodawanie skladników do modelu na sztywno a powyżej pobierane są z bazy danych
        //List<Ingredient> ingredients = Arrays.asList( 
        
                //                new Ingredient("GRBF", "mielona wołowina ", Type.PROTEIN),
                //                new Ingredient("CARN", "kawałki mięsa", Type.PROTEIN),
                //                new Ingredient("TMTO", "pomidory pokrojone w kostkę", Type.VEGGIES),
                //                new Ingredient("LETC", "sałata", Type.VEGGIES),
                //                new Ingredient("CHED", "cheddar", Type.CHEESE),
                //                new Ingredient("JACK", "Monterey Jack", Type.CHEESE),
                //                new Ingredient("SLSA", "pikantny sos pomidorowy", Type.SAUCE),
                //                new Ingredient("SRCR", "śmietana", Type.SAUCE),
                //                new Ingredient("FLTO", "pszenna", Type.WRAP),
                //                new Ingredient("COTO", "kukurydziana", Type.WRAP)
               // );

               
        Type[] types = Ingredient.Type.values();                                                //tworzę szyk (array) z typami składkiników (PROTEIN, VEGGIES itp..)
        for (Type type : types) {

            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type)); //przekazuję do modelu składniki pofiltrowane po typie
        }

    
        return "design";
    }

    private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {              //metoda wykorzystująca Stream
        return ingredients.stream() //metoda stream() - moge jej użyć wyłącznie na jakiejś kolekcji 
                .filter(x -> x.getType().equals(type)) //metoda filter -> wykorzystuję Lambda Expressions (biorę wszystkie te elementy, które mają taki sam typ jaki podałem w parametrzee)
                .collect(Collectors.toList());                                                    //potem wszystkie zbieram i zaposuje jako listę

    }

    @PostMapping
    public String processDesign(@Valid Taco design, Errors errors, Model model, @ModelAttribute Order order) { //po wysłaniu formularza jego pola zostają dołączone do właściwości obiektu Taco
        //do modelu dodałem obiekt newTaco() pod kluczem taco, jeżeli chce żeby to się wszystko bindowało
        //to nazwa klucza musi być taka sama jak nazwa klasy z małej litery, ponieważ domyślna wartość @ModelAttribute to nazwa klasy z małej litery
        //jeżeli nadaje inną nazwę klucza np "myTaco" to musze wtedy przy Taco dodać adnotacje @ModelAttribute("myTaco")
        if (errors.hasErrors()) {                                                 //The default model attribute name is inferred from the declared attribute type (i.e. the method parameter type or method return type), based on the non-qualified class name

           List<Ingredient> ingredients = new ArrayList<>();
           ingredientRepo.findAll().forEach(i->ingredients.add(i));
           
           
            Type[] types = Ingredient.Type.values(); //tworzę szyk (array) z typami składkiników (PROTEIN, VEGGIES itp..)
            for (Type type : types) {

                model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type)); //przekazuję do modelu składniki pofiltrowane po typie
            }

            return "design";
        }
        
        
        Taco saved = designRepo.save(design);
        order.addDesign(saved);
        
        log.info("Przetwarzanie projektu taco: " + design);//wyświetlam komunikat w aplikacji o nazwie i składnikach Taco (w konsoli)

        return "redirect:/orders/current";
    }

}
