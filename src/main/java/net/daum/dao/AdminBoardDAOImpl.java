package net.daum.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.daum.vo.BoardVO;
import net.daum.vo.PageVO;

@Repository
public class AdminBoardDAOImpl implements AdminBoardDAO {

	@Autowired
	private SqlSession sqlSession;//자동 의존성 주입
	//sqlSession은 mybatis 쿼리문 실행 객체

	@Autowired
	private AdminBoardRepository adminBoardRepo;
	
	@Override
	public int getListCount(PageVO p) {
		return this.sqlSession.selectOne("ab_count",p);
	}//검색전후 레코드 개수	

	@Override
	public List<BoardVO> getBoardList(PageVO p) {
		return this.sqlSession.selectList("ab_list", p);
		//selectList()메서드는 복수개의 레코드를 검색해서 컬렉션
		//List로 반환
	}//검색전후 목록

	@Override
	public void insertBoard(BoardVO b) {
		//this.sqlSession.insert("ab_in",b);
		//ab_in은 insert 아이디명
		
		System.out.println(" \n ====================>JPA로 관리자 게시판 저장~");
		int board_no=this.sqlSession.selectOne("boardNoSeq_Find");
		b.setBoard_no(board_no);//게시판 번호 저장
		b.setBoard_ref(board_no);//글 그룹번호 저장		 

		this.adminBoardRepo.save(b);//jpa로 관리자 게시판 저장
	}//관리자 게시판 저장

	@Override
	public BoardVO getAdminBoardCont(int board_no) {
		//return this.sqlSession.selectOne("ab_cont",board_no);
		//selectOne() 메서드는 단 한개의 레코드만 반환
		//ab_cont는 select 아이디명
		
		System.out.println(" \n ====================> JPA로 게시판 번호에 해당하는 DB 레코드 값 내용보기+관리자 수정폼");
		BoardVO bc=this.adminBoardRepo.getReferenceById(board_no);//jpa로 번호에 해당하는 자료를 검색해서 엔티티빈 타입으로 반환
		/* getReferenceById() 내장메서드는 번호에 해당하는 내용이 없는 경우 예외 오류를 발생시킨다. 그러므로 이 예외 오류 처리가 곤란한다. 즉 번호에 해당하는 값이 반드시 
		 * 있는 경우만 사용하고 없는 경우 즉  NULL인 경우는 예외 오류 처리 문제로 되도록 그런 경우는 사용 자제 하는 것이 좋다.
		 */		
		return bc;
	}//내용보기+수정폼

	@Transactional
	@Override
	public void editBoard(BoardVO eb) {
		//this.sqlSession.update("ab_edit",eb);	
		
		System.out.println(" \n ===================> JPA로 관리자 게시판 수정");		
        this.adminBoardRepo.updateAdminBoard(eb.getBoard_name(), eb.getBoard_title(),
        		eb.getBoard_pwd(), eb.getBoard_cont(),eb.getBoard_no());
	}//수정완료

	@Override
	public void deleteBoard(int no) {
		//this.sqlSession.delete("ab_del",no);
		//delete()메서드가 레코드 삭제,ab_del이 delete 아이디명
		
		System.out.println(" \n =================>JPA로 관리자 게시판 삭제");
		this.adminBoardRepo.deleteById(no);//jpa로 번호를 기준으로 자료 삭제
	}//게시물 삭제
}









