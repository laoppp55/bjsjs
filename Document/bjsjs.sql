create table TBL_CollectionInfo
(
        id                            NUMBER                  not null,
	siteid                        NUMBER                  not null,
        articleid                     NUMBER                  not null,
        ename                         VARCHAR2(50) ,
        cname                         varchar2(50),
	type                          NUMBER(1)               not null,       --1:������2�����㣬3���ַ�����4�����ı���5��ʱ���
        textvalue                     LONG,
        stringvalue                   VARCHAR2(4000),
        numericvalue                  NUMBER,
        floatvalue                    NUMBER(9,2),
        primary key (id)
);


create table TBL_SimpleCollectionInfo
(
        id                            NUMBER                  not null,
	siteid                        NUMBER                  not null,
        articleid                     NUMBER                  not null,
        title                         VARCHAR2(500) ,
        content                       varchar2(4000),
	pubflag                       NUMBER(1)               not null,       --1:������0��������
        primary key (id)
);


1���鿴��ǰ�����ݿ�������

 select count(*) from v$process ;    --��ǰ�����ݿ�������

2�����ݿ����������������

 select value from v$parameter where name ='processes';  --���ݿ����������������

3���޸����ݿ����������
 alter system set processes = 300 scope = spfile;  --�޸����������: