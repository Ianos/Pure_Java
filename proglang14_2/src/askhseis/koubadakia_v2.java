package askhseis;

/*Java cannot exceed a list maximum size, so for very large testcases 
 * the application crashes. This method fix the problem, because it 
 * does not utilise lists*/
public class koubadakia_v2 {

	static String AC1 = new String("01");
	static String AC2 = new String("02");
	static int V1,V2,Vg,cur_v1, cur_v2, gcd; 
	
	/*start with 01. find the string*/
	public static void solve01(){
		if(cur_v1==Vg || cur_v2==Vg)
			return;
		else{
			if(cur_v1==0){
				AC1 = AC1 +"-01";
				cur_v1=V1;
			}
			else if (cur_v2==V2){
				AC1 = AC1 + "-20";
				cur_v2=0;
			}
			else{
				AC1=AC1+"-12";
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
				AC2 = AC2 +"-02";
				cur_v2=V2;
			}
			else if (cur_v1==V1){
				AC2 = AC2 + "-10";
				cur_v1=0;
			}
			else{
				AC2=AC2+"-21";
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

		final long startTime = System.currentTimeMillis();		
		V1 = Integer.parseInt(args[0]);
		V2 = Integer.parseInt(args[1]);
		Vg = Integer.parseInt(args[2]);
		
		gcd = GCD(V1,V2);
		
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
			solve01();
		}

		cur_v1=0;
		cur_v2=V2;
		while(cur_v1!=Vg && cur_v2!=Vg){
			solve02();
		}
		if(AC1.length()>AC2.length())
			System.out.println(AC2);
		else
			System.out.println(AC1);

		final long endTime = System.currentTimeMillis();
		System.out.println("Total execution time: " + (endTime - startTime) );

	}
}
