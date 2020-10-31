package hr.algebra.dal;

import hr.algebra.model.Actor;
import hr.algebra.model.Director;
import hr.algebra.model.Movie;
import hr.algebra.model.User;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author filip
 */
public interface Repository {

    //Movie operations
    int createMovie(Movie movie) throws Exception;
    void createMovies(List<Movie> movies) throws Exception;
    Optional<Movie> selectMovie(int id) throws Exception;
    List<Movie> selectMovies() throws Exception;
    void updateMovie(int id, Movie newMovie) throws Exception;
    void deleteMovie(int id) throws Exception;

    //Actor operations
    int createActor(Actor actor) throws Exception;
    void createActors(List<Actor> actors) throws Exception;
    Optional<Actor> selectActor(int id) throws Exception;
    List<Actor> selectActors() throws Exception;
    void updateActor(int id, Actor newActor) throws Exception;
    void deleteActor(int id) throws Exception;
    
    //Director operations
    int createDirector(Director director) throws Exception;
    void createDirectors(List<Director> directors) throws Exception;
    Optional<Director> selectDirector(int id) throws Exception;
    List<Director> selectDirectors() throws Exception;
    void updateDirector(int id, Director newDirector) throws Exception;
    void deleteDirector(int id) throws Exception;
    
    //User operations
    int createUser(User user) throws Exception;
    Optional<User> selectUser(int id) throws Exception;
    List<User> selectUsers() throws Exception;
    User findUser(String username, String password) throws Exception;
    boolean checkExistence(String username, String password) throws Exception;
    boolean checkUsername(String username) throws Exception;
    void updateUser(int id, User newUser) throws Exception;
    void deleteUser(int id) throws Exception;
    
    //Movies_Actor operations
    List<Movie> selectMoviesWithActor(int actorId) throws Exception;
    List<Actor> selectActorsInMovie(int movieId) throws Exception;
    void linkActorAndMovie(int movieId, int actorId) throws Exception;
    void UnlinkMovieWithActors(int movieId) throws Exception;
    void UnlinkActorWithMovies(int actorId) throws Exception;
    
    //General operations
    void deleteAll() throws Exception;
    
}
