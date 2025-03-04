package com.inversionesaraujo.api.business.payload;

import com.inversionesaraujo.api.business.dto.ImageDTO;
import com.inversionesaraujo.api.model.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
  private Long id;
  private Role role;
  private String username;
  private String fullName;
  private ImageDTO image;
  private Boolean isVerified;
  private Long cartId;
  private Double totalCart;
}
