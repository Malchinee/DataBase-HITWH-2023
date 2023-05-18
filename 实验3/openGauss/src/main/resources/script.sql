create table log
(
    log_id      int auto_increment
        primary key,
    email       varchar(255) null,
    last_update timestamp    null,
    content     text         null
);

create table comment
(
    comment_id int auto_increment
        primary key,
    log_id     int       null,
    comment    text      null,
    c_time     timestamp null,
    constraint comment_log_log_id_fk
        foreign key (log_id) references log (log_id)
);

create index log_id
    on comment (log_id);

create table reply
(
    reply_id int auto_increment
        primary key,
    reply    text         not null,
    r_time   timestamp    null,
    sender   varchar(255) null,
    receiver varchar(255) null
);

create table share
(
    share_id int auto_increment
        primary key,
    log_id   int       null,
    share    text      not null,
    s_time   timestamp null,
    constraint share_log_log_id_fk
        foreign key (log_id) references log (log_id)
);

create index log_id
    on share (log_id);

create table user
(
    email       varchar(255) not null
        primary key,
    name        varchar(255) null,
    gender      varchar(10)  null,
    birthday    date         null,
    address     varchar(255) null,
    password    varchar(255) not null,
    other_email varchar(255) null
);

create table edu_profile
(
    edu_id    int auto_increment
        primary key,
    email     varchar(255) null,
    school    varchar(255) null,
    degree    varchar(255) null,
    beginning date         null,
    ending    date         null,
    constraint edu_profile_ibfk_1
        foreign key (email) references user (email)
);

create index email
    on edu_profile (email);

create table friends_group
(
    email1     varchar(255) not null,
    email2     varchar(255) not null,
    group_name varchar(255) null,
    primary key (email1, email2),
    constraint friends_group_ibfk_1
        foreign key (email1) references user (email),
    constraint friends_group_ibfk_2
        foreign key (email2) references user (email)
);

create index email2
    on friends_group (email2);

create table work_profile
(
    work_id      int auto_increment
        primary key,
    email        varchar(255) null,
    organization varchar(255) null,
    position     varchar(255) null,
    beginning    date         null,
    ending       date         null,
    constraint work_profile_ibfk_1
        foreign key (email) references user (email)
);

create index email
    on work_profile (email);

create definer = malchinee@`%` view my_group as
select `social_network`.`friends_group`.`email1`     AS `email1`,
       `social_network`.`friends_group`.`email2`     AS `email2`,
       `social_network`.`friends_group`.`group_name` AS `group_name`
from `social_network`.`friends_group`
where (`social_network`.`friends_group`.`email1` = '1111');

