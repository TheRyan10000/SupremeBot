package supremebot;

import net.dv8tion.jda.api.entities.Member;
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
                if (message[0].equals(">>man") && message[1].equals("adm"))
                {
                    event.getAuthor().openPrivateChannel().queue(privateChannel -> privateChannel.sendMessage
                            (("```>>derank all [server/channel] [if channel, channel ID] \n" +
                                    ">>rank [all/channel] [if channel, channel ID] roleID\n" +
                                    ">>move channel fromChannelID toChannelID\n" +
                                    ">>derank user [userID]\n" +
                                    "to talk in Senate channel: >>senate [message here]" +
                                    " ```")).queue());
                }

                if (message[0].equals(">>senate"))
                {
                    event.getMessage().delete().queue();
                    String temp = event.getMessage().getContentRaw();
                    event.getJDA().getTextChannelById("678853211758657536").sendMessage(temp.substring(8)).queue();
                }

                if (message[0].equals(">>derank") && message[1].equals("user"))
                {
                    event.getMessage().delete().queue();
                    Member user = event.getGuild().getMemberById(message[2]);
                    for (Role userRoles : user.getJDA().getRoles())
                    {
                        event.getGuild().removeRoleFromMember(user, userRoles).queue();
                    }
                }

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
            if (message[0].equals(">>ban"))
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


