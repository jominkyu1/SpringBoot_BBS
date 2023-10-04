package net.daum.dao;

import lombok.RequiredArgsConstructor;
import net.daum.vo.AdminVO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.NoSuchElementException;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AdminDAOImpl implements AdminDAO{

    private final SqlSession sqlSession;
    private final AdminRepository adminRepository;

    @Override
    public void insertAdmin(AdminVO adminVO) {
        // sqlSession.insert("admin_in", adminVO);
        System.out.println("\n ==========> JPA 관리자 정보 저장");
        adminRepository.save(adminVO);
    }

    @Override
    public AdminVO adminLogin(String admin_id) {
       // return sqlSession.selectOne("admin_login", admin_id);

        System.out.println("\n ==========> JPA 관리자 로그인 인증");
        return adminRepository.findById(admin_id).orElse(null);
    }
}
