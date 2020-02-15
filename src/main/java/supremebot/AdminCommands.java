package supremebot;

import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;


public class AdminCommands extends ListenerAdapter
{

    public static boolean derank = false;
    public static String derankChannel = "";

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event)
    {
        String[] message = event.getMessage().getContentRaw().split(" ");


        if (message[0].equals(">>strike"))
        {

        }
        for (Role r : event.getMember().getRoles())
        {
            if (r.getName().equals("Bot Controller"))
            {
                if (message[0].equals(">>derank"))
                {
                    AdminCommands.derank = true;
                    AdminCommands.derankChannel = message[1];


                }
                if (message[0].equals("derank") && message[1].equals("off"))
                {
                    derank = false;
                    derankChannel = "";
                }
            }
            if (message[0].equals("!ban"))
            {
                event.getJDA().getUserById(message[1]);
                event.getChannel().sendMessage
                        (event.getJDA().getUserById(message[1]).getName() +
                                " has been banned " +
                                "for " + message[2] + " days").queue();
                if (message[2].isEmpty())
                    message[2] = "7";
                event.getGuild().ban(event.getJDA().getUserById(message[1]),
                        Integer.parseInt(message[2])).queue();
            }

            if (message[0].equals("!shutdown"))
            {
                event.getChannel().sendMessage("Bot shut down started by " +
                        event.getMember().getEffectiveName()).queue();
                try
                {
                    Thread.sleep(1000);
                    System.exit(1);
                } catch (InterruptedException i)
                {
                    i.printStackTrace();
                }

            }
        }
//            else
//            {
//                if (!(event.getAuthor().isBot()))
//                {
//                    event.getChannel().sendMessage
//                    ("You are not permitted to use moderation commands!")
//                    .queue();
//                }
//            }
    }
}


