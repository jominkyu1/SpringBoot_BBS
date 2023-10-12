package net.daum.dao;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.daum.vo.BoardVO;
import net.daum.vo.GongjiVO;
import net.daum.vo.MemberVO;
import net.daum.vo.PageVO;

@Repository
public class AdminGongjiDAOImpl implements AdminGongjiDAO {//관리자 공지

	@Autowired
	private SqlSession sqlSession;

	@Autowired
	private AdminGongjiRepository adminGongjiRepo;

	@Override
	public int getListCount(PageVO p) {
		return this.sqlSession.selectOne("ag_count",p);
	}//관리자 공지 검색 전후 레코드 개수	

	@Override
	public List<GongjiVO> getGongjiList(PageVO p) {
		return this.sqlSession.selectList("ag_list",p);
	}//관리자 공지 검색 전후 목록

	@Override
	public void insertGongji(GongjiVO g) {
		//this.sqlSession.insert("ag_insert",g);	

		System.out.println(" \n ====================>JPA로 관리자 공지 저장~");		
		this.adminGongjiRepo.save(g);
	}//관리자 공지 저장

	@Override
	public GongjiVO getGongjiCont(int no) {
		//return this.sqlSession.selectOne("ag_cont",no);

		System.out.println(" \n ====================> JPA로 공지 번호에 해당하는 관리자 내용보기+수정폼");
		GongjiVO gc=this.adminGongjiRepo.getReferenceById(no);//jpa로 번호에 해당하는 자료를 검색해서 엔티티빈 타입으로 반환
		/* getReferenceById() 내장메서드는 번호에 해당하는 내용이 없는 경우 예외 오류를 발생시킨다. 그러므로 이 예외 오류 처리가 곤란한다. 즉 번호에 해당하는 값이 반드시 
		 * 있는 경우만 사용하고 없는 경우 즉  NULL인 경우는 예외 오류 처리 문제로 되도록 그런 경우는 사용 자제 하는 것이 좋다.
		 */		
		return gc;
	}//관리자 공지 수정폼과 상세정보

	@Override
	public void editGongji(GongjiVO g) {
		this.sqlSession.update("ag_edit",g);			

		System.out.println(" \n ==============================> jpa로 관리자 공지 수정");
		Optional<GongjiVO> result = this.adminGongjiRepo.findById(g.getGongji_no());
		GongjiVO gongji;
		if(result.isPresent()){//공지 번호에 해당하는 관리자 공지 정보가 있다면 참
			gongji = result.get();//GongjiVO 엔티티 타입 객체를 구함

			gongji.setGongji_name(g.getGongji_name());//수정할 공지 작성자 저장
			gongji.setGongji_title(g.getGongji_title());//수정할 공지 제목 저장
			gongji.setGongji_pwd(g.getGongji_pwd()); //수정할 공지비번 저장
			gongji.setGongji_cont(g.getGongji_cont()); //수정할 공지 내용 저장
			//member.setMem_id(dm.getMem_id());

			this.adminGongjiRepo.save(gongji);
		}
	}//관리자 공지 수정 완료

	@Override
	public void delGongji(int no) {
		//this.sqlSession.delete("ag_del",no);	
		
		System.out.println(" \n =================>JPA로 관리자 공지 삭제");
		this.adminGongjiRepo.deleteById(no);//jpa로 번호를 기준으로 자료 삭제
	}//관리자 공지삭제
}
