package io.sabitovka.audit.repository;

import io.sabitovka.audit.model.AuditRecord;

/**
 * Репозиторий для доступа записей аудита из БД
 */
public interface AuditRepository {
    /**
     * Сохраняет новую запись аудита
     * @param auditRecord Запись аудита для сохранения
     */
    void create(AuditRecord auditRecord);
}
