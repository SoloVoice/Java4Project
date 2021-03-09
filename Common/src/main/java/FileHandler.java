import io.netty.channel.Channel;
import io.netty.channel.socket.SocketChannel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public interface FileHandler {

    static void sendFile(Path repo, String fileName, Channel channel) {
        Path path = repo.resolve(fileName);
        try {
            channel.writeAndFlush(new SendingFile(path));
            System.out.println("Файл отправлен");
        } catch (IOException e) {
            System.out.println("Такого файла не существует!");
        }
    }

    static void fileRequest(SocketChannel channel, String fileName) {
        channel.writeAndFlush(new FileRequest(fileName));
    }

    static void renameRequest(Channel channel, String fileName) {
        channel.writeAndFlush(new FileRenamer(fileName));
    }

    static void deleteRequest(Channel channel, String fileName) {
        channel.writeAndFlush(new FileDeletter(fileName));
    }

    static void save(Path path, SendingFile filePackage) {
        try {
            Files.createFile(path.resolve(filePackage.getFileName()));
            Files.write(path.resolve(filePackage.getFileName()), filePackage.getData(), StandardOpenOption.APPEND);
            System.out.println("Записан фаил: " + filePackage.getFileName());
        } catch (IOException e) {
            System.out.println("Фаил " + filePackage.getFileName() + " не записан");
        }
    }

    static void rename(Path path, String oldNameNewName) {
        String[] names = oldNameNewName.split(" ");
        try {
            Files.move(path.resolve(names[0]), path.resolveSibling(path.resolve(names[1])));
            System.out.println("Фаил " + names[0]+ " переименован в " + names[1]);
        } catch (IOException e) {
            System.out.println("Невозможно переименовать файл");
        }
    }

    static void delete(Path path, String fileName) {
        try {
            Files.delete(path.resolve(fileName));
            System.out.println("Фаил "+ fileName+ " удален!");
        } catch (IOException e) {
            System.out.println("Невозможно удалить фаил!");
        }
    }
    static void showRepository(Path path) {
        try {
            System.out.println(Files.list(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
