package etk.jdbc.mapping;

import java.time.Instant;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Pablo JS dos Santos
 */
public class Insert {
    private String table;
    private boolean ignore;
    private List<String> columnNames;
    private SqlBuilder<SqlBuilder> sqlBuilder;

    private Insert(String table, boolean ignore) {
        this.table = table;
        this.ignore = ignore;
        this.columnNames = new LinkedList<>();
        this.sqlBuilder = new SqlBuilder();
    }

    public Insert value(String columnName, Enum value) {
        this.sqlBuilder.bind(columnName, value);
        this.columnNames.add(columnName);
        return this;
    }

    public Insert value(String columnName, String value) {
        this.sqlBuilder.bind(columnName, value);
        this.columnNames.add(columnName);
        return this;
    }

    public Insert value(String columnName, Integer value) {
        this.sqlBuilder.bind(columnName, value);
        this.columnNames.add(columnName);
        return this;
    }

    public Insert value(String columnName, Character value) {
        this.sqlBuilder.bind(columnName, value);
        this.columnNames.add(columnName);
        return this;
    }

    public Insert value(String columnName, Long value) {
        this.sqlBuilder.bind(columnName, value);
        this.columnNames.add(columnName);
        return this;
    }

    public Insert value(String columnName, Double value) {
        this.sqlBuilder.bind(columnName, value);
        this.columnNames.add(columnName);
        return this;
    }

    public Insert value(String columnName, LocalDate value) {
        this.sqlBuilder.bind(columnName, value);
        this.columnNames.add(columnName);
        return this;
    }

    public Insert value(String columnName, Instant value) {
        this.sqlBuilder.bind(columnName, value);
        this.columnNames.add(columnName);
        return this;
    }

    public Sql build() {
        StringBuilder columns = new StringBuilder();
        StringBuilder values = new StringBuilder();

        for (String columnName : this.columnNames) {
            columns.append(columnName).append(", ");
            values.append("${").append(columnName).append("}, ");
        }

        columns.delete(columns.length() - 2, columns.length());
        values.delete(values.length() - 2, values.length());

        StringBuilder sql = new StringBuilder("INSERT")
            .append(this.ignore ? " IGNORE" : "")
            .append(" INTO ")
            .append(this.table)
            .append(" (")
            .append(columns.toString())
            .append(") VALUES (")
            .append(values.toString())
            .append(")");

        this.sqlBuilder.setStatement(sql.toString());
        return this.sqlBuilder.buildSql();
    }

    public static Insert into(String table) {
        return new Insert(table, false);
    }

    public static Insert ignoreInto(String table) {
        return new Insert(table, true);
    }
}
