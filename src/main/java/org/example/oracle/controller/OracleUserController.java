//package org.example.oracle.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.example.oracle.service.OracleUserService;
//
//
//import java.util.List;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api/oracle-users")
//public class OracleUserController {
//
//    @Autowired
//    private OracleUserService oracleUserService;
//
//    @PostMapping
//    public ResponseEntity<String> createUser(
//            @RequestParam String username,
//            @RequestParam String password,
//            @RequestParam String tablespace,
//            @RequestParam int quota) {
//        oracleUserService.createUser(username, password, tablespace, quota);
//        return ResponseEntity.ok("User created successfully");
//    }
//
//    @GetMapping
//    public ResponseEntity<List<Map<String, Object>>> listUsers() {
//        List<Map<String, Object>> users = oracleUserService.listUsers();
//        return ResponseEntity.ok(users);
//    }
//
//
//
//    @PutMapping("/{username}/quota")
//    public ResponseEntity<String> updateUserQuota(
//            @PathVariable String username,
//            @RequestParam int quota,
//            @RequestParam String tablespace) {
//        if (!username.startsWith("C##")) {
//            username = "C##" + username;
//        }
//        oracleUserService.updateUserQuota(username, quota, tablespace);
//        return ResponseEntity.ok("User quota updated successfully");
//    }
//    @DeleteMapping("/{username}")
//    public ResponseEntity<String> deleteUser(@PathVariable String username) {
//        oracleUserService.deleteUser(username);
//        return ResponseEntity.ok("User deleted successfully");
//    }
//    @PutMapping("/{username}/password")
//    public ResponseEntity<String> updateUserPassword(
//            @PathVariable String username,
//            @RequestParam String newPassword) {
//        oracleUserService.updateUserPassword(username, newPassword);
//        return ResponseEntity.ok("User password updated successfully");
//    }
//
//    // Update user default tablespace
//    @PutMapping("/{username}/tablespace")
//    public ResponseEntity<String> updateUserTablespace(
//            @PathVariable String username,
//            @RequestParam String tablespace) {
//        oracleUserService.updateUserTablespace(username, tablespace);
//        return ResponseEntity.ok("User default tablespace updated successfully");
//    }
//
//    // Lock user account
//    @PutMapping("/{username}/lock")
//    public ResponseEntity<String> lockUserAccount(@PathVariable String username) {
//        oracleUserService.lockUserAccount(username);
//        return ResponseEntity.ok("User account locked successfully");
//    }
//
//    // Unlock user account
//    @PutMapping("/{username}/unlock")
//    public ResponseEntity<String> unlockUserAccount(@PathVariable String username) {
//        oracleUserService.unlockUserAccount(username);
//        return ResponseEntity.ok("User account unlocked successfully");
//    }
//    @PutMapping("/{username}/role")
//    public ResponseEntity<String> assignRoleToUser(
//            @PathVariable String username,
//            @RequestParam String role) {
//        if (!username.startsWith("C##")) {
//            username = "C##" + username;
//        }
//        oracleUserService.assignRoleToUser(username, role,true);
//        return ResponseEntity.ok("Role assigned successfully");
//    }
//
//    // Grant privilege to user
//    @PutMapping("/{username}/privilege")
//    public ResponseEntity<String> grantPrivilegeToUser(
//            @PathVariable String username,
//            @RequestParam String privilege,
//            @RequestParam String tableName) {
//        if (!username.startsWith("C##")) {
//            username = "C##" + username;
//        }
//        oracleUserService.grantPrivilegeToUser(username, privilege, tableName,true);
//        return ResponseEntity.ok("Privilege granted successfully");
//    }
//    @GetMapping("/{username}/roles")
//    public ResponseEntity<List<String>> getUserRoles(@PathVariable String username) {
//        if (!username.startsWith("C##")) {
//            username = "C##" + username;
//        }
//        List<String> roles = oracleUserService.getUserRoles(username);
//        return ResponseEntity.ok(roles);
//    }
//
//    // Get privileges granted to a user
//    @GetMapping("/{username}/privileges")
//    public ResponseEntity<List<Map<String, Object>>> getUserPrivileges(@PathVariable String username) {
//        if (!username.startsWith("C##")) {
//            username = "C##" + username;
//        }
//        List<Map<String, Object>> privileges = oracleUserService.getUserPrivileges(username);
//        return ResponseEntity.ok(privileges);
//    }
//    @PutMapping("/password/min-length")
//    public ResponseEntity<String> changePasswordMinLength(@RequestParam int minLength) {
//        oracleUserService.changePasswordMinLength(minLength);
//        return ResponseEntity.ok("Password minimum length updated successfully");
//    }
//
//    // Endpoint to change password expiration period
//    @PutMapping("/password/expiration")
//    public ResponseEntity<String> changePasswordExpiration(@RequestParam int expirationDays) {
//        oracleUserService.changePasswordExpiration(expirationDays);
//        return ResponseEntity.ok("Password expiration updated successfully");
//    }
//
//    // Endpoint to change password lock time
//    @PutMapping("/password/lock-time")
//    public ResponseEntity<String> changePasswordLockTime(@RequestParam int lockTime) {
//        oracleUserService.changePasswordLockTime(lockTime);
//        return ResponseEntity.ok("Password lock time updated successfully");
//    }
//}

package org.example.oracle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.example.oracle.service.OracleUserService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/oracle-users")
public class OracleUserController {

    @Autowired
    private OracleUserService oracleUserService;

//    @GetMapping
//    public String listUsers(Model model) {
//        List<Map<String, Object>> users = oracleUserService.listUsers();
//        model.addAttribute("users", users);
//        return "user-list";
//    }
    @GetMapping
    public String listUsers(Model model) {
        List<Map<String, Object>> users = oracleUserService.listUsers();
        Map<String, String> settings = oracleUserService.getPasswordAndLockSettings();

        model.addAttribute("users", users);
        model.addAttribute("settings", settings); // Add settings to the model
        return "user-list";
    }

    @GetMapping("/create")
    public String createUserForm() {
        return "user-create";
    }

    @PostMapping("/create")
    public String createUser(@RequestParam String username, @RequestParam String password,
                             @RequestParam String tablespace, @RequestParam int quota) {
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
        System.out.println("Tablespace: " + tablespace);
        System.out.println("Quota: " + quota); // Ajoutez cette ligne

//        if (!username.startsWith("C##")) {
//            username = "C##" + username;
//        }
        oracleUserService.createUser(username, password, tablespace, quota);
        return "redirect:/oracle-users";
    }




//    @PostMapping("/update/{username}")
//    public String updateUserQuota(@PathVariable String username, @RequestParam int quota,
//                             @RequestParam String tablespace, RedirectAttributes redirectAttributes) {
//        if (!username.startsWith("C##")) {
//            username = "C##" + username;
//        }
//        try {
//            oracleUserService.updateUserQuota(username, quota, tablespace);
//        } catch (IllegalArgumentException e) {
//            redirectAttributes.addFlashAttribute("error", e.getMessage());
//            return "redirect:/oracle-users/update/" + username;
//        }
//        return "redirect:/oracle-users";
//    }
//
//    @PostMapping("/update-tablespace/{username}")
//    public String updateUserTablespace(@PathVariable String username, @RequestParam String tablespace,
//                                       RedirectAttributes redirectAttributes) {
//        if (!username.startsWith("C##")) {
//            username = "C##" + username;
//        }
//        try {
//            oracleUserService.updateUserTablespace(username, tablespace);
//        } catch (IllegalArgumentException e) {
//            redirectAttributes.addFlashAttribute("error", e.getMessage());
//            return "redirect:/oracle-users/update/" + username;  // Redirect back to update page
//        }
//        return "redirect:/oracle-users";  // Redirect to the main users list
//    }
//
//
//

    @GetMapping("/delete/{username}")
    public String deleteUser(@PathVariable String username) {
        if (!username.startsWith("C##")) {
            username = "C##" + username;
        }
        oracleUserService.deleteUser(username);
        return "redirect:/oracle-users";
    }
@GetMapping("/user-actions")
public String userActions() {
    return "user-actions";
}

    // Mapping for changing quota
    @PostMapping("/change-quota")
    public String changeQuota(@RequestParam String username, @RequestParam int quota,
                              @RequestParam String tablespace) {
        oracleUserService.updateUserQuota(username, quota, tablespace);
        return "redirect:/oracle-users";
    }

    // Mapping for changing tablespace
    @PostMapping("/change-tablespace")
    public String changeTablespace(@RequestParam String username, @RequestParam String tablespace) {
        oracleUserService.updateUserTablespace(username, tablespace);
        return "redirect:/oracle-users";
    }

    // Mapping for granting a role
    @PostMapping("/grant-role")
    public String grantRole(@RequestParam String username, @RequestParam String role,
                            @RequestParam(required = false, defaultValue = "false") boolean adminOption) {
        oracleUserService.assignRoleToUser(username, role, adminOption);
        return "redirect:/oracle-users";
    }

    // Mapping for locking account
    @PostMapping("/lock-account")
    public String lockAccount(@RequestParam String username) {
        oracleUserService.lockUserAccount(username);
        return "redirect:/oracle-users";
    }

    // Mapping for unlocking account
    @PostMapping("/unlock-account")
    public String unlockAccount(@RequestParam String username) {
        oracleUserService.unlockUserAccount(username);
        return "redirect:/oracle-users";
    }

    // Mapping for changing password expiration
    @PostMapping("/change-password-expiration")
    public String changePasswordExpiration(@RequestParam int expirationDays) {
        oracleUserService.changePasswordExpiration(expirationDays);
        return "redirect:/oracle-users/user-actions";
    }

    // Mapping for changing lock time
    @PostMapping("/change-lock-time")
    public String changeLockTime(@RequestParam int lockTime) {
        oracleUserService.changePasswordLockTime(lockTime);
        return "redirect:/oracle-users/user-actions";
    }

}
