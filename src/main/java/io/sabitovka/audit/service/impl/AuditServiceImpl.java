package io.sabitovka.audit.service.impl;

import io.sabitovka.audit.model.AuditRecord;
import io.sabitovka.audit.repository.AuditRepository;
import io.sabitovka.audit.service.AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Сервис аудита. Реализует интерфейс {@link AuditService}
 */
@RequiredArgsConstructor
@Service
public class AuditServiceImpl implements AuditService {
    private final AuditRepository auditRepository;

    @Override
    public void saveAudit(AuditRecord auditRecord) {
        auditRepository.create(auditRecord);
    }
}
