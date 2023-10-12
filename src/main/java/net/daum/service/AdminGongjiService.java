package net.daum.service;

import java.util.List;

import net.daum.vo.GongjiVO;
import net.daum.vo.PageVO;

public interface AdminGongjiService {

	int getListCount(PageVO p);
	List<GongjiVO> getGongjiList(PageVO p);
	void insertGongji(GongjiVO g);
	GongjiVO getGongjiCont(int no);
	void editGongji(GongjiVO g);
	void delGongji(int no);

}
