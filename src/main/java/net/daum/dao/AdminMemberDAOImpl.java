package net.daum.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.daum.vo.MemberVO;
import net.daum.vo.PageVO;

@Repository
public class AdminMemberDAOImpl implements AdminMemberDAO {

	@Autowired
	private SqlSessionTemplate sqlSession;
	//자동의존성 주입. sqlSession이 mybatis쿼리문 실행객체
	
	@Autowired
	private AdminMemberRepository adminMemberRepo;//JPA 수행하기 위한 자동의존성 주입

	@Override
	public int getListCount(PageVO p) {
		return this.sqlSession.selectOne("am_count",p);
	}//검색전후 회원수

	@Override
	public List<MemberVO> getMemberList(PageVO p) {
		return this.sqlSession.selectList("am_list",p);
	}//검색전후 회원목록

	@Override
	public MemberVO getMem(String mem_id) {
		//return this.sqlSession.selectOne("am_info",mem_id);
		
		System.out.println("  \n ======================> JPA로 관리자 회원관리 상세정보와 수정폼");
		MemberVO am=this.adminMemberRepo.getReferenceById(mem_id);//jpa로 아이디에 해당하는 회원을 검색해서 엔티티빈 타입으로 반환
		/* getReferenceById() 내장메서드는 아이디에 해당하는 회원정보가 없는 경우 예외 오류를 발생시킨다. 그러므로 이 예외 오류 처리가 곤란한다. 즉 아이디에 해당하는 회원정보가 
		 * 반드시 있는 경우만 사용하고 없는 경우 즉  NULL인 경우는 예외 오류 처리 문제로 되도록 그런 경우는 사용 자제 하는 것이 좋다.
		 */
		return am;
	}//회원상세정보+수정폼

	@Transactional//javax.persistence.TransactionRequiredException: Executing an update/delete query 에러가 발생하기 때문에 
	//@Transactional을 해줘야 한다.	
	@Override
	public void editM(MemberVO m) {
		//System.out.println("메일 도메인 : "+m.getMail_domain());
		//this.sqlSession.update("am_edit",m);	
		
		System.out.println(" \n===============================> JPA로 관리자 회원정보 수정");
		this.adminMemberRepo.adminMember_Update(m.getMem_pwd(), m.getMem_name(), m.getMem_zip(), m.getMem_zip2(), m.getMem_addr(), 
				m.getMem_addr2(), m.getMem_phone01(), m.getMem_phone02(), m.getMem_phone03(), m.getMail_id(),
				m.getMail_domain(), m.getMem_state(), m.getMem_delcont(), m.getMem_id());
	}//관리자에서 회원정보수정

	@Override
	public void delM(String mem_id) {
		//this.sqlSession.delete("am_del",mem_id);		
		
		System.out.println(" \n=============================> jpa로 관리자에서 회원 삭제");
		this.adminMemberRepo.deleteById(mem_id);
	}//회원삭제
		
}










