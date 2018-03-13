package com.kinesisstream.app.producer;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.RegionUtils;
import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.AmazonKinesisClientBuilder;
import com.amazonaws.services.kinesis.model.DescribeStreamResult;
import com.amazonaws.services.kinesis.model.PutRecordRequest;
import com.amazonaws.services.sagemaker.model.ResourceNotFoundException;
import com.kinesisstream.app.model.NewsArticle;
import com.kinesisstream.app.utils.ConfigUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.nio.ByteBuffer;

/**
 * Created by supun on 2/15/18.
 */
public class NewsArticlesProducer {

    private static final Log LOG = LogFactory.getLog(NewsArticlesProducer.class);

    /**
     * checking for empty argument
     *
     * @param args
     */
    public static void main(String[] args) throws Exception {
        checkForEmptyArgs(args);

        String streamName = args[0];
        String regionName = args[1];
        Region region = RegionUtils.getRegion(regionName);
        if (region == null) {
            System.err.println(regionName + " is not a valid AWS region.");
            System.exit(1);
        }

        AWSCredentials awsCredentials = ConfigUtils.getCredentialsProvider().getCredentials();

        AmazonKinesis amazonKinesis = AmazonKinesisClientBuilder.defaultClient();

        CheckForStreamAvailability(amazonKinesis,streamName);

        // Repeatedly send new news articles with a 100 milliseconds wait in between
        NewsArticleGenerator newsArticleGenerator = new NewsArticleGenerator();
        while(true) {
            NewsArticle newsArticle = newsArticleGenerator.getNewsArticle();
            sendArticles(newsArticle, amazonKinesis, streamName);
            Thread.sleep(100);
        }

    }

    /**
     *
     * @param newsArticle
     * @param amazonKinesis
     * @param streamName
     */
    private static void sendArticles(NewsArticle newsArticle, AmazonKinesis amazonKinesis, String streamName) {
            byte[] bytes = newsArticle.toJsonAsBytes();
            if (bytes == null) {
                LOG.warn("Could not get JSON bytes for News articles");
                return;
            }

            LOG.info("Putting article: " + newsArticle.toString());
            PutRecordRequest putRecord = new PutRecordRequest();
            putRecord.setStreamName(streamName);
            putRecord.setPartitionKey(newsArticle.getRank());
            putRecord.setData(ByteBuffer.wrap(bytes));

            try {
                amazonKinesis.putRecord(putRecord);
            } catch (AmazonClientException ex) {
                LOG.warn("Error sending record to Amazon Kinesis.", ex);
            }
    }

    /**
     * Checking whether the stream is already exist in account
     * @param amazonKinesis
     * @param streamName
     */
    private static void CheckForStreamAvailability(AmazonKinesis amazonKinesis, String streamName) {
        try {
            DescribeStreamResult result = amazonKinesis.describeStream(streamName);
            if(!"ACTIVE".equals(result.getStreamDescription().getStreamStatus())) {
                System.err.println("Stream " + streamName + " is not active. Please wait a few moments and try again.");
                System.exit(1);
            }
        } catch (ResourceNotFoundException e) {
            System.err.println("Stream " + streamName + " does not exist. Please create it in the console.");
            System.err.println(e);
            System.exit(1);
        } catch (Exception e) {
            System.err.println("Error found while describing the stream " + streamName);
            System.err.println(e);
            System.exit(1);
        }
    }

    /**
     * Checking for empty arguments
     * @param args
     */
    private static void checkForEmptyArgs(String[] args) {
        if (args.length != 2) {
            System.err.println("Usage: " + NewsArticlesProducer.class.getSimpleName()
                    + " <stream name> <region>");
            System.exit(1);
        }
    }
}


