package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.DogCartRemoveService;
import vo.ActionForward;

public class DogCartRemoveAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO 선택한게 삭제 할라면 '배열'로 만들어라
		//주의 : 동시에 여러 개의 장바구니 항목을 삭제 할 수 있기 때문에 배열로 만들어야 한다!!
		String[] kindArray = request.getParameterValues("remove"); // 배열을 써야되서 파라미터벨류즈 써야한다
		
		DogCartRemoveService dogCartRemoveService = new DogCartRemoveService();
		dogCartRemoveService.cartRemove(request, kindArray);//요청 한걸 받아왔다!!
		
		//장바구니에서 선택한 항목들의 삭제시킨후 '장바구니 목록 보기 '요청을 다시 하기 위해
		return new ActionForward("dogCartList.dog", true);
	}

}
