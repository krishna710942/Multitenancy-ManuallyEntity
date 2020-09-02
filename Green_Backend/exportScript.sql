
    create table hibernate_sequence (
       next_val bigint
    ) engine=InnoDB;

    insert into hibernate_sequence values ( 1 );

    insert into hibernate_sequence values ( 1 );

    insert into hibernate_sequence values ( 1 );

    create table organisation_permission (
       org_information_company_id bigint not null,
        permissions_id bigint not null
    ) engine=InnoDB;

    create table OrgInformation (
       company_Id bigint not null auto_increment,
        chief_Name varchar(255) not null,
        chief_OrgUnit varchar(255) not null,
        companyName varchar(255) not null,
        cost_Center varchar(255) not null,
        dailyPlantSupplyVolume bigint,
        driver_capacity integer,
        end_Time time not null,
        file_name varchar(255),
        file_type varchar(255),
        image_url varchar(255),
        isActive tinyint(1) default 1,
        org_Address varchar(255),
        organistaion_email varchar(255),
        organisationExpiryDate datetime(6),
        organisationPassword varchar(255),
        personnelArea varchar(255) not null,
        start_Time time not null,
        orgunit_id_OrgUnit bigint,
        primary key (company_Id)
    ) engine=InnoDB;

    create table OrgUnit (
       id_OrgUnit bigint not null,
        org_Unit_Desc varchar(255) not null,
        org_Unit_Name varchar(255) not null,
        primary key (id_OrgUnit)
    ) engine=InnoDB;

    create table PermissionsSaved (
       permission_Id bigint not null,
        isRead bit,
        isWrite bit,
        permission_Name varchar(255) not null,
        special_Permission varchar(255) not null,
        type bit,
        primary key (permission_Id)
    ) engine=InnoDB;

    create table User (
       userId bigint not null,
        emailId varchar(255),
        fullName varchar(255),
        gender varchar(255),
        mail varchar(255),
        masterDbName varchar(255),
        password varchar(255),
        status varchar(255),
        userName varchar(255),
        primary key (userId)
    ) engine=InnoDB;

    alter table OrgInformation 
       add constraint UK_hwvp9w3g6x9bchm9nabi9vt1p unique (companyName);

    alter table OrgUnit 
       add constraint UK_ibi8enup0ds5sgia8c6saeb90 unique (org_Unit_Name);

    alter table PermissionsSaved 
       add constraint UK_ifuhlr905lqor2cqcnuet4n78 unique (permission_Name);

    alter table organisation_permission 
       add constraint FKelfvhbjd3xmgmr1n7fxqlb6ie 
       foreign key (permissions_id) 
       references PermissionsSaved (permission_Id);

    alter table organisation_permission 
       add constraint FK80domcfyi4ebl08mrh5gbdxnu 
       foreign key (org_information_company_id) 
       references OrgInformation (company_Id);

    alter table OrgInformation 
       add constraint FKelkipxs2ljafpx06pxndwy6k9 
       foreign key (orgunit_id_OrgUnit) 
       references OrgUnit (id_OrgUnit);
