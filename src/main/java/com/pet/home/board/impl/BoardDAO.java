package com.pet.home.board.impl;

import java.util.List;

import com.pet.home.util.Pager;

public interface BoardDAO {

	//글 목록 불러오기
	public List<BoardDTO> getList(Pager pager) throws Exception;
	//글 작성하기
	public int setAdd(BoardDTO boardDTO) throws Exception;
	//글 상세정보 불러오기
	public BoardDTO getDetail(BoardDTO boardDTO) throws Exception;
	//글 수정
	public int setUpdate(BoardDTO boardDTO) throws Exception;
	//글 삭제
	public int setDelete(BoardDTO boardDTO) throws Exception;
	//글 총 개수 불러오기
	public Long getCount() throws Exception;
}
