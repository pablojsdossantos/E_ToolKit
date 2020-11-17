/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package etk.jdbc.mapping;

import java.sql.Types;

/**
 *
 * @author Pablo JS dos Santos
 */
public enum ColumnType {
    CHAR(Types.CHAR),
    STRING(Types.VARCHAR),
    INTEGER(Types.INTEGER),
    DOUBLE(Types.DOUBLE),
    LONG(Types.BIGINT),
    DATE(Types.DATE),
    TIME(Types.TIME),
    TIMESTAMP(Types.TIMESTAMP),
    BOOLEAN(Types.BOOLEAN),
    BLOB(Types.BLOB),
    UUID_TXT(Types.VARCHAR),
    UUID_BIN(Types.BINARY),
    ENUM(Types.VARCHAR);

    private int sqlType;

    private ColumnType(int sqlType) {
        this.sqlType = sqlType;
    }

    public int getSqlType() {
        return sqlType;
    }

}
