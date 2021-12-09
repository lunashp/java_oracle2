package com.tj.java1124;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.sql.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OracleConnect {
	//접속을 위해서 필요한 정보를 문자열 변수로 생성
	//오라클 드라이버 클래스 이름
	private static String driver = "oracle.jdbc.driver.OracleDriver";
	//오라클 접속 url
	private static String url = "jdbc:oracle:thin:@192.168.10.50:1521:xe";
	//계정정보
	private static String id="user16";
	private static String pw="user16";
	public static void main(String[] args) {
		//드라이버 클래스 로드
		//예외가 발생하는 경우 driver가 build path에 추가되지 않았거나 
		//driver이름이 잘못된 경우
		try {
			Class.forName(driver);
			Connection con = DriverManager.getConnection(url, id, pw);

			//SQL을 실행할 수 있는 PreparedStatement 인스턴스 생성
			/*
			PreparedStatement pstmt =
					con.prepareStatement(
							"insert into sample(num,name, birthday) " + "values(?, ?, ?)");
			//물음표의 값을 바인딩
			pstmt.setInt(1, 2);
			pstmt.setString(2, "동해");
			//1986년 10월 15일을 날짜 형식으로 생성
			Calendar cal = new GregorianCalendar(1986, 9, 15);
			Date date = new Date(cal.getTimeInMillis());
			pstmt.setDate(3, date);

			//select 이외의 구문 실행 
			//select 이외의 구문은 영향받은 행의 개수를 리턴합니다. 
			int result = pstmt.executeUpdate();
			if(result >0) {
				System.out.println("데이터 삽입 성공");

			}else {
				System.out.println("데이터 삽입 실패");
			}
			//System.out.println("성공");
			*/

			//데이터 수정 - num이 2번인 데이터를 수정
			/*
			PreparedStatement pstmt =
					con.prepareStatement(
							"update sample set name = ?, birthday = ? where num = ?");
			//값을 바인딩
			pstmt.setString(1, "혁재");
			Calendar cal = new GregorianCalendar(1986, 3, 4);
			pstmt.setDate(2, new Date(cal.getTimeInMillis()));

			pstmt.setInt(3, 2);

			//select 이외의 구문 실행
			int result = pstmt.executeUpdate();

			if(result>=0) {
				System.out.println("수정 성공");
				}else {
					System.out.println("수정 실패");
				}
			*/
		
			PreparedStatement pstmt = 
					con.prepareStatement("select* from sample");
			//select 구문 실행
			ResultSet rs = pstmt.executeQuery();
			
			//0개 이상 리턴 되는 경우
			//처음부터 끝까찌 순회
			
			//여러개의 데이터를 저장할 List 생성
			List<Map<String, Object>> list = new ArrayList<>();
			
			while(rs.next()) {
				//하나의 행을 저장할 인스턴스 생성(Map, DTO)
				Map<String, Object> map = new HashMap<String, Object>();
				//컬럼 이름이나 인덱스를 이용해서 가져와도 됩니다.
				int num = rs.getInt("num");
				String name = rs.getString(2);
				Date birthday = rs.getDate(3);
				map.put("num", num);
				map.put("name", name);
				map.put("birthday", birthday);
				
				list.add(map);
				
			}
			for(Map<String,Object> map : list) {
				System.out.println(map);
			}

			//데이터 베이스 연결 해제
			rs.close();
			pstmt.close();
			con.close();
		}catch(Exception e) {
			System.out.println("실패:" + e.getLocalizedMessage());

		}
	}
}
