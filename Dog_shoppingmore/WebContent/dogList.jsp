<!-- 혜753p-개 상품 목록(dogList)과 오늘 본 개 상품 목록(todayImageList)을 출력해주는 뷰페이지 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isELIgnored="false"%> <!-- isELIgnored="false" 생략가능(최신이클립스에서 자동 설정) -->

<%-- <%@page import="vo.Dog"%> --%> <!-- 추가 -->
<%-- <%@page import="java.util.ArrayList"%> --%> <!-- 추가 -->

<!-- 먼저, 3개의 jar를 다운받아 lib에 붙여넣기 -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
#listForm{
	margin: auto;
	width: 700px;
	height: 500px
	border: 1px solid red;
}

h2{
	text-align:center;
}

table{
	margin: auto;
	width: 550px;
}

/*각 상품의 이미지 스타일 */
#productImage{
 	width: 150px;
 	height: 150px;
 	border: none;
}

#todayImageList{
	text-align: center;
}

/*오늘 본 상품의 이미지 스타일 */
#todayImage{
 	width: 100px;
 	height: 100px;
 	border: none;
}

.div_empty{	
	width: 100%;
	height: 100%;
	text-align:	center;
	background-color: yellow;
}
</style>
</head>
<body>
<section id="listForm">
    <!-- EL언어 사용하면 request.getAttribute("dogList");없이 바로 접근하여 사용가능 -->
    <!-- request영역에 dogList라는 이름으로 공유되어 있는 속성에 접근하니 -->
	<c:if test="${dogList != null}"> <!-- 1.개 상품목록이 있으면 -->
		<h2>
		개 상품 목록   <a href="dogRegistForm.dog">개상품등록(관리자모드)</a>
		</h2>
		<table>
			<tr> <!-- var:반복할 변수이름, items: 반복할 객체 이름, 
			          varStatus: 반복상태 속성 (status.index:0부터 시작)-->
			<c:forEach var="dog" items="${dogList}" varStatus="status"> <!-- 향상된 for -->
				<td>
					<a href="dogView.dog?id=${dog.id}"><img src="images/${dog.image}" id="productImage" /></a>
					상품명: ${dog.kind}<br/>
					가격 :${dog.price}<br/>
				</td>
				<!-- 개 상품 목록을 출력할 때 1줄에 4개씩만 나란히 출력되도록 하기 위해 -->
				<c:if test="${((status.index+1) mod 4) == 0 }">
					</tr>
					<tr>
					<!-- 1 mod 4==0, 2 mod 4==0, 3 mod 4==0 (거짓) </tr><tr> 실행안되고
					     4 mod 4==0 (참) </tr>실행되어 줄만드는 것을 마무리한후 다시<tr>로 줄만들기 시작 후 for문으로 다시 실행...-->
				</c:if>
			</c:forEach>
			</tr>		
		</table>
	</c:if>
	
	<c:if test="${dogList == null}"> <!-- 2.개 상품목록이 없으면 -->
		<div class="div_empty">개상품이 없습니다. 분양불가</div>
	</c:if>
	
	<!-- 3.쿠키객체들(오늘 본 개 상품 이미지)에서 얻어온 "이미지 파일이름들"을 추가한 todayImageList -->
	<c:if test="${(todayImageList != null) && (dogList != null) }"> <!--개상품목록에서 "개이미지"를 클릭하면 -->	
		<div id="todayImageList">
			<h2>오늘 본 개 상품 목록</h2>
			<table>
				<tr>
				<c:forEach var="todayImage" items="${todayImageList}" varStatus="status">
					<td><img src="images/${todayImage}" id="todayImage"/></td>
					<c:if test="${((status.index+1) mod 4) == 0 }">
						</tr>
						<tr>
					</c:if>
				</c:forEach>
				</tr>
			</table>
		</div>	
	</c:if> 
</section>

</body>
</html>







