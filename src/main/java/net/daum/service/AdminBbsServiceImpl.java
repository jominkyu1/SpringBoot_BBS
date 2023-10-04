package net.daum.service;

import lombok.RequiredArgsConstructor;
import net.daum.dao.AdminBbsDao;
import net.daum.vo.BbsVO;
import net.daum.vo.PageVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminBbsServiceImpl implements AdminBbsService{

    private final AdminBbsDao adminBbsDao;

    @Override
    public int getListCount(PageVO p) {
        return adminBbsDao.getListCount(p);
    }

    @Override
    public List<BbsVO> getadminBbsList(PageVO p) {
        return adminBbsDao.getAdminBbsList(p);
    }
}
