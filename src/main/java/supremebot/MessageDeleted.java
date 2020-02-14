package supremebot;

import net.dv8tion.jda.api.events.message.guild.GuildMessageDeleteEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.io.IOException;

public class MessageDeleted extends ListenerAdapter {

    @Override
    public void onGuildMessageDelete(@Nonnull GuildMessageDeleteEvent event) {
        Main.logger.info("A message with ID " + event.getMessageId() + " has been deleted!");

        Main.jsonObject.put("Deleted-Message-ID", event.getMessageId());


        try {
            Main.fw.append(Main.jsonObject.toJSONString());
            Main.fw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
