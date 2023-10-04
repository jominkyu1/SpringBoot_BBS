package net.daum.dao;

import net.daum.vo.BbsVO;
import net.daum.vo.PageVO;

import java.util.List;

public interface AdminBbsDao {
    int getListCount(PageVO p);

    List<BbsVO> getAdminBbsList(PageVO p);
}
