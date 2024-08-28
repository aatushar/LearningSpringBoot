package com.khajana.setting.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class CodeGenerationService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String generateUniqueCode(String table, String column, int length, String prefix) {
        if (!isTableColumnExist(table, column)) {
            throw new IllegalArgumentException("Table '" + table + "' or column '" + column + "' does not exist.");
        }

        Optional<String> lastCode = getLastCode(table, column);
        int lastCodeNumber = lastCode.map(code -> Integer.parseInt(code.substring(prefix.length() + 8))).orElse(0);
        int newCodeNumber = lastCodeNumber + 1;
        String newCode = String.format("%0" + length + "d", newCodeNumber);

        while (isCodeExists(table, column, prefix + getCurrentDate() + '-' + newCode)) {
            newCodeNumber++;
            newCode = String.format("%0" + length + "d", newCodeNumber);
        }

        return prefix + getCurrentDate() + '-' + newCode;
    }

    private boolean isTableColumnExist(String table, String column) {
        return jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM table_name = ? AND column_name = ?",
                Integer.class, table, column) > 0;
    }

    private Optional<String> getLastCode(String table, String column) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(
                "SELECT " + column + " FROM " + table + " ORDER BY " + column + " DESC LIMIT 1",
                String.class));
    }

    private boolean isCodeExists(String table, String column, String code) {
        return jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM " + table + " WHERE " + column + " = ?", Integer.class, code) > 0;
    }

    private String getCurrentDate() {
        return DateTimeFormatter.ofPattern("yyMMdd").format(LocalDate.now());
    }
}
