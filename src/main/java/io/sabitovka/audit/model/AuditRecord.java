package io.sabitovka.audit.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * БД-сущность записи аудита
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuditRecord {
    Long id;
    String username;
    String ip;
    String action;
    String arguments;
    LocalDateTime timestamp;
}
