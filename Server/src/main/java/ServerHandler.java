import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class ServerHandler extends ChannelInboundHandlerAdapter {
    private Path repositoryPath = Paths.get("ServerRepositiry");

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Клиент подключился");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Клиент отключился");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof SendingFile) {
            new SaveFile(repositoryPath, (SendingFile) msg);
        } else if (msg instanceof FileRequest) {
            System.out.println("Запрос файла:" + ((FileRequest) msg).getFileName());
        } else {
            System.out.println("Неизвестная команда клинта!");
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    public void saveFile(SendingFile filePackage) throws IOException {
        Files.createFile(repositoryPath.resolve(filePackage.getFileName()));
        Files.write(repositoryPath.resolve(filePackage.getFileName()), filePackage.getData(), StandardOpenOption.APPEND);
    }
}