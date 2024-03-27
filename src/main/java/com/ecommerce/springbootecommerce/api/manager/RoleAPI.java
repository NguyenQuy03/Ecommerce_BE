package com.ecommerce.springbootecommerce.api.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.springbootecommerce.dto.RoleDTO;
import com.ecommerce.springbootecommerce.exception.CustomException;
import com.ecommerce.springbootecommerce.service.IRoleService;

@RestController(value = "RoleAPIOfManager")
@RequestMapping(value = "/api/v1/manager/role")
public class RoleAPI {

    @Autowired
    private IRoleService roleService;

    @GetMapping()
    public ResponseEntity<?> getRoles() {
        try {
            List<RoleDTO> roles = roleService.findAll();

            return ResponseEntity.ok().body(roles);
        } catch (Exception e) {
            return ResponseEntity.status(((CustomException) e.fillInStackTrace()).getErrorCode()).body(e.getMessage());
        }
    }

}
