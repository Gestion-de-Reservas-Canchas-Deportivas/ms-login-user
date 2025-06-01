package co.gestion.canchas.reservas.autenticacion.configuration;

import co.gestion.canchas.reservas.autenticacion.dto.UserDTO;
import co.gestion.canchas.reservas.autenticacion.dto.UserSecurityDTO;
import co.gestion.canchas.reservas.autenticacion.entity.UserEntity;
import co.gestion.canchas.reservas.autenticacion.repository.UserRepository;
import co.gestion.canchas.reservas.autenticacion.utils.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static co.gestion.canchas.reservas.autenticacion.utils.constants.Constantes.ERROR_USUARIO_NO_ENCONTRADO;


/**
 * Configuración principal para la aplicación.
 */
@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailUserService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean(name = "userDetailsUserService")
    public UserDetailsService userDetailUserService() {
        return username -> {
            UserEntity user = userRepository.findByEmailAddress(username)
                    .orElseThrow(() -> new UsernameNotFoundException(ERROR_USUARIO_NO_ENCONTRADO + username));
            UserDTO userDTO = userMapper.entityToDto(user);

            UserSecurityDTO userSecurityDTO = new UserSecurityDTO(userDTO);
            userSecurityDTO.setPassword(user.getHashPassword());

            return userSecurityDTO;
        };
    }

    @Bean
    public WebMvcConfigurer corsConfigurerDev() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:4200")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*");
            }
        };
    }
}
