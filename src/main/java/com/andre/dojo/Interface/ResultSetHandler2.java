package com.andre.dojo.Interface;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface ResultSetHandler2<T> {
    List<T> handle(ResultSet rs) throws SQLException;
}
