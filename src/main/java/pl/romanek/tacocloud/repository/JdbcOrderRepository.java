package pl.romanek.tacocloud.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import pl.romanek.tacocloud.domain.Order;
import pl.romanek.tacocloud.domain.Taco;

@Repository
public class JdbcOrderRepository implements OrderRepository {

    private SimpleJdbcInsert orderInserter;  //zostanie przygotowany w konstruktorze z wykorzystaniem jdbc 
    private SimpleJdbcInsert orderTacoInserter;
    private ObjectMapper objectMapper;

    @Autowired
    public JdbcOrderRepository(JdbcTemplate jdbc) { //konstruktor
        
        this.orderInserter = new SimpleJdbcInsert(jdbc).withTableName("Taco_Order").usingGeneratedKeyColumns("id"); //skonfirugorwany do pracy z tabela Taco_order i poinstruowany żeby baza generowała id

        this.orderTacoInserter = new SimpleJdbcInsert(jdbc).withTableName("Taco_Order_Tacos");

        this.objectMapper = new ObjectMapper();
    }

    @Override
    public Order save(Order order) {

        order.setPlacedAt(new Date());

        long orderId = saveOrderDetails(order);
        order.setId(orderId);

       
        List<Taco> tacos = order.getTacoOrderList();


        for (Taco taco : tacos) {

            saveTacoToOrder(taco, orderId);
        }

        return order;
    }

    private long saveOrderDetails(Order order) {

        @SuppressWarnings("unchecked")

        Map<String, Object> values = objectMapper.convertValue(order, Map.class);//konwertuje obiekt Order na Mapę

        values.put("placedAt", order.getPlacedAt()); //muszę wykonać taką operację ponieważ ObjectMapper konwertuje mi wartość placetAt (typ Date) do typu Long a ja chce mieć typ Date

        long orderId = orderInserter.executeAndReturnKey(values).longValue();//zapisanie informacji w tabeli Taco_Order oraz zwrot wygenetowanego przez baze danych klucza
        return orderId;
    }

    private void saveTacoToOrder(Taco taco, long orderId) {

        Map<String, Object> values = new HashMap<>();

        values.put("tacoOrder", orderId);
        values.put("taco", taco.getId());
        orderTacoInserter.execute(values);
    }

}
