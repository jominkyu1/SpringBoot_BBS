package net.daum.service;

import lombok.RequiredArgsConstructor;
import net.daum.dao.BbsDAO;
import net.daum.vo.BbsVO;
import net.daum.vo.PageVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

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
    public int getRowCount(PageVO p) {
        return bbsDao.getRowCount(p);
    }

    @Override
    public List<BbsVO> getBbsList(PageVO p) {
        return bbsDao.getBbsList(p);
    }

    //AOP를통한 트랜잭션 적용
    @Transactional(isolation = Isolation.READ_COMMITTED) //트랜잭션 격리 (트랜잭션이 적용되는 중간에 외부간섭을 배제)
    @Override
    public BbsVO getBbsCont(int bbs_no) {
        //조회수증가
        bbsDao.updateHit(bbs_no);
        //내용보기
        return bbsDao.getBbsCont(bbs_no);
    }

    @Override
    public BbsVO getBbsCont2(int bbs_no) {
        //내용보기
        return bbsDao.getBbsCont(bbs_no);
    }

    @Transactional
    @Override
    public void replyBbs(BbsVO rb) {
        //답변 레벨증가
        bbsDao.updateLevel(rb);
        //답변 저장
        bbsDao.replyBbs(rb);
    }

    @Override
    public void editBbs(BbsVO b) {
        bbsDao.editBbs(b);
    }

    @Override
    public void delBbs(int bbs_no) {
        bbsDao.delBbs(bbs_no);
    }
}
