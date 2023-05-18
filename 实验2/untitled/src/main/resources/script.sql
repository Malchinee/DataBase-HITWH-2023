create table department
(
    dname        varchar(50) null,
    dno          varchar(50) null,
    mgrssn       varchar(50) null,
    mgrstartdate varchar(50) null
);

create table employee
(
    ename    varchar(50) null,
    essn     varchar(50) null,
    address  varchar(50) null,
    salary   int         null,
    superssn varchar(50) null,
    dno      varchar(50) null
);

create table project
(
    pname varchar(50) null,
    pno   varchar(50) null,
    dno   varchar(50) null
);

create table works_on
(
    essn  varchar(50) null,
    pno   varchar(50) null,
    hours int         null
);

