package com.papteco.client.bqueue;

import com.papteco.client.netty.ObjectEchoBuilder;
import com.papteco.web.beans.QueueItem;

public class QueueConsumer implements Runnable {

	final DownloadActionQueue downloadActionQueue;

	public QueueConsumer(DownloadActionQueue downloadActionQueue) {
		this.downloadActionQueue = downloadActionQueue;
	}

	public void run() {
		try {
			while (true) {
				QueueItem qItem = downloadActionQueue.getQ();
				if (qItem != null) {
					new ObjectEchoBuilder(qItem).runDownFileEcho();
				}
				Thread.sleep(500);
			}
		} catch (InterruptedException ex) {
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
