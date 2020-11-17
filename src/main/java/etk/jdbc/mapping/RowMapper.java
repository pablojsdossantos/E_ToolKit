package etk.jdbc.mapping;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author Pablo JS dos Santos
 */
public interface RowMapper<T> {
    public static RowMapper<Boolean> BOOLEAN_MAPPER = result -> result.getBoolean();
    public static RowMapper<Integer> INT_MAPPER = result -> result.getInt();
    public static RowMapper<String> STRING_MAPPER = result -> result.getString();
    public static RowMapper<Long> LONG_MAPPER = result -> result.getLong();
    public static RowMapper<Double> DOUBLE_MAPPER = result -> result.getDouble();
    public static RowMapper<BigDecimal> BIG_DECIMAL_MAPPER = result -> result.getBigDecimal();
    public static RowMapper<Character> CHARACTER_MAPPER = result -> result.getCharacter();
    public static RowMapper<LocalDate> LOCAL_DATE_MAPPER = result -> result.getLocalDate();
    public static RowMapper<LocalDateTime> LOCAL_DATE_TIME_MAPPER = result -> result.getLocalDateTime();
    public static RowMapper<Instant> INSTANT_MAPPER = result -> result.getInstant();

    public T map(DbResult resultSet) throws SQLException;
}
