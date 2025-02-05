package org.example.oracle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AuditLogService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> getAuditLogsByUser(String username) {
        String sql = """
        SELECT SESSIONID, DBUSERNAME, ACTION_NAME, OBJECT_NAME, EVENT_TIMESTAMP
        FROM UNIFIED_AUDIT_TRAIL
        WHERE DBUSERNAME = ?
        ORDER BY EVENT_TIMESTAMP DESC
    """;
        return jdbcTemplate.queryForList(sql, username);
    }

}
