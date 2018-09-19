package broker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tags {
	private  Map<String, List<String>> map;

	public Tags() {
		this.map = map= new HashMap<String, List<String>>();
		List<String> ColCar = new ArrayList<String>();
	    ColCar.add("#CARIBE");
	    ColCar.add("#TSUNAMI");
	    ColCar.add("#HURRICANE");
	    // create list two and store values
	    List<String> NicCar = new ArrayList<String>();
	    NicCar.add("#CARIBE");
	    NicCar.add("#TSUNAMI");
	    NicCar.add("#HURRICANE");
	    // create list three and store values
	    List<String> ColPac = new ArrayList<String>();
	    ColPac.add("#PACIFIC");
	    ColPac.add("#TSUNAMI");
	    // list four
	    List<String> EcuCos = new ArrayList<String>();
	    EcuCos.add("#PACIFIC");
	    //list five
	    List<String> BraNor = new ArrayList<String>();
	    BraNor.add("#HURRICANE");

	    // put values into map
	    map.put("COLOMBIA-CARIBE", ColCar);
	    map.put("NICARAGUA-CARIBE", NicCar);
	    map.put("COLOMBIA-PACIFICA", ColPac);
	    map.put("ECUADOR-COSTA", EcuCos);
	    map.put("BRASIL-NORTE", BraNor);
	}

	public Map<String, List<String>> getMap() {
		return map;
	}

	public void setMap(Map<String, List<String>> map) {
		this.map = map;
	}
	
	

    
}
