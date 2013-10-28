package com.papteco.client.bqueue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.papteco.web.beans.QueueItem;

public class QueueBuilder {

	public final static DownloadActionQueue downloadActionQueue = new DownloadActionQueue();

	public final static ExecutorService service = Executors
			.newCachedThreadPool();

	public static void submitMultipleConsumers(int count) {
		for (int i = 0; i < count; i++) {
			service.submit(new QueueConsumer(downloadActionQueue));
		}
	}

	public static void submitSingleQueue(QueueItem queue) {
		service.submit(new QueueProducer(downloadActionQueue, queue));
	}

	public static void closeQueueService() {
		service.shutdownNow();
	}
}
