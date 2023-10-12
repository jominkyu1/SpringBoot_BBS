package net.daum.vo;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString //toString() 메서드 자동제공
@Entity
@Table(name="board")
@EqualsAndHashCode(of="board_no")
/* equals(), hashCode(),canEqual() 메서드 자동제공 * 
 */
public class BoardVO {
 /*
  *  네임피라미터 이름,빈클래스변수명,테이블 컬럼명을 일치시킨다.
  */
	@Id //구분키(식별키) 즉 유일키로 사용될 기본키 컬럼 즉 primary key
	private int board_no;
	private String board_name;
	private String board_title;
	private String board_pwd;
	
	@Column(length=4000) //컬럼크기를 4000으로 설정
	private String board_cont;
	private int board_hit;
	private int board_ref;
	private int board_step;
	private int board_level;
	
	@CreationTimestamp //@CreationTiestamp 는 하이버네이트의 특별한 기능으로 등록시점 날짜값을 기록,mybatis로 실행할 때는 구동 안됨.
	private Timestamp board_date;			
}



















