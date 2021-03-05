import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class SaveFile {
    private SendingFile filePackage;
    private Path repository;

    public SaveFile(Path path, SendingFile filePackage) {
        this.filePackage = filePackage;
        repository = path;
        this.saveFile();
    }

    public void saveFile() {
        try {
            Files.createFile(repository.resolve(filePackage.getFileName()));
            Files.write(repository.resolve(filePackage.getFileName()), filePackage.getData(), StandardOpenOption.APPEND);
            System.out.println("Записан фаил: "+ filePackage.getFileName());
        } catch (IOException e) {
            System.out.println("Фаил " + filePackage.getFileName() + " не записан");
        }

    }
}
