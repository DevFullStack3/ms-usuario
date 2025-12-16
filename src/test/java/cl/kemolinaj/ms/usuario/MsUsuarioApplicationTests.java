package cl.kemolinaj.ms.usuario;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MsUsuarioApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void applicationStartsWithoutErrors() {
        Assertions.assertDoesNotThrow(() -> MsUsuarioApplication.main(new String[]{}));
    }

}
