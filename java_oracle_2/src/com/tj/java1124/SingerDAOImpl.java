package com.tj.java1124;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
//SingerDAO 인터페이스의 메소드를 구현할 클래스
public class SingerDAOImpl implements SingerDAO {
	//서버에서 사용되는 클래스인 경우 Singleton 으로 디자인 하는 것이 일반적
	//Framework를 이용하면 이 작업은 Framework가 자동으로 수행합니다.
	private SingerDAOImpl() {}

	private static SingerDAO singerDAO;

	public static SingerDAO sharedInstance() {
		if(singerDAO == null) {
			singerDAO = new SingerDAOImpl();
		}
		return singerDAO;
	}
	private static String driver;
	private static String url;
	private static String id;
	private static String pw;

	//Connection은 맨 처음 한 번 연결해놓고 계속 사용하는 경우가 일반적
	private static Connection con;

	//static 초기화: 클래스가 처음 사용될 때 한 번만 수행되는 코드
	static {
		try {
			//프로퍼티 파일의 내용을 읽어서 변수에 저장
			File f = new File("./db.properties");
			FileInputStream is = new FileInputStream(f);
			Properties properties = new Properties();
			properties.load(is);

			driver = properties.getProperty("driver");
			url = properties.getProperty("url");
			id = properties.getProperty("id");
			pw = properties.getProperty("pw");

			//드라이버 클래스 로드
			Class.forName(driver);
			//데이터베이스 연결
			con=DriverManager.getConnection(url, id, pw);

			System.out.println("static 초기화 성공");
		}catch(Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
	}
	@Override
	public List<Singer> getList() {
		List<Singer> list = new ArrayList<>();


		try {
			PreparedStatement pstmt =
					con.prepareStatement("select * from singer");
			ResultSet rs = pstmt.executeQuery();

			while(rs.next()) {
				Singer singer = new Singer();
				singer.setNum(rs.getInt("num"));
				singer.setName(rs.getString("name"));
				singer.setBirthday(rs.getDate("birthday"));
				singer.setHometown(rs.getString("hometown"));


				list.add(singer);
			}
			rs.close();
			pstmt.close();
		}catch(Exception e) {
			System.out.println(e.getLocalizedMessage());


		}
		return list;
	}

	@Override
	public Singer getSinger(int num) {
		//1개를 리턴하는 메소드는 처음에 null을 대입한 상태에서 시작
		//1개를 가져오는 메소드를 호출했을 때는 결과가 null인지 확인을 해야 합니다.
		Singer singer = null;
		try {
			PreparedStatement pstmt =
					con.prepareStatement("select * from singer where num = ?");
			//첫번째 물음표의 num의 값을 정수로 바인딩
			pstmt.setInt(1, num);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				singer = new Singer();

				singer.setNum(rs.getInt("num"));
				singer.setName(rs.getString("name"));
				singer.setBirthday(rs.getDate("birthday"));
				singer.setHometown(rs.getString("hometown"));
			}
			rs.close();
			pstmt.close();
		}catch(Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
		return singer;
	}

	@Override
	//데이터 삽입
	//기본키를 입력받는지 그렇지 않은지에 따라 달라짐
	//기본키를 입력을 받는 경우는 기본키를 받아서 중복 체크를 해야 합니다.
	public int insertSinger(Singer singer) {
		//결과를 리턴하기 위한 변수
		//정수를 리턴할때는 -1로 초기화하는 경우가 많습니다.
		//데이터베이스에서는 영향받은 행의 개수를 리턴하기 때문에 
		int result = -1;
		try {
			//자동커밋해제 
			con.setAutoCommit(false);
			//PreparedStatement를 생성
			//num은 시퀀스를 이용해서 삽입하므로 시퀀스를 이용하는 내용을 설정하고
			//나머지 3개는 매번 내용이 바뀌므로 ?로 처리 
			PreparedStatement pstmt = con.prepareStatement(
					"insert into singer(num, name, birthday, hometown) " + "values((singer_seq.nextval, ?, ?, ?)");
			//?에 값을 바인딩
			pstmt.setString(1, singer.getName());
			pstmt.setDate(2, singer.getBirthday());
			pstmt.setString(3, singer.getHometown());

			//실행
			result = pstmt.executeUpdate();

			//pstmt정리
			pstmt.close();
			//작업 내용을 데이터베이스에 반영
			con.commit();

		}catch(Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
		//데이터베이스 작업 로그를 파일에 기록
		//현재 시간과 작업 내용과 성공 여부를 기록 
		//파일 이름을 오늘 날짜로 설정 
		return result;
	}

	@Override
	public int updateSinger(Singer singer) {
		int result = -1;
		try {
			con.setAutoCommit(false);

			PreparedStatement pstmt = con.prepareStatement(
					"update singer set name=?, birthday=?, hometown=? " + "where num = ?");
			pstmt.setString(1, singer.getName());
			pstmt.setDate(2, singer.getBirthday());
			pstmt.setString(3, singer.getHometown());
			pstmt.setInt(4, singer.getNum());

			result = pstmt.executeUpdate();
			pstmt.close();
			con.commit();
		}catch(Exception e) {
			System.out.println(e.getLocalizedMessage());

		}
		return result;
	}

	@Override
	public int deleteSinger(int num) {
		int result = -1;
		try {
			con.setAutoCommit(false);

			PreparedStatement pstmt = con.prepareStatement(
					"delete from singer where num = ?");
			pstmt.setInt(1, num);

			result = pstmt.executeUpdate();
			pstmt.close();
			con.commit();
		}catch(Exception e) {
			System.out.println(e.getLocalizedMessage());

		}
		return result;
	}


}
