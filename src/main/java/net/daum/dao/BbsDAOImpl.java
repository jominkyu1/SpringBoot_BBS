package net.daum.dao;

import lombok.RequiredArgsConstructor;
import net.daum.vo.BbsVO;
import net.daum.vo.PageVO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BbsDAOImpl implements BbsDAO{

    private final SqlSession sqlSession; //mybatis sql
    private final BbsRepository bbsRepository; //사용자 자료실 JPA를 실행하기위한 자동의존성주입

    @Override
    public int getRowCount() {
        return sqlSession.selectOne("bbs_count");
    }

    @Override
    public void insertBbs(BbsVO b) {
        //sqlSession.insert("bbs_in", b); //MyBatis
        int bbs_no = sqlSession.selectOne("bbsNoSeq_Find"); //시퀀스 번호값을 구함
        System.out.println("\n =====> JPA로 자료실 저장");
        b.setBbs_no(bbs_no); //번호 저장
        b.setBbs_ref(bbs_no); //글 그룹번호를 저장

        bbsRepository.save(b);
    }

    @Override
    public List<BbsVO> getBbsList(PageVO p) {
        return sqlSession.selectList("bbs_list", p);
    }
}
