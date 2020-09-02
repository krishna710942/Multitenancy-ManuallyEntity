package com.oodles.green.security;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.oodles.green.master.config.DBContextHolder;
import com.oodles.green.master.repository.OrganisationDatabaseDetailRepository;

import java.util.Map;

@Aspect
@Component
public class RequestAuthorizationIntercept {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private OrganisationDatabaseDetailRepository repo;

    @Around("@annotation(com.oodles.green.security.RequestAuthorization)")
    public Object checkPermission(ProceedingJoinPoint pjp) throws Throwable {
        UserTenantInformation tenantInformation = applicationContext.getBean(UserTenantInformation.class);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (null == userDetails) {
            throw new RuntimeException("Access is Denied. Please again login or  contact service provider");
        }
        Map<String, String> map = tenantInformation.getMap();
        String tenantName = map.get(userDetails.getUsername());
        if (tenantName != null && tenantName.equals(DBContextHolder.getCurrentDb())) {
            return pjp.proceed();
        }
        throw new RuntimeException("Access is Denied. Please again login or contact service provider");
    }
}
