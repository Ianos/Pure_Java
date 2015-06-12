package askhseis;

import java.util.ArrayList;

public class arraylist_exmpl {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		 // create an array list
	      ArrayList al = new ArrayList();
	      Object thischar;
	      System.out.println("Initial size of al: " + al.size());

	      // add elements to the array list
	      al.add("C");
	      al.add("A");
	      al.add("E");
	      al.add("B");
	      al.add("D");
	      al.add("F");
	     // al.add(1, "A2");
	      System.out.println("Size of al after additions: " + al.size());

	      // display the array list
	      System.out.println("Contents of al: " + al);
	      // Remove elements from the array list
	      thischar = al.get(0);
	      System.out.println("first elem: "+ thischar);
	      al.remove(0);
	      System.out.println("first elem: "+ al.get(0));
	      al.remove(0);
	      System.out.println("Size of al after deletions: " + al.size());
	      System.out.println("Contents of al: " + al);
	}

}
