package supremebot;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.json.simple.JSONObject;

import javax.annotation.Nonnull;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static supremebot.Main.jsonObject;

public class JsonLogger extends ListenerAdapter {
    {
        jsonObject = new JSONObject();
    }

    Calendar cal = Calendar.getInstance();
    SimpleDateFormat loggerFile = new SimpleDateFormat("M-d-y-HH-mm-ss-z");

    {
        try {
            Main.fw = new FileWriter(System.getProperty("user.dir") + "-" +
                    loggerFile.format(cal.getTime()) + ".json");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        cal = Calendar.getInstance();
        jsonObject.put("MessageID", event.getMessage().getId());
        jsonObject.put("Username", event.getMember().getEffectiveName());
        jsonObject.put("UserID", event.getAuthor().getId());
        jsonObject.put("ChannelID", event.getChannel().getId());
        jsonObject.put("Message", event.getMessage().getContentRaw());
        jsonObject.put("Time", loggerFile.format(cal.getTime()));
        jsonObject.put("Deleted-Message-ID", "null");
        try {
            Main.fw.append(jsonObject.toJSONString());
            Main.fw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
