package supremebot;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import org.json.simple.JSONObject;

import javax.security.auth.login.LoginException;
import java.io.*;
import java.util.Properties;


public class Main
{
    public static java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger("myLog");
    public static Properties prop = new Properties();
    public static FileWriter fw;
    public static JSONObject jsonObject;
    public static JDABuilder api;


    public static void main(String[] args) throws IOException
    {

        try
        {
            File file = new File(System.getProperty("user.dir")
                    + "/config.properties");
            System.out.println(file.getAbsolutePath());
            InputStream is = new FileInputStream(file);
            prop = new Properties();
            prop.load(is);
        } catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
        String token = prop.getProperty("token");
        try {
            api = new JDABuilder();
            api.setToken(token);


            api.setStatus(OnlineStatus.ONLINE);
            api.setActivity(Activity.watching("for commands"));

            api.addEventListeners(new Logger());
            api.addEventListeners(new AdminCommands());
            api.addEventListeners(new MessageDeleted());
            api.addEventListeners(new JsonLogger());
            api.addEventListeners(new Derank());
            api.build();
        } catch (LoginException e) {
            System.out.println("Login Failure!");
        }
    }
}
