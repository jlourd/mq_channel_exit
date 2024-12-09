import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import com.ibm.mq.exit.*;

public class ChannelMessageExit implements MQChannelExit {
    private static final String LOG_FILE = System.getProperty("user.home") + "/mq_messages.log";

    @Override
    public byte[] channelExit(MQChannelExitData channelExitData) {
        try (PrintWriter logWriter = new PrintWriter(new FileWriter(LOG_FILE, true))) {
            // Log channel details
            logWriter.println("Channel Name: " + channelExitData.getChannelName());
            
            // Log message details if available
            if (channelExitData.getMessageBuffer() != null) {
                String message = new String(channelExitData.getMessageBuffer());
                logWriter.println("Message Length: " + channelExitData.getMessageBuffer().length);
                logWriter.println("Message Content: " + message);
            } else {
                logWriter.println("No message content.");
            }

            logWriter.println("--------------------------------");

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Return the message buffer unchanged for normal processing
        return channelExitData.getMessageBuffer();
    }
}
