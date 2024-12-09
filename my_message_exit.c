#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <cmqc.h>
#include <cmqxc.h>

#define LOG_FILE "/var/log/mq_channel_exit.log"

/* Message Exit Function */
void MQENTRY MyMessageExit(
    PMQVOID    channelExitParms,   /* Channel exit parameter structure */
    PMQVOID    channelDefinition,  /* Channel definition structure */
    PMQVOID    dataLength,         /* Length of message data */
    PMQVOID    messageBuffer       /* Message data */
) {
    PMQCXP pCXP = (PMQCXP)channelExitParms;
    PMQCD  pCD  = (PMQCD)channelDefinition;
    FILE  *logFile;

    /* Open log file */
    logFile = fopen(LOG_FILE, "a");
    if (logFile == NULL) {
        return; /* Exit if log file cannot be opened */
    }

    /* Log channel details */
    fprintf(logFile, "Channel Name: %.20s\n", pCD->ChannelName);
    fprintf(logFile, "Exit Reason: %d\n", pCXP->ExitReason);

    /* Log message details if available */
    if (messageBuffer != NULL && *(PMQLONG)dataLength > 0) {
        fprintf(logFile, "Message Length: %d\n", *(PMQLONG)dataLength);
        fprintf(logFile, "Message Content: %.*s\n", *(PMQLONG)dataLength, (char *)messageBuffer);
    } else {
        fprintf(logFile, "No message content available\n");
    }

    /* Close the log file */
    fclose(logFile);
}
