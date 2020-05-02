package pl.romanek.tacocloud.domain;

import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class Taco {
    
    
    private Long id;
    private Date createdAt;
    @NotNull
    @Size(min=5, message = "Nazwa musi składać się z przynajmniej pięciu znaków.")
    private String name;
    @NotNull (message= "Musisz wybrać przynajmniej jeden składnik.")
    private List<Ingredient> ingredients;

    
}

