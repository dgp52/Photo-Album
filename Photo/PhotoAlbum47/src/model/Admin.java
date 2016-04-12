package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author hassan and deep
 *
 */
public class Admin implements Serializable {

	/**
	 * directory to store files
	 */
	public static final String storeDir = "data";
	
	 
	/**
	 * serialVersionUID for serializing
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * list of users
	 */
	private List<User> users = new ArrayList<>();
	/**
	 * name of amdin
	 */
	private String name;
	
	/**
	 * instance of amdin
	 */
	private static Admin admin = null;
	
	/**constructor for admin
	 * @param name Sting
	 */
	private  Admin(String name) {
		this.name = name; 
	}
	/**adding a new user
	 * @param user User
	 */
	public void addUser(User user) {
		users.add(user);
		users.sort(new Comparator<User>() {

			@Override
			public int compare(User o1, User o2) {
				return o1.getName().compareToIgnoreCase(o2.getName());
			}
		});
	}
	
	/**getting the name
	 * @return name
	 */
	public String getName() {
		return name;
	}
	
	/**getting the users
	 * @return list of user
	 */
	public List<User> getUsers() {
		return users;
	}
	/**to make we only have 1 instance of admin
	 * @param s String
	 * @return admin
	 */
	public  static Admin getInstance(String s) {
		if(admin == null)
			admin = new Admin(s);
		return admin;
	}
	
	/**serialize the admin and users
	 * @param a Admin
	 * @throws IOException input/output exception
	 */
	public void make (Admin a) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storeDir + File.separator + "admin"));
		oos.writeObject(a);
		for(int i =0; i < users.size(); i++){
			ObjectOutputStream oo = new ObjectOutputStream(new FileOutputStream(storeDir + File.separator + users.get(i).getName()));
			oo.writeObject(users.get(i));
			oo.close();
		}
		oos.close();
	}
	
	/**de-serialize the admin
	 * @return admin
	 * @throws IOException input/output exception
	 * @throws ClassNotFoundException class not found exception
	 */
	public static Admin remake()throws IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream("data/admin"));
		 admin = (Admin)ois.readObject();
		 ois.close();
		 return admin; 
	}
}