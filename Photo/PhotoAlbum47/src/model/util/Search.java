package model.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import model.Album;
import model.Hash;
import model.Photo;

/**
 * @author hassan and deep
 *	util class.  it provides all the search capabilities
 */
public class Search {
	/**
	 * list of hashes 
	 */
	public static List<Hash> hash = new ArrayList<>();

	/**
	 * private constructor for search
	 */
	private Search() {
	}

	/** searching for photos
	 * @param album list of albums
	 * @param tags list of tags
	 * @param s category to search
	 * @return list of photos
	 */
	public static List<Photo> searchPeople(List<Album> album, List<String> tags, String s) {
		List<Photo> photo = new ArrayList<>();
		for (int i = 0; i < tags.size(); i++) {
			for (int j = 0; j < album.size(); j++) {
				for (int k = 0; k < album.get(j).getPhotos().size(); k++) {
					String g = tags.get(i);
					Object[] tag;
					if (s.equalsIgnoreCase("people")) {
						tag = album.get(j).getPhotos().get(k).getTags().getTags("people").toArray();
					} else if (s.equalsIgnoreCase("location")) {
						tag = album.get(j).getPhotos().get(k).getTags().getTags("location").toArray();
					} else {
						tag = album.get(j).getPhotos().get(k).getTags().getTags("other").toArray();
					}
					Arrays.sort(tag);
					if (Arrays.binarySearch(tag, g) >= 0 && !hash.contains(album.get(j).getPhotos().get(k).getHash())){
						photo.add(album.get(j).getPhotos().get(k));
						hash.add(album.get(j).getPhotos().get(k).getHash());
					}
				}
			}
		}
		return photo;
	}

	/**getting all selfies
	 * @param album list of albums
	 * @return list of photos
	 */
	public static List<Photo> selfies(List<Album> album) {
		List<Photo> photo = new ArrayList<>();
		for (int j = 0; j < album.size(); j++) {
			for (int k = 0; k < album.get(j).getPhotos().size(); k++) {
				if (album.get(j).getPhotos().get(k).isSelfie() && !hash.contains(album.get(j).getPhotos().get(k).getHash())){
					photo.add(album.get(j).getPhotos().get(k));
					hash.add(album.get(j).getPhotos().get(k).getHash());				}
			}
		}
		return photo;
	}

	/**seraching for date range
	 * @param album list of album
	 * @param beg beginning date
	 * @param end ending date
	 * @return list of photos
	 */
	public static List<Photo> range(List<Album> album, Calendar beg, Calendar end) {
		List<Photo> photo = new ArrayList<>();
		for (int j = 0; j < album.size(); j++) {
			for (int k = 0; k < album.get(j).getPhotos().size(); k++) {
				int cp = album.get(j).getPhotos().get(k).getD().compareTo(beg);
				int c = album.get(j).getPhotos().get(k).getD().compareTo(end);
				if (cp >= 0 && c <= 0 && !hash.contains(album.get(j).getPhotos().get(k).getHash())){
					photo.add(album.get(j).getPhotos().get(k));
					hash.add(album.get(j).getPhotos().get(k).getHash());
				}
			}
		}
		return photo;
	}
	
	/**searching specific selfies
	 * @param album list of albums
	 * @param tags list of tags
	 * @param s category to search
	 * @return list of photos
	 */ 
	public static List<Photo> otherselfie(List<Album> album, List<String> tags, String s) {
		hash.clear();
		List<Photo> photo = selfies(album);
		List<Album> a = new ArrayList<>();
		Album b = new Album("dummy");
		b.setallp(photo);
		a.add(b);
		hash.clear();
		List<Photo> p = searchPeople(a, tags, s);
		return p;
	}
}
