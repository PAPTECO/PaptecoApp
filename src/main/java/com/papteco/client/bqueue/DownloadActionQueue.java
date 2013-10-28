package com.papteco.client.bqueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import com.papteco.web.beans.QueueItem;

public class DownloadActionQueue {

	BlockingQueue<QueueItem> downloadActionQueue = new ArrayBlockingQueue<QueueItem>(
			10);

	public void putQ(QueueItem queueItem) throws InterruptedException {
		downloadActionQueue.put(queueItem);
	}

	public QueueItem getQ() throws InterruptedException {
		return downloadActionQueue.take();
	}

}
