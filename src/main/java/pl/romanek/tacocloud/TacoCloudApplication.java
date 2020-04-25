package pl.romanek.tacocloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication //Ta Adnotacja zawiera w sobie trzy inne adnotacje (@SpringBootConfiguration - określa klasę jako konfiguracyjną , @EnableAutoConfiguration, @ComponentScan)
public class TacoCloudApplication { //Jest to Klasa Początkowa Aplikacji

	public static void main(String[] args) {//Metoda statyczna main zostaje wykonana tuż po uruchomieniu pliku JAR
		SpringApplication.run(TacoCloudApplication.class, args);  //Wywołana zostaje metoda statyczna run() klasy SpringApplication. Metoda run przyjmuje dwa parametry
                                                                          //Klasę konfiguracyjną (w tym przypadku jest to ta sama klasa) oraz argumenty powłoki 
	}

}
