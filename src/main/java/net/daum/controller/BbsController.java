package net.daum.controller;

import com.oreilly.servlet.MultipartRequest;
import lombok.RequiredArgsConstructor;
import net.daum.service.BbsService;
import net.daum.vo.BbsVO;
import net.daum.vo.PageVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
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
        int page=1;
        int limit=10; //한페이지 보여지는 아이템갯수 10개
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }

        int totalCount = bbsService.getRowCount(); //총 레코드갯수

        //페이징
        p.setStartrow((page - 1) * 10 + 1); //시작행 번호
        p.setEndrow(p.getStartrow()+limit-1); //끝행 번호

        List<BbsVO> blist = bbsService.getBbsList(p); //검색전 목록

        //총 페이지수
        int maxpage=(int)((double)totalCount/limit+0.95);
        //시작페이지(1,11,21 ..)
        int startpage=(((int)((double)page/10+0.9))-1)*10+1;
        //현재 페이지에 보여질 마지막 페이지(10,20 ..)
        int endpage=maxpage;
        if(endpage>startpage+10-1) endpage=startpage+10-1;
        
        ModelAndView listM = new ModelAndView("bbs/bbs_list");
        listM.addObject("blist", blist);
        
        //페이징
        listM.addObject("page", page); //현재 페이지
        listM.addObject("startpage", startpage); //시작페이지
        listM.addObject("endpage", endpage); //마지막페이지
        listM.addObject("maxpage", maxpage); //최대페이지
        listM.addObject("listcount", totalCount); //검색전후 레코드갯수

        return listM;
    }
}
