package net.daum.dao;

import java.util.List;
import java.util.Optional;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import net.daum.vo.BoardVO;
import net.daum.vo.GongjiVO;

@Repository
public class GongjiDAOImpl implements GongjiDAO {
	
	@Autowired
	private GongjiRepository gongjiRepo;

	@Autowired
	private SqlSessionTemplate sqlSession;

	@Override
	public List<GongjiVO> getList() {
		return this.sqlSession.selectList("g_list");        
	}

	@Override
	public void updateHit(int gongji_no) {
		//this.sqlSession.update("g_hit",gongji_no);
		
		System.out.println(" \n ===================> JPA로 레코드 검색후 조회수 증가");
		Optional<GongjiVO> gongji_hit=this.gongjiRepo.findById(gongji_no);//JPA로 번호를 기준으로 레코드 검색

		gongji_hit.ifPresent(gongji_hit2 ->{//해당 자료가 있다면
			gongji_hit2.setGongji_hit(gongji_hit2.getGongji_hit()+1);//조회수+1			
			this.gongjiRepo.save(gongji_hit2);//JPA로 번호를 기준으로 조회수 증가
		});	 
	}//공지 조회수증가	

	@Override
	public GongjiVO getGCont(int gongji_no) {
		//return this.sqlSession.selectOne("g_cont",gongji_no);
		
		System.out.println(" \n ====================>jpa로 공지 번호에 해당하는 DB 레코드 값 내용보기");
		GongjiVO gc=this.gongjiRepo.getReferenceById(gongji_no);//jpa로 번호에 해당하는 자료를 검색해서 엔티티빈 타입으로 반환
		/* getReferenceById() 내장메서드는 번호에 해당하는 내용이 없는 경우 예외 오류를 발생시킨다. 그러므로 이 예외 오류 처리가 곤란한다. 즉 번호에 해당하는 값이 반드시 
		 * 있는 경우만 사용하고 없는 경우 즉  NULL인 경우는 예외 오류 처리 문제로 되도록 그런 경우는 사용 자제 하는 것이 좋다.
		 */		
		return gc;		
	}//사용자 공지 내용보기
}










