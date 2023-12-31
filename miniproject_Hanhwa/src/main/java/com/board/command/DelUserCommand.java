package com.board.command;

import java.util.Arrays;

import jakarta.validation.constraints.NotEmpty;

public class DelUserCommand {
    @NotEmpty(message = "최소 하나 이상 선택하세요")
   String[] id;

   public DelUserCommand() {
      super();
      // TODO Auto-generated constructor stub
   }

   public DelUserCommand(@NotEmpty(message = "최소하나 선택해주세요")String[] id) {
      super();
      this.id = id;
   }

   @Override
   public String toString() {
      return "DelUserCommand [id=" + Arrays.toString(id) + "]";
   }

   public String[] getId() {
      return id;
   }

   public void setId(String[] id) {
      this.id = id;
   }
}