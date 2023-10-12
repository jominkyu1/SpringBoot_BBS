package net.daum.dao;

import java.util.List;

import net.daum.vo.BoardVO;
import net.daum.vo.PageVO;

public interface AdminBoardDAO {

	int getListCount(PageVO p);
	List<BoardVO> getBoardList(PageVO p);
	void insertBoard(BoardVO b);
	BoardVO getAdminBoardCont(int board_no);
	void editBoard(BoardVO eb);
	void deleteBoard(int no);

}
