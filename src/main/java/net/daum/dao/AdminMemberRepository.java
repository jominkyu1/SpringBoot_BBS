package net.daum.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import net.daum.vo.MemberVO;

public interface AdminMemberRepository extends JpaRepository<MemberVO, String> {

	@Modifying 
	@Query("update MemberVO m set m.mem_pwd=?1, m.mem_name=?2, m.mem_zip=?3, m.mem_zip2=?4, m.mem_addr=?5,"
			+" m.mem_addr2=?6, mem_phone01=?7, mem_phone02=?8, mem_phone03=?9, m.mail_id=?10, m.mail_domain=?11,"
			+ "m.mem_state=?12,m.mem_delcont=?13 where m.mem_id=?14")
	public void adminMember_Update(String pwd,String name,String zip,String zip2,String addr,String addr2,String phone01,String phone02,
			String phone03,String mail_id,String mail_domain,int state, String delcont,String id); //관리자 회원 정보 수정

}
