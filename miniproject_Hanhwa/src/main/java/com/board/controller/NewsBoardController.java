package com.board.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartRequest;

import com.board.command.NewsDelBoardCommand;
import com.board.command.NewsInsertBoardCommand;
import com.board.command.NewsUpdateBoardCommand;
import com.board.dtos.FileBoardDto;
import com.board.dtos.NewsBoardDto;
import com.board.service.FileService;
import com.board.service.NewsBoardService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "/news")
public class NewsBoardController {

	@Autowired
	private NewsBoardService newsBoardService;
	
	@Autowired
	private FileService fileService;
	
	@GetMapping(value = "/boardList")
	public String NewsBoardList(Model model) {
		System.out.println("글목록 보기");
		List<NewsBoardDto> list = newsBoardService.getAllList();
		model.addAttribute("list", list);
		model.addAttribute("delBoardCommand", new NewsDelBoardCommand());
		
		return "news/newsboardList";
	}
	
	@GetMapping(value = "/newsBoardInsert")
	public String boardInsertForm(Model model) {
		System.out.println("글추가폼 이동");
		model.addAttribute("insertBoardCommand", new NewsInsertBoardCommand());
		
		return "news/newsBoardInsertForm";
	}
	
	@PostMapping(value = "/newsBoardInsert")
	public String boardInsert(@Validated NewsInsertBoardCommand insertBoardCommand,
									BindingResult result, MultipartRequest multipartRequest,
									HttpServletRequest request,
									Model model) throws IllegalStateException, IOException {
		
		if(result.hasErrors()) {
			System.out.println("글을 모두 입력하라고");
			return "news/newsBoardInsertForm";
		}
		
		newsBoardService.insertBoard(insertBoardCommand, multipartRequest, request);
		
		return "redirect:/news/boardList";
	}
	@GetMapping(value = "/newsBoardDetail")
	public String boardDetail(int board_seq, Model model) {
		NewsBoardDto dto = newsBoardService.getBoard(board_seq);
		model.addAttribute("updateBoardCommand", new NewsUpdateBoardCommand());
		model.addAttribute("dto",dto);
		return "news/newsBoardDetail";
	}
	
	@PostMapping(value = "/newsBoardUpdate")
	public String boardUpdate(@Validated NewsUpdateBoardCommand updateBoardCommand
										,BindingResult result) {
		System.out.println("수정시작");
		if(result.hasErrors()) {
			System.out.println("수정내용을 모두 입력하셈");
			return "news/newsBoardDetail";
		}
		
		newsBoardService.updateBoard(updateBoardCommand);
		
		return "redirect:/news/newsBoardDetail?board_seq="+updateBoardCommand.getBoard_seq();
		
	}
	
	@GetMapping(value = "/download")
	public void download(int file_seq, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		FileBoardDto fdto = fileService.getFileInfo(file_seq);
		
		fileService.fileDownload(fdto.getOrigin_filename(),fdto.getStored_filename(),request,response);
	}
	
}
