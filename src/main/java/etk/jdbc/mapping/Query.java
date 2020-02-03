package etk.jdbc.mapping;

import etk.jdbc.connection.Session;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Pablo JS dos Santos
 */
public class Query<T> extends SqlBuilder<Query<T>> {
    private Session session;
    private RowMapper<T> mapper;

    public Query(Session session, String statement) {
        super(statement);
        this.session = session;
    }

    public Query<T> map(RowMapper<T> mapper) {
        this.mapper = mapper;
        return this;
    }

    public List<T> list() {
        Sql sql = this.buildSql();
        return this.session.executeQuery(sql, this.mapper);
    }

    public Optional<T> findFirst() {
        return this.list()
            .stream()
            .findFirst();
    }
}
