package net.daum.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.daum.dao.BoardDAO;
import net.daum.vo.BoardVO;
import net.daum.vo.PageVO;

@Service //서비스 애노테이션 추가해서 스프링에 서비스 등록
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardDAO boardDAO;

	@Override
	public void insertBoard(BoardVO b) {
		this.boardDAO.insertBoard(b);
	}

	@Override
	public List<BoardVO> getBoardList(PageVO p) {
		return this.boardDAO.getBoardList(p);
	}

	@Override
	public int getListCount(PageVO p) {
		return this.boardDAO.getListCount(p);
	}

	//스프링 AOP를 통한 트랜잭션 적용 대상
	@Transactional
	@Override
	public BoardVO getBoardCont(int board_no) {
		this.boardDAO.updateHit(board_no);
		BoardVO bc = this.boardDAO.getBoardCont(board_no);		
		return bc;
	}

	@Override
	public BoardVO getBoardCont2(int board_no) {
		return this.boardDAO.getBoardCont(board_no);
	}
	
	//AOP를 통한 트랜잭션 적용 대상
	@Override
	public void replyBoard(BoardVO rb) {
		this.boardDAO.updateLevel(rb);//답변 레벨
		//증가
		this.boardDAO.replyBo(rb);//답변 저장
		//답변 저장이 실패되면 답변 레벨 증가도 트랜잭션을 적용해
		//서 롤백 처리를 해야 한다.즉 쿼리문 실행 취소를 시켜야
		//한다.
	}

	@Override
	public void editBoard(BoardVO eb) {
		this.boardDAO.editBoard(eb);		
	}

	@Override
	public void delBoard(int board_no) {
		this.boardDAO.delBoard(board_no);		
	}	
}























