package net.pluriel.gestionApp;

import net.pluriel.gestionApp.ControllersAndServices.user.UserService;
import net.pluriel.gestionApp.DTO.RoleDto;
import net.pluriel.gestionApp.DTO.UserDto;
import net.pluriel.gestionApp.Models.Role;
import net.pluriel.gestionApp.Models.Status;
import net.pluriel.gestionApp.Models.Permission;
import net.pluriel.gestionApp.Reposotories.PermissionRepository;
import net.pluriel.gestionApp.Reposotories.RoleRepository;
import net.pluriel.gestionApp.mappers.DtoMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.*;

@SpringBootApplication
@EnableTransactionManagement
public class GestionAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionAppApplication.class, args);}

		@Bean
		public CommandLineRunner commandLineRunner(
				UserService service, PermissionRepository permissionRepository, DtoMapper dtoMapper, RoleRepository roleRepository) {
			return args -> {
				List<Permission> permissionsAdmin=new ArrayList<>(List.of(
				new Permission(1,"add_user"),
						new Permission(2,"edit_user"),
						new Permission(3,"delete_user"),
						new Permission(3,"view_users"),
						new Permission(4,"add_contract"),
						new Permission(5,"edit_contract"),
						new Permission(6,"delete_contract"),
						new Permission(7,"view_contracts"),
						new Permission(8,"add_client"),
						new Permission(9,"edit_client"),
						new Permission(10,"delete_client"),
						new Permission(11,"view_clients"),
						new Permission(12,"add_material"),
						new Permission(13,"edit_material"),
						new Permission(14,"delete_material"),
						new Permission(15,"view_material"),
						new Permission(16,"edit_materials"),
						new Permission(17,"delete_materials"),
						new Permission(18,"view_materials")));

				permissionRepository.saveAll(permissionsAdmin);

				List<Permission> permissionsManager=new ArrayList<>(List.of(
						new Permission(4,"add_contract"),
						new Permission(5,"edit_contract"),
						new Permission(6,"delete_contract"),
						new Permission(7,"view_contracts"),
						new Permission(8,"add_client"),
						new Permission(9,"edit_client"),
						new Permission(10,"delete_client"),
						new Permission(11,"view_clients"),
						new Permission(12,"add_material"),
						new Permission(13,"edit_material"),
						new Permission(14,"delete_material"),
						new Permission(15,"view_material"),
						new Permission(16,"edit_materials"),
						new Permission(17,"delete_materials"),
						new Permission(18,"view_materials")
						));

				List<Permission> permissionsUser=new ArrayList<>(List.of(
						new Permission(12,"add_material"),
						new Permission(13,"edit_material"),
						new Permission(14,"delete_material"),
						new Permission(15,"view_material")));

				var roleManager= Role.builder()
						.roleName("MANAGER")
						.permissions(permissionsManager)
						.build();
				var roleUser=Role.builder()
						.roleName("USER")
						.permissions(permissionsUser)
						.build();
				roleRepository.saveAll(List.of(roleManager,roleUser));
				var admin = UserDto.builder()
						.firstName("Admin")
						.lastName("Admin")
						.username("Pluriel")
						.password("az")
						.status(Status.ACTIVE)
						.role(RoleDto.builder()
								.roleName("ADMIN")
								.user(List.of())
								.permissions(permissionRepository.findAll())
								.build())
						.build();

				System.out.println("Admin token: " + service.addUser(admin).getAccessToken());

			};
		}

}
