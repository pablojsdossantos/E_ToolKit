package etk.jdbc.mapping;

import etk.data.Pair;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Pablo JS dos Santos
 */
public class SqlBuilder<T extends SqlBuilder> {
    private String statement;
    private SqlVariableMap variables;

    public SqlBuilder() {
        this(null);
    }

    public SqlBuilder(String statement) {
        this.statement = statement;
        this.variables = new SqlVariableMap();
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public SqlVariableMap getVariables() {
        return variables;
    }

    public T setVariables(SqlVariableMap variables) {
        this.variables = variables;
        return (T) this;
    }

    public T bind(String variable, Boolean value) {
        this.variables.bind(variable, value);
        return (T) this;
    }

    public T bind(String variable, String value) {
        this.variables.bind(variable, value);
        return (T) this;
    }

    public T bind(String variable, Character value) {
        this.variables.bind(variable, value);
        return (T) this;
    }

    public T bind(String variable, Integer value) {
        this.variables.bind(variable, value);
        return (T) this;
    }

    public T bind(String variable, Double value) {
        this.variables.bind(variable, value);
        return (T) this;
    }

    public T bind(String variable, Long value) {
        this.variables.bind(variable, value);
        return (T) this;
    }

    public T bind(String variable, Date value) {
        this.variables.bind(variable, value);
        return (T) this;
    }

    public T bind(String variable, LocalDate value) {
        this.variables.bind(variable, value);
        return (T) this;
    }

    public T bind(String variable, Instant value) {
        this.variables.bind(variable, value);
        return (T) this;
    }

    public T bind(String variable, LocalDateTime value) {
        this.variables.bind(variable, value);
        return (T) this;
    }

    public T bind(String variable, UUID value) {
        this.variables.bind(variable, value);
        return (T) this;
    }

    public T bind(String variable, UUID value, boolean binary) {
        this.variables.bind(variable, value, binary);
        return (T) this;
    }

    public T bind(String variable, Enum value) {
        this.variables.bind(variable, value);
        return (T) this;
    }

    private T bind(String variable, ColumnType type, Object value) {
        this.variables.bind(variable, type, value);
        return (T) this;
    }

    protected Sql buildSql() {
        List<Pair<ColumnType, Object>> parameters = new ArrayList<>();
        StringBuilder sql = new StringBuilder(this.statement);

        if (!this.variables.isEmpty()) {
            for (int startParamIndex = sql.indexOf("${"); startParamIndex >= 0; startParamIndex = sql.indexOf("${")) {
                int endParamIndex = sql.indexOf("}", startParamIndex);
                String param = sql.substring(startParamIndex, endParamIndex + 1);

                sql.replace(startParamIndex, endParamIndex + 1, "?");
                parameters.add(this.variables.get(param));
            }
        }

        return new Sql(sql.toString(), parameters);
    }
}
