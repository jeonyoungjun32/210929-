<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page isELIgnored="false" %> <!-- 최신 이클립스는 자동으로 설정해줘서 생략 가능하다 -->
    
    <!-- 페이지 지시자에 "core라이브러리를 사용 하겠다"하고 JSTL 선언해야 함. c 접두어로 시작해야함 -->
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <!-- 이걸 해줘야 쓸수 있다 -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>★★★	장바구니 목록임!!	★★★이제 보이게 할라고 css해보자잉!! 아 주의!! shopMain만은 폴더에 넣으면 안된다!! 왜 ? index이닌깐 넣으면 안된다</title>

<!-- dogList.jsp의 css을 가져왔다 -->
<style type="text/css">
#listForm{
	margin: auto;
	width: 640px;
	border: 1px solid red;
}

h2{
	text-align:center;
}

table{
	margin: auto;
	width: 550px;
}
.tr_top{
	background-color: #F8E0F7;
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

/*	장바구니 스타일 */
#cartImage{
 	width: 70px;
 	height: 70px;
 	border: none;
}
#select{
	text-align: right;
}
#commandList{
	text-align: center;
}
#upImage{/* 수량에 업 버튼 css */
	width: 15px;
}
#downImage{ /* 수량에 다운 버튼 css */
	width: 15px;
}

.div_empty{	/* 장바구니목록에 비었습니다 꺼임 */
	width:100%;
	height: 100%;
	text-align:	center;
	background-color: yellow;
}
.td_command{
	text-align: right;
}
</style>
</head>
<script type="text/javascript">
//전체 체크박스를 통해서 각 장바구니 항목의 체크박스를 체크 또는 해제하는 함수(메서드)!!
//remove한 이유 한개 체크하고 삭제 하고 그렇게 할라고 . ★★★★  즉 이것은 전체체크 전체체크해제 할라고 만든것이다!!★★★★★★★★★★★★
function checkAll(theForm) {/* theForm하면 전부 매개값을 던져주고있다  */
	if(theForm.remove.length == undefined){/* undefined = 정의가 되지않았다 */ //장바구니 항목이 선택하는 체크박스가 1개일 때 length가 undefined(이유 length는 결과가 배열일 때 존재한다)
		theForm.remove.checked = theForm.allCheck.checked;//전부체크한 all체크가 true이면  전부다 체크할라고 true한다   여러개는배열(remove[i])로 들어간다 
	}else {//장바구니 항목을 선택하는 체크박스가 한개일때 undefined로 나오고 여러개일대는 배열객체로들어간다.  
		//length가 존재한다면 실행 하는곳 
		for(var i=0;i<theForm.remove.length;i++){//i가 전체(theForm)
			theForm.remove[i].checked =  theForm.allCheck.checked; // remove로 받은걸 똑같이 체크 하겠다!!
		}
	}
}
//encodeURIComponent() = 모든 문자를 인코딩하는 함수 
//encodeURI() 		   = 인터넷 주소에서 사용하는 : ; / = ? & 등을 제외하고 인코딩 하는 함수
		
//decodeURIComponent() = encodeURIComponent()로 인코딩한 문자열을 디코딩하는 함수!
//decodeURI() 		   = encodeURI()로 인코딩한 문자열을 디코딩하는 함수!

function checkQty(kind, qty) {//수량에 있는 버튼 . kind왜썻냐 개종류가 기준이다 
	if(qty != 1){//즉 1보다 크면!! 해야지 수량이0개 일수는 없으닌깐
		//장바구니 항목 수량 감소 요청을 함. ★★★★ 이때 kind값이 한글이면 한글이 깨지지 않도록 인코딩처리하여 한글파라미터로 전송
		//location.href="dogCartQtydown.dog?kind="+encodeURIComponent(kind);//kind값을 utf-8로 인코딩됨
		location.href="dogCartQtyDown.dog?encodingKind="+encodeURIComponent(kind);//kind값을 utf-8로 인코딩됨
	}
}
</script>
<body>
<!-- 젤첨에 장바구니뜨는건 검색 작업이 하지 않은것 -->
<!-- 검색에 사용되는 startMoney값과 최고머니는endMoney값을 속성으로 설정하는 부분
이때, 검색작업을 하지 않고  장바구니 목록 보기 페이지가 실행된 경우는 이 값들이 null이기 때문에
해당 속성들을 사용할 때 NullPoingterException이 발생한다.
따라서, NullPoingterException이 발생하지 않도록 if문으로 처리 해줌!! -->
<!-- mini1000까지 -->
<c:if test="${startMoney != null }">
<c:set var="startMoney" value="${startMoney }"></c:set>
</c:if>

<!-- max4000까지 있는것 -->
<c:if test="${endMoney != null }">
<c:set var="endMoney" value="${endMoney }"></c:set>
</c:if>

<%-- <%(ArrayList)request.getAttribute("cartList") %>  옛날에는 이렇게 request에 get써서 가지고왔는데 --%>
<section id="listForm"> <!-- 장바구니에 아무것도 없이 올수도 있다 -->
<!-- request안에 totalMoney와 cartList 을 가지고 왔다 -->
<c:if test="${cartList != null && cartList.size() > 0 }"> <!-- 장바구니가 널이 아니거나     cartList.size > 0 = 장바구니안에 뭐라도 담겨져있으면 -->
<h2>장바구니 목록</h2>
<!-- 767P 최하 최고 금액순 보이게 하는거  -->
<!-- c:choose = java코드여서 주석이 안된다 -->
<form action="" method="post">
	<table>
		<tr id="select"> 
			<td colspan="6">
				<select name="startMoney">
					<option>=최하=</option>
					<!-- choose = JSTL임 즉 java언어로 변환되어 주석처리는 인터넷에 쳐보라! -->
					<!--  switch문 case..default -->
						<c:choose>
							<c:when test="${startMoney == 1000 }"> 
								<option selected ="selected">1000</option>
								<option>2000</option>
								<option>3000</option>
								<option>4000</option>
							</c:when>
							<c:when test="${startMoney == 2000 }">
								<option>1000</option>
								<option selected ="selected">2000</option>
								<option>3000</option>
								<option>4000</option>
							</c:when>
							<c:when test="${startMoney == 3000 }"> 
								<option>1000</option>
								<option>2000</option>
								<option selected ="selected">3000</option>
								<option>4000</option>
							</c:when>
							<c:when test="${startMoney == 4000 }">
								<option>1000</option>
								<option>2000</option>
								<option>3000</option>
								<option selected ="selected">4000</option>
							</c:when>
							<c:otherwise> 
								<option>1000</option>
								<option>2000</option>
								<option>3000</option>
								<option>4000</option>
							</c:otherwise>
						</c:choose>
				</select>
				
				<select name="endMoney">
					<option>=최고=</option>
						<c:choose>
							<c:when test="${endMoney == 1000 }"> <!-- switch문의 case이다! -->
								<option selected ="selected">1000</option>
								<option>2000</option>
								<option>3000</option>
								<option>4000</option>
							</c:when>
							<c:when test="${endMoney == 2000 }"> 
								<option>1000</option>
								<option selected ="selected">2000</option>
								<option>3000</option>
								<option>4000</option>
							</c:when>
							<c:when test="${endMoney == 3000 }"> <!-- switch문의 case이다! -->
								<option>1000</option>
								<option>2000</option>
								<option selected ="selected">3000</option>
								<option>4000</option>
							</c:when>
							<c:when test="${endMoney == 4000 }"> <!-- switch문의 case이다! -->
								<option>1000</option>
								<option>2000</option>
								<option>3000</option>
								<option selected ="selected">4000</option>
							</c:when>
							<c:otherwise> <!-- default문 즉 아무것도 선택이 안되면!! -->
								<option>1000</option>
								<option>2000</option>
								<option>3000</option>
								<option>4000</option>
							</c:otherwise>
				
						</c:choose> <!-- 이거 빼도 된다잉!! -->
				</select>
				<!-- [검색] 버튼을 클릭하면 '최하가격~최고가격으로 장바구니 항목을 다사검색하는 요청!'-->
				<input type="submit" value="검색" formaction="dogCartSearch.dog"/> <!-- 다시 처리 할수 있도록 dogCartSearch.dog을 만들어줘야함 -->
				
				
			</td>
		</tr>
		<!-- 가격별 검색부분 처리 (끝) -->
		
		<tr class="tr_top">
			<td><!-- 맨위 체크박스만들기 전부체크되거나 전부체크 해제 하는거 만든다 checkAll-->
				<input type="checkbox" name="allCheck" onclick="checkAll(this.form)"><!-- 체크박스를 form을 주겟다 -->
			</td>
			
			<td>번호</td>
			<td>상품 이미지</td>
			<td>상품명</td>
			<td>가격</td>
			<td>수량</td>
		</tr>
		
		<!-- 이제 cartList에 있는걸 뿌려줘야함 -->
		<%-- <%request.getParameter("cartList") %>를 안서도된다--%>
		
		<!-- 향상된 for문 시작!-->
		<%-- <%for(Cart cart : cartList){} %> -와 같다 --%>
			<!-- cart안에 첫번째로 푸들이 들어있다 -->
			<!-- 있으면 줄이 만들어지고 items = 참조하는 객체,  cartList를 가져왔다 -->
		<c:forEach var="cart" items="${cartList }" varStatus="status" > 
			<tr>
				<td><input type="checkbox" name="remove" value="${cart.kind }"></td> <!-- kind가 아이스크림id로 들어가도된다!! -->
				<!-- status.index+1 = 1번 2번 3번 이렇게 차근차근하게 번호가 생긴다!! -->
				<td>${status.index+1}</td> <!-- jsp에서 index를 어떻게 찾을까?? varStatus을써서 staus.index = 0부타 시작하는게 생긴다-->
				<td><img src="images/${cart.image}" id="cartImage"> </td> <!--자바로 따지면 <5= %>과 같다  -->
				<td>${cart.kind } </td>
				<td>${cart.price } </td>
				<td> <!--★★★★★★★★★    ${cart.kind }그냥 보내면 utf-8로 설정 안되어 있어서 찾을수가 없다 ★★★★★★★ 그래서 utf-8로 할라고 메서드 하나 만든다!!  -->
					<!-- ▲클릭시 : '장바구니 항목의 수량 증가' 요청(이 때, utf-8로 인코딩 처리된 kind값을 파라미터로 전송해야 함) -->
					<a href="dogCartQtyUp.dog?encodingKind=${cart.encodingKind }"><img src="images/up.jpg" id="upImage" border=0></a> <!-- 수량 1씩 증가할라면 qtyup을 요청한다 -->
					<br> 
					${cart.qty} <!-- 현재수량! -->
					<br>          
					<!-- ▼클릭시 : '장바구니 항목의 수량 감소' 요청하기위해 함수호출checkQty()!(이 때, kind값과 현재수량을 매개값으로 전송해야 함) -->
					<a href="javascript:checkQty('${cart.kind }','${cart.qty }')"><img src="images/down.jpg" id="downImage" border=0></a> <!-- 수량 1씩 증가할라면 qtyup을 요청한다 -->
						
				 </td>
			</tr>
		</c:forEach>
		<!-- 향상된 for문 끝 -->
		
		<tr>
			<td colspan="6" align="center" >총 금액 : ${totalMoney}원</td> 
			
		</tr>
		<tr>
			<td colspan="6" align="center">
				<input type="submit" value="삭제" formaction="dogCartRemove.dog">
			</td>
		</tr>
		
	</table>
</form>
</c:if>
<!------------------------------------------------------  -->
<c:if test="${cartList == null }">
	<section class="div_empty">장바구니목록에 비어있습니다.</section> 
</c:if>
<!------------------------------------------------------  -->
<nav id="">
	<a href="dogList.dog">쇼핑 계속하기</a>
</nav> 
</section>



</body>
</html>













