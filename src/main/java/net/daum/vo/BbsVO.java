package net.daum.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Getter
@Setter
@ToString
@Entity //JPA Entity
@Table(name = "bbs")
@EqualsAndHashCode(of = "bbs_no")
public class BbsVO { //자료실 Entity

    @Id
    private int bbs_no;

    private String bbs_name; //글쓴이
    private String bbs_title; //글제목
    private String bbs_pwd; //비밀번호
    @Column(length = 4000)
    private String bbs_cont; //글내용

    private String bbs_file; //첨부파일 경로와 파일명
    private int bbs_hit;

    //계단형 계층형 자료실을 만들기 위해 필요한것 -> 관리자 답변기능 추가 컬럼
    private int bbs_ref; //글 그룹번호 -> 원본글과 답변글을 묶어주는 기능을 한다.

    private int bbs_step; //원본글과 답변글을 구분하는 번호값이면서 몇번째 답변글인가를 알려줌
    //원본 0, 첫번째 답글1, 두번째 답글 2
    private int bbs_level; //답변글 정렬순서

    @CreationTimestamp //Hibernate의 기능
    private Timestamp bbs_date; //글등록날짜
}
