package realtyhub.user.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

/**
 * @author BSA
 * Date 25.01.25
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public final class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @Column(name = "name")
    @NotBlank
    @Size(min = 3, max = 20, message = "Name must be between 2 and 20 characters!")
    private String name;

    @Column(name = "surname")
    @NotBlank
    @Size(min = 2, max = 20, message = "Surname must be between 2 and 20 characters!")
    private String surname;

    @Positive
    @NotNull
    @Column(name = "age")
    private int age;

    @Column(name = "email")
    @NotBlank
    @Email(message = "Email is invalid!")
    private String email;

    @Column(name = "password")
    @NotBlank
    @Size(min = 8, message = "Password must be at least 8 characters long!")
    private String password;

    @Column(name = "phone_number")
    @NotBlank
    @Pattern(regexp = "^(\\+90|0)?[5][0-9]{9}$", message = "Invalid phone number!")
    private String phoneNumber;

    @Column(name = "otp_code")
    private String otpCode;

    @Column(name = "confirm_code")
    private String confirmCode;

    @Column(name = "password_change_day")
    private LocalDate lastPasswordChange;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + userRole.name()));
    }

    @Override
    public String getUsername() {
        return getEmail();
    }

}
