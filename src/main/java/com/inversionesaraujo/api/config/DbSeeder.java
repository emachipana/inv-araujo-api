package com.inversionesaraujo.api.config;

import java.time.LocalDate;
import java.time.Month;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.inversionesaraujo.api.model.entity.Admin;
import com.inversionesaraujo.api.model.entity.Profit;
import com.inversionesaraujo.api.model.entity.Role;
import com.inversionesaraujo.api.model.entity.User;
import com.inversionesaraujo.api.service.IAdmin;
import com.inversionesaraujo.api.service.IProfit;
import com.inversionesaraujo.api.service.IUser;

@Configuration
public class DbSeeder {
    @Autowired
    private IAdmin adminService;
    @Autowired
    private IUser userService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private IProfit profitService;

    @Bean
    CommandLineRunner seedDatabase() {
        return args -> {
			String email = System.getenv("ADMIN_EMAIL");
            Admin admin = adminService.findByEmail(email);

          	if(admin == null) {
              	Admin newAdmin = Admin
                	.builder()
					.email(email)
					.firstName("Yurfa")
					.lastName("Araujo Estrada")
					.build();

				Admin savedAdmin = adminService.save(newAdmin);
				User newUser = User
					.builder()
					.admin(savedAdmin)
					.username(email)
					.password(passwordEncoder.encode(System.getenv("ADMIN_PASSWORD")))
					.role(Role.ADMINISTRADOR)
					.build();
				
				User savedUser = userService.save(newUser);

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
			Profit profit = profitService.findByMonth(month.toString());
			if(profit == null) {
				Admin adminFound = adminService.findByEmail(email);
				Profit newProfit = profitService.save(Profit.builder()
					.admin(adminFound)
					.date(now)
					.month(month.toString())
					.build());
				
				if(newProfit != null) {
					System.out.println("El registro de ingresos del mes se creo correctamente");
				}else {
					System.out.println("Ocurrio un problema creando el registro");
				}
			}else {
				System.out.println("El registro de ingresos del mesa ya existe");
			}
      	};
    }
}
