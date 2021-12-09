package com.tj.java1124;

import java.util.List;

public interface SingerDAO {
	//여러개의 데이터를 가져오는 메소드 - 목록보기
	//페이징에 익숙해지면 여기 매개변수는 페이지 번호입니다.
	public List<Singer> getList();
	
	//기본키를 가지고 하나의 데이터를 가져오는 메소드 - 상세보기
	public Singer getSinger(int num);
	
	//데이터를 삽입하는 메소드
	public int insertSinger(Singer singer);
	
	//기본키를 가지고 데이터를 수정하는 메소드 
	public int updateSinger(Singer singer);
	
	//기본키를 가지고 데이터를 삭제하는 메소드 
	public int deleteSinger(int num);
}
