package net.pluriel.gestionApp;

import net.pluriel.gestionApp.service.UserService;

import net.pluriel.gestionApp.dto.UserDto;
import net.pluriel.gestionApp.models.Role;
import net.pluriel.gestionApp.models.Status;
import net.pluriel.gestionApp.models.Permission;
import net.pluriel.gestionApp.reposotorie.PermissionRepository;
import net.pluriel.gestionApp.reposotorie.RoleRepository;
import net.pluriel.gestionApp.reposotorie.UserRepository;
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

		/*@Bean
		public CommandLineRunner commandLineRunner(
				UserService service, PermissionRepository permissionRepository, DtoMapper dtoMapper, RoleRepository roleRepository, UserRepository userRepository) {
			return args -> {



				var roleAdmin= Role.builder()
						.roleName("ADMIN")
						.build();
				Role roleAdminSaved=roleRepository.save(roleAdmin);
				var roleManager= Role.builder()
						.roleName("MANAGER")
						.build();
				Role roleManagerSaved=roleRepository.save(roleManager);
				var roleUser=Role.builder()
						.roleName("USER")
						.build();
				Role roleUserSaved=roleRepository.save(roleUser);
				List<Permission> permissions=new ArrayList<>(List.of(
						new Permission(1,"add_user",List.of(roleAdminSaved,roleManagerSaved)),
						new Permission(2,"edit_user",List.of(roleAdminSaved,roleManagerSaved)),
						new Permission(3,"delete_user",List.of(roleAdminSaved,roleManagerSaved)),
						new Permission(4,"view_users",List.of(roleAdminSaved,roleManagerSaved)),
						new Permission(5,"add_contract",List.of(roleAdminSaved)),
						new Permission(6,"edit_contract",List.of(roleAdminSaved)),
						new Permission(7,"delete_contract",List.of(roleAdminSaved)),
						new Permission(8,"view_contracts",List.of(roleAdminSaved)),
						new Permission(9,"add_client",List.of(roleAdminSaved,roleManagerSaved)),
						new Permission(10,"edit_client",List.of(roleAdminSaved,roleManagerSaved)),
						new Permission(11,"delete_client",List.of(roleAdminSaved,roleManagerSaved)),
						new Permission(12,"view_clients",List.of(roleAdminSaved,roleManagerSaved)),
						new Permission(13,"add_material",List.of(roleAdminSaved,roleManagerSaved,roleUserSaved)),
						new Permission(14,"edit_material",List.of(roleAdminSaved,roleManagerSaved,roleUserSaved)),
						new Permission(15,"delete_material",List.of(roleAdminSaved,roleManagerSaved,roleUserSaved)),
						new Permission(16,"view_material",List.of(roleAdminSaved,roleManagerSaved,roleUserSaved)),
						new Permission(17,"edit_materials",List.of(roleAdminSaved)),
						new Permission(18,"view_materials",List.of(roleAdminSaved)),
						new Permission(19,"add_role",List.of(roleAdminSaved)),
						new Permission(20,"edit_role",List.of(roleAdminSaved)),
						new Permission(21,"delete_role",List.of(roleAdminSaved)),
						new Permission(22,"view_roles",List.of(roleAdminSaved)),
						new Permission(23,"add_annonce",List.of(roleAdminSaved)),
						new Permission(24,"edit_annonce",List.of(roleAdminSaved))
				));
				permissionRepository.saveAll(permissions);
				roleAdminSaved.setPermissions(permissions);
				roleAdminSaved=roleRepository.save(roleAdminSaved);
				List<Permission> managerPermissions = Arrays.asList(
						permissionRepository.findByName("add_user").orElseThrow(),
						permissionRepository.findByName("edit_user").orElseThrow(),
						permissionRepository.findByName("delete_user").orElseThrow(),
						permissionRepository.findByName("view_users").orElseThrow(),
						permissionRepository.findByName("add_client").orElseThrow(),
						permissionRepository.findByName("edit_client").orElseThrow(),
						permissionRepository.findByName("delete_client").orElseThrow(),
						permissionRepository.findByName("add_material").orElseThrow(),
						permissionRepository.findByName("edit_material").orElseThrow(),
						permissionRepository.findByName("delete_material").orElseThrow(),
						permissionRepository.findByName("view_material").orElseThrow()
				);
				roleManagerSaved.setPermissions(managerPermissions);
				roleRepository.save(roleManagerSaved);
				List<Permission> userPermissions = Arrays.asList(
						permissionRepository.findByName("add_material").orElseThrow(),
						permissionRepository.findByName("edit_material").orElseThrow(),
						permissionRepository.findByName("delete_material").orElseThrow(),
						permissionRepository.findByName("view_material").orElseThrow());

				roleUserSaved.setPermissions(userPermissions);
				roleRepository.save(roleUserSaved);
				var admin = UserDto.builder()
						.firstName("Admin")
						.lastName("Admin")
						.username("Yassine")
						.password("az")
						.status(Status.ACTIVE)
						.role(dtoMapper.toRoleDto(roleAdminSaved))
						.build();

				System.out.println("Admin token: " + service.addUser(admin).getAccessToken());

			};
		}*/

}
