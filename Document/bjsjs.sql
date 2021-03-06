create table TBL_CollectionInfo
(
        id                            NUMBER                  not null,
	siteid                        NUMBER                  not null,
        articleid                     NUMBER                  not null,
        ename                         VARCHAR2(50) ,
        cname                         varchar2(50),
	type                          NUMBER(1)               not null,       --1:整数，2：浮点，3：字符串，4：长文本，5：时间戳
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
	pubflag                       NUMBER(1)               not null,       --1:公开，0：不公开
        primary key (id)
);


1、查看当前的数据库连接数

 select count(*) from v$process ;    --当前的数据库连接数

2、数据库允许的最大连接数

 select value from v$parameter where name ='processes';  --数据库允许的最大连接数

3、修改数据库最大连接数
 alter system set processes = 300 scope = spfile;  --修改最大连接数:
