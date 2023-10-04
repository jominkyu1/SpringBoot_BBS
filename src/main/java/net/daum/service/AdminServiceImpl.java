package net.daum.service;

import lombok.RequiredArgsConstructor;
import net.daum.dao.AdminDAO;
import net.daum.vo.AdminVO;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{

    private final AdminDAO adminDAO;

    @Override
    public void insertAdmin(AdminVO adminVO) {
        adminDAO.insertAdmin(adminVO);
    }

    @Override
    public AdminVO adminLogin(String admin_id) {
        return adminDAO.adminLogin(admin_id);
    }
}
