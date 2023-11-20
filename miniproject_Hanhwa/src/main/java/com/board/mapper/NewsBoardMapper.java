package com.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.board.dtos.NewsBoardDto;


@Mapper
public interface NewsBoardMapper {

	
		// 글 목록
		public List<NewsBoardDto> getAllList();
		
		// 글 상세 조회
		public NewsBoardDto getBoard(int board_seq);
		
		// 글 추가
		public boolean insertBoard(NewsBoardDto dto);
		
		// 글 수정
		public boolean updateBoard(NewsBoardDto dto);
		
		// 글 삭제
		public boolean mulDel(String[] seqs);
}