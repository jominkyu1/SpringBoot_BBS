package net.daum.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import net.daum.vo.BoardVO;

public interface BoardRepository extends JpaRepository<BoardVO, Integer> {

	
	@Modifying
	@Query("update BoardVO b set b.board_level=b.board_level+1 where b.board_ref=?1 and b.board_level > ?2 ")
    public void updateLevel(int ref,int level); //답변 레벨 증가
	
	@Modifying
	@Query("update BoardVO b set b.board_name=?1,board_title=?2,b.board_cont=?3 where b.board_no=?4 ")
    public void updateBoard(String name,String title,String cont,int no);// 번호를 기준으로 글쓴이,글제목,글내용을  수정
	
}
