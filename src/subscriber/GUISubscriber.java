/**
 * 
 */
package subscriber;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * @author valya
 *
 */
public class GUISubscriber extends JFrame{

	/**
	 * 
	 */
//	No sé qué hace esto, pero, si lo quitan aparece una advertencia
	private static final long serialVersionUID = 8822183302464536392L;
	/**
	 * 
	 */
	JComboBox<String> comboregion;
	JComboBox<String> combocountry;
	
	private String[] collecteddata = new String[2];
	
	public GUISubscriber() {
		setSize(700, 600);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// TODO Auto-generated constructor stub
		getContentPane().setLayout(null);
		JLabel country = new JLabel("Select your country");
		JLabel region = new JLabel("Select your region");
		JButton button = new JButton("Send");
		country.setBounds(30, 30, 200, 30);
		region.setBounds(30, 100, 200, 30);
		button.setBounds(200, 180, 100, 40);
		combocountry = new JComboBox<>(Country.names());
		combocountry.setBounds(190,30,300,30);
		comboregion = new JComboBox<>();
		comboregion.setBounds(190,100,300,30);
		
		getContentPane().add(country);
		getContentPane().add(region);
		getContentPane().add(button);
		getContentPane().repaint();
		getContentPane().add(combocountry);
		getContentPane().add(comboregion);
		combocountry.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
//				Se muestra la lista de regiones según la enumeración Country
				String[] regions = (Country.valueOf((String) combocountry.getSelectedItem())).getRegions();
//				Se actualiza el combobox de regiones cuando se selecciona un país
				comboregion.removeAllItems();
				for (String string : regions) {
					comboregion.addItem(string.toUpperCase());
				}
			}
		});
//		Al presionar el botón, la ventana se cierra y la clase subscriber puede realizar el envío de los datos que el método guarda
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
//				Se almacenan los datos en un atributo para que la clase Subscriber tenga acceso a ellos
				collecteddata[1] = (String) comboregion.getSelectedItem();
				collecteddata[0] = (String) combocountry.getSelectedItem();
				dispose();
			}
		});
	}
	public String[] getcollecteddata() {
		return collecteddata;
	}

}