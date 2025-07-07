package com.inversionesaraujo.api.business.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inversionesaraujo.api.model.Client;
import com.inversionesaraujo.api.model.Employee;
import com.inversionesaraujo.api.model.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
	private Long id;
	private RoleDTO role;
	private Long clientId;
	private Long employeeId;
	private ImageDTO image;
	private String fullName;
	private String username;
	private Boolean isVerified;
	@JsonIgnore
	private String password;

	public static UserDTO toDTO(User user) {
		return UserDTO
			.builder()
			.id(user.getId())
			.role(RoleDTO.toDTO(user.getRole()))
			.clientId(user.getClient() != null ? user.getClient().getId() : null)
			.employeeId(user.getEmployee() != null ?  user.getEmployee().getId() : null)
			.image(ImageDTO.toDTO(user.getImage()))
			.username(user.getUsername())
			.password(user.getPassword())
			.fullName(user.getEmployee() == null ? user.getClient().getRsocial() : user.getEmployee().getRsocial())
			.isVerified(user.getIsVerified() == null ? false : user.getIsVerified())
			.build();
	}

	public static User toEntity(UserDTO user) {
		Client client = new Client();
		if(user.getClientId() != null) client.setId(user.getClientId());
		else client = null;

		Employee employee = new Employee();
		if(user.getEmployeeId() != null) employee.setId(user.getEmployeeId());
		else employee = null;

		return User
			.builder()
			.id(user.getId())
			.role(RoleDTO.toEntity(user.getRole()))
			.client(client)
			.employee(employee)
			.image(ImageDTO.toEntity(user.getImage()))
			.username(user.getUsername())
			.password(user.getPassword())
			.isVerified(user.getIsVerified() == null ? false : user.getIsVerified())
			.build();
	}

	public static List<UserDTO> toListDTO(List<User> users) {
		return users
			.stream()
			.map(UserDTO::toDTO)
			.collect(Collectors.toList());
	}
}
