package com.example.mtstest.services;

import com.example.mtstest.dto.Tariff;
import org.springframework.stereotype.Service;
import org.sqlite.SQLiteConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Service
public class DBService {

    private static final String DATABASE_URL = "jdbc:sqlite:db.db";

    public Connection getConnection() throws SQLException {
        SQLiteConfig config = new SQLiteConfig();
        config.enforceForeignKeys(true);
        return DriverManager.getConnection(DATABASE_URL, config.toProperties());
    }

    public void addNewTariff(Tariff tariff) {
        String sql = String.format("INSERT INTO data(tariff_name, description, options, price) VALUES('%s', '%s', '%s', %s)"
                , tariff.getName(), tariff.getDescription(), tariff.getOptions(), tariff.getPrice());

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();
        } catch (SQLException e) {

        }
    }
}
