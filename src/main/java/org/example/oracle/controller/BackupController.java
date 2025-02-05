package org.example.oracle.controller;

import org.example.oracle.service.BackupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/backup")
public class BackupController {

    private final BackupService backupService;

    @Autowired
    public BackupController(BackupService backupService) {
        this.backupService = backupService;
    }

    // Endpoint pour exécuter une sauvegarde
    @PostMapping("/run")
    public String runBackup(@RequestParam(name = "incremental", defaultValue = "true") boolean isIncremental,
                            RedirectAttributes redirectAttributes) {
        String result = backupService.runBackup(isIncremental);
        redirectAttributes.addFlashAttribute("message", result);
        return "redirect:/backup";
    }

    // Endpoint pour récupérer l'historique des sauvegardes
    @GetMapping("/history")
    public String getBackupHistory(Model model) {
        String history = backupService.getBackupHistory();
        model.addAttribute("history", history);
        return "backup-history"; // Affiche la page backup-history.html
    }

    // Endpoint pour restaurer la base de données à une date spécifique
    @PostMapping("/restore")
    public String restoreDatabase(@RequestParam String restoreDate, RedirectAttributes redirectAttributes) {
        String result = backupService.restoreDatabaseToDate(restoreDate);
        redirectAttributes.addFlashAttribute("message", result);
        return "redirect:/backup";
    }

    // Page d'accueil pour afficher l'état de la sauvegarde
    @GetMapping
    public String showBackupPage(Model model) {
        return "backup"; // Affiche la page backup.html
    }
}
