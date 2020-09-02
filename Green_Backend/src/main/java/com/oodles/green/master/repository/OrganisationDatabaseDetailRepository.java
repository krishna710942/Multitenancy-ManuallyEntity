package com.oodles.green.master.repository;

import com.oodles.green.master.entity.OrganisationDatabaseDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/*
Author : krishna pratap singh
 */

@Repository
public interface OrganisationDatabaseDetailRepository extends JpaRepository<OrganisationDatabaseDetail, Serializable> {
    OrganisationDatabaseDetail findByDataBaseName(String replaceAll);

    OrganisationDatabaseDetail findByOrganisationEmailId(String organisationEmail);
}
