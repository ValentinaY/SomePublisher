/**
 * 
 */
package broker;

import java.util.ArrayList;

/**
 * @author valya
 *
 */
public class New {

	private ArrayList<String> tags;
	private ArrayList<String> content;
	private int validated=0;
	/**
	 * 
	 */
	public New() {
		// TODO Auto-generated constructor stub
	}
	public ArrayList<String> getTags() {
		return tags;
	}
	public void setTags(ArrayList<String> tags) {
		this.tags = tags;
	}
	public ArrayList<String> getContent() {
		return content;
	}
	public void setContent(ArrayList<String> content) {
		this.content = content;
	}
	public int getValidated() {
		return validated;
	}
	public void setValidated(int validated) {
		this.validated = validated;
	}
	public void validatedpp() {
		this.validated=this.validated+1;
	}
	public void addContent(String data) {
		this.content.add(data);
	}
	public void addTag(String tag) {
		this.tags.add(tag);
	}
}
