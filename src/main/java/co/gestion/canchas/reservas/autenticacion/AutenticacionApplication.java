package co.gestion.canchas.reservas.autenticacion;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AutenticacionApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutenticacionApplication.class, args);

        //String password = "Mf 1010119827@";
        //String bcryptHash = BCrypt.withDefaults().hashToString(12, password.toCharArray());
        //System.out.println("🔐 Hash con BCrypt (sin Spring): " + bcryptHash);
    }

}
