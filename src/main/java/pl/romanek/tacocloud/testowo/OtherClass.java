package pl.romanek.tacocloud.testowo;

public class OtherClass {

    RomanekClass romanekClass = new RomanekClass();

    public void run() {

        romanekClass.metodaRomanek("Mateusz", "Romanek",
                new RomanekInterface() {
            public String say() {
                return "Siemanko";
            }
        });

    }
    
}
