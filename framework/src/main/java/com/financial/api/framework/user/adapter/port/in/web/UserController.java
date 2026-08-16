package com.financial.api.framework.user.adapter.port.in.web;

import com.financial.api.user.application.port.in.CreateUserUseCase;
import com.financial.api.user.application.port.in.DeleteUserByIdUseCase;
import com.financial.api.user.application.port.in.FindUserByEmailUseCase;
import com.financial.api.user.application.port.in.FindUserByIdUseCase;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/users")
public class UserController {



   @GetMapping
   public ResponseEntity<String> getUsers(){
      return ResponseEntity.status(HttpStatus.OK).body("teste");
   }

}
