package supremebot;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;


public class Logger extends ListenerAdapter {
    Calendar cal = Calendar.getInstance();
    SimpleDateFormat loggerFile = new SimpleDateFormat("M-d-y-HH-mm-ss-z");
    SimpleDateFormat consoleOutput = new SimpleDateFormat("M-d-HH-mm");

    {
        try {

            FileHandler fh = new FileHandler("server-log-" + loggerFile.format(cal.getTime()) + ".txt");
            fh.setFormatter(new SimpleFormatter());
            Main.logger.addHandler(fh);
            Main.logger.setUseParentHandlers(false);
            Main.logger.info("This begins the log from: " + loggerFile.format(cal.getTime()));

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        cal = Calendar.getInstance();
        String messageID = event.getMessageId();
        Main.logger.info(loggerFile.format(cal.getTime()) + ":" + event.getAuthor().getAsTag() + ":" +
                messageID +
                event.getMessage().getContentRaw());
        System.out.println(consoleOutput.format(cal.getTime()) + ":" +
                event.getAuthor().getAsTag() + ":" + event.getMessage().getContentRaw());
    }


}