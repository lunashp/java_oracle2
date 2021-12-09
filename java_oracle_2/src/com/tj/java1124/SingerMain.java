package com.tj.java1124;

import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class SingerMain {

	public static void main(String[] args) {
		//SingerDAO 인스턴스 생성
		SingerDAO singerDAO = SingerDAOImpl.sharedInstance();

		//데이터 가져오기 호출
		List<Singer> list = singerDAO.getList();
		for(Singer singer : list) {
			System.out.println(singer);
		}
		//데이터 1개 가져오기
		Singer singer = singerDAO.getSinger(1);
		if(singer == null) {
			System.out.println("데이터가 존재하지 않습니다");
		}else {
			System.out.println(singer);
		}
		/*
		singer = new Singer();
		singer.setName("정수");
		singer.setHometown("서울");
		Calendar cal = new GregorianCalendar(1983, 6, 1);
		singer.setBirthday(new Date(cal.getTimeInMillis()));
		//데이터 삽입
		int r = singerDAO.insertSinger(singer);
		if(r >=0) {
			System.out.println("삽입 성공");
		}else {
			System.out.println("삽입 실패");
		}
		//전체 데이터 목록을 가져와서 출력
		list = singerDAO.getList();
		for(Singer s : list) {
			System.out.println(s);
		 */
		/*
		singer = new Singer();
		singer.setName("종운");
		singer.setHometown("천안");
		Calendar cal = new GregorianCalendar(1984, 7, 24);
		singer.setBirthday(new Date(cal.getTimeInMillis()));
		//데이터 삽입
		int r = singerDAO.updateSinger(singer);
		if(r >=0) {
			System.out.println("삽입 성공");
		}else {
			System.out.println("삽입 실패");
		}
		//전체 데이터 목록을 가져와서 출력
		list = singerDAO.getList();
		for(Singer s : list) {
			System.out.println(s);
			*/
		int r = singerDAO.deleteSinger(21);
		if(r >=0) {
			System.out.println("삽입 성공");
		}else {
			System.out.println("삽입 실패");
		}
		//전체 데이터 목록을 가져와서 출력
		list = singerDAO.getList();
		for(Singer s : list) {
			System.out.println(s);

		}
	}

}
