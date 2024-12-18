package com.inversionesaraujo.api.business.dto.payload;

import com.inversionesaraujo.api.model.Image;
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
  public Integer id;
  public Role role;
  public String name;
  public String lastName;
  public String username;
  public Image image;
}
