package svc;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import vo.Cart;

public class DogCartQtyUpService {
	//여기는 장바구니 수량 올리는 곳이다
	
	//멤버변수
	
	//기본생성자
	
	//메서드
	public void upCartQty(String kind, HttpServletRequest request){ //HttpServletRequest request = 장바구니에 정보들이 들어있다!!!!
		//장바구니는 request객체에서 session영역을 얻어온 후
		HttpSession session =  request.getSession();
		
		//session영역에 공유한 cartList속성값인 "장바구니 목록 객체"를 얻어와
		ArrayList<Cart> cartList = (ArrayList<Cart>)session.getAttribute("cartList");
		
		/*---  수량을 증가시킬 대상 장바구니객체를 kind값으로 비교하여 검색한 후 수량을 증가 시킨다  */
		for(int i=0;i<cartList.size();i++) {
			if(cartList.get(i).getKind().equals(kind)) {//get(i)=배열에있는값현재는0이다
				cartList.get(i).setQty(cartList.get(i).getQty()+1);
				break;//1증가시킨 후 반복문을 빠져 나감. break를 안적으면 for문을 계속 돈다
			}
		}
	}
}
