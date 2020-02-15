/**
 * Derank class
 */


package supremebot;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class Derank extends ListenerAdapter {


    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {

        String[] message = event.getMessage().getContentRaw().split(" ");


        for (Role r : event.getMember().getRoles()) {
            if (r.getName().equals("Bot Controller")) {


                if (message[0].equals(">>move") && message[1].equals("channel")) {
                    event.getMessage().delete().queue();
                    for (Member u : event.getGuild().getGuildChannelById(message[2]).getMembers()) {
                        event.getGuild().moveVoiceMember(u, event.getGuild().getVoiceChannelById(message[3])).queue();
                    }
                }

                if (message[0].equals(">>derank") && message[1].equals("all") && message[2].equals("channel")) {
                    event.getMessage().delete().queue();
                    for (Member u : event.getGuild().getGuildChannelById(message[3]).getMembers()) {
                        for (Role ur : u.getRoles()) {
                            if (ur.getName().equalsIgnoreCase("Bot Controller")) {
                                event.getJDA().getUserById(u.getId()).openPrivateChannel().queue(privateChannel ->
                                        privateChannel.sendMessage("Your rank has been preserved").queue());
                            } else event.getGuild().removeRoleFromMember(u, ur).queue();
                        }
                    }
                }

                if (message[0].equals(">>derank") && message[1].equals("all") && message[2].equals("server")) {
                    for (Member u : event.getGuild().getMembers()) {
                        for (Role ur : u.getRoles()) {
                            if (ur.getName().equals("Bot Controller"))
                            {
                                event.getJDA().getUserById(u.getId()).openPrivateChannel().queue(privateChannel ->
                                        privateChannel.sendMessage("Your rank has been preserved").queue());
                            } else if (!u.getUser().isBot())
                            {
                                event.getGuild().removeRoleFromMember(u, ur).queue();
                            }
                        }
                    }
                }

                if (message[0].equals(">>rank") && message[1].equals("all"))
                {
                    event.getMessage().delete().queue();
                    for (Member u : event.getGuild().getMembers())
                    {
                        if (!message[2].isEmpty())
                        {
                            event.getGuild().addRoleToMember(u.getId(), event.getJDA().getRoleById(message[2])).queue();
                        } else
                        {
                            throw new IllegalArgumentException("Message[2] null!");
                        }
                    }
                } else if (message[0].equals(">>rank") && message[1].equals("channel"))
                {
                    for (Member u : event.getJDA().getGuildChannelById(message[2]).getMembers())
                    {
                        if (!message[3].isEmpty())
                        {
                            event.getGuild().addRoleToMember(u.getId(), event.getJDA().getRoleById(message[3])).queue();
                        }
                    }
                }


                if (message[0].equals(">>man")) {
                    event.getChannel().sendMessage("```>>derank all [server/channel] [if channel, channel ID] \n" +
                            ">>rank [all/channel] [if channel, channel ID] roleID\n" +
                            ">>move channel fromChannelID toChannelID ```").queue();
                }
            }
        }

    }
}