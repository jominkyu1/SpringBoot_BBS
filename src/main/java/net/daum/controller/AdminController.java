package net.daum.controller;

import lombok.RequiredArgsConstructor;
import net.daum.pwdconv.PwdChange;
import net.daum.service.AdminService;
import net.daum.vo.AdminVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private static Integer sequence = 0;

    //관리자 로그인 페이지
    @GetMapping("/admin_login")
    public ModelAndView admin_login() {
        return new ModelAndView("admin/admin_Login");
    }

    //관리자 정보 저장
    @PostMapping("admin_login_ok")
    public String admin_login_ok(AdminVO adminVO,
                                 HttpServletResponse response,
                                 HttpServletRequest request,
                                 HttpSession session) throws Exception {

        response.setContentType("text/html;charset=UTF-8");
        //응답시에 JavaScript/HTML 코드등의 글자깨짐 방지
        PrintWriter out = response.getWriter();
        //출력스트림 객체 생성

        adminVO.setAdmin_pwd(PwdChange.getPassWordToXEMD5String(adminVO.getAdmin_pwd()));
        //adminVO.setAdmin_name("관리자");
        //adminVO.setAdmin_no(++sequence);
        //adminVO.setAdmin_no(1);
        //adminService.insertAdmin(adminVO);

        AdminVO admin_info = adminService.adminLogin(adminVO.getAdmin_id());
        //관리자 아이디로 로그인 인증
        if (admin_info == null) {
            out.println("<script> alert('관리자 정보가 없습니다!'); history.back(); </script>");
        } else {
            if (!admin_info.getAdmin_pwd().equals(adminVO.getAdmin_pwd())) {
                out.println("<script> alert('비밀번호가 다릅니다!'); history.go(-1); </script>");
            } else {
                session.setAttribute("admin_id", adminVO.getAdmin_id());
                session.setAttribute("admin_name", admin_info.getAdmin_name());

                return "redirect:/admin_index";
            }
        }
        return null;
    }


    //관리자 로그인 인증 후 관리 메인페이지로 이동
    @RequestMapping("/admin_index")
    public ModelAndView admin_index(HttpServletResponse response,
                                    HttpSession session) throws Exception {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String admin_id = (String) session.getAttribute("admin_id");

        if (admin_id == null) {
            out.println("<script>alert('관리자 아이디로 로그인 하세요!'); location='admin_login';</script>");
        } else {
            return new ModelAndView("admin/admin_main");
        }
        return null;
    }

    @RequestMapping("/admin_logout")
    public String admin_logout(HttpServletResponse response, HttpSession session) throws Exception {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        session.invalidate();

        out.println("<script>alert('로그아웃 되었습니다!'); location='admin_login';</script>");

        return null;
    }
}
