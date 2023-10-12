package net.daum.service;

import java.util.List;

import net.daum.vo.BoardVO;
import net.daum.vo.PageVO;

public interface BoardService {

	void insertBoard(BoardVO b);
	List<BoardVO> getBoardList(PageVO p);
	int getListCount(PageVO p);
	//void updateHit(int board_no);
	BoardVO getBoardCont(int board_no);
	void replyBoard(BoardVO rb);
	void editBoard(BoardVO eb);
	void delBoard(int board_no);
	BoardVO getBoardCont2(int board_no);
}
