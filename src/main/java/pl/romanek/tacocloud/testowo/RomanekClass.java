
package pl.romanek.tacocloud.testowo;

public class RomanekClass {
    

    
    public String metodaRomanek(String imie, String nazwisko,RomanekInterface romanekInterface){
        
        return imie + nazwisko + romanekInterface.say();
        
    }


    
}
    

    
    
    
    
////Stworzona Klasa, która implementuje interfejs RomanekIterface
// RomanekInterface obiektElo = new RomanekInterface(){
//     @Override
//     public void say() {
//         System.out.println("Witam");
//     }
//    
// };
// 
// public void tell(){
//     
//     obiektElo.say();
//     
// }
//
//
// 
//    
//}
