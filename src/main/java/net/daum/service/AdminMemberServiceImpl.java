package net.daum.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.daum.dao.AdminMemberDAO;
import net.daum.vo.MemberVO;
import net.daum.vo.PageVO;

@Service
public class AdminMemberServiceImpl implements AdminMemberService {

	@Autowired
	private AdminMemberDAO adminMemberDAO;

	@Override
	public int getListCount(PageVO p) {
		return this.adminMemberDAO.getListCount(p);
	}

	@Override
	public List<MemberVO> getMemberList(PageVO p) {
		return this.adminMemberDAO.getMemberList(p);
	}

	@Override
	public MemberVO getMem(String mem_id) {
		return this.adminMemberDAO.getMem(mem_id);
	}

	@Override
	public void editM(MemberVO m) {
		this.adminMemberDAO.editM(m);		
	}

	@Override
	public void delM(String mem_id) {
		this.adminMemberDAO.delM(mem_id);		
	}		
}









