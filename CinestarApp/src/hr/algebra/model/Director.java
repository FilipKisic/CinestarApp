package hr.algebra.model;
/**
 *
 * @author filip
 */
public class Director extends Person{
    
    public Director(String firstName, String lastName) {
        super(firstName, lastName);
    }
    
    public Director(int id, String firstName, String lastName) {
        super(id, firstName, lastName);
    }

    @Override
    public String toString() {
        return this.getFirstName() + " " + this.getLastName();
    }
    
}
