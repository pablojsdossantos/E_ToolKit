package etk.jdbc.mapping;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Pablo JS dos Santos
 */
public class Insert {
    private String table;
    private boolean ignore;
    private boolean onDuplicateKey;
    private List<String> columnNames;
    private List<String> columnsToUpdate;
    private SqlBuilder<SqlBuilder> sqlBuilder;

    private Insert(String table, boolean ignore) {
        this.table = table;
        this.ignore = ignore;
        this.columnNames = new LinkedList<>();
        this.columnsToUpdate = new ArrayList<>();
        this.sqlBuilder = new SqlBuilder();
    }

    public Insert value(String columnName, UUID value, boolean binary) {
        this.sqlBuilder.bind(columnName, value, binary);
        this.columnNames.add(columnName);
        return this;
    }

    public Insert value(String columnName, Boolean value) {
        this.sqlBuilder.bind(columnName, value);
        this.columnNames.add(columnName);
        return this;
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

    public Insert onDuplicate() {
        this.onDuplicateKey = true;
        return this;
    }

    public Insert update(String columnName, UUID value, boolean binary) {
        this.sqlBuilder.bind("updt_" + columnName, value, binary);
        this.columnsToUpdate.add(columnName);
        return this;
    }

    public Insert update(String columnName, Boolean value) {
        this.sqlBuilder.bind("updt_" + columnName, value);
        this.columnsToUpdate.add(columnName);
        return this;
    }

    public Insert update(String columnName, Enum value) {
        this.sqlBuilder.bind("updt_" + columnName, value);
        this.columnsToUpdate.add(columnName);
        return this;
    }

    public Insert update(String columnName, String value) {
        this.sqlBuilder.bind("updt_" + columnName, value);
        this.columnsToUpdate.add(columnName);
        return this;
    }

    public Insert update(String columnName, Integer value) {
        this.sqlBuilder.bind("updt_" + columnName, value);
        this.columnsToUpdate.add(columnName);
        return this;
    }

    public Insert update(String columnName, Character value) {
        this.sqlBuilder.bind("updt_" + columnName, value);
        this.columnsToUpdate.add(columnName);
        return this;
    }

    public Insert update(String columnName, Long value) {
        this.sqlBuilder.bind("updt_" + columnName, value);
        this.columnsToUpdate.add(columnName);
        return this;
    }

    public Insert update(String columnName, Double value) {
        this.sqlBuilder.bind("updt_" + columnName, value);
        this.columnsToUpdate.add(columnName);
        return this;
    }

    public Insert update(String columnName, LocalDate value) {
        this.sqlBuilder.bind("updt_" + columnName, value);
        this.columnsToUpdate.add(columnName);
        return this;
    }

    public Insert update(String columnName, Instant value) {
        this.sqlBuilder.bind("updt_" + columnName, value);
        this.columnsToUpdate.add(columnName);
        return this;
    }

    public Sql build() {
        StringBuilder columns = new StringBuilder();
        StringBuilder values = new StringBuilder();

        for (String columnName : this.columnNames) {
            columns.append(columnName)
                .append(", ");

            values.append("${")
                .append(columnName)
                .append("}, ");
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

        if (this.onDuplicateKey) {
            sql.append(" ON DUPLICATE KEY UPDATE ");

            StringBuilder updateBuilder = new StringBuilder();

            for (String columnName : this.columnsToUpdate) {
                updateBuilder.append(columnName)
                    .append(" = ${updt_")
                    .append(columnName)
                    .append("}, ");
            }

            updateBuilder.delete(updateBuilder.length() - 2, updateBuilder.length());
            sql.append(updateBuilder);
        }

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
