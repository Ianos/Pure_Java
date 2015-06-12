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

public class bats {

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
	
	static int check(double bat_x1, double bat_y1, double bat_x2, double bat_y2, double wall_x, double wall_y ){
		double wall_TRx, wall_TRy, wall_BRx, wall_BRy, wall_TLx, wall_TLy, wall_BLx,wall_BLy;
		double a, res_x, res_y;
		
		wall_TRx = wall_x + 0.5;
		wall_TRy = wall_y + 0.5;
		
		wall_TLx = wall_x - 0.5;
		wall_TLy = wall_y + 0.5;
		
		wall_BRx = wall_x + 0.5;
		wall_BRy = wall_y - 0.5;
		
		wall_BLx = wall_x - 0.5;
		wall_BLy = wall_y - 0.5;
//	System.out.println("bat1: "+bat_x1+" "+bat_y1);	
//	System.out.println("bat2: "+bat_x2+" "+bat_y2);
//	System.out.println("wall: "+wall_x+" "+wall_y);
	if(bat_y2!=bat_y1 && bat_x2!=bat_x1){
		a = (bat_y2-bat_y1)/(bat_x2-bat_x1);
//	System.out.println("a: "+a);
		
		res_y = a*(wall_TRx-bat_x1) + bat_y1;
//		System.out.println("res_y: "+res_y+" wall_BRy: "+wall_BRy+" wall_TRy"+wall_TRy);
		if(res_y>=wall_BRy && res_y<=wall_TRy)
			return -1;
		
		res_y = a*(wall_TLx-bat_x1) + bat_y1;
//		System.out.println("res_y: "+res_y+" wall_BLy: "+wall_BLy+" wall_TLy"+wall_TLy);
		if(res_y>=wall_BLy && res_y<=wall_TLy)
			return -1;
		
		res_x = ((wall_TRy-bat_y1)/a) + bat_x1;
//		System.out.println("res_x: "+res_x+" wall_TRx: "+wall_TRx+" wall_TLx"+wall_TLx);
		if(res_x<=wall_TRx && res_x>=wall_TLx)
			return -1;
		
		res_x = ((wall_BRy-bat_y1)/a) + bat_x1;
//		System.out.println("res_x: "+res_x+" wall_BRx: "+wall_BRx+" wall_BLx"+wall_BLx);
		if(res_x<=wall_BRx && res_x>=wall_BLx)
			return -1;
	}
	else {
//		System.out.println("Linear wall!");
		return -1;
	}
//		System.out.println("pass!");
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
		// TODO Auto-generated method stub
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
		
//	System.out.println(""+x_axis+" "+y_axis+" "+lines);
		
		room = new int[y_axis][x_axis];
		key = 0;
		for(i=0;i<lines;i++){
			B="B";
			A="A";
			text = input.readLine();
//	System.out.println(text);
			token = new StringTokenizer(text," ");
			pos_x = Integer.parseInt(token.nextToken());
			pos_y = Integer.parseInt(token.nextToken());
			obj = token.nextToken();
//	System.out.println("obj: "+obj+" x: "+pos_x+" y: "+pos_y);
			if(obj.equals(B)){
//	System.out.println("got in B");
				room[pos_x][pos_y] = 1;
				key = xyToI(pos_x,pos_y);
//	System.out.println("key: "+key);
				double distance = Double.MAX_VALUE;
				
				cache.put(key, distance);
//				key++;
			}
			if(obj.equals(A)){
//	System.out.println("got in A");
				room[pos_x][pos_y] = 2;
				key = xyToI(pos_x,pos_y);
//	System.out.println("key: "+key);
				double distance = Double.MAX_VALUE;
				
				cache.put(key, distance);
//				key++;
				
			}
			if(obj.equals("-")){
				room[pos_x][pos_y] = -1;
				key = xyToI(pos_x,pos_y);
				walls.put(key, 1.0);
			}
		}
		
		cache.remove(0);
		result.put(0,0.0);
		
		pos_x = 0;
		pos_y = 0;
		
//	System.out.println("cache: " + cache);
		
		for(Map.Entry<Integer, Double> entry : cache.entrySet()){
//	System.out.println("went in for");
			check_result = 0;
			second_key = entry.getKey();
			second_distance = entry.getValue();
			second_x = iTox(second_key);
			second_y = iToy(second_key);
			old_distance = (second_x-pos_x)*(second_x-pos_x)*1.0+(second_y-pos_y)*(second_y-pos_y)*1.0;
			old_distance = Math.sqrt(old_distance);
//			System.out.println("bat1: "+ pos_x +" "+pos_y +" | bat2: "+second_x +" "+second_y);
//			System.out.println("old_distance: "+old_distance+" second_distance: "+second_distance);
			
			if(old_distance<second_distance){
				for(i=pos_x; i<=second_x; i++){
					for(j=pos_y; j<=second_y; j++){
//		System.out.println("wall: "+i+" "+j);
						if(room[i][j]==-1){
//					System.out.println("check case 1");
							check_result = check(pos_x,pos_y,second_x,second_y,i,j);
							if(check_result==-1)
								break;
						}
					}
					if(check_result==-1)
						break;
				}
			}
			if(check_result!=-1){
				cache.put(second_key, old_distance);
			}
		}
		
		while(!cache.isEmpty()&& found_spider==false){
			
//			System.out.println("cache: " + cache);
//			System.out.println("result: " + result);
			key = findminkey(cache);
			pos_x = iTox(key);
			pos_y = iToy(key);
//			System.out.println("min key: "+key+" x: "+pos_x+" y: "+pos_y);
			if(room[pos_x][pos_y]==2){
//				System.out.println("found!");
				found_spider=true;
			}
			
			current_distance = cache.get(key);
			result.put(key, current_distance);
			cache.remove(key);
			
			for(Map.Entry<Integer, Double> entry : cache.entrySet()){
//				System.out.println("went in for");
						check_result=0;
						second_key = entry.getKey();
						old_distance = entry.getValue();
						second_x = iTox(second_key);
						second_y = iToy(second_key);
						new_distance = (second_x-pos_x)*(second_x-pos_x)*1.0+(second_y-pos_y)*(second_y-pos_y)*1.0;
						new_distance = Math.sqrt(new_distance);
//			System.out.println("bat1: "+ pos_x +" "+pos_y +" | bat2: "+second_x +" "+second_y);
//			System.out.println("old_distance: "+old_distance+" new_distance: "+new_distance+" current_distance: "+current_distance);
			if(new_distance+current_distance<old_distance){
							if(pos_x>second_x){
//				System.out.println("pos_x>second_x");				
								for(i=second_x; i<=pos_x; i++){
									if(pos_y>second_y){
//							System.out.println("pos_y>second_y");
										for(j=second_y; j<=pos_y; j++){
											if(room[i][j]==-1){
												check_result = check(pos_x,pos_y,second_x,second_y,i,j);
												if(check_result==-1){
													break;
												}
											}
										}
									}
									else if(pos_y<second_y){
	//						System.out.println("pos_y<second_y");
										for(j=pos_y;j<=second_y;j++){
											if(room[i][j]==-1){
												check_result = check(pos_x,pos_y,second_x,second_y,i,j);
												if(check_result==-1){
													break;
												}
											}
										}
									}
									else if(pos_y==second_y){
	//							System.out.println("pos_y=second_y");
										if(room[i][pos_y]==-1){
											check_result = check(pos_x,pos_y,second_x,second_y,i,pos_y);
//											if(check_result==-1){
//												break;
//											}
										}
									}
									if(check_result==-1)
										break;
								}
							}
							else if(pos_x<second_x){
	//							System.out.println("pos_x<second_x");
								for(i=pos_x; i<=second_x; i++){
									if(pos_y>second_y){
	//					System.out.println("pos_y>second_y");
										for(j=second_y; j<=pos_y; j++){
											if(room[i][j]==-1){
												check_result = check(pos_x,pos_y,second_x,second_y,i,j);
												if(check_result==-1){
													break;
												}
											}
										}
									}
									else if(pos_y<second_y){
		//								System.out.println("pos_y<second_y");
										for(j=pos_y;j<=second_y;j++){
		//						System.out.println(""+i+" "+j);
											if(room[i][j]==-1){
												check_result = check(pos_x,pos_y,second_x,second_y,i,j);
												if(check_result==-1){
													break;
												}
											}
										}
									}
									else if(pos_y==second_y){
		//								System.out.println("pos_y=second_y");										
										if(room[i][pos_y]==-1){
											check_result = check(pos_x,pos_y,second_x,second_y,i,pos_y);
//											if(check_result==-1){
//												break;
//											}
										}
									}
									if(check_result==-1)
										break;
								}
							}
							else if(pos_x==second_x){
		//						System.out.println("pos_x=second_x");
								if(pos_y>second_y){
		//							System.out.println("pos_y>second_y");
									for(j=second_y; j<=pos_y; j++){
										if(room[pos_x][j]==-1){
											check_result = check(pos_x,pos_y,second_x,second_y,pos_x,j);
											if(check_result==-1){
												break;
											}
										}
									}
								}
								else if(pos_y<second_y){
		//							System.out.println("pos_y<second_y");
									for(j=pos_y;j<=second_y;j++){
										if(room[pos_x][j]==-1){
											check_result = check(pos_x,pos_y,second_x,second_y,pos_x,j);
											if(check_result==-1){
												break;
											}
										}
									}
								}
								else if(pos_y==second_y){
		//							System.out.println("pos_y=second_y");
									if(room[pos_x][pos_y]==-1){
										check_result = check(pos_x,pos_y,second_x,second_y,pos_x,pos_y);
//										if(check_result==-1){
//											break;
//										}
									}
								}
//								if(check_result==-1)
//									break;

							}
							if(check_result!=-1){
								double final_distance = current_distance+new_distance;
	//							System.out.println("second_key: "+ second_key+" final_distance: "+final_distance);
								cache.put(second_key, final_distance);
							}
	//						System.out.println("");
						}
			}
//			System.out.println("finished with a bat");
//			System.out.println("");
		}
		
/*		for(Map.Entry<Integer, Double> entry: result.entrySet()){
			System.out.println(""+entry.getValue());
		}
*/		System.out.println(String.format("%.2f",result.get(key)));
	}
}