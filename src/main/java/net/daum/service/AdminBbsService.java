package net.daum.service;

import net.daum.vo.BbsVO;
import net.daum.vo.PageVO;

import java.util.List;

public interface AdminBbsService {
    int getListCount(PageVO p);

    List<BbsVO> getadminBbsList(PageVO p);
}
