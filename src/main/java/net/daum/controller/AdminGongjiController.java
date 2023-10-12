package net.daum.controller;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import net.daum.service.AdminGongjiService;
import net.daum.vo.GongjiVO;
import net.daum.vo.PageVO;

@Controller
public class AdminGongjiController {

	@Autowired
	private AdminGongjiService adminGongjiService;

	/* 과제물 겸 문제) 관리자 자료실 목록 소스를 참조해서 관리자 공지 목록만을 만들어 보자. 
	 */

	//관리자 공지목록
	@RequestMapping("/admin_gongji_list")
	public ModelAndView admin_gongji_list(@ModelAttribute PageVO p,HttpServletRequest request,
			HttpSession session,HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=UTF-8");

		if(isAdminLogin(session, response)) {//관리자로 로그인 된 경우
			int page=1;//쪽번호
			int limit=7;//한페이지에 보여지는 목록개수
			if(request.getParameter("page") != null) {
				page=Integer.parseInt(request.getParameter("page"));			
			}
			String find_name=request.getParameter("find_name");//검색어
			String find_field=request.getParameter("find_field");//검색
			//필드
			p.setFind_field(find_field);
			p.setFind_name("%"+find_name+"%");
			//%는 오라클 와일드 카드 문자로서 하나이상의 임의의 문자와
			//매핑 대응

			int listcount=this.adminGongjiService.getListCount(p);
			//전체 레코드 개수 또는 검색전후 레코드 개수
			//System.out.println("총 게시물수:"+listcount+"개");

			p.setStartrow((page-1)*7+1);//시작행번호
			p.setEndrow(p.getStartrow()+limit-1);//끝행번호

			List<GongjiVO> glist=this.adminGongjiService.getGongjiList(p);
			//관리자 공지 검색 전후 목록

			//총페이지수
			int maxpage=(int)((double)listcount/limit+0.95);
			//현재 페이지에 보여질 시작페이지 수(1,11,21)
			int startpage=(((int)((double)page/10+0.9))-1)*10+1;
			//현재 페이지에 보여줄 마지막 페이지 수(10,20,30)
			int endpage=maxpage;
			if(endpage > startpage+10-1) endpage=startpage+10-1;

			ModelAndView listM=new ModelAndView("admin/admin_gongji_list");

			listM.addObject("glist",glist);
			//glist 키이름에 값 저장
			listM.addObject("page",page);
			listM.addObject("startpage",startpage);
			listM.addObject("endpage",endpage);
			listM.addObject("maxpage",maxpage);
			listM.addObject("listcount",listcount);	
			listM.addObject("find_field",find_field);
			listM.addObject("find_name", find_name);

			return listM;
		}
		return null;
	}//admin_gongji_list()


	//관리자 공지 작성
	@RequestMapping("/admin_gongji_write")
	public ModelAndView admin_gongji_write(HttpServletResponse response,
			HttpSession session,HttpServletRequest request) throws Exception{
		response.setContentType("text/html;charset=UTF-8");

		if(isAdminLogin(session, response)){
			int page=1;
			if(request.getParameter("page") != null) {
				page=Integer.parseInt(request.getParameter("page"));
			}

			ModelAndView wm=new ModelAndView();
			wm.addObject("page",page);//페이징 목록에서 책갈피 기능을 구현하기 위한것
			wm.setViewName("admin/admin_gongji_write");
			return wm;
		}
		return null;
	}//admin_gongji_write()


	//관리자 공지 저장
	@RequestMapping("/admin_gongji_write_ok")
	public ModelAndView admin_gongji_write_ok(GongjiVO g,HttpSession session,
			HttpServletResponse response) throws Exception{
		/* 네임 피라미터 이름과 빈클래스 변수명이 같으면 GongjiVO g에서 g객체에 이름,공지제목,비번,공지내용까지 
		 * 저장되어 있다.
		 */
		response.setContentType("text/html;charset=UTF-8");

		if(isAdminLogin(session, response)){
			this.adminGongjiService.insertGongji(g);//공지 저장
			/* 문제) 스프링 MVC구조 흐름도에 맞게 컨트롤러,서비스,모델 dao,매퍼태그까지 관리자 공지 저장되게 만들어보자.
			 * 개발자 테스트까지 한다.
			 */

			return new ModelAndView("redirect:/admin_gongji_list");
		}
		return null;
	}//admin_gongji_write_ok()


	//관리자 공지 수정과 상세정보
	@RequestMapping("/admin_gongji_cont")
	public ModelAndView admin_gongji_cont(HttpServletRequest request,
			HttpServletResponse response,HttpSession session,
			@RequestParam("no") int no,
			@RequestParam("state") String state)
					throws Exception{
		response.setContentType("text/html;charset=UTF-8");

		if(isAdminLogin(session, response)){
			int page=1;
			if(request.getParameter("page") != null) {
				page=Integer.parseInt(request.getParameter("page"));		
			}
			GongjiVO g=this.adminGongjiService.getGongjiCont(no);
			String g_cont=g.getGongji_cont().replace("\n","<br/>");
			//textarea영역에서 엔터키 친 부분을 다음줄로 줄바꿈

			ModelAndView cm=new ModelAndView();
			cm.addObject("g",g);
			cm.addObject("g_cont",g_cont);
			cm.addObject("page",page);

			if(state.equals("cont")) {//내용보기
				cm.setViewName("admin/admin_gongji_cont");
			}else if(state.equals("edit")) {//수정폼
				cm.setViewName("admin/admin_gongji_edit");
			}
			return cm;
		}
		return null;
	}//admin_gongji_cont()


	//관리자 공지 수정완료
	@RequestMapping("/admin_gongji_edit_ok")
	public ModelAndView admin_gongji_edit_ok(GongjiVO g,
			HttpServletRequest request,
			HttpServletResponse response,HttpSession session)
					throws Exception{
		response.setContentType("text/html;charset=UTF-8");

		if(isAdminLogin(session, response)) {
			int page=1;
			if(request.getParameter("page") != null) {
				page=Integer.parseInt(request.getParameter("page"));
			}
			this.adminGongjiService.editGongji(g);//공지 수정
			return new ModelAndView("redirect:/admin_gongji_list?page="+page);
		}
		return null;
	}//admin_gongji_edit_ok()


	//관리자 공지 삭제
	@RequestMapping("/admin_gongji_del")
	public String admin_gongji_del(int no,
			HttpServletResponse response,HttpSession session,HttpServletRequest request)
					throws Exception{
		response.setContentType("text/html;charset=UTF-8");		

		if(isAdminLogin(session, response)) {
			int page=1;
			if(request.getParameter("page") != null) {
				page=Integer.parseInt(request.getParameter("page"));		
			}
			this.adminGongjiService.delGongji(no);//공지 삭제

			return "redirect:/admin_gongji_list?page="+page;
		}
		return null;
	}//admin_gongji_del()

	//관리자 로그아웃되었을 때 반복적인 코드를 안하기 위한 부분 코드 추가
	public static boolean isAdminLogin(HttpSession session, HttpServletResponse response)
			throws Exception{
		PrintWriter out=response.getWriter();
		String admin_id=(String)session.getAttribute("admin_id");//관리자 세션아이디를 구함

		if(admin_id == null) {//관리자 로그아웃 되었을 때
			out.println("<script>");
			out.println("alert('관리자로 다시 로그인 하세요!');");
			out.println("location='admin_login';");
			out.println("</script>");

			return false;
		}
		return true;
	}//isAdminLogin()
}











