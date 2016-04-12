package model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author hassan and deep
 *
 */

public class Photo implements Serializable {

	/**
	 * serialVersionUID for serializing
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * caption of the photo
	 */
	private String caption;
	/**
	 * SerialIimage
	 */
	private SerialImage img;
	/**
	 * date of the photo
	 */
	private Calendar date = Calendar.getInstance();
	/**
	 * tags of the photo
	 */
	private Tag tags = new Tag();
	/**
	 * hash of the photo
	 */
	private Hash hash;
	/**
	 * selfie flag
	 */
	private boolean selfie = false;

	/**constructor for Photo
	 * @param img SerialImage
	 */
	public Photo(SerialImage img) {
		this.img = img;
		hash = new Hash(img);
		date.set(Calendar.MILLISECOND, 0);
	}
	/**setting the tags for people
	 * @param tags list of tags
	 */
	public void setPeople(List<String> tags) {
		this.tags.setPeople(tags);
	}
	
	/**getting the people tag
	 * @return list of tags for people
	 */
	public List<String> getPeople(){
		return tags.getPeople();
	}
	
	/**getting the location tags
	 * @return list of location tags
	 */
	public List<String> getLocation(){
		return tags.getLocation();
	}	

	/**getting other tags
	 * @return list of ohter tags
	 */
	public List<String> getOther(){
		return tags.getOther();
	}
	
	/**set the location tags
	 * @param lo list of location tags
	 */
	public void setLocation(List<String> lo) {
		this.tags.setLocation(lo);
	}


	/**set the caption
	 * @param caption caption
	 */
	public void setCaption(String caption) {
		this.caption = caption;
	}

	/**getting the date
	 * @return date
	 */
	public String getDate() {
		SimpleDateFormat n = new SimpleDateFormat("MM/dd/yyyy");
		return  n.format(date.getTime());
	}

	/**getting calendar date
	 * @return Calendar
	 */
	public Calendar getD() {
		return date;
	}
	
	/**getting the time
	 * @return time
	 */
	public String getTime() {
		SimpleDateFormat n = new SimpleDateFormat("hh:mm:ss a");
		return "Time taken: " + n.format(date.getTime());
	}
	
	/**getting the caption
	 * @return caption
	 */
	public String getCaption() {
		return caption;
	}

	/**getting the SerialImage
	 * @return SerialImage
	 */
	public SerialImage getSerial() {
		return img;
	}

	/**getting the hash of the photo
	 * @return hash
	 */
	public Hash getHash() {
		return hash;
	}

	/**getting all the tags
	 * @return tags
	 */
	public Tag getTags() {
		return tags;
	}

	/**setting the selfie flag
	 * @param b boolean
	 */
	public void setSelfit(boolean b) {
		selfie = b;
	}

	/**whether photo is selfie
	 * @return boolean
	 */
	public boolean isSelfie() {
		return selfie;
	}
	
	/**setting the date
	 * @param d Date
	 */
	public void setDate(Date d) {
		date.setTime(d);
	}
}