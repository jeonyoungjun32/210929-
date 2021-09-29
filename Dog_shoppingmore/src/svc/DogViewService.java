package svc;

import static db.JdbcUtil.*;

import java.sql.Connection;

import dao.DogDAO;
import vo.Dog;

public class DogViewService {
	//해당 개 상품의 '조회수 1증가' -> 'id로 조회한 개상품정보를 Dog객체로 반환'
	public Dog getDogView(int id){
		//1.커넥션 풀에서 Connection객체 얻어와
		Connection con = getConnection();
		//2.싱글톤 패턴:DogDAO객체 생성
		DogDAO dogDAO = DogDAO.getInstance();
		//3.DB작업에 사용될 Connection객체를 DogDAO의 멤버변수로 삽입하여 DB 연결
		dogDAO.setConnection(con);
		
		/*----DogDAO의 해당 메서드를 호출하여 처리-------------------------*/
		//해당 개 상품의 '조회수 1증가'
		int updateCount = dogDAO.updateReadCount(id);		
		
		/*-(update,delete,insert)성공하면 commit 실패하면 rollback
		 * (select제외)----*/
		if(updateCount>0) {
			commit(con);
		}
		else {
			rollback(con);
		}
		
		//'id로 조회한 개상품정보를 Dog객체로 반환'
		Dog dog = dogDAO.selectDog(id);
		
		/*--------------------------------------------------------*/
		 
		 //4.해제
		close(con);//Connection객체 해제
		
		return dog;
	}
}
