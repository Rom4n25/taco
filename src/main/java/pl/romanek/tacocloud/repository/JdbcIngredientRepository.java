package pl.romanek.tacocloud.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import pl.romanek.tacocloud.domain.Ingredient;

@Repository
public class JdbcIngredientRepository implements IngredientRepository {

    private JdbcTemplate jdbc; //Java Data Base Concectivity

    @Autowired
    public JdbcIngredientRepository(JdbcTemplate jdbc) { //gdy spring tworzy obiekt bean JdbcIngredientRepository wstrzykuje JdbcTemplate za pomocą konstrukcji oznaczonej @Autowired
        this.jdbc = jdbc;
    }

    @Override
    public Iterable<Ingredient> findAll() {
        return jdbc.query("select id, name,type from Ingredient", this::mapRowToIngredient); //metoda query() akceptuje kod SQL dla zapytania oraz implementację Springa RowMapper pozwalającą na mapowanie każdego wierza w zbiorze wynikowym na obiekt
                                                                                             //RowMapper jest to interfejs funkcyjny, który ma jedną metodę mapRow() -  za pomocą this:: robie mapowanie medody, która będzie implementacją metody mapRow
    }                                                                                       //natomiast jako ostatni argument akceptuje listę wszeklich parametrów wymaganych w zapytaniu. Jednak w omawianej sytuacji nie ma żadnych wymaganych parametrów          

    @Override
    public Ingredient findById(String id) {
        return jdbc.queryForObject("select id, name, type from Ingredient where id=?", this::mapRowToIngredient, id); //w zapytaniu jest wymagane id więc jako ostatni pojawia się argument id 
    }

//    @Override
//    public Ingredient findOne(String id) {
//
//        tworzę poniżej klasę, która implementuje intefrejs RowMapper (jest to tak zwana Anonnymous Inner Class) -- nie muszę tworzyć dodatkowej klasy Implementacyjnej ten interfejs 
//        RowMapper<Ingredient> mapper = new RowMapper<Ingredient>() {
//            @Override
//            public Ingredient mapRow(ResultSet rs, int rowNum) throws SQLException {
//                return new Ingredient(rs.getString("id"), rs.getString("name"), Ingredient.Type.valueOf(rs.getString("type")));
//            }
//
//        };
//
//        return jdbc.queryForObject("select id, name, type from Ingredient where id=?", mapper, id);
//
//    }

    @Override
    public Ingredient save(Ingredient ingredient) { //zapisuje w bazie danych w tabeli Ingredient wartości obiektu
        jdbc.update("insert into Ingredient (id,name,type) values (?,?,?)",ingredient.getId(),ingredient.getName(),ingredient.getType().toString());
        return ingredient;
    }

    private Ingredient mapRowToIngredient(ResultSet rs, int rowNum) throws SQLException {  //
        return new Ingredient(
                rs.getString("id"),
                rs.getString("name"),
                Ingredient.Type.valueOf(rs.getString("type")));
    }

}
