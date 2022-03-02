drop table if exists posting;

create table posting
(
    posting_num      bigint                                  not null
        primary key,
    register_user_id varchar(100)                            not null,
    url              varchar(100)                            not null,
    title            varchar(300)                            not null,
    post_date        varchar(100)                            not null,
    image_count      int         default 0                   null,
    video_count      int         default 0                   null,
    word_count       int         default 0                   null,
    audit_id         varchar(30) default 'postingController' null,
    audit_at         datetime    default CURRENT_TIMESTAMP   null,
    is_deleted       tinyint(1)  default 0                   null,
    created_at       datetime    default CURRENT_TIMESTAMP   null
);

drop table if exists tag;

create table tag
(
    tag_num     int auto_increment
        primary key,
    posting_num bigint                                  null,
    video_num   bigint                                  null,
    tag_type    varchar(10)                             not null,
    content     varchar(300)                            not null,
    audit_id    varchar(30) default 'postingController' not null,
    audit_at    datetime    default CURRENT_TIMESTAMP   null,
    is_deleted  tinyint(1)  default 0                   not null,
    created_at  datetime    default CURRENT_TIMESTAMP   null
);

drop table if exists user_score_history;
create table user_score_history
(
    standard_date       varchar(8)  default (date_format(now(), _utf8mb4'%Y%m%d')) not null,
    user_id             varchar(100)                                               not null,
    total_posting_count bigint      default 0                                      not null,
    month_posting_count bigint      default 0                                      not null,
    day_visitor         bigint      default 0                                      not null,
    total_visitor       bigint      default 0                                      not null,
    neighbor_count      bigint      default 0                                      not null,
    tag_count           bigint      default 0                                      not null,
    total_score         double      default 0                                      not null,
    audit_id            varchar(50) default 'UserController'                       not null,
    audit_at            datetime    default CURRENT_TIMESTAMP                      null,
    is_deleted          tinyint(1)  default 0                                      not null,
    created_at          datetime    default CURRENT_TIMESTAMP                      null,
    primary key (standard_date, user_id)
);

drop table if exists video;
create table video
(
    posting_num bigint                                  not null,
    video_num   int auto_increment
        primary key,
    title       varchar(300)                            not null,
    description varchar(3000)                           null,
    audit_id    varchar(30) default 'postingController' not null,
    audit_at    datetime    default CURRENT_TIMESTAMP   null,
    is_deleted  tinyint(1)  default 0                   not null,
    created_at  datetime    default CURRENT_TIMESTAMP   null
);

