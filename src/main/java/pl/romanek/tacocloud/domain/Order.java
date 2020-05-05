
package pl.romanek.tacocloud.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Data;


@Data
public class Order {
    
    private Long id;
    private Date placedAt;
    private List<Taco> tacoOrderList = new ArrayList<>();
    
    
    @NotBlank(message = "Podanie imienia i nazwiska jest obowiązkowe")
    private String deliveryName;
    
    @NotBlank(message = "Podanie ulicy jest obowiązkowe")
    private String deliveryStreet;
    
    @NotBlank(message = "Podanie miejscowości jest obowiązkowe")
    private String deliveryCity;
    
    @NotBlank(message = "Podanie województwa jest obowiązkowe")
    private String deliveryState;
    
    @NotBlank(message = "Podanie kodu pocztowego jest obowiązkowe")
    private String deliveryZip;
    
    //@CreditCardNumber(message = "To nie jest prawidłowy numer karty kredytowej")
    private String ccNumber;
    
    @Pattern(regexp="^(0[1-9]|1[0-2])([\\/])([1-9][0-9])$", message="Wartość musi być w formacie MM/RR.")
    private String ccExpiration;
    
    @Digits(integer=3, fraction=0, message="Nieprawidłowy kod CVV")
    private String ccCVV;
    
    
    public void addDesign(Taco taco){
        
        this.tacoOrderList.add(taco);
    }
}
