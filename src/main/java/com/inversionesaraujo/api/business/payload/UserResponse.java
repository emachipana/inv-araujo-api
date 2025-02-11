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
  public Long id;
  public Role role;
  public String username;
  public String fullName;
  public ImageDTO image;
}
