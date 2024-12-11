#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <cmqec.h>

#define LOG_FILE "/var/log/channel_exit_messages.log"

/* Dummy Entry Point */
void MQStart() { ; } /* For consistency only */

/* Channel Exit Function */
void MQENTRY ChannelExit(
    PMQCXP  pChannelExitParms,    /* Channel exit parameter structure */
    PMQCD   pChannelDefinition,   /* Channel definition structure */
    PMQLONG pDataLength,          /* Length of message data */
    PMQLONG pAgentBufferLength,   /* Length of agent buffer */
    PMQVOID pAgentBuffer,         /* Message data (agent buffer) */
    PMQLONG pExitBufferLength,    /* Length of exit buffer */
    PMQPTR  pExitBufferAddr       /* Address of exit buffer */
) {
    FILE *logFile;

    /* Open the log file */
    logFile = fopen(LOG_FILE, "a");
    if (logFile == NULL) {
        /* Cannot open log file */
        return;
    }

    /* Log general channel information */
    fprintf(logFile, "Channel Name: %.20s\n", pChannelDefinition->ChannelName);
    fprintf(logFile, "Exit Reason Code: %ld\n", pChannelExitParms->ExitReason);

    /* Log message details if available */
    if (pAgentBuffer != NULL && *pDataLength > 0) {
        fprintf(logFile, "Message Length: %ld\n", *pDataLength);
        fprintf(logFile, "Message Content: %.*s\n", (int)*pDataLength, (char *)pAgentBuffer);
    } else {
        fprintf(logFile, "No message data available\n");
    }

    /* Log a separator for clarity */
    fprintf(logFile, "---------------------------------\n");

    /* Close the log file */
    fclose(logFile);

    /* Ensure the channel proceeds as normal */
    pChannelExitParms->ExitResponse = MQXCC_OK;
}
