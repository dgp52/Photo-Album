package model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;

import javafx.scene.control.Alert;
import model.util.Dialog;

/**
 * @author hassan and deep
 *
 */
public class Album implements Serializable {

	/**
	 * serialVersionUID for serializing
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * name of the album
	 */
	private String name;
	/**
	 * list of the photos
	 */
	private List<Photo> photos;
	/**
	 * list of hashes for the photos
	 */
	private List<Hash> hash;
	
	/**
	 * list of the dates for photos
	 */
	private List<Calendar> dates;
	
	/**constructor fo album
	 * @param name String
	 * @param photos list of photos
	 */
	public Album(String name, List<Photo> photos) {
		this.name = name;
		this.photos = photos;
	}

	/**constructor for album
	 * @param name Sring
	 */
	public Album(String name) {
		this.name = name;
		photos = new ArrayList<Photo>();
		hash = new ArrayList<Hash>();
		dates = new ArrayList<Calendar>();
	}

	/**add all the photos to album
	 * @param p list of photo
	 */
	public void setallp(List<Photo> p) {
		photos.addAll(p);
		for(Photo s : p) {
			dates.add(s.getD());
		}
		sdate();
	}
	
	/**add all the hashes of the photo to album
	 * @param p list of photo
	 */
	public void setallh(List<Photo> p) {
		for(Photo s: p) {
			hash.add(s.getHash());
		}

	}
	/**settin the name for album
	 * @param name String
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**adding a new photo
	 * @param p Photo
	 */
	public void addPhotos(Photo p) {
		if (!hash.contains(p.getHash())) {
			photos.add(p);
			hash.add(p.getHash());
			dates.add(p.getD());
			sdate();
		} else {
			Dialog.pop(Alert.AlertType.ERROR, "info", "picture already exists in album!");
		}
	}
	
	/**
	 * sorting the dates for photos
	 */
	public void sdate() {
		dates.sort(new Comparator<Calendar>() {

			@Override
			public int compare(Calendar o1, Calendar o2) {
				return o1.compareTo(o2);
			}
		});
	}

	/**getting the name of the album
	 * @return name of album
	 */
	public String getName() {
		return name;
	}

	/**getting oldest date
	 * @return String oldest date
	 */
	public String getOldest() {
		if(dates.size() > 0)
			return new SimpleDateFormat("MM/dd/yyyy").format(dates.get(0).getTime());
		return "";
	}

	/**getting the range
	 * @return String range of dates
	 */
	public String getRange() {
		if(dates.size() > 0)
			return getOldest() + " - " + new SimpleDateFormat("MM/dd/yyy").format(dates.get(dates.size()-1).getTime());
		return "";
		
	}

	/**getting the number of photos
	 * @return int number of photos
	 */
	public int numPhotos() {
		return photos.size();
	}

	/**getting the list of photos
	 * @return list of photo
	 */
	public List<Photo> getPhotos() {
		return photos;
	}

	/**getting the list of hashes
	 * @return list of hashes
	 */
	public List<Hash> getHashes() {
		return hash;
	}

	/**getting the list of dates
	 * @return list of dates
	 */
	public List<Calendar> getdates() {
		return dates;
	}
	
	public String toString() {
		return getName();
	}

	public boolean equals(Object o) {
		if (o == null || !(o instanceof Album))
			return false;
		Album album = (Album) o;
		return getName().equals(album.getName());
	}
}
