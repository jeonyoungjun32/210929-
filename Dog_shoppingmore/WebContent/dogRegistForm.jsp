<!-- ★관리자모드 : 새로운 개 상품 등록 정보를 입력하는 뷰페이지  -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- 754p 그림참조하여 스타일 만들기 -->
<style type="text/css">
#registForm{
	margin:auto;
	width:500px;
	height:600px;
	border:1px solid red;
}

h2{
	text-align:center;
}

table{
	margin: auto;
	width: 450px;
}

.td_left{
	width: 150px;
	background: orange;
}

.td_right{
	width: 300px;
	background: skyblue;
}

#commandList{
	text-align: center;
}

</style>
</head>
<body>
<section id="registForm">
	<header><h2>개 정보 등록</h2></header>
	<!-- 파일 업로드 요청하기 위해 enctype="multipart/form-data"로 지정해야 함 -->
	<form action="dogRegist.dog" method="post" name="writeForm" enctype="multipart/form-data">
		<table>
			<tr>
				<td colspan="2"><a href="dogList.dog">목록보기</a></td>
			</tr>
			<tr>
				<th class="td_left">품종</th> <!-- 장바구니에서 품종으로 구분하여 사용하기 위해 -->
				<td class="td_right"><input type="text" name="kind" required="required"></td>
			</tr>
			<tr>
				<th class="td_left">원산지</th>
				<td class="td_right"><input type="text" name="country"></td>
			</tr>
			<tr>
				<th class="td_left">가격</th>
				<td class="td_right"><input type="text" name="price"></td>
			</tr>
			<tr>
				<th class="td_left">신장</th>
				<td class="td_right"><input type="text" name="height"></td>
			</tr>
			<tr>
				<th class="td_left">체중</th>
				<td class="td_right"><input type="text" name="weight"></td>
			</tr>
			<tr>
				<th class="td_left">글내용</th>
				<td class="td_right">
				    <!-- wrap="off"(자동 줄바꿈 안함) -->
					<textarea name="content" rows="13" clos="40" wrap="off"> 
					</textarea>
				</td>
			</tr>
			<tr>
				<th class="td_left">상품이미지</th>
				<td class="td_right"><input type="file" name="image" required="required"></td>
			</tr>
			<tr>
				<td colspan="2" id="commandCell">
					<input type="submit" value="개상품등록">
					<input type="reset" value="다시작성">
					<input type="button" value="개상품목록보기" onclick="window.location.href='dogList.dog'" />
				</td>
			</tr>
		</table>
	</form>

</section>

</body>
</html>


