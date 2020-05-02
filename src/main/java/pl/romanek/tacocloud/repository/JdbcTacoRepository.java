package pl.romanek.tacocloud.repository;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.Arrays;
import java.util.Date;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import pl.romanek.tacocloud.domain.Ingredient;
import pl.romanek.tacocloud.domain.Taco;

public class JdbcTacoRepository implements TacoRepository{

    private JdbcTemplate jdbc;

    public JdbcTacoRepository(JdbcTemplate jdbc) {  //wstrzkuję przez konstruktor JdbcTemplate
        this.jdbc = jdbc;
    }   

   
    
    @Override
       public Taco save(Taco taco) {

        long tacoId = saveTacoInfo(taco);  //za pomocą metody zwracam wartość Taco id 
        
        taco.setId(tacoId); //ustawiam wartość Taco id
        
        for (Ingredient ingredient : taco.getIngredients()){ 
            
           saveIngredientToTaco(ingredient, tacoId); //każdy ingredient przypisuje w bazie danych do tego konkretnego Taco
            
        }
        
        return taco;
       
    }


    
    //podczas wstawiania rekordu do repozytorim Taco trzeba znać identyfikator wygenerowany 
    //przez bazę danych aby można się było do niego odwołać jak coś
    private long saveTacoInfo(Taco taco) {
        
        taco.setCreatedAt(new Date());

        PreparedStatementCreator psc = new PreparedStatementCreatorFactory("insert into Taco (name, createdAt) values (?, ?)",Types.VARCHAR, Types.TIMESTAMP)
                .newPreparedStatementCreator(Arrays.asList(taco.getName(),new Timestamp(taco.getCreatedAt().getTime())));

        
        KeyHolder keyHolder = new GeneratedKeyHolder();//generuje id Taco
        
        jdbc.update(psc,keyHolder);//inna metoda wstawiania rekordu
                                  //metoda akceptuje PreparedStatementCreator i KeyHolder
                                  //keyHolder dostarczy wygenerowany identyfikator
                                  //lecz zanim można go będzie użyć to trzeba utworzyć PreparedStatementCreator
        
        return keyHolder.getKey().longValue(); //zwracam id Taco
        
    }
    
    
    private void saveIngredientToTaco(Ingredient ingredient, long tacoId){
        
        jdbc.update("insert into Taco_Ingredients (taco,ingredient) values(?,?)",tacoId,ingredient.getId());
    }



}
