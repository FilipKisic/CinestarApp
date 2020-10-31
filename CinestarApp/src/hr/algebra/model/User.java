package hr.algebra.model;

/**
 *
 * @author filip
 */
public class User {

    private int id;
    private String username;
    private String password;
    private boolean isAdmin;

    public User(int id, String username, String password, boolean isAdmin) {
        this(username, password, isAdmin);
        this.id = id;
    }

    public User(String username, String password, boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
    
}
