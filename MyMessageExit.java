import com.ibm.mq.*;

public class MQCCDTExample {
    public static void main(String[] args) {
        MQQueueManager qMgr = null;

        try {
            // Connect to the queue manager using the CCDT file
            qMgr = new MQQueueManager("QM1");

            // Open a queue for PUT and GET
            int openOptions = CMQC.MQOO_INPUT_AS_Q_DEF | CMQC.MQOO_OUTPUT;
            MQQueue queue = qMgr.accessQueue("MY.QUEUE", openOptions);

            // Create a message
            MQMessage message = new MQMessage();
            message.writeString("Hello from Java with CCDT!");

            // Put the message on the queue
            MQPutMessageOptions pmo = new MQPutMessageOptions();
            queue.put(message, pmo);
            System.out.println("Message sent to the queue.");

            // Get a message from the queue
            MQMessage receivedMessage = new MQMessage();
            MQGetMessageOptions gmo = new MQGetMessageOptions();
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
            } catch (MQException e) {
                e.printStackTrace();
            }
        }
    }
}
