package ru.lanit.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.lanit.web.services.AdminConsoleService;

@RestController
@RequestMapping("/clear")
public class AdminConsoleController {

    @Autowired
    AdminConsoleService adminConsoleService;

    @GetMapping
    public void clearAllTables() {
        adminConsoleService.clearAllTables();
    }
}
