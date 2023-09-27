package net.daum.controller;

import com.oreilly.servlet.MultipartRequest;
import lombok.RequiredArgsConstructor;
import net.daum.service.BbsService;
import net.daum.vo.BbsVO;
import net.daum.vo.PageVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

@Controller
@RequiredArgsConstructor
public class BbsController {

    private final BbsService bbsService;

    //자료실 글쓰기 폼
    @GetMapping("/bbs_write")
    public ModelAndView bbs_write(HttpServletRequest request) {
        //페이징에서 내가 본 페이지번호로 바로 이동하는 책갈피 기능
        int page = 1;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
            //get으로 전달된 쪽번호가 있는경우 정수숫자로 변경해서 저장
        }

        ModelAndView mv = new ModelAndView();
        mv.addObject("page", page); //책갈피기능
        mv.setViewName("bbs/bbs_write");

        return mv;
    }

    @PostMapping("/bbs_write_ok")
    public String bbs_write_ok(BbsVO b, HttpServletRequest request) throws Exception {
        String saveFolder = request.getRealPath("upload"); //이진파일 업로드 서버 경로

        int fileSize = 5 * 1024 * 1024;
        MultipartRequest multi = null; //이진파일 업로드 참조변수
        multi = new MultipartRequest(request, saveFolder, fileSize, "UTF-8");

        String bbs_name = multi.getParameter("bbs_name");
        String bbs_title = multi.getParameter("bbs_title");
        String bbs_pwd = multi.getParameter("bbs_pwd");
        String bbs_cont = multi.getParameter("bbs_cont");

        File upFile = multi.getFile("bbs_file"); //첨부한 이진파일 가져옴

        if (upFile != null) {
            String fileName = upFile.getName(); //첨부한 파일명
            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH) + 1; //1월 -> 0
            int date = c.get(Calendar.DATE);

            String homedir = saveFolder + "/" + year + "-" + month + "-" + date;
            File path01 = new File(homedir);

            if (!(path01.exists())) {
                path01.mkdir();
            }

            Random r = new Random();
            int random = r.nextInt(100000000); //0이상 1억미만 정수숫자 난수

            int index = fileName.lastIndexOf(".");
            String fileExtendsion = fileName.substring(index + 1);
            //.이후부터 마지막문자까지(확장자명)
            String refileName = "bbs" + year + month + date + random + "." + fileExtendsion;
            // 첨부파일명을 변경하여 저장
            String fileDBName = "/" + year + "-" + month + "-" + date + "/" + refileName;
            //데이터베이스 저장용

            upFile.renameTo(new File(homedir + "/" + refileName));
            //변경된 파일명으로 실제 업로드

            b.setBbs_file(fileDBName); //데이터베이스에 저장될 레코드값

        } else { //첨부파일이 존재하지않을경우
            String fileDBName = "";
            b.setBbs_file(fileDBName);
        }

        b.setBbs_name(bbs_name);
        b.setBbs_title(bbs_title);
        b.setBbs_pwd(bbs_pwd);
        b.setBbs_cont(bbs_cont);

        bbsService.insertBbs(b); //자료실 저장

        return "redirect:/bbs_list";
    }


    //자료실 리스트 (페이징과 검색기능)
    @RequestMapping("/bbs_list")
    public ModelAndView bbs_list(HttpServletRequest request, PageVO p) {
        int page = 1;
        int limit = 10; //한페이지 보여지는 아이템갯수 10개
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }

        //검색과 관련된 부분
        String find_name = request.getParameter("find_name"); //검색어
        String find_field = request.getParameter("find_field"); //검색필드(select의 option)

        p.setFind_name("%" + find_name + "%"); // %검색어%
        p.setFind_field(find_field);

        int totalCount = bbsService.getRowCount(p); //검색전 총 레코드갯수, 검색후 레코드 갯수

        //페이징
        p.setStartrow((page - 1) * 10 + 1); //시작행 번호
        p.setEndrow(p.getStartrow() + limit - 1); //끝행 번호

        List<BbsVO> blist = bbsService.getBbsList(p); //검색전 목록

        //총 페이지수
        int maxpage = (int) ((double) totalCount / limit + 0.95);
        //시작페이지(1,11,21 ..)
        int startpage = (((int) ((double) page / 10 + 0.9)) - 1) * 10 + 1;
        //현재 페이지에 보여질 마지막 페이지(10,20 ..)
        int endpage = maxpage;
        if (endpage > startpage + 10 - 1) endpage = startpage + 10 - 1;

        ModelAndView listM = new ModelAndView("bbs/bbs_list");
        listM.addObject("blist", blist);

        //페이징
        listM.addObject("page", page); //현재 페이지
        listM.addObject("startpage", startpage); //시작페이지
        listM.addObject("endpage", endpage); //마지막페이지
        listM.addObject("maxpage", maxpage); //최대페이지
        listM.addObject("listcount", totalCount); //검색전후 레코드갯수

        //검색필드저장
        listM.addObject("find_field", find_field);
        listM.addObject("find_name", find_name);

        return listM;
    }

    @GetMapping("/bbs_cont")
    public ModelAndView bbs_cont(int bbs_no, int page, String state, BbsVO b) {

        if (state.equals("cont")) { // 내용가져오기 + 조회수 증가
            b = bbsService.getBbsCont(bbs_no);
        } else { // 답변, 수정, 삭제일때는 조회수 증가 안함
            b = bbsService.getBbsCont2(bbs_no);
        }

        String bbs_cont = b.getBbs_cont().replace("\n", "<br>"); // textarea 줄바꿈 처리

        ModelAndView mv = new ModelAndView();
        mv.addObject("page", page); //책갈피
        mv.addObject("b", b);
        mv.addObject("bbs_cont", bbs_cont); //줄바꿈처리된 글내용 전달

        //폼경로
        switch (state) {
            case "cont":
                mv.setViewName("bbs/bbs_cont");
                break;
            case "reply":
                mv.setViewName("bbs/bbs_reply");
                break;
            case "edit":
                mv.setViewName("bbs/bbs_edit");
                break;
            case "del":
                mv.setViewName("bbs/bbs_del");
                break;
            default:
                mv.setViewName("/bbs_list");
        }

        return mv;
    }

    //답변저장
    @PostMapping("/bbs_reply_ok")
    public String bbs_reply_ok(@ModelAttribute BbsVO rb,
                               @RequestParam int page) {

        bbsService.replyBbs(rb); //답변 레벨 증가와 답변 저장
        return "redirect:/bbs_list?page="+page;
    }

    //자료실 수정
    @PostMapping("/bbs_edit_ok")
    public ModelAndView bbs_edit_ok(HttpServletRequest request,
                                    HttpServletResponse response,
                                    BbsVO b) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        //웹브라우저로 출력되는 문자와 태그 ,언어코딩 타입을 설정
        PrintWriter out=response.getWriter();//출력스트림 out생성
        String saveFolder = request.getRealPath("upload");//수정 첨부되는 이진파일 업로드
        //될 실제 경로를 구함.
        int fileSize = 5*1024*1024;//이진파일 업로드 최대크기
        MultipartRequest multi=null;//첨부된 파일을 받을 참조변수

        multi = new MultipartRequest(request,saveFolder,fileSize,"UTF-8");

        int bbs_no = Integer.parseInt(multi.getParameter("bbs_no"));//히든으로 전달된
        //자료실 번호를 정수 숫자로 변경해서 저장, form태그내에 enctype="multipart/form-data"가
        //지정되어 있어면 request.getParameter()로 못받아 온다. multi로 받아와야 한다.

        int page=1;
        if(multi.getParameter("page") != null) {
            page=Integer.parseInt(multi.getParameter("page"));
        }

        String bbs_name = multi.getParameter("bbs_name");
        String bbs_title = multi.getParameter("bbs_title");
        String bbs_pwd = multi.getParameter("bbs_pwd");
        String bbs_cont = multi.getParameter("bbs_cont");

        BbsVO db_pwd = this.bbsService.getBbsCont2(bbs_no);//조회수가 증가되지 않는 것으로
        //해서 오라클로 부터 비번을 가져옴

        if(!db_pwd.getBbs_pwd().equals(bbs_pwd)) {
            out.println("<script>");
            out.println("alert('비번이 다릅니다!');");
            out.println("history.back();");
            out.println("</script>");
        }else {
            File upFile = multi.getFile("bbs_file");//수정 첨부된 파일을 가져옴
            if(upFile  != null) {//수정 첨부된 파일이 있는 경우
                String fileName=upFile.getName();//첨부된 파일명을 구함.
                File delFile=new File(saveFolder+db_pwd.getBbs_file());//삭제할 파일객체
                //생성
                if(delFile.exists()) {//기존파일이 있다면
                    delFile.delete();//기존 첨부파일 삭제
                }
                Calendar c=Calendar.getInstance();
                int year=c.get(Calendar.YEAR);
                int month=c.get(Calendar.MONTH)+1;
                int date=c.get(Calendar.DATE);

                String homedir=saveFolder+"/"+year+"-"+month+"-"+date;
                File path01=new File(homedir);
                if(!(path01.exists())) {
                    path01.mkdir();//오늘 날짜 폴더 생성
                }

                Random r=new Random();
                int random=r.nextInt(100000000);

                /*첨부파일 확장자를 구함*/
                int index=fileName.lastIndexOf(".");//마침표 위치번호를 구함
                String fileExtendsion = fileName.substring(index+1);//첨부파일에서 확장자만
                //구함

                String refileName = "bbs"+year+month+date+random+"."+fileExtendsion;
                //새로운 파일명 구함
                String fileDBName = "/"+year+"-"+month+"-"+date+"/"+refileName;
                //db에 저장될 레코드값
                upFile.renameTo(new File(homedir+"/"+refileName));//실제 업로드

                b.setBbs_file(fileDBName);
            }else {//수정 첨부파일이 없는 경우
                String fileDBName="";
                if(db_pwd.getBbs_file() != null) {//기존 첨부파일이 있는 경우
                    b.setBbs_file(db_pwd.getBbs_file());
                }else {
                    b.setBbs_file(fileDBName);
                }
            }//if else

            b.setBbs_name(bbs_name); b.setBbs_title(bbs_title);
            b.setBbs_cont(bbs_cont); b.setBbs_no(bbs_no);

            this.bbsService.editBbs(b);//자료실 수정

            ModelAndView em = new ModelAndView("redirect:/bbs_cont");
            em.addObject("bbs_no",bbs_no);
            em.addObject("page",page);
            em.addObject("state","cont");

            return em;//브라우저 주소창에 다음과 같이 실행된다. bbs_cont?bbs_no=번호&page=쪽번호
            //&state=cont 3개의 피라미터 값이 get방식으로 전달된다.=>쿼리스트링 방식임.
        }//if else
        return null;
    }

    @RequestMapping("/bbs_del_ok")
    public ModelAndView bbs_del_ok(int bbs_no,int page,@RequestParam("del_pwd")
    String del_pwd, HttpServletResponse response,HttpServletRequest request)
            throws Exception{
        /**  @RequestParam("del_pwd") 스프링의 애노테이션의 의미는
         *   request.getParameter("del_pwd")와 같은 기능이다.
         */
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out=response.getWriter();
        String delFolder=request.getRealPath("upload");

        BbsVO db_pwd=this.bbsService.getBbsCont2(bbs_no);
        if(!db_pwd.getBbs_pwd().equals(del_pwd)) {
            out.println("<script>");
            out.println("alert('비번이 다릅니다!');");
            out.println("history.go(-1);");
            out.println("</script>");
        }else {
            this.bbsService.delBbs(bbs_no);//자료실 삭제

            if(db_pwd.getBbs_file() != null) {//기존 첨부파일이 있는 경우
                File delFile=new File(delFolder+db_pwd.getBbs_file());//삭제할 파일객체
                //생성
                delFile.delete();//폴더는 삭제 안되고,기존 파일만 삭제됨.
            }

            ModelAndView dm=new ModelAndView();
            dm.setViewName("redirect:/bbs_list?page="+page);
            return dm;
        }
        return null;
    }//bbd_del_ok()
}
