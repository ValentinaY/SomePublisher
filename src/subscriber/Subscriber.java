/**
 * 
 */
package subscriber;

import javax.swing.JFrame;

/**
 * @author valya
 *
 */
public class Subscriber {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame window = new GUISubscriber();
		while(window.isActive());
		GUISubscriber suscriber = (GUISubscriber) window;
		System.out.println(suscriber.getcollecteddata());
		
	}

}
