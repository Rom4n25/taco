package pl.romanek.tacocloud.repository;

import pl.romanek.tacocloud.domain.Ingredient;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcIngredientRepository implements IngredientRepository {

    private final JdbcTemplate jdbc; //Java Data Base Concectivity - jest to łączę do baz danych w języku java

    @Autowired
    public JdbcIngredientRepository(JdbcTemplate jdbc) { //gdy spring tworzy obiekt bean JdbcIngredientRepository wstrzykuje JdbcTemplate za pomocą konstrukcji oznaczonej @Autowired
        this.jdbc = jdbc;
    }

    @Override
    public Iterable<Ingredient> findAll() {

        //RowMapper jest to interfejs funkcyjny, który ma jedną metodę mapRow()
        RowMapper<Ingredient> mapper = new RowMapper<Ingredient>() { //jest to tak zwana Anonnymous Inner Class) -- nie muszę tworzyć dodatkowej klasy Implementacyjnej ten interfejs 
            @Override
            public Ingredient mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Ingredient(rs.getString("id"), rs.getString("name"), Ingredient.Type.valueOf(rs.getString("type")));
            }

        };

        //Poniżej jako LambdaExpression jest zapisane to co wyżej
        //RowMapper<Ingredient> mapper = (ResultSet rs, int rowNum) -> new Ingredient(rs.getString("id"), rs.getString("name"), Ingredient.Type.valueOf(rs.getString("type")));
        return jdbc.query("select id, name,type from Ingredient", mapper);
     
    }

    @Override
    public Ingredient findById(String id) {
        return jdbc.queryForObject("select id, name, type from Ingredient where id=?", this::mapRowToIngredient, id); //metoda query() akceptuje kod SQL dla zapytania oraz implementację Springa RowMapper pozwalającą na mapowanie każdego wierza w zbiorze wynikowym na obiekt, zwraca listę obiektów
        //w zapytaniu jest wymagane id więc jako ostatni pojawia się argument id 
    }                                                                                                                  // :: - double colon pozwala na odwołanie się do metody danej klasy, w tym przypadku używając słowa this oznaczam że chodzi o tą klasę w której jestem i jej metodę

    @Override
    public Ingredient save(Ingredient ingredient) { //zapisuje w bazie danych w tabeli Ingredient wartości obiektu
        jdbc.update("insert into Ingredient (id,name,type) values (?,?,?)", ingredient.getId(), ingredient.getName(), ingredient.getType().toString());
        return ingredient;
    }

    private Ingredient mapRowToIngredient(ResultSet rs, int rowNum) throws SQLException {  //tworzę klasę mapRowToIngredient, która później będzie nadpisywać metodę mapRow interfejsu funkcyjnego RowMapper - określam jak i co ma być zmapowane na obiekt
        return new Ingredient(
                rs.getString("id"), //wartość z kolumny id zostanie przypisana do pola id obiektu Ingredient
                rs.getString("name"), //wartość z kolumny name zostanie przypisana do pola name obiektu Ingredient
                Ingredient.Type.valueOf(rs.getString("type")));                              //do stringów jak powyżej od razu mogę przypisać do pola za pomocą rs.
        //natomiast do Typowi wyliczeniowemu muszę przypisać tą wartość odwołując się głębiej
    }

}
