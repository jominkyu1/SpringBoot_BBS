package net.daum.dao;

import lombok.RequiredArgsConstructor;
import net.daum.vo.BbsVO;
import net.daum.vo.PageVO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AdminBbsDaoImpl implements AdminBbsDao{

    private final SqlSession sqlSession;
    private final AdminBbsRepository adminBbsRepository;

    @Override
    public int getListCount(PageVO p) {
        return sqlSession.selectOne("abbs_count", p);
    }//관리자 자료실 검색전후 레코드 개수

    @Override
    public List<BbsVO> getAdminBbsList(PageVO p) {
        return sqlSession.selectList("abbs_list", p);
    }//관리자 자료실 검색전후 목록

    @Override
    public void adminInsertBbs(BbsVO b) {
        sqlSession.insert("abbs_in", b);
    }

    @Override
    public BbsVO getAdminBbsCont(int no) {
        return null;
    }

    @Override
    public void adminUpdateBbs(BbsVO b) {

    }
}
