
package pl.romanek.tacocloud.repository;

import pl.romanek.tacocloud.domain.Taco;

public interface TacoRepository {
    
    Taco save(Taco taco);
    
}
