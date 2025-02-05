//package com.oodles.green.master.service;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.oodles.green.master.entity.MasterTenant;
//import com.oodles.green.master.repository.MasterTenantRepository;
//
//@Service
//public class MasterTenantService {
//    private static final Logger LOG = LoggerFactory.getLogger(MasterTenantService.class);
//
//    @Autowired
//    MasterTenantRepository masterTenantRepository;
//
//    public MasterTenant findByClientId(Integer clientId) {
//        LOG.info("findByClientId() method call...");
//        return masterTenantRepository.findByTenantClientId(clientId);
//    }
//}
//