package security;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 *
 * @author Andoni Sanz Alcalde
 */
@Singleton
@Startup
public class SymmetricCryptographyInitializer {

    @PostConstruct
    public void onStartup() {
        SymmetricCryptography symCryp = new SymmetricCryptography();
        symCryp.cifrarTexto("clave", "ttestingson7@gmail.com/lsrc ibnj bdjp vgvq");
    }
}
