package pl.romanek.tacocloud.controllers;

import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import pl.romanek.tacocloud.domain.Order;
import pl.romanek.tacocloud.repository.OrderRepository;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("order")//dalej obiekt Order order jest w modelu 
public class OrderController {

    private OrderRepository orderRepo;

    public OrderController(OrderRepository orderRepo) {
        this.orderRepo = orderRepo;
    }

    @GetMapping("/current")
    public String orderForm(Model model) {

     
        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid Order order, Errors errors, SessionStatus sessionStatus) {

        if (errors.hasErrors()) {

            return "orderForm";
        }

        orderRepo.save(order); //teraz dodaję obiekt Order do bazy danych   
        sessionStatus.setComplete(); //obiekt session przestaje istniec
        log.info("Zamówienie zostało złożone: " + order); //wyświetlam komunikat w aplikacji o złożonym zamówieniu (w konsoli)
        return "redirect:/";
    }
}
