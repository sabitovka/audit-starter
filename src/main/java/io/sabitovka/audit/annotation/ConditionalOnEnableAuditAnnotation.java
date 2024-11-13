package io.sabitovka.audit.annotation;

import io.sabitovka.audit.condition.OnEnableAuditAnnotation;
import org.springframework.context.annotation.Conditional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Условие для подключения бина при наличии в контексте аннотации @{@link EnableAudit}
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Conditional(OnEnableAuditAnnotation.class)
public @interface ConditionalOnEnableAuditAnnotation {
}
