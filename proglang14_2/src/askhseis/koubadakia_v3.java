//package askhseis;

/*Java cannot exceed a list maximum size, so for very large testcases 
 * the application crashes. This method fix the problem, because it 
 * does not utilise lists
 * We find the 2 possible routes to a solution. the one that starts with 01 and
 * the other that starts with 02 and we choose the shortest.
 * First we find the length of the paths ( find01() and find02() ). Then we choose the
 * smallest length and we calculate the output. 
 * */
public class koubadakia_v3 {

	static String AC1 = new String("01");
	static String AC2 = new String("02");
	static int V1,V2,Vg,cur_v1, cur_v2, gcd,length01,length02; 
	
	/*start with 01. find the string*/
	public static void solve01(){
		if(cur_v1==Vg || cur_v2==Vg)
			return;
		else{
			if(cur_v1==0){
//				AC1 = AC1 +"-01";
				System.out.print("-01");
				cur_v1=V1;
			}
			else if (cur_v2==V2){
//				AC1 = AC1 + "-20";
				System.out.print("-20");
				cur_v2=0;
			}
			else{
//				AC1=AC1+"-12";
				System.out.print("-12");
				if((cur_v1+cur_v2)<=V2){
					cur_v2 = cur_v1+cur_v2;
					cur_v1=0;
				}
				else{
					cur_v1=cur_v1-(V2-cur_v2);
					cur_v2=V2;
				}
				
			}
			return;
		}
	}
	public static void find01(){
		if(cur_v1==Vg || cur_v2==Vg)
			return;
		else{
			if(cur_v1==0){
				length01++;
				cur_v1=V1;
			}
			else if (cur_v2==V2){
				length01++;
				cur_v2=0;
			}
			else{
				length01++;
				if((cur_v1+cur_v2)<=V2){
					cur_v2 = cur_v1+cur_v2;
					cur_v1=0;
				}
				else{
					cur_v1=cur_v1-(V2-cur_v2);
					cur_v2=V2;
				}
				
			}
			return;
		}
	}
	
	/*start with 02. find the string*/
	public static void solve02(){
		if(cur_v1==Vg || cur_v2==Vg)
			return;
		else{
			if(cur_v2==0){
//				AC2 = AC2 +"-02";
				System.out.print("-02");
				cur_v2=V2;
			}
			else if (cur_v1==V1){
//				AC2 = AC2 + "-10";
				System.out.print("-10");
				cur_v1=0;
			}
			else{
//				AC2=AC2+"-21";
				System.out.print("-21");
				if((cur_v1+cur_v2)<=V1){
					cur_v1 = cur_v2+cur_v1;
					cur_v2=0;
				}
				else{
					cur_v2=cur_v2-(V1-cur_v1);
					cur_v1=V1;
				}
				
			}
			return;
		}
	}
	
	public static void find02(){
		if(cur_v1==Vg || cur_v2==Vg)
			return;
		else{
			if(cur_v2==0){
				length02++;
				cur_v2=V2;
			}
			else if (cur_v1==V1){
				length02++;
				cur_v1=0;
			}
			else{
				length02++;
				if((cur_v1+cur_v2)<=V1){
					cur_v1 = cur_v2+cur_v1;
					cur_v2=0;
				}
				else{
					cur_v2=cur_v2-(V1-cur_v1);
					cur_v1=V1;
				}
				
			}
			return;
		}
	}
	
	public static int GCD(int number1, int number2) {
        //base case
        if(number2 == 0){
            return number1;
        }
        return GCD(number2, number1%number2);
    }
	
	public static void main(String[] args) {

//		final long startTime = System.currentTimeMillis();		
		V1 = Integer.parseInt(args[0]);
		V2 = Integer.parseInt(args[1]);
		Vg = Integer.parseInt(args[2]);
		
		gcd = GCD(V1,V2);
		
		length01=0;
		length02=0;
		
		if(Vg%gcd != 0){
			System.out.println("impossible");
			return;
		}
		if(V1<Vg && V2<Vg){
			System.out.println("impossible");
			return;
		}
		
		V1 = V1/gcd;
		V2 = V2/gcd;
		Vg = Vg/gcd;
		
		cur_v1=V1;
		cur_v2=0;
		while(cur_v1!=Vg && cur_v2!=Vg){
			find01();
		}

		cur_v1=0;
		cur_v2=V2;
		while(cur_v1!=Vg && cur_v2!=Vg){
			find02();
		}
		
		if(length01<length02){
			cur_v1=V1;
			cur_v2=0;
			System.out.print("01");
			while(cur_v1!=Vg && cur_v2!=Vg){
				solve01();
			}
		}
		else{
			cur_v1=0;
			cur_v2=V2;
			System.out.print("02");
			while(cur_v1!=Vg && cur_v2!=Vg){
				solve02();
			}
		}
		
		System.out.println("");

//		final long endTime = System.currentTimeMillis();
//		System.out.println("Total execution time: " + (endTime - startTime) );

	}
}
