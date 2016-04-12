package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hassan and deep
 *
 */
public class User implements Serializable {
	/**
	 * serialVersionUID for serializing
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * list of albums
	 */
	private List<Album> albums;
	/**
	 * name of the user
	 */
	private String name;

	/**constructor for the user
	 * @param name name of the user
	 */
	public User(String name) {
		this.name = name;
		albums = new ArrayList<Album>();
	}

	/**getting the name of the user
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**adding new album
	 * @param album Album
	 */
	public void addAlbum(Album album) {
		albums.add(album);
	}

	/**getting the list of albums
	 * @return list of albums
	 */
	public List<Album> getAlbums() {
		return albums;
	}

	public boolean equals(Object o) {
		if (o == null || !(o instanceof User))
			return false;
		User s = (User) o;
		return (this.getName().equals(s.getName()) ? true : false);
	}

	public String toString() {
		return getName();
	}
}
