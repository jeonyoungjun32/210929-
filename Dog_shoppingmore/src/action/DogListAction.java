//개 상품 목록보기 요청을 처리하는 Action클래스
package action;

import java.util.ArrayList;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.DogListService;
import vo.ActionForward;
import vo.Dog;

public class DogListAction implements Action{
/*
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		DogListService dogListService = new DogListService();
		ArrayList<Dog> dogList = dogListService.getDogList();
		//request영역에 dogList이름으로 "개 상품 목록 정보"를 속성값으로 공유
		request.setAttribute("dogList", dogList);
		
		//혜-753p-두번째 그림(dogList.jsp) : "오늘본 개 상품 목록" 정보를 알기 위해서는 '개 하나의 상세정보 보기'를 한 후
		Cookie[] cookieArray = request.getCookies();
		ArrayList<String> todayImageList = new ArrayList<String>();
		
		if(cookieArray != null) {
			for(int i=0;i<cookieArray.length;i++) {
				if(cookieArray[i].getName().startsWith("today")) {//getName() : "today1"   "today3"
					todayImageList.add(cookieArray[i].getValue());//getValue() : "pu.jpg"  "jin.jpg"
				}
			}
		}
		//request영역에 todayImageList라는 이름으로 '오늘 본 개 상품 이미지 목록 정보'를 속성값으로 공유
		request.setAttribute("todayImageList", todayImageList);
		
		//dogList.jsp로 디스패치 방식으로 포워딩
		ActionForward forward=new ActionForward("dogList.jsp", false);
		return forward;
	}
*/
	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		/* 전체 개 상품 목록을 보여주는 뷰 페이지에서 오늘 본 상품의 이미지도 출력하므로 
		 * 오늘 본 상품의 이미지를 저장할 ArrayList 객체 생성
		 */
		ArrayList<String> todayImageList = new ArrayList<String>();
		
		/* 클라이언트에서 넘어온 쿠키 객체들을 배열 형태로 반환받는 부분
		 * 사이트에서 오늘 본 상품이 있다면 각 상품의 이미지가 쿠키로 저장되어 있다.
		 */
		Cookie[] cookieArray = request.getCookies();
		
		/* 요청에 넘어온 쿠키 객체 중 오늘 본 상품 이미지 이름을 저장하고 있는 쿠키 객체를 찾아서 
		 * todayImageList ArrayList 객체에 쿠키 객체의 값 즉, 이미지 이름을 요소로 추가한다.
		 * (단, 특정 상품의 자세한 내용을 볼 때 DogListService 클래스에서 내용을 본 상품의 이미지를 
		 * today문자열 뒤에 해당 상품의 아이디를 붙인 이름으로 쿠키 객체에 저장한다.
		 * (▶ DogViewAction.java에서 today문자열 뒤에 해당 상품의 아이디를 붙임))
		 */
		if(cookieArray != null){
			for (int i = 0; i < cookieArray.length; i++) {
				if(cookieArray[i].getName().startsWith("today")){
					todayImageList.add(cookieArray[i].getValue());
				}
			}
		}
		
		//개 상품 목록보기 요청을 처리하는 서비스 객체를 생성한 후//---▶이 파일 소스 작성하기
		DogListService dogListService = new DogListService();
		
		//등록되어 있는 개 상품 정보를 ArrayList타입으로 얻어와
		ArrayList<Dog> dogList = dogListService.getDogList();//---▶이 파일 소스 작성하기
		
		//request영역에 dogList이름으로 '개 상품 목록 정보'를 속성값으로 '공유'하고
		request.setAttribute("dogList", dogList);
		//request영역에 todayImageList이름으로 '오늘 본 개 상품 이미지 목록 정보'를 속성값으로 '공유'하여
		request.setAttribute("todayImageList", todayImageList);
		
		/* 포워딩 정보를  ActionForward 객체로 생성하여 리턴한다.
		 * 포워딩될 페이지는 dogList.jsp페이지이고 포워딩 방식은 디스패치 방식으로 지정함
		 * 따라서 dogList.jsp에서 request요청영역을 공유하여 화면에 출력함
		 */
		//디스패치 포워드 방식(request요청영역 공유함)으로 dogList.jsp페이지로 이동하여 화면에 출력함
		ActionForward forward = new ActionForward("dogList.jsp", false);//---▶이 파일 소스 작성하기
		
		return forward;
	}

}








