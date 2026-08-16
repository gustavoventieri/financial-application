package com.financial.api.framework.user.adapter.port.in.web;

import com.financial.api.framework.shared.config.security.annotations.RequiredRole;

import com.financial.api.shared.enumerated.Roles;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/users")
public class UserController {



   @GetMapping
   @RequiredRole({
           Roles.ADMIN
   })
   public ResponseEntity<String> getUsers(){
      return ResponseEntity.status(HttpStatus.OK).body("teste");
   }

}
