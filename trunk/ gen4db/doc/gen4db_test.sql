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
   id                   bigint not null comment '������ʾ',
   user_id              bigint not null comment '�����û�',
   name                 varchar(100) not null comment '��������',
   create_time          datetime not null comment '��������ʱ��',
   primary key (id)
)
comment = "�û�������Ϣ";

/*==============================================================*/
/* Table: t_role                                                */
/*==============================================================*/
create table t_role
(
   name                 varchar(100) not null comment '��ɫ����',
   description          varchar(200) comment '��ɫ����',
   state                int not null comment '��ɫ״̬',
   primary key (name)
)
comment = "��ɫ��Ϣ";

/*==============================================================*/
/* Table: t_test_many_pk                                        */
/*==============================================================*/
create table t_test_many_pk
(
   test_pk_1_id         bigint not null comment '����1��ʾ',
   test_pk_2_id         bigint not null comment '����2��ʾ',
   description          varchar(200) comment '����',
   primary key (test_pk_1_id, test_pk_2_id)
)
comment = "������������Ϣ";

/*==============================================================*/
/* Table: t_test_pk_1                                           */
/*==============================================================*/
create table t_test_pk_1
(
   id                   bigint not null comment '����1��ʾ',
   name                 varchar(100) not null comment '����',
   primary key (id)
)
comment = "����1��Ϣ";

/*==============================================================*/
/* Table: t_test_pk_2                                           */
/*==============================================================*/
create table t_test_pk_2
(
   id                   bigint not null comment '����2��ʾ',
   name                 varchar(100) not null comment '����',
   primary key (id)
)
comment = "����2��Ϣ";

/*==============================================================*/
/* Table: t_user                                                */
/*==============================================================*/
create table t_user
(
   id                   bigint not null comment '�û�������ʾ',
   name                 varchar(50) not null comment '�û�����',
   sex                  char(1) comment '�û��Ա�',
   old                  int comment '�û�����',
   weight               float comment '�û�����',
   create_date          date not null comment '��������',
   primary key (id)
)
comment = "�û���Ϣ";

/*==============================================================*/
/* Table: t_user_account                                        */
/*==============================================================*/
create table t_user_account
(
   user_id              bigint not null comment '�û���ʾ',
   login_name           varchar(50) not null comment '�û���¼��',
   password             varchar(200) not null comment '�û���¼����',
   state                char(1) not null comment '�ʺ�״̬',
   primary key (user_id)
)
comment = "�û��ʺ���Ϣ";

/*==============================================================*/
/* Table: t_user_role                                           */
/*==============================================================*/
create table t_user_role
(
   user_id              bigint not null comment '�û�������ʾ',
   role_name            varchar(100) not null comment '��ɫ����',
   primary key (user_id, role_name)
)
comment = "�û�����ɫ��ϵ��Ϣ";

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

