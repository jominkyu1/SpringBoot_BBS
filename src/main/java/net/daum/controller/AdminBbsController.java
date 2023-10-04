package net.daum.controller;

import lombok.RequiredArgsConstructor;
import net.daum.service.AdminBbsService;
import net.daum.vo.BbsVO;
import net.daum.vo.PageVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminBbsController {

    private final AdminBbsService adminBbsService;

    //관리자 자료실 목록
    @RequestMapping("/admin_bbs_list")
    public ModelAndView admin_bbs_list(BbsVO b, HttpServletResponse response, HttpServletRequest request,
                                       HttpSession session, PageVO p) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String admin_id = (String) session.getAttribute("admin_id"); //관리자 세션 아이디를 구함
        if (admin_id == null) {
            out.println("<script>alert('관리자로 로그인 하세요!'); location='admin_login';</script>");
        } else {
            int page = 1;//쪽번호
            int limit = 7;//한페이지에 보여지는 목록개수
            if (request.getParameter("page") != null) {
                page = Integer.parseInt(request.getParameter("page"));
            }
            String find_name = request.getParameter("find_name");//검색어
            String find_field = request.getParameter("find_field");//검색 필드
            p.setFind_field(find_field);
            p.setFind_name("%" + find_name + "%");
            //%는 sql문에서 검색 와일드 카드 문자로서 하나이상의 임의의 모르는 문자와 매핑 대응,
            //하나의 모르는 문자와 매핑 대응하는 와일드 카드문자는 _

            int listcount = this.adminBbsService.getListCount(p);
            //검색전 전체 레코드 개수 또는 검색후 레코드 개수
            //System.out.println("총 게시물수:"+listcount+"개");

            p.setStartrow((page - 1) * 7 + 1);//시작행번호
            p.setEndrow(p.getStartrow() + limit - 1);//끝행번호

            List<BbsVO> blist = this.adminBbsService.getadminBbsList(p);//검색 전후 목록

            //총페이지수
            int maxpage = (int) ((double) listcount / limit + 0.95);
            //현재 페이지에 보여질 시작페이지 수(1,11,21)
            int startpage = (((int) ((double) page / 10 + 0.9)) - 1) * 10 + 1;
            //현재 페이지에 보여줄 마지막 페이지 수(10,20,30)
            int endpage = maxpage;
            if (endpage > startpage + 10 - 1) endpage = startpage + 10 - 1;

            ModelAndView listM = new ModelAndView();

            listM.addObject("blist", blist);//blist 키이름에 값 저장
            listM.addObject("page", page);
            listM.addObject("startpage", startpage);
            listM.addObject("endpage", endpage);
            listM.addObject("maxpage", maxpage);
            listM.addObject("listcount", listcount);
            listM.addObject("find_field", find_field);
            listM.addObject("find_name", find_name);

            listM.setViewName("admin/admin_bbs_list");//뷰페이지 경로
            return listM;
        }

        return null;
    }

    //관리자 자료실 글쓰기
    @GetMapping("/admin_bbs_write")
    public ModelAndView admin_bbs_write(HttpServletResponse response,
                                        HttpSession session,
                                        HttpServletRequest request) throws Exception{
        response.setContentType("text/html;charset=UTF-8");
        ModelAndView mv = new ModelAndView("admin/admin_bbs_write");

        if(isAdminLogin(session, response)){
            int page=1;

            if (request.getParameter("page") != null) {
                page = Integer.parseInt(request.getParameter("page"));
            }
            mv.addObject("page", page);
        }
        return mv;
    }
    
    //반복적인 관리자 로그인을 안하기 위한 코드 추가
    public static boolean isAdminLogin(HttpSession session,
                                       HttpServletResponse response) throws Exception{
        PrintWriter out = response.getWriter();
        String admin_id = (String) session.getAttribute("admin_id");
        if(admin_id==null){ //관리자 로그아웃 되었을때
            out.println("<script>alert('관리자로 로그인 하세요!'); location='admin_login';</script>");
            return false;
        }
        return true;
    }
}
