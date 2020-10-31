package hr.algebra.dal.sql;

import hr.algebra.dal.Repository;
import hr.algebra.model.Actor;
import hr.algebra.model.Director;
import hr.algebra.model.Movie;
import hr.algebra.model.User;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;

/**
 *
 * @author filip
 */
public class SqlRepository implements Repository {

    //MOVIE CONSTANTS
    private static final String ID_MOVIE = "IDMovie";
    private static final String TITLE = "Title";
    private static final String PUBLISH_DATE = "PublishDate";
    private static final String DESCRIPTION = "MovieDescription";
    private static final String ORIGINAL_TITLE = "OriginalTitle";
    private static final String DIRECTOR_ID = "DirectorID";
    private static final String DURATION = "Duration";
    private static final String GENRE = "Genre";
    private static final String PICTURE_PATH = "PicturePath";
    private static final String LINK = "Link";
    private static final String START_DATE = "StartDate";

    //ACTOR CONSTANTS
    private static final String ID_ACTOR = "IDActor";
    private static final String ACTOR_FIRST_NAME = "FirstName";
    private static final String ACTOR_LAST_NAME = "LastName";

    //DIRECTOR CONSTANTS
    private static final String ID_DIRECTOR = "IDDirector";
    private static final String DIRECTOR_FIRST_NAME = "FirstName";
    private static final String DIRECTOR_LAST_NAME = "LastName";

    //USER CONSTANTS
    private static final String ID_APP_USER = "IDAppUser";
    private static final String USERNAME = "Username";
    private static final String PSWRD = "Pswrd";
    private static final String IS_ADMIN = "IsAdmin";

    //MOVIE PROCEDURE CONSTANTS
    private static final String CREATE_MOVIE = " { call spCreateMovie (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) } ";
    private static final String SELECT_MOVIE = " { call spSelectMovie (?) } ";
    private static final String SELECT_MOVIES = " { call spSelectMovies } ";
    private static final String UPDATE_MOVIE = " { call spUpdateMovie (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) } ";
    private static final String DELETE_MOVIE = " { call spDeleteMovie (?) } ";

    //ACTOR PROCEDURE CONSTANTS
    private static final String CREATE_ACTOR = " { call spCreateActor (?, ?, ?) } ";
    private static final String SELECT_ACTOR = " { call spSelectActor (?) } ";
    private static final String SELECT_ACTORS = " { call spSelectActors } ";
    private static final String UPDATE_ACTOR = " { call spUpdateActor (?, ?, ?) } ";
    private static final String DELETE_ACTOR = " { call spDeleteActor (?) } ";

    //DIRECTOR PROCEDURE CONSTANTS
    private static final String CREATE_DIRECTOR = " { call spCreateDirector (?, ?, ?) } ";
    private static final String SELECT_DIRECTOR = " { call spSelectDirector (?) } ";
    private static final String SELECT_DIRECTORS = " { call spSelectDirectors } ";
    private static final String UPDATE_DIRECTOR = " { call spUpdateDirector (?, ?, ?) } ";
    private static final String DELETE_DIRECTOR = " { call spDeleteDirector (?) } ";

    //USER PROCEDURE CONSTANTS
    private static final String CREATE_USER = " { call spCreateUser (?, ?, ?, ?) } ";
    private static final String SELECT_USER = " { call spSelectUser (?) } ";
    private static final String SELECT_USERS = " { call spSelectUsers } ";
    private static final String FIND_USER = " { call spFindUser (?, ?) } ";
    private static final String GET_USER = " { call spGetUser (?, ?, ?) } ";
    private static final String CHECK_USER = " { call spCheckUsername (?, ?) } ";
    private static final String UPDATE_USER = " { call spUpdateUser (?, ?, ?, ?) } ";
    private static final String DELETE_USER = " { call spDeleteUser (?) } ";

    //MOVIES_ACTORS PROCEDURE CONSTANTS
    private static final String SELECT_MOVIES_WITH_ACTOR = " { call spSelectMoviesWithActor (?) } ";
    private static final String SELECT_ACTORS_IN_MOVIE = " { call spSelectActorsInMovie (?) } ";
    private static final String LINK_ACTOR_AND_MOVIE = " { call spLinkActorAndMovie (?, ?) } ";
    private static final String UNLINK_MOVIE_WITH_ACTORS = " { call spUnlinkMovieWithActors (?) } ";
    private static final String UNLINK_ACTOR_WITH_MOVIES = " { call spUnlinkActorWithMovies (?) } ";
    
    //GENERAL PROCEDURE CONSTANTS
    private static final String DELETE_ALL = " { call spDeleteAll } ";

    @Override
    public int createMovie(Movie movie) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(CREATE_MOVIE)) {
            stmt.registerOutParameter(1, Types.INTEGER);
            stmt.setString(2, movie.getTitle());
            stmt.setString(3, movie.getPublishDate().format(Movie.DATE_TIME_FORMATTER));
            stmt.setString(4, movie.getDescription());
            stmt.setString(5, movie.getOriginalTitle());
            stmt.setInt(6, createDirector(movie.getDirector()));
            stmt.setInt(7, movie.getDuration());
            stmt.setString(8, movie.getGenre());
            stmt.setString(9, movie.getPicturePath());
            stmt.setString(10, movie.getLink());
            stmt.setString(11, movie.getStartDate().format(Movie.DATE_TIME_FORMATTER));

            stmt.executeUpdate();

            return stmt.getInt(1);
        }
    }

    @Override
    public void createMovies(List<Movie> movies) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(CREATE_MOVIE)) {
            for (Movie movie : movies) {
                stmt.registerOutParameter(1, Types.INTEGER);
                stmt.setString(2, movie.getTitle());
                stmt.setString(3, movie.getPublishDate().format(Movie.DATE_TIME_FORMATTER));
                stmt.setString(4, movie.getDescription());
                stmt.setString(5, movie.getOriginalTitle());
                stmt.setInt(6, createDirector(movie.getDirector()));
                stmt.setInt(7, movie.getDuration());
                stmt.setString(8, movie.getGenre());
                stmt.setString(9, movie.getPicturePath());
                stmt.setString(10, movie.getLink());
                stmt.setString(11, movie.getStartDate().format(Movie.DATE_TIME_FORMATTER));

                stmt.executeUpdate();
            }
        }
    }

    @Override
    public Optional<Movie> selectMovie(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_MOVIE)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Movie(rs.getInt(ID_MOVIE),
                            rs.getString(TITLE),
                            LocalDateTime.parse(rs.getString(PUBLISH_DATE), Movie.DATE_TIME_FORMATTER),
                            rs.getString(DESCRIPTION),
                            rs.getString(ORIGINAL_TITLE),
                            selectDirector(rs.getInt(DIRECTOR_ID)).get(),
                            rs.getInt(DURATION),
                            rs.getString(GENRE),
                            rs.getString(PICTURE_PATH),
                            rs.getString(LINK),
                            LocalDateTime.parse(rs.getString(START_DATE), Movie.DATE_TIME_FORMATTER),
                            selectActorsInMovie(rs.getInt(ID_MOVIE))));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Movie> selectMovies() throws Exception {
        List<Movie> movies = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_MOVIES);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                movies.add(new Movie(rs.getInt(ID_MOVIE),
                        rs.getString(TITLE),
                        LocalDateTime.parse(rs.getString(PUBLISH_DATE), Movie.DATE_TIME_FORMATTER),
                        rs.getString(DESCRIPTION),
                        rs.getString(ORIGINAL_TITLE),
                        selectDirector(rs.getInt(DIRECTOR_ID)).get(),
                        rs.getInt(DURATION),
                        rs.getString(GENRE),
                        rs.getString(PICTURE_PATH),
                        rs.getString(LINK),
                        LocalDateTime.parse(rs.getString(START_DATE), Movie.DATE_TIME_FORMATTER),
                        selectActorsInMovie(rs.getInt(ID_MOVIE))));
            }
        }
        return movies;
    }

    @Override
    public void updateMovie(int id, Movie newMovie) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(UPDATE_MOVIE)) {
            stmt.setInt(1, id);
            stmt.setString(2, newMovie.getTitle());
            stmt.setString(3, newMovie.getPublishDate().format(Movie.DATE_TIME_FORMATTER));
            stmt.setString(4, newMovie.getDescription());
            stmt.setString(5, newMovie.getOriginalTitle());
            stmt.setInt(6, createDirector(newMovie.getDirector()));
            stmt.setInt(7, newMovie.getDuration());
            stmt.setString(8, newMovie.getGenre());
            stmt.setString(9, newMovie.getPicturePath());
            stmt.setString(10, newMovie.getLink());
            stmt.setString(11, newMovie.getStartDate().format(Movie.DATE_TIME_FORMATTER));

            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteMovie(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(DELETE_MOVIE)) {
            stmt.setInt(1, id);

            stmt.execute();
        }
    }

    @Override
    public int createActor(Actor actor) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(CREATE_ACTOR)) {
            stmt.registerOutParameter(1, Types.INTEGER);
            stmt.setString(2, actor.getFirstName());
            stmt.setString(3, actor.getLastName());

            stmt.executeUpdate();
            return stmt.getInt(1);
        }
    }

    @Override
    public void createActors(List<Actor> actors) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(CREATE_ACTOR)) {
            for (Actor actor : actors) {
                stmt.registerOutParameter(1, Types.INTEGER);
                stmt.setString(2, actor.getFirstName());
                stmt.setString(3, actor.getLastName());

                stmt.executeUpdate();
            }
        }
    }

    @Override
    public Optional<Actor> selectActor(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_ACTOR)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Actor(
                            rs.getInt(ID_ACTOR),
                            rs.getString(ACTOR_FIRST_NAME),
                            rs.getString(ACTOR_LAST_NAME)));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Actor> selectActors() throws Exception {
        List<Actor> actors = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_ACTORS);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                actors.add(new Actor(
                        rs.getInt(ID_ACTOR),
                        rs.getString(ACTOR_FIRST_NAME),
                        rs.getString(ACTOR_LAST_NAME)));
            }
        }
        return actors;
    }

    @Override
    public void updateActor(int id, Actor newActor) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(UPDATE_ACTOR)) {
            stmt.setInt(1, id);
            stmt.setString(2, newActor.getFirstName());
            stmt.setString(3, newActor.getLastName());

            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteActor(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(DELETE_ACTOR)) {
            stmt.setInt(1, id);
            stmt.execute();
        }
    }

    @Override
    public int createDirector(Director director) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(CREATE_DIRECTOR)) {
            stmt.registerOutParameter(1, Types.INTEGER);
            stmt.setString(2, director.getFirstName());
            stmt.setString(3, director.getLastName());

            stmt.executeUpdate();
            return stmt.getInt(1);
        }
    }

    @Override
    public void createDirectors(List<Director> directors) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(CREATE_DIRECTOR)) {
            for (Director director : directors) {
                stmt.registerOutParameter(1, Types.INTEGER);
                stmt.setString(2, director.getFirstName());
                stmt.setString(3, director.getLastName());

                stmt.executeUpdate();
            }
        }
    }

    @Override
    public Optional<Director> selectDirector(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_DIRECTOR)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Director(
                            rs.getInt(ID_DIRECTOR),
                            rs.getString(DIRECTOR_FIRST_NAME),
                            rs.getString(DIRECTOR_LAST_NAME)));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Director> selectDirectors() throws Exception {
        List<Director> directors = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_DIRECTORS);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                directors.add(new Director(
                        rs.getInt(ID_DIRECTOR),
                        rs.getString(DIRECTOR_FIRST_NAME),
                        rs.getString(DIRECTOR_LAST_NAME)));
            }
        }
        return directors;
    }

    @Override
    public void updateDirector(int id, Director newDirector) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(UPDATE_DIRECTOR)) {
            stmt.setInt(1, id);
            stmt.setString(2, newDirector.getFirstName());
            stmt.setString(3, newDirector.getLastName());

            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteDirector(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(DELETE_DIRECTOR)) {
            stmt.setInt(1, id);

            stmt.execute();
        }
    }

    @Override
    public int createUser(User user) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(CREATE_USER)) {
            stmt.registerOutParameter(1, Types.INTEGER);
            stmt.setString(2, user.getUsername());
            stmt.setString(3, user.getPassword());
            stmt.setInt(4, user.getIsAdmin() ? 1 : 0);

            stmt.executeUpdate();
            return stmt.getInt(1);
        }
    }

    @Override
    public Optional<User> selectUser(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_USER)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new User(
                            rs.getInt(ID_APP_USER),
                            rs.getString(USERNAME),
                            rs.getString(PSWRD),
                            (rs.getInt(IS_ADMIN) == 1)));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<User> selectUsers() throws Exception {
        List<User> users = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_USERS);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                users.add(new User(
                        rs.getString(USERNAME),
                        rs.getString(PSWRD),
                        (rs.getInt(IS_ADMIN) == 1)));
            }
        }
        return users;
    }

    @Override
    public User findUser(String username, String password) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(FIND_USER)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getInt(ID_APP_USER),
                            rs.getString(USERNAME),
                            rs.getString(PSWRD),
                            (rs.getInt(IS_ADMIN) == 1));
                }
            }
        }
        return null;
    }

    @Override
    public boolean checkExistence(String username, String password) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(GET_USER)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.registerOutParameter(3, Types.INTEGER);
            stmt.execute();
            return stmt.getInt(3) == 1;
        }
    }

    @Override
    public boolean checkUsername(String username) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(CHECK_USER)) {
            stmt.setString(1, username);
            stmt.registerOutParameter(2, Types.INTEGER);
            stmt.execute();
            return stmt.getInt(2) == 1;
        }
    }

    @Override
    public void updateUser(int id, User newUser) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(UPDATE_USER)) {
            stmt.setInt(1, id);
            stmt.setString(2, newUser.getUsername());
            stmt.setString(3, newUser.getPassword());
            stmt.setInt(4, newUser.getIsAdmin() ? 1 : 0);

            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteUser(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(DELETE_USER)) {
            stmt.setInt(1, id);

            stmt.execute();
        }
    }

    @Override
    public List<Movie> selectMoviesWithActor(int actorId) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        List<Movie> movies = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_MOVIES_WITH_ACTOR)) {
            stmt.setInt(1, actorId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    movies.add(new Movie(rs.getInt(ID_MOVIE),
                            rs.getString(TITLE),
                            LocalDateTime.parse(rs.getString(PUBLISH_DATE), Movie.DATE_TIME_FORMATTER),
                            rs.getString(DESCRIPTION),
                            rs.getString(ORIGINAL_TITLE),
                            selectDirector(rs.getInt(DIRECTOR_ID)).get(),
                            rs.getInt(DURATION),
                            rs.getString(GENRE),
                            rs.getString(PICTURE_PATH),
                            rs.getString(LINK),
                            LocalDateTime.parse(rs.getString(START_DATE), Movie.DATE_TIME_FORMATTER),
                            selectActorsInMovie(rs.getInt(ID_MOVIE))));
                }
            }
            return movies;
        }
    }

    @Override
    public List<Actor> selectActorsInMovie(int movieId) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        List<Actor> actors = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_ACTORS_IN_MOVIE)) {
            stmt.setInt(1, movieId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    actors.add(new Actor(
                            rs.getInt(ID_ACTOR),
                            rs.getString(ACTOR_FIRST_NAME),
                            rs.getString(ACTOR_LAST_NAME)));
                }
            }
            return actors;
        }
    }

    @Override
    public void linkActorAndMovie(int movieId, int actorId) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(LINK_ACTOR_AND_MOVIE)) {
            stmt.setInt(1, movieId);
            stmt.setInt(2, actorId);

            stmt.executeUpdate();
        }
    }

    @Override
    public void UnlinkMovieWithActors(int movieId) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(UNLINK_MOVIE_WITH_ACTORS)) {
            stmt.setInt(1, movieId);
            stmt.execute();
        }
    }

    @Override
    public void UnlinkActorWithMovies(int actorId) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(UNLINK_ACTOR_WITH_MOVIES)) {
            stmt.setInt(1, actorId);
            stmt.execute();
        }
    }

    @Override
    public void deleteAll() throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try(Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(DELETE_ALL)){
            stmt.execute();
        }
    }
}
