package net.daum.dao;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.daum.vo.BoardVO;
import net.daum.vo.PageVO;

@Repository //이 애노테이션을 등록해서 스프링에 DAOImpl 인식
public class BoardDAOImpl implements BoardDAO {

	@Autowired
	private SqlSession sqlSession;//자동 의존성 주입.
	//mybatis 쿼리문 실행객체

	@Autowired
	private BoardRepository boardRepo;

	@Override
	public void insertBoard(BoardVO b) {
		//this.sqlSession.insert("board_in",b);
		//insert()메서드는 레코드 저장, board_in은 insert 아이디명
		//아이디명은 유일해야 한다.중복 있어면 안된다.

		System.out.println(" \n====================>JPA로 게시판 저장~");
		int board_no=this.sqlSession.selectOne("boardNoSeq_Find");
		b.setBoard_no(board_no);//게시판 번호 저장
		b.setBoard_ref(board_no);//글 그룹번호 저장		 

		this.boardRepo.save(b);//jpa로 게시판 저장
	}//게시판 저장	

	@Override
	public List<BoardVO> getBoardList(PageVO p) {
		return this.sqlSession.selectList("board_li",p);
		//selectList()메서드는 복수개의 레코드를 검색해서 컬렉션 List로
		//반환한다. select 아이디명 board_li		
	}//검색 전후 게시판 목록

	@Override
	public int getListCount(PageVO p) {
		return this.sqlSession.selectOne("board_row",p);
		//selectOne() 메서드는 단 한개의 레코드만 반환. board_row는
		//select 아이디명		
	}//검색 전후 레코드 개수

	@Override
	public void updateHit(int board_no) {
		//this.sqlSession.update("board_hi",board_no);
		//board_hi는 update 아이디명,update()메서드가 레코드
		//수정. this.은 생략가능

		System.out.println(" \n ===================> JPA로 레코드 검색후 조회수 증가");
		Optional<BoardVO> board_hit=this.boardRepo.findById(board_no);//JPA로 번호를 기준으로 레코드 검색

		board_hit.ifPresent(board_hit2 ->{//해당 자료가 있다면
			board_hit2.setBoard_hit(board_hit2.getBoard_hit()+1);//조회수+1			
			this.boardRepo.save(board_hit2);//JPA로 번호를 기준으로 조회수 증가
		});	 
	}//조회수 증가	

	@Override
	public BoardVO getBoardCont(int board_no) {
		//return this.sqlSession.selectOne("board_co",board_no);
		
		System.out.println(" \n ====================>jpa로 게시판 번호에 해당하는 DB 레코드 값 내용보기");
		BoardVO bc=this.boardRepo.getReferenceById(board_no);//jpa로 번호에 해당하는 자료를 검색해서 엔티티빈 타입으로 반환
		/* getReferenceById() 내장메서드는 번호에 해당하는 내용이 없는 경우 예외 오류를 발생시킨다. 그러므로 이 예외 오류 처리가 곤란한다. 즉 번호에 해당하는 값이 반드시 
		 * 있는 경우만 사용하고 없는 경우 즉  NULL인 경우는 예외 오류 처리 문제로 되도록 그런 경우는 사용 자제 하는 것이 좋다.
		 */		
		return bc;
	}//내용보기

	@Transactional
	@Override
	public void updateLevel(BoardVO rb) {
		//this.sqlSession.update("reply_up",rb);
		
		System.out.println(" \n ======================>JPA로 게시판 답변 레벨 증가");
		this.boardRepo.updateLevel(rb.getBoard_ref(), rb.getBoard_level());
	}//답변 레벨 증가

	@Override
	public void replyBo(BoardVO rb) {
		//this.sqlSession.insert("reply_in",rb);
		
		System.out.println("\n======================>JPA로 게시판 답변 저장~");
		int board_no=this.sqlSession.selectOne("boardNoSeq_Find");		
		rb.setBoard_no(board_no);//게시판 번호 저장
		rb.setBoard_step(rb.getBoard_step()+1);
		rb.setBoard_level(rb.getBoard_level()+1);
		
		this.boardRepo.save(rb);
	}//답변 저장

	@Transactional
	@Override
	public void editBoard(BoardVO eb) {
		//this.sqlSession.update("board_up", eb);	
		
		System.out.println(" \n ===================> JPA로 게시판 수정");		
        this.boardRepo.updateBoard(eb.getBoard_name(), eb.getBoard_title(),eb.getBoard_cont(),eb.getBoard_no());
	}//게시물 수정

	@Override
	public void delBoard(int board_no) {
		//this.sqlSession.delete("board_del",board_no);
		//delete()메서드로 레코드 삭제, board_del은 delete 아이
		//디 명
		
		System.out.println("\n=================>JPA로 게시물 삭제");
		this.boardRepo.deleteById(board_no);//jpa로 번호를 기준으로 자료 삭제
	}//게시물 삭제
}












