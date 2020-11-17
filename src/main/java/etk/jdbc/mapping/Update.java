package etk.jdbc.mapping;

import etk.jdbc.connection.DbSession;

/**
 *
 * @author Pablo JS dos Santos
 */
public class Update extends SqlBuilder<Update> {
    private DbSession session;

    public Update(DbSession session, String statement) {
        super(statement);
        this.session = session;
    }

    public long execute() {
        Sql sql = this.buildSql();
        return this.session.executeUpdate(sql, false);
    }

    public long executeAndReturnGeneratedKey() {
        Sql sql = this.buildSql();
        return this.session.executeUpdate(sql, true);
    }
}
