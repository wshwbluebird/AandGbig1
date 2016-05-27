package ALUJ;



public class script {
    public static void main(String[] args) {
    	ALU alu = new ALU();
    	//System.out.println(alu.floatRepresentation("0.00000001", 5, 11));
    	//System.out.println(alu.integerTrueValue("111110"));
    	String test="10101011";
    	if(test.equals(alu.integerRepresentation(alu.integerTrueValue(test), test.length()))){
    		System.out.println("true");
    		System.out.println(alu.integerTrueValue(test));
    		
    	}
    	
    	
    	
	}
}
