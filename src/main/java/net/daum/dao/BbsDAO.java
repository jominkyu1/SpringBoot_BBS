package net.daum.dao;


import net.daum.vo.BbsVO;
import net.daum.vo.PageVO;

import java.util.List;

public interface BbsDAO {
    int getRowCount(PageVO p);
    void insertBbs(BbsVO b);

    List<BbsVO> getBbsList(PageVO p);

    void updateHit(int bbs_no);

    BbsVO getBbsCont(int bbs_no);

    void updateLevel(BbsVO rb);

    void replyBbs(BbsVO rb);

    void editBbs(BbsVO b);

    void delBbs(int bbs_no);
}
