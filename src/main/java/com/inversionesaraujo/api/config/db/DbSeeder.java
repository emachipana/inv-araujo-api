package com.inversionesaraujo.api.config.db;

import java.time.LocalDate;
import java.time.Month;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.inversionesaraujo.api.business.dto.EmployeeDTO;
import com.inversionesaraujo.api.business.dto.ProfitDTO;
import com.inversionesaraujo.api.business.dto.UserDTO;
import com.inversionesaraujo.api.business.service.IEmployee;
import com.inversionesaraujo.api.business.service.IProfit;
import com.inversionesaraujo.api.business.service.IUser;
import com.inversionesaraujo.api.model.Role;

@Configuration
public class DbSeeder {
    @Autowired
    private IUser userService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private IProfit profitService;
	@Autowired
	private IEmployee employeeService;

    @Bean
    CommandLineRunner seedDatabase() {
        return args -> {
			String email = System.getenv("ADMIN_EMAIL");
			String document = System.getenv("ADMIN_DOCUMENT");
			String phone = System.getenv("ADMIN_PHONE");
            EmployeeDTO admin = employeeService.findByEmail(email);

          	if(admin == null) {
              	EmployeeDTO newAdmin = EmployeeDTO
                	.builder()
					.email(email)
					.document(document)
					.rsocial("YURFA ARAUJO ESTRADA")
					.phone(phone)
					.build();

				EmployeeDTO savedAdmin = employeeService.save(newAdmin);
				String password = passwordEncoder.encode(System.getenv("ADMIN_PASSWORD"));
				UserDTO newUser = UserDTO
					.builder()
					.employeeId(savedAdmin.getId())
					.username(email)
					.role(Role.ADMINISTRADOR)
					.password(password)
					.isVerified(true)
					.build();
				
				UserDTO savedUser = userService.save(newUser);

				if(savedUser != null) {
					System.out.println("Administrador creado");
				}else {
					System.out.println("Ocurrio un problema creando el administrador");
				}

          	}else {
            	System.out.println("El administrador ya existe");
        	}

			LocalDate now = LocalDate.now();
			Month month = now.getMonth();
			ProfitDTO profit = profitService.findByMonth(month.toString());
			if(profit == null) {
				ProfitDTO newProfit = profitService.save(ProfitDTO.builder()
					.date(now)
					.month(month.toString())
					.income(0.0)
					.profit(0.0)
					.totalExpenses(0.0)
					.build());
				
				if(newProfit != null) {
					System.out.println("El registro de ingresos del mes se creo correctamente");
				}else {
					System.out.println("Ocurrio un problema creando el registro");
				}
			}else {
				System.out.println("El registro de ingresos del mes ya existe");
			}
      	};
    }
}
