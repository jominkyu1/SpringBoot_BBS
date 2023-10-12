package net.daum.vo;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter //setter()메서드 자동제공
@Getter //getter()메서드 자동제공
@ToString //toString() 메서드 자동제공
@Entity
@SequenceGenerator(//@SequenceGenerator는 시퀀스 생성기를 설정하는 애노테이션
		name="gongji_no_seq_gename", //시퀀스 제너레이터 이름
		sequenceName="gongji_no_seq", //시퀀스 이름
		initialValue=1, //시작값
		allocationSize=1 //메모리를 통해 할당할 범위 사이즈=>기본값은 50이며, 1로 설정하는 경우 매번 insert시마다 DB의 시퀀스를 호출해서 db시퀀스 번호값을 가져와서
		//1증가한 값이 할당된다. 1씩 증가. 증가값		
		)
@Table(name="gongji")
@EqualsAndHashCode(of="gongji_no")
/* equals(), hashCode(),canEqual() 메서드 자동제공 * 
 */
public class GongjiVO { /* 공지사항 테이터 저장빈 클래스, 되도록이면 테이블 컬럼명과 일치하는 변수명을 정의한다. */

	@Id //구분키(식별키) 즉 유일키로 사용될 기본키 컬럼 즉 primary key
	@GeneratedValue(
			strategy=GenerationType.SEQUENCE, //사용할 전략을 시퀀스로  선택
			generator="gongji_no_seq_gename" //식별자 생성기를 설정해놓은 bno_seq3_gename 시퀀스 제너레이터 이름 으로 설정
			)
	private int gongji_no;//공지번호
	
	private String gongji_name;//공지 작성자
	private String gongji_title;//공지 제목
	private String gongji_pwd;//공지 비번
	
	@Column(length=4000) //컬럼크기를 4000으로 설정
	private String gongji_cont; //공지내용
	private int gongji_hit; //조회수
	
	@CreationTimestamp //@CreationTiestamp 는 하이버네이트의 특별한 기능으로 등록시점 날짜값을 기록,mybatis로 실행할 때는 구동 안됨.
	private Timestamp gongji_date;// 등록날짜	

}
