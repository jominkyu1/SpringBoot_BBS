package net.daum.dao;


import net.daum.vo.BbsVO;
import net.daum.vo.PageVO;

import java.util.List;

public interface BbsDAO {
    int getRowCount();
    void insertBbs(BbsVO b);

    List<BbsVO> getBbsList(PageVO p);
}
