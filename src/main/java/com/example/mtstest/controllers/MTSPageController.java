package com.example.mtstest.controllers;

import com.example.mtstest.Driver;
import com.example.mtstest.dto.Tariff;
import com.example.mtstest.services.DBService;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.util.HtmlUtils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MTSPageController {
    @Autowired
    private DBService dbService;

    @GetMapping("/main")
    public String getMainPage(Model model) {
        List<Tariff> tariffList = new ArrayList<>();
        try (Connection connection = dbService.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM data");
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String name = resultSet.getString("tariff_name");
                String options = resultSet.getString("options");
                String description = resultSet.getString("description");
                double price = resultSet.getDouble("price");

                Tariff tariff = new Tariff(name, options, description, price);
                tariffList.add(tariff);
            }
        } catch (SQLException e) {
            // Обработка ошибок
        }

        model.addAttribute("tariffList", tariffList);

        return "index";
    }

    @PostMapping("/parse")
    public String parseAndSaveData() throws IOException, SQLException {
        Driver driver = new Driver();
        for (Tariff tariff : driver.getTariffsList()) {
            dbService.addNewTariff(tariff);
        }

        return "redirect:/main";
    }
}
