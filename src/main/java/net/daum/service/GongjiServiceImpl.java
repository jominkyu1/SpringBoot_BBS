package net.daum.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.daum.dao.GongjiDAO;
import net.daum.vo.GongjiVO;

@Service
public class GongjiServiceImpl implements GongjiService {

	@Autowired
	private GongjiDAO gongjiDAO;

	@Override
	public List<GongjiVO> getList() {
		return this.gongjiDAO.getList();
	}

	//트랜잭션 적용대상
	@Transactional
	@Override
	public GongjiVO getGCont(int gongji_no) {
        this.gongjiDAO.updateHit(gongji_no);//조회수 증가		
		return this.gongjiDAO.getGCont(gongji_no);
	}
}


















