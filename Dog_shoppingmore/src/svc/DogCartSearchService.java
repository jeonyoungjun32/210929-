package svc;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import vo.Cart;

public class DogCartSearchService {
	// 멤버변수

	// 기본생성자

	// 메서드
	public ArrayList<Cart> getCartSearchList(int startMoney, int endMoney, HttpServletRequest request) {// 장바구니 가져 올라면 request가 꼭
		//장바구니 항목을 그대로 리턴 하는게 아니라!! 최하가격과  최고가격 그사이에 있는걸  가져온다								// 필요하다
		// request로부터 session영역을 얻어온 후
		HttpSession session = request.getSession();
		// session영역에 공유한 cartList속성값인 "장바구니 목록 객체(ArrayList<Cart>)"를 얻어와
		ArrayList<Cart> cartList = (ArrayList<Cart>) session.getAttribute("cartList");// 공유한 cartList를 얻어왔다!!
		
		//장바구니 목록(oldCartList)을 반복하면서 검색 범위에 장바구니 항목을 찾아서 새로 생성한 ArrayList<Cart>객체에 추가하고 돌려준다!!
		//뭘?! 새로 생성한  ArrayList<Cart>를!!돌려준다잉!
		//해당하는 장바구니 목록을 반복하면서  가격이 검색 가격에 해당하는지 체크!!
		
		//searchCartList = 검색한장바구니리스트
		ArrayList<Cart> searchCartList= new ArrayList<Cart>(); //다 null로 들어 가있는데 oldCartList를 써서 옛날 정보를 가져와
		for(int i = 0;i<cartList.size();i++) {
			if (cartList.get(i).getPrice() >= startMoney && cartList.get(i).getPrice() <= endMoney) {
				searchCartList.add(cartList.get(i)); //검색리스트에 옛날정보들을 담겠다 
			}
		}
		
		return searchCartList;//새로 생성한 ArrayList<Cart>객체를 반환
		//즉 있으면 담고 있으면 담는다는 것이다
		
		/*
		 * oldCartList처음에 3000원 까지 있는것이다
		 * 최하를 누르면 파라미터로 넘어간다 넘어간걸 액션에서 받아야한다
		 * 넘겨줄때 post로 줘서 get파라미터를 써서 얻어온다
		 * 다시 최하 최고 
		 * 장바구니목록 1000~2000등등은 session에 들어가 있다
		 * if문을 썻서 1000과 2000사이에 있는걸 ArrayList객체를 만들어서
		 * 2개만 담아서 searchCartList에 담아서 view페이지에 넘겨줘야한다
		 * 
		 */
	}

}





















