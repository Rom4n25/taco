package pl.romanek.tacocloud;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)//@RunWith to adnotacja JUnit, która wskazuje mechanizm do wykonania testu przez JUnit
                            //W tym przykładzie JUnit wykorzysta SpringRunner czyli dostarczony przez Springa mechanizm wykonywania testów
                            //zapewniający kontekst aplikacji Springa potrzebny dla testów

@SpringBootTest //Adnotacja, że jest to klasa testująca
class TacocloudApplicationTests {
    
              
	@Test //Jest to metoda testu
	void contextLoads() {  //Sprawdzane jest czy udało sie wczytać kontekst aplikacji Springa
	}

}
