package com.board.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartRequest;

import com.board.command.InsertCalReplyCommand;
import com.board.command.InsertReplyCommand;
import com.board.command.NewsDelBoardCommand;
import com.board.command.NewsInsertBoardCommand;
import com.board.command.NewsUpdateBoardCommand;
import com.board.dtos.FileBoardDto;
import com.board.dtos.FreeBoardDto;
import com.board.dtos.NewsBoardDto;
import com.board.dtos.UserFileBoardDto;
import com.board.service.FreeBoardService;
import com.board.service.UserFileService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value="/free")
public class FreeBoardController {
   
   @Autowired
   private FreeBoardService freeBoardService;
   
   @Autowired
   private UserFileService userfileService;
   
   @GetMapping(value="/freeboardList")
   public String FreeBoardList(Model model) {
      System.out.println("글목록 보기");
      List<FreeBoardDto> list=freeBoardService.getAllList();
      model.addAttribute("list", list);
      model.addAttribute("delBoardCommand", new NewsDelBoardCommand());
      
      return "free/freeboardList";
   }
   @GetMapping(value = "/freeBoardInsert")
   public String boardInsertForm(Model model) {
      System.out.println("글추가폼 이동");
      model.addAttribute("insertBoardCommand", new NewsInsertBoardCommand());
      
      return "free/freeBoardInsertForm";
   }
   @PostMapping(value = "/freeBoardInsert")
   public String boardInsert(@Validated NewsInsertBoardCommand insertBoardCommand,
                           BindingResult result, MultipartRequest multipartRequest,
                           HttpServletRequest request,
                           Model model) throws IllegalStateException, IOException {
      
      if(result.hasErrors()) {
         System.out.println("글을 모두 입력하라고");
         return "free/freeBoardInsertForm";
      }
         
      freeBoardService.insertBoard(insertBoardCommand, multipartRequest, request);
         
      return "redirect:/free/freeboardList";
   }   
      @GetMapping(value = "/freeBoardDetail")
      public String boardDetail(int board_seq, Model model, NewsUpdateBoardCommand newsUpdateBoardCommand) {
         FreeBoardDto dto = freeBoardService.getBoard(board_seq);
         model.addAttribute("updateBoardCommand", new NewsUpdateBoardCommand());
         model.addAttribute("dto",dto);
         int seq = newsUpdateBoardCommand.getBoard_seq();
         
         
         freeBoardService.readCount(seq);//조회수 증가
 
         return "free/freeBoardDetail";
      }
      @PostMapping(value = "/freeBoardUpdate")
      public String boardUpdate(@Validated NewsUpdateBoardCommand updateBoardCommand
                                 ,BindingResult result) {
         System.out.println("수정시작");
         if(result.hasErrors()) {
            System.out.println("수정내용을 모두 입력하셈");
            return "free/freeBoardDetail";
         }
         
         freeBoardService.updateBoard(updateBoardCommand);
         
         return "redirect:/free/freeBoardDetail?board_seq="+updateBoardCommand.getBoard_seq();
         
      }
      @GetMapping(value = "/download")
      public void download(int file_seq, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
         UserFileBoardDto fdto = userfileService.getFileInfo(file_seq);
         
         userfileService.fileDownload(fdto.getOrigin_filename(),fdto.getStored_filename(),request,response);
      }
      @RequestMapping(value="mulDel",method = {RequestMethod.GET, RequestMethod.POST})
      public String mulDel(@Validated NewsDelBoardCommand delBoardCommand
                      ,BindingResult result
                         , Model model) {
         if(result.hasErrors()) {
            System.out.println("최소하나 체크하기");
            List<FreeBoardDto> list=freeBoardService.getAllList();
            model.addAttribute("list", list);
            return "free/freeboardList";
         }
         freeBoardService.mulDel(delBoardCommand.getSeq());
         System.out.println("글삭제함");
         return "redirect:/free/freeboardList";
      }
      @ResponseBody
      @GetMapping(value = "/addReplyBoard")
      public String addCalReply(@Validated InsertReplyCommand insertCommand,
                          BindingResult result) throws Exception {
         System.out.println("댓글추가");
         if(result.hasErrors()) {
            System.out.println("글을 모두 입력해야 함");
            return "news/NewsBoardDetail";
         }
         System.out.println(insertCommand);
//         logger.info("탈출ㅋ");
         freeBoardService.insertReply(insertCommand);
         
        // return "redirect:/schedule/calBoardDetail";
         return "";
      }
      

      @ResponseBody
      @GetMapping(value = "/showReplyBoard")
      public Map<String, List<FreeBoardDto>> NewsBoardList(@Validated InsertCalReplyCommand insertCalCommand, BindingResult result,  Model model, int seq) throws Exception {
         System.out.println("댓글 보여주기");
         List<FreeBoardDto> list = freeBoardService.showReply(seq);
         Map<String, List<FreeBoardDto>> map = new HashMap<>();
         map.put("list", list);

         model.addAttribute("insertCalCommand", new InsertCalReplyCommand());
         
         return map;
      }
}