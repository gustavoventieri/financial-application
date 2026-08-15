package com.financial.api.framework.user.adapter.port.in.web;

import com.financial.api.user.application.port.in.CreateUserUseCase;
import com.financial.api.user.application.port.in.DeleteUserByIdUseCase;
import com.financial.api.user.application.port.in.FindUserByEmailUseCase;
import com.financial.api.user.application.port.in.FindUserByIdUseCase;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "Bearer Authentication")
public class UserController {

   private final CreateUserUseCase createUserUseCase;
   private final DeleteUserByIdUseCase deleteUserByIdUseCase;
   private final FindUserByEmailUseCase findUserByEmailUseCase;
   private final FindUserByIdUseCase findUserByIdUseCase;


   public UserController(
           CreateUserUseCase createUserUseCase,
           DeleteUserByIdUseCase deleteUserByIdUseCase,
           FindUserByIdUseCase findUserByIdUseCase,
           FindUserByEmailUseCase findUserByEmailUseCase
   ){
      this.createUserUseCase = createUserUseCase;
      this.deleteUserByIdUseCase = deleteUserByIdUseCase;
      this.findUserByEmailUseCase = findUserByEmailUseCase;
      this.findUserByIdUseCase = findUserByIdUseCase;
   }
}
