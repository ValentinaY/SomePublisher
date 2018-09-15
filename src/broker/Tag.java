package broker;

public enum Tag {
	CARIBE("CARIBE", new String[]{"COLOMBIA-CARIBE","NICARAGUA-CARIBE"}),
	PACIFIC("PACIFIC", new String[] {"COLOMBIA-PACIFICA","ECUADOR-COSTA"}),
	TSUNAMI("TSUNAMI", new String[] {"COLOMBIA-PACIFICA", "COLOMBIA-CARIBE","NICARAGUA-CARIBE"}),
	HURRICANE("HURRICANE", new String[] {"COLOMBIA-CARIBE","NICARAGUA-CARIBE","BRASIL-NORTE"});
	private String tag;
	private String[] codes;
	
	Tag(String tag, String[] codes){
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String[] getCodes() {
		return codes;
	}

	public void setCodes(String[] codes) {
		this.codes = codes;
	}
}
