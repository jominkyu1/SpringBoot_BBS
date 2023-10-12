package net.daum.dao;

import java.util.List;

import net.daum.vo.BoardVO;
import net.daum.vo.PageVO;

public interface BoardDAO {

	void insertBoard(BoardVO b);
	List<BoardVO> getBoardList(PageVO p);
	int getListCount(PageVO b);
	void updateHit(int board_no);
	BoardVO getBoardCont(int board_no);
	void updateLevel(BoardVO rb);
	void replyBo(BoardVO rb);
	void editBoard(BoardVO eb);
	void delBoard(int board_no);
}
