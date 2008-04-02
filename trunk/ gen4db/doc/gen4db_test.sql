/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2008-4-2 17:53:25                            */
/*==============================================================*/


drop table if exists t_order;

drop table if exists t_role;

drop table if exists t_test_many_pk;

drop table if exists t_test_pk_1;

drop table if exists t_test_pk_2;

drop table if exists t_user;

drop table if exists t_user_account;

drop table if exists t_user_role;

/*==============================================================*/
/* Table: t_order                                               */
/*==============================================================*/
create table t_order
(
   id                   bigint not null comment '订单标示',
   user_id              bigint not null comment '订单用户',
   name                 varchar(100) not null comment '订单名称',
   create_time          datetime not null comment '订单创建时间',
   primary key (id)
)
comment = "用户订单信息";

/*==============================================================*/
/* Table: t_role                                                */
/*==============================================================*/
create table t_role
(
   name                 varchar(100) not null comment '角色名称',
   description          varchar(200) comment '角色描述',
   state                int not null comment '角色状态',
   primary key (name)
)
comment = "角色信息";

/*==============================================================*/
/* Table: t_test_many_pk                                        */
/*==============================================================*/
create table t_test_many_pk
(
   test_pk_1_id         bigint not null comment '主键1标示',
   test_pk_2_id         bigint not null comment '主键2标示',
   description          varchar(200) comment '描述',
   primary key (test_pk_1_id, test_pk_2_id)
)
comment = "多主键测试信息";

/*==============================================================*/
/* Table: t_test_pk_1                                           */
/*==============================================================*/
create table t_test_pk_1
(
   id                   bigint not null comment '主键1标示',
   name                 varchar(100) not null comment '名称',
   primary key (id)
)
comment = "主键1信息";

/*==============================================================*/
/* Table: t_test_pk_2                                           */
/*==============================================================*/
create table t_test_pk_2
(
   id                   bigint not null comment '主键2标示',
   name                 varchar(100) not null comment '名称',
   primary key (id)
)
comment = "主键2信息";

/*==============================================================*/
/* Table: t_user                                                */
/*==============================================================*/
create table t_user
(
   id                   bigint not null comment '用户主键标示',
   name                 varchar(50) not null comment '用户名称',
   sex                  char(1) comment '用户性别',
   old                  int comment '用户年龄',
   weight               float comment '用户体重',
   create_date          date not null comment '创建日期',
   primary key (id)
)
comment = "用户信息";

/*==============================================================*/
/* Table: t_user_account                                        */
/*==============================================================*/
create table t_user_account
(
   user_id              bigint not null comment '用户标示',
   login_name           varchar(50) not null comment '用户登录名',
   password             varchar(200) not null comment '用户登录密码',
   state                char(1) not null comment '帐号状态',
   primary key (user_id)
)
comment = "用户帐号信息";

/*==============================================================*/
/* Table: t_user_role                                           */
/*==============================================================*/
create table t_user_role
(
   user_id              bigint not null comment '用户主键标示',
   role_name            varchar(100) not null comment '角色名称',
   primary key (user_id, role_name)
)
comment = "用户、角色关系信息";

alter table t_order add constraint FK_Reference_3 foreign key (user_id)
      references t_user (id) on delete restrict on update restrict;

alter table t_test_many_pk add constraint FK_Reference_6 foreign key (test_pk_1_id)
      references t_test_pk_1 (id) on delete restrict on update restrict;

alter table t_test_many_pk add constraint FK_Reference_7 foreign key (test_pk_2_id)
      references t_test_pk_2 (id) on delete restrict on update restrict;

alter table t_user_account add constraint FK_Reference_5 foreign key (user_id)
      references t_user (id) on delete restrict on update restrict;

alter table t_user_role add constraint FK_Reference_1 foreign key (user_id)
      references t_user (id) on delete restrict on update restrict;

alter table t_user_role add constraint FK_Reference_2 foreign key (role_name)
      references t_role (name) on delete restrict on update restrict;

