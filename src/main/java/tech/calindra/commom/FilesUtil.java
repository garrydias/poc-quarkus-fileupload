package tech.calindra.commom;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class FilesUtil {

    /**
     * Move um arquivo criando toda hierarquia de diretorios do target caso ela nao exista.
     * @param source
     * @param target
     * @return
     * @throws IOException
     */
    public static Path mv(Path source, Path target) throws IOException {
        Files.createDirectories(target.getParent());
        return Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
    }
}
