import com.ibm.mq.MQQueueManager;
import com.ibm.mq.constants.CMQC;

public class ChannelExitTest {
    public static void main(String[] args) {
        MQQueueManager qMgr = null;

        try {
            // Connect to the queue manager
            qMgr = new MQQueueManager("QM1");

            // Open a queue for PUT and GET
            int openOptions = CMQC.MQOO_INPUT_AS_Q_DEF | CMQC.MQOO_OUTPUT;
            com.ibm.mq.MQQueue queue = qMgr.accessQueue("MY.QUEUE", openOptions);

            // Simulate sending a message
            com.ibm.mq.MQMessage message = new com.ibm.mq.MQMessage();
            message.writeString("Test message for channel exit");
            com.ibm.mq.MQPutMessageOptions pmo = new com.ibm.mq.MQPutMessageOptions();
            queue.put(message, pmo);
            System.out.println("Message sent.");

            // Simulate receiving a message
            com.ibm.mq.MQMessage receivedMessage = new com.ibm.mq.MQMessage();
            com.ibm.mq.MQGetMessageOptions gmo = new com.ibm.mq.MQGetMessageOptions();
            queue.get(receivedMessage, gmo);
            System.out.println("Message received: " + receivedMessage.readStringOfByteLength(receivedMessage.getMessageLength()));

            // Close the queue
            queue.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (qMgr != null) {
                    qMgr.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
