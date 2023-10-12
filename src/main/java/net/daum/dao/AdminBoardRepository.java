package net.daum.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import net.daum.vo.BoardVO;

public interface AdminBoardRepository extends JpaRepository<BoardVO, Integer> {

	@Modifying
	@Query("update BoardVO b set b.board_name=?1,board_title=?2,b.board_pwd=?3,b.board_cont=?4 where b.board_no=?5 ")
    public void updateAdminBoard(String name,String title,String pwd,String cont,int no);// 번호를 기준으로 글쓴이,글제목,비번, 글내용을  수정
}
