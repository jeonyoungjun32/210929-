package svc;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import vo.Cart;

public class DogCartListService {// 1. DogCartListAction에서 왔다
	
	//멤버변수
	
	//기본 생성자
	
	//메서드
	public ArrayList<Cart> getCartList(HttpServletRequest request){//주의 : 매개값을 request
		
	
	//request로 부터 요청한 클라이언트의 session영역을 얻어와!!
	HttpSession session = request.getSession();
	ArrayList<Cart> cartList = (ArrayList<Cart>) session.getAttribute("cartList"); // cartList는 어디서 셋팅 했냐 하면 DogCartAddService 에서 셋팅 시킨것이다
	
	return cartList;
	}	
}
