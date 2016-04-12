package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hassan and deep
 *
 */
public class Tag implements Serializable {

	/**
	 * serialVersionUID for serializing
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * list of people tags
	 */
	private List<String> people;
	/**
	 * list of location tags
	 */
	private List<String> location;
	/**
	 * list of other tags
	 */
	private List<String> other;

	/**
	 * constructor for Tag
	 */
	public Tag() {
		people = new ArrayList<String>();
		location = new ArrayList<String>();
		other = new ArrayList<String>();
	}

	/**setting the people tags
	 * @param people list of people tags
	 */
	public void setPeople(List<String> people) {
		this.people = people;
	}
	
	/**getting people tags
	 * @return list of people tags
	 */
	public List<String> getPeople(){
		return people;
	}
	
	/**getting location tags
	 * @return list of location tags
	 */
	public List<String> getLocation() {
		return location;
	}
	
	/**getting other tags
	 * @return list of other tags
	 */
	public List<String> getOther() {
		return other;
	}

	/**setting location tags
	 * @param location list of location tags
	 */
	public void setLocation(List<String> location) {
		this.location = location;
	}

	/**setting the other tags
	 * @param other list of other tags
	 */
	public void setOhter(List<String> other) {
		this.other = other;
	}

	/** getting all the tags
	 * @param s String
	 * @return list of all tags
	 */
	public List<String> getTags(String s) {
		List<String> t = new ArrayList<>();
		if (s.equalsIgnoreCase("people")) {
			
			for(String j : people)
				t.add(j.toLowerCase());
			return t;
		} else if (s.equalsIgnoreCase("location")) {
			for(String j : location)
				t.add(j.toLowerCase());
			return t;
		} else {
			for(String j : other)
				t.add(j.toLowerCase());
			return t;
		}
	}
}
