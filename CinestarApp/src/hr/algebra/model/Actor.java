package hr.algebra.model;

/**
 *
 * @author filip
 */
public class Actor extends Person{
    
    public Actor(String firstName){
        super(firstName);
    }
    
    public Actor(String firstName, String lastName) {
        super(firstName, lastName);
    }
    
    public Actor(int id, String firstName, String lastName) {
        super(id, firstName, lastName);
    }

    @Override
    public String toString() {
        return this.getFirstName() + " " + this.getLastName();
    }
    
}
