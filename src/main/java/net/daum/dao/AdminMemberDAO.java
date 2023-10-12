package net.daum.dao;

import java.util.List;

import net.daum.vo.MemberVO;
import net.daum.vo.PageVO;

public interface AdminMemberDAO {

	int getListCount(PageVO p);
	List<MemberVO> getMemberList(PageVO p);
	MemberVO getMem(String mem_id);
	void editM(MemberVO m);
	void delM(String mem_id);

}
