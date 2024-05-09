package net.pluriel.gestionApp.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roleId;

    private String roleName;

    @OneToMany(mappedBy = "role")
    @JsonIgnore
    private List<User> users;


    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {

        var authorities = new ArrayList<SimpleGrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.roleName));
        if (permissions != null) {
            var list=getPermissions()
                    .stream()
                    .map(permission -> new SimpleGrantedAuthority(permission.getName()))
                            .toList();
            authorities.addAll(list);
        }
        return authorities;
    }

}
