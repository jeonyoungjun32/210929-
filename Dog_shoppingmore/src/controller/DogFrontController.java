package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import action.DogCartAddAction;
import action.DogCartListAction;
import action.DogCartQtyDownAction;
import action.DogCartQtyUpAction;
import action.DogCartRemoveAction;
import action.DogCartSearchAction;
import action.DogListAction;
import action.DogRegistAction;
import action.DogViewAction;
import vo.ActionForward;

/**
 * Servlet implementation class BoardFrontController
 */
//확장자가 dog이면 무조건 DogFrontController로 이동하여 doProcess()메서드 실행함
//@WebServlet("*.dog")//마지막 url이 *.dog로 끝나는 요청을 매핑
public class DogFrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DogFrontController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doProcess(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doProcess(request, response);
	}
	
	//이 서블릿으로 들어오는 post나 get방식의 모든 요청은 doProcess()를 호출하여 처리
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");//반드시 첫줄
		
		//요청객체로부터 '프로젝트명+파일경로'까지 가져옴(예)/project/boardWriteForm.dog
		String requestURI=request.getRequestURI();
		//요청 URL : http://localhost:8090/project/boardWriteForm.dog
		//요청 URI : /project/boardWriteForm.dog
		
		//요청객체로부터 '프로젝트 path'만 가져옴(예)/project
		String contextPath=request.getContextPath();
		
		/* URI에서 contextPath길이만큼 잘라낸 나머지 문자열
		 * /project/boardWriteForm.dog - /project = "/boardWriteForm.dog"
		 */
		String command=requestURI.substring(contextPath.length());//(index 8~끝까지) 부분문자열 반환
		
		/* 요청이 파악되면 해당 요청을 처리하는 각 Action클래스를 사용해서 요청 처리
		 * 각 요청에 해당하는 Action클래스 객체들을 다형성을 이용해서 동일한 타입(Action)으로 참조하기 위해
		 * 'Action 인터페이스' 타입의 변수 선언(혜574p) 
		 */
		Action action = null;
		ActionForward forward = null;
		
		/* 글쓰기 페이지를 열어주는 요청인 경우는 특별한 비지니스 로직을 실행할 필요없이
		 * 글쓰기 할 수 있는 뷰페이지로만 포워딩하면 됨
		 */
		
		if(command.equals("/dogList.dog")) {//'개 상품 목록보기'요청이면
			action = new DogListAction();
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		else if(command.equals("/dogView.dog")) {//'특정 개 상품의 상세 정보 보기' 요청이면						
			action = new DogViewAction();			
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		else if(command.equals("/dogRegistForm.dog")) {//'새로운 개 상품 정보 등록 폼의 뷰페이지 보기' 요청이면						
			/*
			action = new DogRegistFormAction();			
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
			*/
			forward=new ActionForward("dogRegistForm.jsp", false);
		}
		else if(command.equals("/dogRegist.dog")) {//'새로운 개 상품 정보 등록을 처리하는' 요청이면						
			action = new DogRegistAction();			
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		/******************		시발!! 지금 부터 장바구니 한다 잘들어라!!		****************************************/
		else if(command.equals("/dogCartAdd.dog")) {//'★★★★  새로운 장바구니 항목을 추가하는 장바구니 담기  ★★★★★★' 요청이면						
			action = new DogCartAddAction();			
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		else if(command.equals("/dogCartList.dog")) {//'★★★★★★★★    장바구니 목록 보기!!  ★★★★' 요청이면						
			action = new DogCartListAction();			
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		//dogCartList.jsp에서 맨밑부분소스에 검색 클릭시 이동하는것!!
		else if(command.equals("/dogCartSearch.dog")) {//'★★★★★★★★    최하가격과 최고가격으로 장바구니 목록을 검색  ★★★★' 요청이면						
			action = new DogCartSearchAction();		//dogCartSerch.dog을 호출하면 	DogCartSearchAction 로 가서 
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		//장바구니 항목 수량 증가!!
		else if(command.equals("/dogCartQtyUp.dog")) {//'★★★★★★★★    장바구니 항목 수량 증가  ★★★★' 요청이면						
			action = new DogCartQtyUpAction();		//dogCartSerch.dog을 호출하면 	DogCartSearchAction 로 가서 
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		//장바구니 항목 수량 감소!!
		else if(command.equals("/dogCartQtyDown.dog")) {//'★★★★★★★★    장바구니 항목 수량 감소  ★★★★' 요청이면						
			action = new DogCartQtyDownAction();		//dogCartSerch.dog을 호출하면 	DogCartSearchAction 로 가서 
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		//제품 삭제
		else if(command.equals("/dogCartRemove.dog")) {//'★★★★★★★★    장바구니 항목 수량 감소  ★★★★' 요청이면						
			action = new DogCartRemoveAction();		//dogCartSerch.dog을 호출하면 	DogCartSearchAction 로 가서 
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		
		
		
		
		
		
		//하 시발 이거 안하닌깐 안나오지 시발 븅신 새끼야!!!
		if(forward != null) {
			if(forward.isRedirect()) {//isRedirect=true : 주소변경(새요청), request 공유 못함
				response.sendRedirect(forward.getPath());//응답-리다이렉트 방식
				//
			}else {//isRedirect=false :디스패치 방식
				//RequestDispatcher dispatcher=request.getRequestDispatcher(forward.getPath());
				//dispatcher.forward(request, response);//기존요청,기존응답 그대로 보내므로 주소 그대로.request공유
				request.getRequestDispatcher(forward.getPath()).forward(request, response);
			}
		}
	}

}
