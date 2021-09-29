package action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.DogCartSearchService;
import vo.ActionForward;
import vo.Cart;

public class DogCartSearchAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO 최하검색과 최고검색을 만들었다
		DogCartSearchService dogCartSearchService = new DogCartSearchService(); //svc안에 있는 DogCartSearchService에 넘어가야한다
		
		//String타입을 int로 번경(왜? >=, <=와 같은비교연산자를 사용하기 위해)
		//parameter에 담겨서 온걸 받아야지?
		int startMoney = Integer.parseInt(request.getParameter("startMoney")); //최하 금액
		int endMoney = Integer.parseInt(request.getParameter("endMoney"));		//최고 금액
		
		//DogCartSearchService안에 있는 3개자를 다 가지고왔다
		//ArrayList<Cart> searchCartList = dogCartSearchService.getCartSearchList(startMoney,endMoney,request);
		ArrayList<Cart> cartList = dogCartSearchService.getCartSearchList(startMoney,endMoney,request);
		/* ---책 769p : "검색한 항목에 대한 총 금액  계산 			*/
		//지역변수 초기화
		int totalMoney = 0; //검색한 항목의 지불할 총 금액
		int money = 0; //검색한 항목 하나에 대한 지불 금액 .   
		
		for(int i=0;i<cartList.size() ;i++) {
			//푸들 가격 1000 * 수량 1 이라는 소리이다  ->그다음   불독의 가격 2000 * 1
			money = cartList.get(i).getPrice()*cartList.get(i).getQty(); //
			totalMoney += money; //0+1000+2000 = 3000원이 나온다
		}
		/**********		dogCartList.jsp 뷰페이지로 총금액(totalMoney)만 request영역에 공유하여	디스패치 방식으로 보냈다	***************************************************/
		request.setAttribute("totalMoney", totalMoney);//총 금액을 담아서 jsp파일에 던져줄라고 작성햇다
		request.setAttribute("cartList", cartList);//넣는게 맞다! 책에는 없는것인데 넣는게 맞다 왜? 위에ArrayList<Cart>를 다시 받아왔기에 작성해주는게 맞다
		request.setAttribute("startMoney", startMoney);//이거 한 이유!!! 1000원 3000원 검색을 했을때 딱 그렇게 셋팅 시켜 놓을라고 !!!  검색에 사용된 시작 금액을 request영역에 속성으로 공유
		request.setAttribute("endMoney", endMoney);//검색에 사용된 마지막 금액을 request영역에 속성으로 공유한 후
		ActionForward forward = new ActionForward("dogCartList.jsp", false);
		return forward;
	}

}



