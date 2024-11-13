package io.sabitovka.audit.service;

import io.sabitovka.audit.model.AuditRecord;

/**
 * Интерфейс сервиса для сохранения действий пользователя
 */
public interface AuditService {
    /**
     * Выполняет сохранение действий пользователя в БД
     * @param auditRecord - Информация о действиях пользователя
     */
    void saveAudit(AuditRecord auditRecord);
}
