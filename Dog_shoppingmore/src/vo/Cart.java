package vo;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Cart {

	private String image; // 개 상품 이미지
	private String kind; // 개 상품명
	private int price; // 가격

	private int qty; // 수량 . sql에 수량이 없지만 추가 했다.
	private String encodingKind; //★★★   인코딩된 개 상품명 추가,   //UTF-8 인코딩 할라고 get만 만들었다!!

	// 기본생성자 만들고
	public Cart() {
	}
	
	
	/*
	 * 링크 방식으로 파라미터 값을 전소앟면 자동으로 인코딩 되지 않아서
	 * 
	 *  서버쪽에서 그 값을 받았을때 한글 파라미터를 받으면 한글이 깨진다.
	 *  
	 *  그래서 kind값을 UTF-8로 인코딩 해서 반환해주는 메서드를 정의해야함!!!!
	 * 
	 */
	
	public String getEncodingKind() { //UTF-8 인코딩 할라고 get만 만들었다!!
		try {
			encodingKind = URLEncoder.encode(kind, "UTF-8"); //서버로 전송하기에 net안에 있다!   URLEncoder = encoder인코딩이란
		} catch (UnsupportedEncodingException e) {
			// TODO kind(개종류)를 UTF-8로 인코딩해서 보낸다잉!!
			e.printStackTrace();
		}
		return encodingKind;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	

}
