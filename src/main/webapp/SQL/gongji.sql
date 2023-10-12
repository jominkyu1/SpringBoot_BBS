--공지 사항 테이블 생성
create table gongji(
  gongji_no number(38) primary key  -- 공지번호
  , gongji_name varchar2(50) not null  --공지 작성자
  , gongji_title varchar2(200) not null  -- 공지제목
  , gongji_pwd varchar2(20) not null -- 공지 비번
  , gongji_cont  varchar2(4000) not null --공지 내용
  , gongji_hit number(38) default 0 -- 조회수
  , gongji_date date -- 등록날짜 
  );
  
  ALTER TABLE gongji MODIFY (gongji_cont VARCHAR2(4000));

  --gongji_no_seq 시퀀스 생성
  create sequence gongji_no_seq
  start with 1
  increment by 1
  nocache;
  
  insert into gongji (gongji_no, gongji_name, gongji_title, gongji_pwd, gongji_cont, gongji_date)
  values(gongji_no_seq.nextval, '관리자','공지 제목','999','공지내용',sysdate);
  
  select * from gongji order by gongji_no desc;
  
  alter sequence gongji_no_seq
  nocache; --nocache로 시퀀스 수정
  
  commit;
  
  select * from (select gongji_no,gongji_title,gongji_date from gongji order by gongji_no desc) 	 where rownum<6;
  
  
  
  
  