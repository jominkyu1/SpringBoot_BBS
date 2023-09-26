package net.daum.service;

import net.daum.vo.BbsVO;
import net.daum.vo.PageVO;

import java.util.List;

public interface BbsService {
    void insertBbs(BbsVO b);

    int getRowCount();

    List<BbsVO> getBbsList(PageVO p);
}
