import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ServerHandler extends ChannelInboundHandlerAdapter {
    private Path repositoryPath = Paths.get("ServerRepository");

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
            FileHandler.save(repositoryPath, (SendingFile) msg);
        } else if (msg instanceof FileRequest) {
            FileHandler.sendFile(repositoryPath, ((FileRequest) msg).getFileName(), ctx.channel());
        } else if (msg instanceof FileRenamer) {
            FileHandler.rename(repositoryPath, ((FileRenamer) msg).getFileName());
        } else if (msg instanceof FileDeletter) {
            FileHandler.delete(repositoryPath, (((FileDeletter) msg).getFileName()));
        } else {
            System.out.println("Неизвестная команда клинта!");
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}