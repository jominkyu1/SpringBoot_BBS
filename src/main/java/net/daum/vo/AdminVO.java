package net.daum.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Setter
@Getter
@ToString
@Entity
@Table(name = "admin") //admin 관리자 테이블
@EqualsAndHashCode(of = "admin_id")

public class AdminVO {

    private int admin_no;

    @Id
    private String admin_id; //관리자 아이디
    private String admin_pwd;
    private String admin_name;

    @CreationTimestamp //hibernate의 기능 (mybatis로 실행하면 미작동)
    private Timestamp admin_date;
}
