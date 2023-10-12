package net.daum.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.daum.dao.AdminGongjiDAO;
import net.daum.vo.GongjiVO;
import net.daum.vo.PageVO;

@Service /* 관리자 공지사항 서비스 -> 스프링의 aop를 통한 트랜잭션 적용할 곳 */
public class AdminGongjiServiceImpl implements AdminGongjiService {

	@Autowired
	private AdminGongjiDAO adminGongjiDao;

	@Override
	public int getListCount(PageVO p) {
		return this.adminGongjiDao.getListCount(p);
	}

	@Override
	public List<GongjiVO> getGongjiList(PageVO p) {
		return this.adminGongjiDao.getGongjiList(p);
	}

	@Override
	public void insertGongji(GongjiVO g) {
		this.adminGongjiDao.insertGongji(g);		
	}

	@Override
	public GongjiVO getGongjiCont(int no) {
		return this.adminGongjiDao.getGongjiCont(no);
	}

	@Override
	public void editGongji(GongjiVO g) {
		this.adminGongjiDao.editGongji(g);		
	}

	@Override
	public void delGongji(int no) {
		this.adminGongjiDao.delGongji(no);		
	}		
}
