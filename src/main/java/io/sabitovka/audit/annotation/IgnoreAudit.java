package io.sabitovka.audit.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Используется для того, чтобы указать, что аннотированный параметр не должен отображаться в логе аудита.
 * Например, переданный пароль пользователя не желательно сохранять в аудите
 */
@Target({ ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface IgnoreAudit {
}
