package com.kinesisstream.app.consumer;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.RegionUtils;
import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessorFactory;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.KinesisClientLibConfiguration;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.Worker;
import com.amazonaws.util.CredentialUtils;
import com.kinesisstream.app.utils.ConfigUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.UUID;
import java.util.logging.Logger;

/**
* Created by supun on 2/22/18.
*/
public class NewsArticlesConsumer {
    public static final Log LOG = LogFactory.getLog(NewsArticlesConsumer.class);
    private static final Logger ROOT_LOGGER = Logger.getLogger("");
    private static final Logger PROCESSOR_LOGGER =
            Logger.getLogger("com.kinesisstream.app.consumer.NewsArticlesConsumer");


    public static void main(String[] args) throws Exception {
        checkUsage(args);

        String applicationName = args[0];
        String streamName = args[1];
        Region region = RegionUtils.getRegion(args[2]);
        if (region == null) {
            System.err.println(args[2] + " is not a valid AWS region.");
            System.exit(1);
        }


        AWSCredentialsProvider credentialsProvider = ConfigUtils.getCredentialsProvider();

        String workerId = String.valueOf(UUID.randomUUID());
        KinesisClientLibConfiguration kclConfig =
                new KinesisClientLibConfiguration(applicationName, streamName, credentialsProvider, workerId)
                        .withRegionName(region.getName())
                        .withCommonClientConfig(ConfigUtils.getClientConfigWithUserAgent());

        IRecordProcessorFactory recordProcessorFactory = new NewsArticleRecordProcessorFactory();

        Worker worker = new Worker(recordProcessorFactory, kclConfig);

        int exitCode = 0;
        try {
            worker.run();
        } catch (Throwable t) {
            LOG.error("Caught throwable while processing data.", t);
            exitCode = 1;
        }
        System.exit(exitCode);

    }

    private static void checkUsage(String[] args) {
        if (args.length != 3) {
            System.err.println("Usage: " + NewsArticlesConsumer.class.getSimpleName()
                    + " <application name> <stream name> <region>");
            System.exit(1);
        }
    }



}

