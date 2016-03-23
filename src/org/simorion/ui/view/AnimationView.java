package org.simorion.ui.view;


public class AnimationView extends DefaultView {

	static int tick = 0;
	static {
		new Thread(new Runnable(){
			public void run() {
				while(true) { tick++; tick %= 16; try {
					Thread.sleep(100);
					GUI.getInstance().update();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} }
			}
		}).start();
	}
	/**
	 * Retrieves and returns whether or not the button at coordinate {@code x}, {@code y} is lit.
	 * @param x The x coordinate of the button.
	 * @param y The y coordinate of the button.
	 * @return True if lit, false otherwise.
	 */
	@Override
	public boolean isLit(int x, int y) {
		boolean outside = x < tick || x > (15-tick) || y < tick || y > (15-tick);
		if(tick < 8) return outside;
		int tick1 = tick - 8;
		return !(x < tick1 || x > (15-tick1) || y < tick1 || y > (15-tick1));
	}
	
}
