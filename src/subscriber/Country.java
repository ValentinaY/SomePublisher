/**
 * 
 */
package subscriber;

import java.util.Arrays;

/**
 * @author valya
 *
 */
public enum Country {
	BRASIL("Brasil", new String[]{"Norte","Noreste","Oeste","Sureste","Sur"} ),
	COLOMBIA("Colombia", new String[] {"Amazonas","Andina","Caribe", "Orinoquia", "Pacifica"}),
	ECUADOR("Oriente", new String[] {"Costa","Oriente","Sierra"}),
	NICARAGUA("Nicaragua", new String[] {"Caribe","Central","Pacifica"});
	
	private String[] regions;
	private String name;
	
	Country(String name, String[] regions){
		this.name = name;
		this.regions = regions;
	}
	
	String getName() {
		return name;
	}
	
	String[] getRegions() {
		return regions;
	}
	
	public static String[] names() {
        String valuesStr = Arrays.toString(Country.values());
        return valuesStr.substring(1, valuesStr.length()-1).replace(" ", "").split(",");
    }
}
