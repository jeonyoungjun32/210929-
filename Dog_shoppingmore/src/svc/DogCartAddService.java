package svc;

import static db.JdbcUtil.close;
import static db.JdbcUtil.getConnection;

import java.sql.Connection;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import dao.DogDAO;
import vo.Cart;
import vo.Dog;

public class DogCartAddService {
	// 매개값으로 전달받은 id로 조회한 개 상품 정보 하나를 Dog객체로 반환!!
	public Dog getCartDog(int id) {// 먼저 아이디를 가져와야한다

		// 1.커넥션 풀에서 Connection객체 얻어와
		Connection con = getConnection();
		// 2.싱글톤 패턴:DogDAO객체 생성
		DogDAO dogDAO = DogDAO.getInstance();
		// 3.DB작업에 사용될 Connection객체를 DogDAO의 멤버변수로 삽입하여 DB 연결
		dogDAO.setConnection(con);

		/*----DogDAO의 해당 메서드를 호출하여 처리-------------------*/
		// 개 하나의 정보를 가져와야한다. select는 commit 할 필요 없다
		Dog dog = dogDAO.selectDog(id);

		/*-(update,delete,insert)성공하면 commit 실패하면 rollback
		 * (select제외)----*/

		// 4.해제

		close(con);// Connection객체 해제
		return dog;

	}
	/*--얻어온 특정 개 상품(cartDog)을 장바구니 항목을 추가
	 * ★★★★★★장부구니 항목을 유지할 수 있도록 session영역에 장바구니 항목을 추가해야 하므로
	 * ★★매가값으로 전송된 request객체와 장바구니에 추가할 개 정보객체(cartDog) .★★
	 * (매개값을 받은 쪽에서 session영역을 얻어올 떄 request.getSession();호출해서 얻어오므로)
	 */

	//addCart는 requset와 개정보(cartDog)을 이용한다
	public void addCart(HttpServletRequest request, Dog cartDog) {// request객체와 개정보객체(cartDog)을 받아왔다  cartDog안에는 정보가 다들어있다
		// TODO session영역 만들라고 이 메서드가 생겼다
		// 현재 session영역에 저장되어 있는 장바구니 목록을 얻어오는 부분
		HttpSession session = request.getSession();// getSession을 하는 이유 없으면 만들어지고 있으면 기존Session을 가져온다
		// session영역에 설정된!! 이름 "cartList"의 속성 값 을 이제 VO패키지에 Cart만들어야한다
		ArrayList<Cart> cartList = (ArrayList<Cart>) session.getAttribute("cartList"); // cartList라는 속성 값을 얻어온다
		// vo로 넘어 가자잉!!

		// 카트리스트에 물건이 있는지 확인해보자
		// 장바구니 담기가 처음이라 session영역에 없으면!!
		if (cartList == null) {
			cartList = new ArrayList<Cart>();
			session.setAttribute("cartList", cartList); // 이제서야 처음으로 셋팅 시켰다!!!
		}
		//isNewCart = 진돗개가 없으면 새롭게 추가 할라고 true했다
		boolean isNewCart = true; // isNewCart = 지금 장바구니에 담는 항목이 새로 추가 되는 항목인지를 저장할 변수이다!! true한 이유는
		// 기존에 장바구니 항목이 존재하면 같은 개상품(Kind)을 찾아서 수량에 1증가 시킴
		for (int i = 0; i < cartList.size(); i++) {// cartList.size = ArrayList안에 있는 것들 전부 !!를 돌리겠다
			// get(i) = 0을 가지고있다
			// if(cartDog.getKind().equals(cartList.get(i).getKind()) && 다른걸 넣어도된다) {// 여
			// if문의 해석은 getKind=개종류,
			if (cartDog.getKind().equals(cartList.get(i).getKind())) {// 여 if문의 해석은 getKind=개종류,
				// 찾는 기준 : 개 종류(기준은 자기 알아서 처리 해도된다)
				isNewCart = false; // 같은개종류가 있으닌깐 false
				cartList.get(i).setQty(cartList.get(i).getQty() + 1);// 똑같은 개 상품을 눌렀을때 수량1증가 시키고 setQty으로 다시 셋팅 시켜라!!
				// ArrayList가 생기고 세션영역에 추가 하고
				break;

				/*
				 * 진돗개가 이미 있으면 수량만 1증가 하면 된다 !!
				 * 
				 * 진돗개가 지금 1개 밖에 없으닌깐
				 * 
				 * getKind쓴거지 진돗개가 여러개면
				 * 
				 * Kinde하지말고 번호를 들고 와야겠지?
				 * 
				 * 세션영역에 추가 하고  유지 할라고
				 * 
				 * 아니면 새롭게 셋팅 해야하ㅏㄴ다
				 * 
				 * isNewCart 가 true이닌깐 
				 * 
				 * 객체 만들고 매개값으로 정보를받는다 정보 cartDog으로 받았고
				 * 
				 * 기본값으로 있던걸 getImage, getKind,getPrice를 다시 셋팅하고
				 * 
				 * add하겠다
				 * 
				 * true일때만 inNewCart소스가 실행 된다 
				 */

			}

		}
		//위에서 true면 위에서 끝나고 장바구니에 아무것도 없으면  여기서 객체 추가하고 설정한다
		//true이면 cart를 만들고 셋팅 하고 cartList.add 하겠다
		if (isNewCart == true) { // 장바구니에 진돗개가 없으면 다시 새롭게 하는 메서드임!!
			Cart cart = new Cart();
			cart.setImage(cartDog.getImage());// cartDog안에 진돗개 정보가 다 들어있다
			cart.setKind(cartDog.getKind());
			cart.setPrice(cartDog.getPrice());
			// cart.setQty(cartDog.get); 수량은 안나와서 1로 셋팅 해야한다
			cart.setQty(1);// 수량은 처음이닌깐 1로 셋팅
		
			cartList.add(cart); // 다 만든걸 여기다 저장하겠다
		}
	}

}












