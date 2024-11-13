package io.sabitovka.audit.condition;

import io.sabitovka.audit.annotation.EnableAudit;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * Условие подключения бина или конфигурации при наличии аннотации {@link EnableAudit}
 */
public class OnEnableAuditAnnotation implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        if (beanFactory == null) {
            return false;
        }

        String[] annotations = beanFactory.getBeanNamesForAnnotation(EnableAudit.class);
        return annotations.length > 0;
    }
}
