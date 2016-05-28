package ALUJ;



public class script {
    public static void main(String[] args) {
    	ALU alu = new ALU();
      System.out.println(alu.floatRepresentation("0.00001", 5, 11));
    	//System.out.println(alu.floatRepresentation("1.0",8,11));
    	//System.out.println(alu.integerTrueValue("111110"));
//    	String test="10101011";
//    	if(test.equals(alu.integerRepresentation(alu.integerTrueValue(test), test.length()))){
//    		System.out.println("true");
//    		System.out.println(alu.integerTrueValue(test));
//    		
//    	}
    	System.out.println(alu.floatTrueValue("00000000101001111", 5, 11));
    	
//    	0 01101110 01001111100010110101100
//    	0 01101110 01001111100010110101100
//    	0 0110111 10100111110001011010110
    	
    	
	}
}
