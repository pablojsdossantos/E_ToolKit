package etk.jdbc.mapping;

import etk.data.Pair;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author Pablo JS dos Santos
 */
public class SqlBuilder<T extends SqlBuilder> {
    private String statement;
    private Map<String, Pair<ColumnType, Object>> variables;

    public SqlBuilder() {
        this(null);
    }

    public SqlBuilder(String statement) {
        this.statement = statement;
        this.variables = new HashMap<>();
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public Map<String, Pair<ColumnType, Object>> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, Pair<ColumnType, Object>> variables) {
        this.variables = variables;
    }

    public T bind(String variable, Boolean value) {
        return this.bind(variable, ColumnType.BOOLEAN, value);
    }

    public T bind(String variable, String value) {
        return this.bind(variable, ColumnType.STRING, value);
    }

    public T bind(String variable, Character value) {
        return this.bind(variable, ColumnType.CHAR, value);
    }

    public T bind(String variable, Integer value) {
        return this.bind(variable, ColumnType.INTEGER, value);
    }

    public T bind(String variable, Double value) {
        return this.bind(variable, ColumnType.DOUBLE, value);
    }

    public T bind(String variable, Long value) {
        return this.bind(variable, ColumnType.LONG, value);
    }

    public T bind(String variable, Date value) {
        return this.bind(variable, ColumnType.DATE, value);
    }

    public T bind(String variable, LocalDate value) {
        return this.bind(variable, ColumnType.DATE, value);
    }

    public T bind(String variable, Instant value) {
        return this.bind(variable, ColumnType.TIMESTAMP, value);
    }

    public T bind(String variable, LocalDateTime value) {
        return this.bind(variable, ColumnType.TIMESTAMP, value);
    }

    public T bind(String variable, UUID value) {
        return this.bind(variable, ColumnType.UUID, value);
    }

    public T bind(String variable, Enum value) {
        return this.bind(variable, ColumnType.ENUM, value);
    }

    private T bind(String variable, ColumnType type, Object value) {
        this.variables.put(this.toParameter(variable), new Pair<>(type, value));
        return (T) this;
    }

    private String toParameter(String variable) {
        variable = variable.replace("$", "");
        variable = variable.replace("{", "");
        variable = variable.replace("}", "");

        return String.format("${%s}", variable);
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
