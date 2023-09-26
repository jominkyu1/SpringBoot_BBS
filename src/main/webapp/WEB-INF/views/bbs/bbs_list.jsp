<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
  <title>자료실 목록</title>
  <link rel="stylesheet" type="text/css" href="./css/board.css">
</head>
<body>
<div id="bList_wrap">
  <h2 class="bList_title">사용자 자료실 목록</h2>
  <div class="bList_count">글개수: ${listcount}</div>
  <table id="bList_t">
    <tr>
      <th width="6%" height="26">번호</th>
      <th width="50%">제목</th>
      <th width="14%">작성자</th>
      <th width="17%">작성일</th>
      <th width="14%">조회수</th>
    </tr>
    
    <c:if test="${!empty blist}">
      <c:forEach var="b" items="${blist}">
        <tr>
          <td align="center">${b.bbs_ref}</td>
          <td align="center"><a
              href="bbs_cont?bbs_no=${b.bbs_no}&state=cont&page=${page}">
              ${b.bbs_title}</a></td>
          <td align="center">${b.bbs_name}</td>
          <td align="center">${fn:substring(b.bbs_date,0,10)}</td>
            <%-- 0이상 10미만 사이의 년월일만 반환 --%>
          <td align="center">${b.bbs_hit}</td>
        </tr>
      </c:forEach>
    </c:if>
    
    <c:if test="${empty blist}">
      <tr>
        <th colspan="5">자료실 목록이 없습니다.</th>
      </tr>
    </c:if>
  </table>
  
  <%--페이징(쪽나누기)--%>
  <div id="bList_paging">
    <%--검색전 페이징 --%>
    <c:if test="${(empty find_field)&&(empty find_name)}">
      <c:if test="${page <=1}">
        [이전]&nbsp;
      </c:if>
      <c:if test="${page >1}">
        <a href="bbs_list?page=${page-1}">[이전]</a>&nbsp;
      </c:if>
      
      <%--쪽번호 출력부분 --%>
      <c:forEach var="a" begin="${startpage}" end="${endpage}" step="1">
        <c:if test="${a == page}"><${a}></c:if>
        
        <c:if test="${a != page}">
          <a href="bbs_list?page=${a}">[${a}]</a>&nbsp;
        </c:if>
      </c:forEach>
      
      <c:if test="${page>=maxpage}">[다음]</c:if>
      <c:if test="${page<maxpage}">
        <a href="bbs_list?page=${page+1}">[다음]</a>
      </c:if>
    </c:if>
    
    <%-- 검색후 페이징(쪽나누기) --%>
    <c:if test="${(!empty find_field) || (!empty find_name)}">
      <c:if test="${page <=1}">
        [이전]&nbsp;
      </c:if>
      <c:if test="${page >1}">
        <a href="bbs_list?page=${page-1}">[이전]</a>&nbsp;
      </c:if>
      
      <%--쪽번호 출력부분 --%>
      <c:forEach var="a" begin="${startpage}" end="${endpage}" step="1">
        <c:if test="${a == page}"><${a}></c:if>
        
        <c:if test="${a != page}">
          <a href="bbs_list?page=${a}">[${a}]</a>&nbsp;
        </c:if>
      </c:forEach>
      
      <c:if test="${page>=maxpage}">[다음]</c:if>
      <c:if test="${page<maxpage}">
        <a href="bbs_list?page=${page+1}">[다음]</a>
      </c:if>
    </c:if>
  </div>
  
  <div id="bList_menu">
    <input type="button" value="글쓰기"
           onclick="location='bbs_write?page=${page}';" />
  </div>

</div>
</body>
</html>