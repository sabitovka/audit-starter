package io.sabitovka.audit.service;

import org.springframework.stereotype.Component;

/**
 * Интерфейс сервиса для получения информации о пользователе.
 * Может быть реализован для кастомизации аудита
 */
@Component
public interface AuditUserService {
    default String getUsername() {
        return "";
    }
    default String getIp() {
        return "";
    }
}
