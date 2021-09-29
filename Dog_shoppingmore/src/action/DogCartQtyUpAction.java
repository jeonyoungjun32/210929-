package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.DogCartQtyUpService;
import vo.ActionForward;

public class DogCartQtyUpAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO 장바구니 항목 수량 증가
		
		//dogCartList.jsp에서 encodingKind값을 가져왔다!!
		String kind = request.getParameter("encodingKind");
		
		DogCartQtyUpService dogCartQtyUpService = new DogCartQtyUpService();
		dogCartQtyUpService.upCartQty(kind, request);//2개를 보내고
		
		//이 방법으로 해도되고 그냥 return new ActionForward("dogCartList.dog",true)해도되고
		//ActionForward forword = new ActionForward("dogCartList.dog", true); //true한 이유 디케스트 로 바로 보낼라고 
		
		
		//장바구니 해당 항목의 수량을 증가시킨 후 '장바구니 목록 보기 간다잉!' 요청을 다시 하기 위해서 
		return new ActionForward("dogCartList.dog", true);
	}
}
