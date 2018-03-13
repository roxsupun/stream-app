package com.kinesisstream.app.consumer;

import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessor;
import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessorFactory;


/**
 * Created by supun on 2/22/18.
 */
public class NewsArticleRecordProcessorFactory implements IRecordProcessorFactory {

    public NewsArticleRecordProcessorFactory() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IRecordProcessor createProcessor() {
        return new NewsArticlesRecordConsumer();
    }
}
