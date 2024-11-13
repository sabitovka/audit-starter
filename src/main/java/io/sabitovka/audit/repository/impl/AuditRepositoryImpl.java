package io.sabitovka.audit.repository.impl;

import io.sabitovka.audit.model.AuditRecord;
import io.sabitovka.audit.repository.AuditRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий аудита действий пользователя. Реализует интерфейс {@link AuditRepository}
 */
@Repository
@RequiredArgsConstructor
public class AuditRepositoryImpl implements AuditRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void create(AuditRecord auditRecord) {
        String sql = "insert into %s (username, ip, action, arguments, timestamp) values (?, ?, ?, ?, ?)".formatted("audit");
        Object[] args = new Object[]{
                auditRecord.getUsername(),
                auditRecord.getIp(),
                auditRecord.getAction(),
                auditRecord.getArguments(),
                auditRecord.getTimestamp()
        };
        jdbcTemplate.update(sql, args);
    }
}
