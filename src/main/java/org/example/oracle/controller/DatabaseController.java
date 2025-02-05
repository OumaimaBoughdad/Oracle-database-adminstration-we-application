package org.example.oracle.controller;

import org.example.oracle.service.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/database")
public class DatabaseController {

    @Autowired
    private DatabaseService databaseService;

    // Afficher le formulaire pour récupérer les statistiques d'index
    @GetMapping("/index-stats")
    public String showIndexStatsForm() {
        return "index-stats"; // Affiche le formulaire pour les stats d'index
    }

    // Récupérer les statistiques de l'index
    @PostMapping("/index-stats")
    public String gatherIndexStats(@RequestParam String owner, @RequestParam String indexName, Model model) {
        // Récupérer les statistiques
        Map<String, Object> stats = databaseService.getIndexStats(owner, indexName);
        model.addAttribute("stats", stats);
        model.addAttribute("owner", owner);
        model.addAttribute("indexName", indexName);

        return "index-stats";  // Affiche la page avec les statistiques d'index
    }

    // Mettre à jour la date d'analyse de l'index
    @PostMapping("/update-index-stats")
    public String updateIndexStats(@RequestParam String owner, @RequestParam String indexName, Model model) {
        // Lancer la procédure pour mettre à jour les statistiques de l'index
        databaseService.gatherIndexStats(owner, indexName);

        // Récupérer les nouvelles statistiques
        Map<String, Object> stats = databaseService.getIndexStats(owner, indexName);
        model.addAttribute("stats", stats);
        model.addAttribute("owner", owner);
        model.addAttribute("indexName", indexName);
        model.addAttribute("message", "Index stats updated successfully.");

        return "index-stats";  // Affiche la page avec les statistiques d'index mises à jour
    }

    // Afficher le formulaire pour récupérer les statistiques de table
    @GetMapping("/table-stats")
    public String showTableStatsForm() {
        return "table-stats"; // Affiche le formulaire pour les stats de table
    }

    // Récupérer les statistiques de la table
    @PostMapping("/table-stats")
    public String gatherTableStats(@RequestParam String owner, @RequestParam String tableName, Model model) {
        // Récupérer les statistiques
        Map<String, Object> stats = databaseService.getTableStats(owner, tableName);
        model.addAttribute("stats", stats);
        model.addAttribute("owner", owner);
        model.addAttribute("tableName", tableName);

        return "table-stats";  // Affiche la page avec les statistiques de table
    }

    // Mettre à jour la date d'analyse de la table
    @PostMapping("/update-table-stats")
    public String updateTableStats(@RequestParam String owner, @RequestParam String tableName, Model model) {
        // Lancer la procédure pour mettre à jour les statistiques de la table
        databaseService.gatherTableStats(owner, tableName);

        // Récupérer les nouvelles statistiques
        Map<String, Object> stats = databaseService.getTableStats(owner, tableName);
        model.addAttribute("stats", stats);
        model.addAttribute("owner", owner);
        model.addAttribute("tableName", tableName);
        model.addAttribute("message", "Table stats updated successfully.");

        return "table-stats";  // Affiche la page avec les statistiques de table mises à jour
    }

}
