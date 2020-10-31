package hr.algebra.model;

/**
 *
 * @author filip
 */
public abstract class Person {
    
    private int id;
    private String firstName;
    private String lastName;

    public Person(String firstName){
        this.firstName = firstName;
    }
    
    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Person(int id, String firstName, String lastName) {
        this(firstName, lastName);
        this.id = id;
    }

    public int getId() {
        return id;
    }
    
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    @Override
    public String toString() {
        return "Person{" + "firstName=" + firstName + ", lastName=" + lastName + '}';
    }
}
