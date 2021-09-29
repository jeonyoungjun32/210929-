/*
 * 책 820p 참조
 * 한글 이름 등을 입력한 후 파라미터로 전송하면 한글이 깨지는 것을 방지!!!!!!
 */
package controller;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
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
 * 한글 처리 할라고 할라면 필터를 꼭 써야한다  
 * controller에서 *.dog를 없어야한다 
 * 확장자가 무조건 .dog이면 무조건 DogFrontControllerFilter로 이동하여
 * doProcess()메서드가 아닌 doFilter()메서듣 실행할수 있도록  
 */
@WebFilter("*.dog")
public class DogFrontControllerFilter implements Filter {

    /**
     * Default constructor. 
     */
    public DogFrontControllerFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		// TODO Filter기능 : 한글 이름 등을 입력 하고 파라미터로 전송하면 한글 깨지는걸 방지
		/* 1. ★★★★★  한글 필터 처리를 위해서 새롭게 추가한 내용(두군데 중 첫번째) ★★*/
		//서블릿을 상속 받지 않았음 1.ServletRequest, 2.ServletResponse가 있다
		// 1-1. req를 강제 형변환 하기
		HttpServletRequest request = (HttpServletRequest) req;
		// 1-2. res를 강제 형변환
		HttpServletResponse response = (HttpServletResponse) res;
		/***********************************************************************************************************/
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
				//장바구니 항목 수량 감소!!
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
		
				/***********************************************************************************************************/	
		
	
				/*2. ★★★★★  한글 필터 처리를 위해서 새롭게 추가한 내용(두군데 중 두번째) ★★*/
				/*doFilert()호출하는 부분이 없어야 요청 처리가 필터에서만 처리되고 마무리 된다!!
				 * 이부분이 있으면 다시 서블릿으로 요청이 넘어간다!! 그러면 무한 처리로 되서 오류!!
				 *  chain.doFilter(request, response);<<이걸 주석처리해야 무한로딩 안된다!!
				 */
		
		//chain.doFilter(request, response); 이거는 주석처리해야하고
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
