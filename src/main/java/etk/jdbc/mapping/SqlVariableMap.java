package etk.jdbc.mapping;

import etk.data.Pair;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author Pablo JS dos Santos
 */
public class SqlVariableMap {
    private Map<String, Pair<ColumnType, Object>> variables;

    public SqlVariableMap() {
        this.variables = new HashMap<>();
    }

    public void bind(String variable, Boolean value) {
        this.bind(variable, ColumnType.BOOLEAN, value);
    }

    public void bind(String variable, String value) {
        this.bind(variable, ColumnType.STRING, value);
    }

    public void bind(String variable, Character value) {
        this.bind(variable, ColumnType.CHAR, value);
    }

    public void bind(String variable, Integer value) {
        this.bind(variable, ColumnType.INTEGER, value);
    }

    public void bind(String variable, Double value) {
        this.bind(variable, ColumnType.DOUBLE, value);
    }

    public void bind(String variable, Long value) {
        this.bind(variable, ColumnType.LONG, value);
    }

    public void bind(String variable, Date value) {
        this.bind(variable, ColumnType.DATE, value);
    }

    public void bind(String variable, LocalDate value) {
        this.bind(variable, ColumnType.DATE, value);
    }

    public void bind(String variable, Instant value) {
        this.bind(variable, ColumnType.TIMESTAMP, value);
    }

    public void bind(String variable, LocalDateTime value) {
        this.bind(variable, ColumnType.TIMESTAMP, value);
    }

    public void bind(String variable, UUID value) {
        this.bind(variable, value, false);
    }

    public void bind(String variable, UUID value, boolean binary) {
        this.bind(variable, binary ? ColumnType.UUID_BIN : ColumnType.UUID_TXT, value);
    }

    public void bind(String variable, Enum value) {
        this.bind(variable, ColumnType.ENUM, value);
    }

    public void bind(String variable, ColumnType type, Object value) {
        this.variables.put(this.toParameter(variable), new Pair<>(type, value));
    }

    private String toParameter(String variable) {
        variable = variable.replace("$", "");
        variable = variable.replace("{", "");
        variable = variable.replace("}", "");

        return String.format("${%s}", variable);
    }

    public boolean isEmpty() {
        return this.variables.isEmpty();
    }

    public Pair<ColumnType, Object> get(String param) {
        return this.variables.get(param);
    }
}
