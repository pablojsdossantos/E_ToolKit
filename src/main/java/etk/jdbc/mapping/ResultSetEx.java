package etk.jdbc.mapping;

import etk.text.Strings;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 *
 * @author Pablo JS dos Santos
 */
public class ResultSetEx {
    private ResultSet resultSet;

    public ResultSetEx(ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    public String getString(String columnName) throws SQLException {
        return resultSet.getString(columnName);
    }

    public String getString() throws SQLException {
        return this.resultSet.getString(1);
    }

    public boolean getBoolean(String columnName) throws SQLException {
        return resultSet.getBoolean(columnName);
    }

    public Boolean getBooleanWrapper(String columnName) throws SQLException {
        return this.wrapper(this.getBoolean(columnName));
    }

    public Boolean getBoolean() throws SQLException {
        return this.wrapper(this.resultSet.getBoolean(1));
    }

    public short getShort(String columnName) throws SQLException {
        return resultSet.getShort(columnName);
    }

    public Short getShortWrapper(String columnName) throws SQLException {
        return this.wrapper(this.getShort(columnName));
    }

    public Short getShort() throws SQLException {
        return this.wrapper(this.resultSet.getShort(1));
    }

    public int getInt(String columnName) throws SQLException {
        return resultSet.getInt(columnName);
    }

    public Integer getIntWrapper(String columnName) throws SQLException {
        return this.wrapper(this.getInt(columnName));
    }

    public Integer getInt() throws SQLException {
        return this.wrapper(this.resultSet.getInt(1));
    }

    public long getLong(String columnName) throws SQLException {
        return resultSet.getLong(columnName);
    }

    public Long getLongWrapper(String columnName) throws SQLException {
        return this.wrapper(this.getLong(columnName));
    }

    public Long getLong() throws SQLException {
        return this.wrapper(this.resultSet.getLong(1));
    }

    public float getFloat(String columnName) throws SQLException {
        return resultSet.getFloat(columnName);
    }

    public Float getFloatWrapper(String columnName) throws SQLException {
        return this.wrapper(this.getFloat(columnName));
    }

    public Float getFloat() throws SQLException {
        return this.wrapper(this.resultSet.getFloat(1));
    }

    public double getDouble(String columnName) throws SQLException {
        return resultSet.getDouble(columnName);
    }

    public Double getDoubleWrapper(String columnName) throws SQLException {
        return this.wrapper(this.getDouble(columnName));
    }

    public Double getDouble() throws SQLException {
        return this.wrapper(this.resultSet.getDouble(1));
    }

    public BigDecimal getBigDecimal(String columnName) throws SQLException {
        return resultSet.getBigDecimal(columnName);
    }

    public BigDecimal getBigDecimal() throws SQLException {
        return this.resultSet.getBigDecimal(1);
    }

    public Character getCharacter() throws SQLException {
        return Optional.ofNullable(this.getString())
            .filter(str -> !str.isEmpty())
            .map(str -> str.charAt(0))
            .orElse(null);
    }

    public Character getCharacter(String columnName) throws SQLException {
        return Optional.ofNullable(this.getString(columnName))
            .filter(str -> !str.isEmpty())
            .map(str -> str.charAt(0))
            .orElse(null);
    }

    public <T> T getEnum(Class type) throws SQLException {
        String string = this.getString();

        if (!Strings.isValid(string)) {
            return null;
        }

        return (T) Enum.valueOf(type, string);
    }

    public <T> T getEnum(String columnName, Class type) throws SQLException {
        String string = this.getString(columnName);

        if (!Strings.isValid(string)) {
            return null;
        }

        return (T) Enum.valueOf(type, string);
    }

    public LocalDate getLocalDate() throws SQLException {
        return Optional.ofNullable(this.resultSet.getDate(1))
            .map(Date::toLocalDate)
            .orElse(null);
    }

    public LocalDate getLocalDate(String columnName) throws SQLException {
        return Optional.ofNullable(this.resultSet.getDate(columnName))
            .map(Date::toLocalDate)
            .orElse(null);
    }

    public LocalDateTime getLocalDateTime() throws SQLException {
        return Optional.ofNullable(this.resultSet.getTimestamp(1))
            .map(Timestamp::toLocalDateTime)
            .orElse(null);
    }

    public LocalDateTime getLocalDateTime(String columnName) throws SQLException {
        return Optional.ofNullable(this.resultSet.getTimestamp(columnName))
            .map(Timestamp::toLocalDateTime)
            .orElse(null);
    }

    public Instant getInstant() throws SQLException {
        return Optional.ofNullable(this.resultSet.getTimestamp(1))
            .map(Timestamp::toInstant)
            .orElse(null);
    }

    public Instant getInstant(String columnName) throws SQLException {
        return Optional.ofNullable(this.resultSet.getTimestamp(columnName))
            .map(Timestamp::toInstant)
            .orElse(null);
    }

    private <T> T wrapper(Object value) throws SQLException {
        return this.resultSet.wasNull() ? null : (T) value;
    }

}
