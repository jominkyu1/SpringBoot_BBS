package net.daum.dao;

import lombok.RequiredArgsConstructor;
import net.daum.vo.BbsVO;
import net.daum.vo.PageVO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BbsDAOImpl implements BbsDAO {

    private final SqlSession sqlSession; //mybatis sql
    private final BbsRepository bbsRepository; //사용자 자료실 JPA를 실행하기위한 자동의존성주입

    @Override
    public int getRowCount(PageVO p) {
        return sqlSession.selectOne("bbs_count", p);
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

    //조회수증가
    @Override
    public void updateHit(int bbs_no) {
        //sqlSession.update("bbs_hi", bbs_no); //MyBatis

        //JPA
        System.out.println(" \n ============> JPA 조회수 증가");
        Optional<BbsVO> bbs_hit = bbsRepository.findById(bbs_no);

        bbs_hit.ifPresent(bbsVO -> {
            bbsVO.setBbs_hit(bbsVO.getBbs_hit() + 1);
            bbsRepository.save(bbsVO);
        });
    }

    //내용보기
    @Override
    public BbsVO getBbsCont(int bbs_no) {
        // return sqlSession.selectOne("bbs_co", bbs_no); //MyBatis

        // JPA
        System.out.println(" \n ============> JPA 내용보기");

        /** getReferenceById() JPA 내장 메서드 특징)
         *  반환값이 null이 발생하는 경우는 예외오류 발생
         *  null이 발생할 일이 없는 경우에만 사용하는것이 좋음
         *
         *  findById는 null일경우 Optional로 empty 반환
         * */
        return bbsRepository.getReferenceById(bbs_no);
        //JPA로 번호에 해당하는 자료를 검색해서 Optional이 아닌 Entity 타입으로 반환
    }

    @Override // level = 답변글 계단식 정렬을 위한 컬럼
    public void updateLevel(BbsVO rb) {
        // sqlSession.update("levelUp", rb); //MyBatis

        System.out.println(" \n ============> JPA 답변 레벨 증가");
        bbsRepository.updateLevel(rb.getBbs_ref(), rb.getBbs_level());
    }

    @Override // 답변 저장
    public void replyBbs(BbsVO rb) {
        // sqlSession.insert("reply_in2", rb);

        System.out.println(" \n ============> JPA 답변 저장");
        int bbs_no = sqlSession.selectOne("bbsNoSeq_Find");
        rb.setBbs_no(bbs_no); //자료실 번호값 시퀀스 +1
        rb.setBbs_step(rb.getBbs_step() + 1);
        rb.setBbs_level(rb.getBbs_level() + 1);

        bbsRepository.save(rb);
    }

    @Override
    public void editBbs(BbsVO b) {
        // sqlSession.update("bbs_edit", b);

        System.out.println(" \n =================>JPA로 자료실 수정");
        this.bbsRepository.updateBbs(b.getBbs_name(), b.getBbs_no(), b.getBbs_title(),
                b.getBbs_cont(), b.getBbs_file());
    }

    @Override
    public void delBbs(int bbs_no) {
        sqlSession.delete("bbs_del", bbs_no);

        // System.out.println(" \n =================>JPA로 자료실 삭제");
        // BbsVO bbsVo = getBbsCont(bbs_no);
        //bbsRepository.deleteby
    }
}
