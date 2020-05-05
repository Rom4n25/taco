package pl.romanek.tacocloud;

import pl.romanek.tacocloud.controllers.HomeController;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.romanek.tacocloud.repository.IngredientRepository;
import pl.romanek.tacocloud.repository.OrderRepository;
import pl.romanek.tacocloud.repository.TacoRepository;

@RunWith(SpringRunner.class) 
@WebMvcTest(HomeController.class) //Adnotacja dostarczona przez springa i jest przeznaczona do wykonywania testów w kontekście aplikacji Spring MVC
                                  //Powoduje zarejestrowanie klasy HomeController w Spring MVC aby można było wykonywać do niej żądania 
                                  //Ponadto adnotacja ta konfiguruje Springa do obsługi w zakresie testowania Spring MVC
public class HomeControllerTest {

    @Autowired                   //Można uruchomić serwer ale do tych celów wystarczy mechanizm imitacji w Spring MVC
                                 //Klasa testowa zostaje wstrzyknięta z obiektem MockMvc pomoagającym w zarządzaniu imitacją
    private MockMvc mockMvc;
    
  @MockBean
  private IngredientRepository ingredientRepository;

  @MockBean
  private TacoRepository designRepository;

  @MockBean
  private OrderRepository orderRepository;

    @Test
    public void testHomePage() throws Exception {
        mockMvc.perform(get("/")) //wykonanie żądania HTTP Get do "/"
                .andExpect(status().isOk()) //oczekiwany kod stanu HTTP 200
                .andExpect(view().name("home")) //oczekiwany jest widok home
                .andExpect(content().string( //oczekiwany jest ciąg tekstowy Witaj w...
                        containsString("Witaj w Taco!")));
    }
}
