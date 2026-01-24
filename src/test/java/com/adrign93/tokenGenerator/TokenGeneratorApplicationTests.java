package com.adrign93.tokenGenerator;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TokenGeneratorApplicationTests {

	@Test
	void mainMethodTest() {
		// Se llama al main con un array de argumentos vacío
		// Esto levanta la app y luego la cierra (si no hay errores)
		TokenGeneratorApplication.main(new String[] {});
	}

}
