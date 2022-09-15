package com.pet.home.board.impl;

import java.util.List;

public interface BoardDAO {

	
	public List<BoardDTO> getList() throws Exception;
	
	public int setAdd(BoardDTO boardDTO) throws Exception;
}
