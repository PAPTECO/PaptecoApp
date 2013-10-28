package com.papteco.client.bqueue;

import java.util.concurrent.Callable;

import com.papteco.web.beans.QueueItem;

public class QueueProducer implements Callable<Boolean> {

	final DownloadActionQueue downloadActionQueue;
	private QueueItem queueItem;

	public QueueProducer(DownloadActionQueue downloadActionQueue,
			QueueItem queueItem) {
		this.downloadActionQueue = downloadActionQueue;
		this.queueItem = queueItem;
	}

	public void run() {
		try {
			downloadActionQueue.putQ(queueItem);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Boolean call() throws Exception {
		// TODO Auto-generated method stub
		try {
			downloadActionQueue.putQ(queueItem);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
}
