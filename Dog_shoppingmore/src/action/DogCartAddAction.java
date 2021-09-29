package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.DogCartAddService;
import vo.ActionForward;
import vo.Dog;

public class DogCartAddAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO 시발ㅠㅠㅠㅠ 장바구니 한다 다행이다 ㅠㅠㅠ 대가리 꺠지는줄 알았네
		// 1. DogService만들고
		DogCartAddService dogCartAddService = new DogCartAddService();

		// 2. int타입 id로 받을라면
		int id = Integer.parseInt(request.getParameter("id")); // request.getParameter("id")=String 이고 int타입으로 받을라고integer씀
		// dogView에서 id를 던져 주닌깐 여서 id를 받는다
		Dog cartDog = dogCartAddService.getCartDog(id);

		/*--얻어온 특정 개 상품(cartDog)을 장바구니 항목을 추가하는 메서드를 호출
		 * ★★★★★★장부구니 항목을 유지할 수 있도록 session영역에 장바구니 항목을 추가해야 하므로
		 * ★★매가값으로 request객체를 던진다.★★
		 * (매개값을 받은 쪽에서 session영역을 얻어올 떄 request.getSession();호출해서 얻어오므로)
		 */
		
		//Service에서 가져왔다
		dogCartAddService.addCart(request,cartDog);//★★★★존나 중요 request와 cartDog가 담긴걸 던져준다!!
		
		//장바구니 항목에 선택한 개 상품을 추가한 후 장바구니 목록보기
		//장바구니 요청을 볼라고한다 요청한다.    
		//ActionForward forward = new ActionForward("dogCartList.dog", true);
		//return forward;
		//위에껄 해도되고
		return new ActionForward("dogCartList.dog", true); //이걸해도된다 
	}

}
