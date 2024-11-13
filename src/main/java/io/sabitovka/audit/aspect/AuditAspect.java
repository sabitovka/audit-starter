package io.sabitovka.audit.aspect;

import io.sabitovka.audit.annotation.Audit;
import io.sabitovka.audit.annotation.IgnoreAudit;
import io.sabitovka.audit.model.AuditRecord;
import io.sabitovka.audit.service.AuditService;
import io.sabitovka.audit.service.AuditUserService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Аспект, перехватывающий все методы с аннотацией {@link Audit}.
 * Сохраняет информацию аудита в базу данных
 */
@Aspect
@RequiredArgsConstructor
public class AuditAspect {
    private final AuditService auditService;
    private final AuditUserService auditUserService;

    @Pointcut("@annotation(io.sabitovka.audit.annotation.Audit)")
    public void annotatedByAudit() {}

    @Around("annotatedByAudit()")
    public Object logAuditAction(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = joinPoint.getTarget()
                .getClass()
                .getMethod(signature.getName(), signature.getParameterTypes());
        Audit audit = method.getAnnotation(Audit.class);

        Object result = joinPoint.proceed();

        String action = audit.action().isEmpty() ? signature.getMethod().getName() : audit.action();
        String username = auditUserService.getUsername();
        String ip = auditUserService.getIp();
        LocalDateTime localDateTime = LocalDateTime.now();

        Parameter[] parameters = signature.getMethod().getParameters();
        Object[] joinPointArgs = joinPoint.getArgs();
        List<String> args = new ArrayList<>();
        for (int i = 0; i < joinPointArgs.length; i++) {
            String arg = !parameters[i].isAnnotationPresent(IgnoreAudit.class) ? joinPointArgs[i].toString() : "*";
            args.add(arg);
        }

        AuditRecord auditRecord = new AuditRecord();
        auditRecord.setUsername(username);
        auditRecord.setIp(ip);
        auditRecord.setTimestamp(localDateTime);
        auditRecord.setArguments(args.toString());
        auditRecord.setAction(action);

        auditService.saveAudit(auditRecord);

        return result;
    }
}
