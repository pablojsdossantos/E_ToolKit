/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package etk.jdbc.connection;

import etk.data.Pair;
import etk.jdbc.exceptions.PersistenceException;
import etk.jdbc.mapping.ColumnType;
import etk.jdbc.mapping.Query;
import etk.jdbc.mapping.ResultSetEx;
import etk.jdbc.mapping.RowMapper;
import etk.jdbc.mapping.Sql;
import etk.jdbc.mapping.Update;
import java.io.Closeable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Pablo JS dos Santos
 */
public class Session implements Closeable {
    private Connection connection;

    public Session(Connection connection) {
        this.connection = connection;
    }

    public <T> Query<T> createQuery(String query) {
        return new Query<>(this, query);
    }

    public Update createStatement(String statement) {
        return new Update(this, statement);
    }

    public void execute(String sql) {
        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            statement.execute();
        } catch (SQLException ex) {
            throw new PersistenceException("EJCE40", ex.getMessage(), ex);
        }
    }

    public long executeUpdate(Sql sql, boolean autoIncrement) {
        try (PreparedStatement statement = this.preparedStatement(sql, autoIncrement)) {
            int affectedRowCount = statement.executeUpdate();

            if (autoIncrement) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                }

                return 0;
            }

            return affectedRowCount;
        } catch (SQLException ex) {
            throw new PersistenceException("EJCE59", ex.getMessage(), ex);
        }
    }

    public <T> List<T> executeQuery(Sql sql, RowMapper<T> mapper) {
        try (PreparedStatement statement = this.preparedStatement(sql, false)) {
            ResultSet resultSet = statement.executeQuery();
            ResultSetEx wrapper = new ResultSetEx(resultSet);

            List<T> rows = new ArrayList<>();

            while (resultSet.next()) {
                rows.add(mapper.map(wrapper));
            }

            return rows;
        } catch (SQLException ex) {
            throw new PersistenceException("EJCEQ75", ex.getMessage(), ex);
        }
    }

    private PreparedStatement preparedStatement(Sql sql, boolean autoIncrement) throws SQLException {
        PreparedStatement statement = autoIncrement
            ? this.connection.prepareStatement(sql.getCommand(), Statement.RETURN_GENERATED_KEYS)
            : this.connection.prepareStatement(sql.getCommand());

        List<Pair<ColumnType, Object>> parameters = sql.getParameters();

        for (int i = 0; i < parameters.size(); i++) {
            this.setParameter(statement, parameters.get(i), i + 1);
        }

        return statement;
    }

    private void setParameter(PreparedStatement statement, Pair<ColumnType, Object> parameter, int index) throws SQLException {
        if (parameter.getRight() == null) {
            statement.setNull(index, parameter.getLeft().getSqlType());
            return;
        }

        switch (parameter.getLeft()) {
            case STRING:
            case UUID:
            case CHAR:
                statement.setString(index, parameter.getRight().toString());
                break;

            case INTEGER:
                statement.setInt(index, (int) parameter.getRight());
                break;

            case DOUBLE:
                statement.setDouble(index, (double) parameter.getRight());
                break;

            case LONG:
                statement.setLong(index, (long) parameter.getRight());
                break;

            case DATE:
                this.setDate(statement, index, parameter.getRight());
                break;

            case TIME:
                statement.setTime(index, new Time(((java.util.Date) parameter.getRight()).getTime()));
                break;

            case TIMESTAMP:
                statement.setTimestamp(index, new Timestamp(((java.util.Date) parameter.getRight()).getTime()));
                break;

            case BOOLEAN:
                statement.setBoolean(index, (boolean) parameter.getRight());
                break;

            case BLOB:
                statement.setBytes(index, (byte[]) parameter.getRight());
                break;

            default:
                statement.setObject(index, parameter.getRight(), parameter.getLeft().getSqlType());
                break;
        }
    }

    private void setDate(PreparedStatement statement, int index, Object value) throws SQLException {
        if (value instanceof Date) {
            Date sqlDate = (Date) value;
            statement.setDate(index, sqlDate);
        }

        if (value instanceof LocalDate) {
            LocalDate localDate = (LocalDate) value;
            Date sqlDate = Date.valueOf(localDate);
            statement.setDate(index, sqlDate);
            return;
        }

        if (value instanceof java.util.Date) {
            java.util.Date utilDate = (java.util.Date) value;
            Date sqlDate = new Date(utilDate.getTime());
            statement.setDate(index, sqlDate);
            return;
        }

        throw new PersistenceException("EJCSD182", "Unsupportd Date Value", null);
    }

    @Override
    public void close() {
        try {
            this.connection.close();
        } catch (SQLException ex) {
            throw new PersistenceException("EJCC165", ex.getMessage(), ex);
        }
    }
}