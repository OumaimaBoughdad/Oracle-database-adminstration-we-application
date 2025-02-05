package org.example.oracle.controller;

import org.example.oracle.service.AuditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/audit-logs")
public class AuditLogController {

    private final AuditLogService auditLogService;

    @Autowired
    public AuditLogController(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }

    // Afficher le formulaire de recherche des logs
    @GetMapping("/search")
    public String showAuditLogSearchForm() {
        return "audit-log-search"; // Correspond au fichier audit-log-search.html
    }

    // Soumettre la recherche des logs d'audit
    @PostMapping("/search")
    public String searchAuditLogs(
            @RequestParam("user") String user,
            RedirectAttributes redirectAttributes) {
        try {
            var logs = auditLogService.getAuditLogsByUser(user);
            if (logs.isEmpty()) {
                redirectAttributes.addFlashAttribute("message", "Aucun log trouvé pour cet utilisateur.");
            } else {
                redirectAttributes.addFlashAttribute("message", "Logs d'audit récupérés avec succès.");
                redirectAttributes.addFlashAttribute("logs", logs);
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Erreur lors de la récupération des logs.");
        }
        return "redirect:/audit-logs"; // Redirection vers la page d'affichage
    }

    // Page principale des logs d'audit
    @GetMapping
    public String showAuditLogPage(Model model) {
        return "audit-log"; // Correspond au fichier audit-log.html
    }
}
