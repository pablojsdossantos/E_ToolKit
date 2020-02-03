package etk.jdbc.mapping;

import etk.data.Pair;
import java.util.List;

/**
 *
 * @author Pablo JS dos Santos
 */
public class Sql {
    private String sql;
    private List<Pair<ColumnType, Object>> parameters;

    public Sql(String sql, List<Pair<ColumnType, Object>> parameters) {
        this.sql = sql;
        this.parameters = parameters;
    }

    public String getCommand() {
        return sql;
    }

    public List<Pair<ColumnType, Object>> getParameters() {
        return parameters;
    }
}
