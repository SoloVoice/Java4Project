import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


public class SendingFile extends CanBeSerializable {
    private int parts;
    private int currentPart;
    private String fileName;
    private byte[] data;

    public String getFileName() { return fileName;}

    public byte[] getData() {return data;}

    public SendingFile (Path path) throws IOException {
        fileName =path.getFileName().toString();
        data = Files.readAllBytes(path);

    }
}
