package com.rbbnbb.TediTry1;

import com.rbbnbb.TediTry1.domain.Role;
import com.rbbnbb.TediTry1.domain.User;
import com.rbbnbb.TediTry1.repository.RoleRepository;
import com.rbbnbb.TediTry1.repository.UserRepository;
import com.rbbnbb.TediTry1.services.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@SpringBootApplication
@EnableScheduling
public class TediTry1Application {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(TediTry1Application.class, args);
	}
	@Bean
	CommandLineRunner run(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder){
		return args ->{
			if (roleRepository.findByAuthority("ADMIN").isPresent()) return;
			Role adminRole = roleRepository.save(new Role("ADMIN"));
			roleRepository.save(new Role("TENANT"));
			roleRepository.save(new Role("HOST"));

			Set<Role> roles = new HashSet<>();
			roles.add(adminRole);

			User admin = new User(1L,"admin",passwordEncoder.encode("password"),roles);
			userRepository.save(admin);
		};
	}

	@Scheduled(fixedDelay = 1000L * 60 * 60 * 24, initialDelay = 1000L * 60)
	public void updateRecommendedRentals(){
		Role tenantRole = roleRepository.findByAuthority("TENANT").get();
		List<User> allTenants = userRepository.findAll();
		allTenants.removeIf(t -> (!t.getAuthorities().contains(tenantRole)));
		RecommendationService.setRentalsToRecommend(allTenants);
	}
}
