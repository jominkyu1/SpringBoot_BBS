package net.daum.service;

import lombok.RequiredArgsConstructor;
import net.daum.dao.BbsDAO;
import net.daum.vo.BbsVO;
import net.daum.vo.PageVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BbsServiceImpl implements BbsService{

    private final BbsDAO bbsDao;

    @Override
    public void insertBbs(BbsVO b) {
        bbsDao.insertBbs(b);
    }

    @Override
    public int getRowCount() {
        return bbsDao.getRowCount();
    }

    @Override
    public List<BbsVO> getBbsList(PageVO p) {
        return bbsDao.getBbsList(p);
    }
}
