package action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.DogCartListService;
import vo.ActionForward;
import vo.Cart;

public class DogCartListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO 장바구니 목록 리스트 보는 곳 이다
		DogCartListService dogCartListService = new DogCartListService();  // svc에 DogCartListService생성 후 다시 와야 한다
		//DogCartListService에서 작성 후 
		//주의 : 매개값으로 request 전송이유? session영역에 공유되어 있는  "장바구니 목록객체(cartList) "을 얻어오기 위해
		//먼저 session먼저 얻어오고 cartList를 받을수 있다
		ArrayList<Cart> cartList =  dogCartListService.getCartList(request); // 장바구니 메서드를 얻어왔다 request가 있어야 값을 받아올수 있다
		
		/**************		762p  : 총 금액 계산 		*****************************************/
		int totalMoney = 0; //지불할 총 금액
		int money = 0; //장바구니 항목 하나에 대한 지불 금액 .   개 하나당 금액!!
		
		for(int i=0;i<cartList.size() ;i++) {
			//불록의 가격 2000 * 수량 1 이라는 소리이다  ->그다음   진도개의 가격 3000 * 2 
			money = cartList.get(i).getPrice()*cartList.get(i).getQty(); //
			totalMoney += money; //0이였다가 볼독2천원 받고 진도개2마리 6000 해서  0+2000+6000이다잉
		}
		
		/**********		dogCartList.jsp 뷰페이지로 총금액(totalMoney)과 전체 장바구니 목록(cartList)을 request영역에 공유하여	디스패치 방식으로 보냈다	***************************************************/
		request.setAttribute("totalMoney", totalMoney);//총 금액을 담아서 jsp파일에 던져줄라고 작성햇다
		request.setAttribute("cartList", cartList);//cartList = DB에서 가져온 정보들!!
	ActionForward forward =	new ActionForward("dogCartList.jsp", false); // false로 해서 디스패치 방식으로 해야 된다!! 이유 = jsp에서 바로 보이게 할라고 값을 안준다
		//디스패치 방식으로 포워딩 함!!
		
		return forward;
	}

}
