package svc;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import vo.Cart;

public class DogCartRemoveService {
	// 장바구니에 선택된 상품들을 '삭제 하는 기능'들

	// 이 3가지가 중요 하다!!
	// 멤버변수
	// 기본생성자
	// 메서드

	public void cartRemove(HttpServletRequest request, String[] kindArray) {// String[] kindArray을 밖의 for문으로 써야한다
		// 장바구니는 request객체에서 session영역을 얻어온 후
		HttpSession session = request.getSession();

		// session영역에 공유한 cartList속성값인 "장바구니 목록 객체"를 얻어와야 선택한걸 삭제 할수 있지 장바구니 목록도 없이 뭘 보고 ★삭제 할라고 하니?★
		ArrayList<Cart> cartList = (ArrayList<Cart>) session.getAttribute("cartList");

		// 주의!! String[] kindeArray 와 cartList 은 둘다 배열이기 때문에 조심해야 하고
		// ★★★★★ 바깥 for: 삭제할 대상
		for (int i = 0; i < kindArray.length; i++) {// []배열 이닌깐 length
			for (int k = 0; k < cartList.size(); k++) {// ArrayList이닌깐 size
				//kindArray[i]=삭제할 대상 .equal써서 cartList안에 있는 것과 같으면 삭제 한다
				if (kindArray[i].equals(cartList.get(k).getKind())) {// 삭제할 kind와 장바구니 항목이 같으면!! 장바구니 목로겡서 제거!!
					cartList.remove(cartList.get(i));//
					break;//가장 가까운 반복문을 빠져나감 이걸쓰면 효과가 더 좋음 (kind 가 각 1가지만 있을 때)
				}
			}
		}

	}

}

















































