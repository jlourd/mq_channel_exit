import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import com.ibm.mq.exit.*;

public class MyMessageExit implements MQChannelExit {
    private static final String LOG_FILE = "/var/log/mq_channel_exit.log";

    @Override
    public byte[] channelExit(MQChannelExitData channelExitData) {
        try (PrintWriter logWriter = new PrintWriter(new FileWriter(LOG_FILE, true))) {
            // Log channel details
            logWriter.println("Channel Name: " + channelExitData.getChannelName());

            // Log message details
            if (channelExitData.getMessageBuffer() != null) {
                String message = new String(channelExitData.getMessageBuffer());
                logWriter.println("Message Content: " + message);
                logWriter.println("Message Length: " + channelExitData.getMessageBuffer().length);
            } else {
                logWriter.println("No message content");
            }

            // Log the exit reason
            logWriter.println("Exit Reason: " + channelExitData.getExitReason());
            logWriter.println("-------------------------------");

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Pass the message buffer back to MQ for normal processing
        return channelExitData.getMessageBuffer();
    }
}
