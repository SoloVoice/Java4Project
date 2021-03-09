import java.nio.file.Path;

public class NewSendingFile {
    private int parts;
    private int currentPart = 1;
    private String fileName;
    private byte[] data;

    public NewSendingFile(Path path, int parts, byte[] data) {
        this.fileName = path.getFileName().toString();
        this.parts = parts;
        this.data = data;
    }
}
