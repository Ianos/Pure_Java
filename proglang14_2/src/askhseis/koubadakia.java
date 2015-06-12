/* references: codesgeek.com/2013/10/watr-jug-problem-source-code/ */
package askhseis;

import java.util.ArrayList;


public class koubadakia {

	static int k,V1,V2,Vg,cur_v1, cur_v2, cur_state;
	static int[] visited;
	static String[] sequence;
	static int[] parent;
	static ArrayList<Integer> queue = new ArrayList<Integer>();
	static ArrayList<String> result = new ArrayList<String>();
	

	/*transform a state of (n,m) pair into a state i of a single integer*/
	public static int nmToI(int n, int m){
		return n*(V2+1)+m;
	}
	/*get the n from state i*/
	public static int iToN(int i){
		return i/(V2+1);
	}
	/*get the m from state i*/
	public static int iToM(int i){
		return i%(V2+1);
	}
	
	static void test(int v1, int v2, int n_v1, int n_v2, String seq){

		/*check if (v1,v2) and (n_v1,n_v2) states are out of bounds*/
		if(n_v1 < 0 || n_v1 > V1 || n_v2 < 0 || n_v2 > V2 ){
			return;
		}

		/*get the id of the new state and check if we have already visited that state*/
		int i1 = nmToI (n_v1, n_v2);
		if (visited[i1]!=0){
			return;
		}
		
		/*update visited array and sequence array*/
		visited[i1] = 1;
		sequence[i1] = seq;
		
		/*find parent of current state and update parent array*/
		int i = nmToI(v1,v2);
		parent[i1] = i;
		queue.add(i1);
		return;
		
	}
	
	 static int solve(){
		
		/*initialize the queue*/
		cur_v1=0;
		cur_v2=0;
		visited[0]=1;
		queue.add(0);
		
		/*untill the queue is empty remove the first element of teh queue and*/
		/*find states linked to the first element and put them in the queue (test)*/
		while(!queue.isEmpty()){
			cur_state = queue.get(0);
			cur_v1 = iToN(cur_state);
			cur_v2 = iToM(cur_state);
			
			queue.remove(0);
			
			if(cur_v1==Vg || cur_v2==Vg /*|| cur_v1+cur_v2==Vg*/)
				return cur_state;
			
			//empty jug
			test(cur_v1,cur_v2,0,cur_v2,"10");
			test(cur_v1,cur_v2,cur_v1,0,"20");
			
			//fill jug
			test(cur_v1,cur_v2,V1,cur_v2,"01");
			test(cur_v1,cur_v2,cur_v1,V2,"02");
			
			//pour one jug until source empty
			test(cur_v1,cur_v2,0,cur_v1+cur_v2,"12");
			test(cur_v1,cur_v2,cur_v1+cur_v2,0,"21");
			
			//pour jug untill other is full
			test(cur_v1,cur_v2,cur_v1-V2+cur_v2,V2,"12");
			test(cur_v1,cur_v2,V1,cur_v2-V1+cur_v1,"21");
			
		}
		System.out.println("impossible");
		return 0;
	}
	
	/*print the resulting sequence*/ 
	static void print_res(int final_state){
		while(final_state!=0){
			result.add(0,sequence[final_state]);
			final_state = parent[final_state];
		}
		//result.add(0, sequence[final_state]);
		
		while(!result.isEmpty()){
			System.out.print(result.get(0));
			result.remove(0);
			if(!result.isEmpty()){
				System.out.print("-");
			}
		}
	}
	
	public static void main(String[] args) {

		int s;
		
		V1 = Integer.parseInt(args[0]);
		V2 = Integer.parseInt(args[1]);
		Vg = Integer.parseInt(args[2]);

		
		if(V1<Vg && V2<Vg){
			System.out.println("impossible");
			return;
		}
		
		visited = new int[(V1+1)*(V2+1)];
		sequence = new String[(V1+1)*(V2+1)];
		parent = new int[(V1+1)*(V2+1)];
		
		s = solve();
		print_res(s);
	}

}
