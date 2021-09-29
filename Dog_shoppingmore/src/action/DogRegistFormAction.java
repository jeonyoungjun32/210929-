package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vo.ActionForward;

public class DogRegistFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//특별한 로직이 필요없이 바로 뷰페이지만 보여주면 된다.
		ActionForward forward=new ActionForward("dogRegistForm.jsp", false);
		return forward;
	}

}
