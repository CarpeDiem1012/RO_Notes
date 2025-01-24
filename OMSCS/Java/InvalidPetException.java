public class InvalidPetException extends RuntimeException{

    public InvalidPetException(String s) {
        super(s);
    }
    public InvalidPetException() {
        this("Your pet is invalid!");
    }
    
}