package askhseis;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;

public class bats_v2 {

	static int[][] room;	
	
	static class animal {
		double x;
		double y;
		int type;
//		double distance;
	}
	
	static Map<Integer,Double> cache = new HashMap<Integer,Double>();
	static Map<Integer,Double> walls = new HashMap<Integer,Double>();
	static Map<Integer,Double> result = new HashMap<Integer,Double>();
	static List<Map.Entry<Integer,Double>> entries;
	
	/*check if we have collision with the wall*/
	static int check(double bat_x1, double bat_y1, double bat_x2, double bat_y2, double wall_x, double wall_y ){
		double wall_TRx, wall_TRy, wall_BRx, wall_BRy, wall_TLx, wall_TLy, wall_BLx,wall_BLy;
		double a, res_x, res_y;
		
		/*find edje coordinates of the wall block*/
		wall_TRx = wall_x + 0.5;
		wall_TRy = wall_y + 0.5;
		
		wall_TLx = wall_x - 0.5;
		wall_TLy = wall_y + 0.5;
		
		wall_BRx = wall_x + 0.5;
		wall_BRy = wall_y - 0.5;
		
		wall_BLx = wall_x - 0.5;
		wall_BLy = wall_y - 0.5;
	
	/*if bats are not in the same line or column do the below*/	
	if(bat_y2!=bat_y1 && bat_x2!=bat_x1){
		
		/*find a in y-bat_y1 = a*(x-bat_x1)*/
		a = (bat_y2-bat_y1)/(bat_x2-bat_x1);

		/*check if line pass through the wall*/
		res_y = a*(wall_TRx-bat_x1) + bat_y1;
		if(res_y>=wall_BRy && res_y<=wall_TRy)
			return -1;
		
		res_y = a*(wall_TLx-bat_x1) + bat_y1;
		if(res_y>=wall_BLy && res_y<=wall_TLy)
			return -1;
		
		res_x = ((wall_TRy-bat_y1)/a) + bat_x1;
		if(res_x<=wall_TRx && res_x>=wall_TLx)
			return -1;
		
		res_x = ((wall_BRy-bat_y1)/a) + bat_x1;
		if(res_x<=wall_BRx && res_x>=wall_BLx)
			return -1;
	}
	/*if the bats are in the same line there is always the wall between them*/
	else {
		return -1;
	}
	/*if no wall intersects with the line return o (success)*/
		return 0;
		
	}
	
	public static int xyToI(int x, int y){
		return x*1001+y;
	}
	public static int iTox(int i){
		return i/1001;
	}
	
	public static int iToy(int i){
		return i%1001;
	}
	
	/*find minimum key of the hashmap*/
	public static int findminkey(Map<Integer, Double> cache2){
		Double min = Collections.min(cache2.values());
		int minkey=0;
		for(Map.Entry<Integer, Double> entry : cache2.entrySet()){
			if(min.equals(entry.getValue())){
				minkey = entry.getKey();
				break;
			}
		}
		return minkey;
	}
	
	public static void main(String[] args) throws IOException {
		
		int x_axis, y_axis, lines;
		int pos_x, pos_y;
		int i,j,key, check_result;
		int second_key, second_x, second_y;
		double second_distance,new_distance, old_distance,current_distance;
		boolean found_spider=false;
		String B = new String();
		String A = new String();
		String obj = new String();
		BufferedReader input = new BufferedReader(new FileReader(args[0]));
		
		String text = input.readLine();
		StringTokenizer token = new StringTokenizer(text," ");
		x_axis = Integer.parseInt(token.nextToken());
		y_axis = Integer.parseInt(token.nextToken());
		lines = Integer.parseInt(token.nextToken());
		
		/*initialize data*/
		room = new int[x_axis][y_axis];
		key = 0;
		for(i=0;i<lines;i++){
			B="B";
			A="A";
			text = input.readLine();
			token = new StringTokenizer(text," ");
			pos_x = Integer.parseInt(token.nextToken());
			pos_y = Integer.parseInt(token.nextToken());
			obj = token.nextToken();
			if(obj.equals(B)){
				room[pos_x][pos_y] = 1;
				key = xyToI(pos_x,pos_y);
				double distance = Double.MAX_VALUE;
				
				cache.put(key, distance);
			}
			if(obj.equals(A)){
				room[pos_x][pos_y] = 2;
				key = xyToI(pos_x,pos_y);
				double distance = Double.MAX_VALUE;
				cache.put(key, distance);
			}
			if(obj.equals("-")){
				room[pos_x][pos_y] = -1;
				key = xyToI(pos_x,pos_y);
				walls.put(key, 1.0);
			}
		}
		

		/*find and update routes from first bat*/
		cache.remove(0);
		result.put(0,0.0);
		
		pos_x = 0;
		pos_y = 0;
		
		for(Map.Entry<Integer, Double> entry : cache.entrySet()){
			check_result = 0;
			second_key = entry.getKey();
			second_distance = entry.getValue();
			second_x = iTox(second_key);
			second_y = iToy(second_key);
			old_distance = (second_x-pos_x)*(second_x-pos_x)*1.0+(second_y-pos_y)*(second_y-pos_y)*1.0;
			old_distance = Math.sqrt(old_distance);
			
			if(old_distance<second_distance){
				for(Map.Entry<Integer,Double> wall_entry: walls.entrySet()){
					Integer wall_key = wall_entry.getKey();
					int wall_x = iTox(wall_key);
					int wall_y = iToy(wall_key);
					if((wall_x>=pos_x&&wall_x<=second_x)||(wall_x<=pos_x&&wall_x>=second_x)){
						if((wall_y>=pos_y&&wall_y<=second_y)||(wall_y<=pos_y&&wall_y>=second_y)){
							check_result = check(pos_x,pos_y,second_x,second_y,wall_x,wall_y);
							if(check_result==-1)
								break;
						}
					}
				}
			}
			if(check_result!=-1){
				cache.put(second_key, old_distance);
			}
		}

		/*find and update routes from the other nodes. stop when you find spider*/
		while(!cache.isEmpty()&& found_spider==false){
			key = findminkey(cache);
			pos_x = iTox(key);
			pos_y = iToy(key);
			current_distance = cache.get(key);
			result.put(key, current_distance);
			cache.remove(key);
			
			/*if you find the spider break from the while */
			if(room[pos_x][pos_y]==2){
				found_spider=true;
				break;
			}
			
			for(Map.Entry<Integer, Double> entry : cache.entrySet()){
						check_result=0;
						second_key = entry.getKey();
						old_distance = entry.getValue();
						second_x = iTox(second_key);
						second_y = iToy(second_key);
						new_distance = (second_x-pos_x)*(second_x-pos_x)*1.0+(second_y-pos_y)*(second_y-pos_y)*1.0;
						new_distance = Math.sqrt(new_distance);
						if(new_distance+current_distance<old_distance){
							for(Map.Entry<Integer,Double> wall_entry: walls.entrySet()){
								Integer wall_key = wall_entry.getKey();
								int wall_x = iTox(wall_key);
								int wall_y = iToy(wall_key);
								if((wall_x>=pos_x&&wall_x<=second_x)||(wall_x<=pos_x&&wall_x>=second_x)){
									if((wall_y>=pos_y&&wall_y<=second_y)||(wall_y<=pos_y&&wall_y>=second_y)){
										check_result = check(pos_x,pos_y,second_x,second_y,wall_x,wall_y);
										if(check_result==-1)
											break;
									}
								}
							}
							if(check_result!=-1){
								double final_distance = current_distance+new_distance;
								cache.put(second_key, final_distance);
							}
								
						}
			}
		}
		System.out.println(String.format("%.2f",result.get(key)));

	}
}