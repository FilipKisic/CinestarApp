package hr.algebra.dal;

import hr.algebra.dal.sql.SqlRepository;

/**
 *
 * @author filip
 */
public class RepositoryFactory {

    public static Repository getRepository() {
        return new SqlRepository();
    }
}
