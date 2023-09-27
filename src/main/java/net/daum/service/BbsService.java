package net.daum.service;

import net.daum.vo.BbsVO;
import net.daum.vo.PageVO;

import java.util.List;

public interface BbsService {
    void insertBbs(BbsVO b);

    int getRowCount(PageVO p);

    List<BbsVO> getBbsList(PageVO p);

    BbsVO getBbsCont(int bbs_no);

    BbsVO getBbsCont2(int bbs_no);

    void replyBbs(BbsVO rb);

    void editBbs(BbsVO b);

    void delBbs(int bbs_no);
}
