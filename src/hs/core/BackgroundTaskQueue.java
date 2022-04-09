package hs.core;

public class BackgroundTaskQueue extends Thread {
	
	@Override
	public void run() {
		while(true) {
			
			
			
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}
