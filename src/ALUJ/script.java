package ALUJ;



public class script {
    public static void main(String[] args) {
    	ALU alu = new ALU();
      //System.out.println(alu.floatRepresentation("0.00001", 5, 11));
    	//System.out.println(alu.floatRepresentation("4.125675",8,23));
    	//System.out.println(alu.integerTrueValue("111110"));
//    	String test="10101011";
//    	if(test.equals(alu.integerRepresentation(alu.integerTrueValue(test), test.length()))){
//    		System.out.println("true");
//    		System.out.println(alu.integerTrueValue(test));
//    		
//    	}
    	//System.out.println(alu.floatTrueValue("01000000100001000000010110000111", 8, 23));
    	
//    	0 01101110 01001111100010110101100
//    	0 01101110 01001111100010110101100
//    	0 0110111 10100111110001011010110
    	//System.out.println(alu.negation("01010111011"));
    	//System.out.println(alu.leftShift("111100", 4));
    	//System.out.println(alu.logRightShift("11", 2));
    	//System.out.println(alu.ariRightShift("10101", 1));
    	//System.out.println(alu.Xor('0','0','1'));
    	//System.out.println(alu.Or('0','1','0'));
    	//System.out.println(alu.fullAdder('0', '0', '0'));
    	//System.out.println(alu.claAdder("1111", "0001", '0'));
    	//System.out.println(alu.oneAdder("011"));

    	//System.out.println(alu.adder("1111", "0001", '0', 4));
    	//System.out.println(alu.integerAddition("0111", "0111", 4));
    	//System.out.println(alu.integerSubtraction("1111", "1000", 8));
    	//System.out.println(alu.integerDivision("00001001", "0000011", 8));
    	//System.out.println(alu.signedAddition("11111", "10001", 4));

    	//System.out.println(alu.adder("0111", "0111", '1', 4));
    	//System.out.println(alu.integerAddition("0111", "0111", 4));
    	//System.out.println(alu.integerSubtraction("1111", "1000", 8));
    	//System.out.println(alu.integerMultiplication("0111", "0101", 8));
    	//System.out.println(Math.pow(2, -15));
    	//System.out.println(alu.minus2exp(15));
        //System.out.println(alu.bigadd("31.25", "4.75"));
        //System.out.println(alu.twoexp(-3));
    	//System.out.println(alu.UnsignedBtoH("001111"));
//    	System.out.println(alu.floatAddition("10111111000000000000000000000000", "00111110111000000000000000000000", 
//    			8, 23, 0));
//    	
//    	System.out.println(alu.floatTrueValue("10111101100000000000000000000000", 8, 23));
//    	double a,b;
//    	a=2.0;
//    	b=3.0;
    	String aa ,bb;
    	aa=alu.floatRepresentation("0.5", 8, 23);
    	System.out.println("aa:"+aa);
    	bb=alu.floatRepresentation("0.4375", 8, 23);
    	System.out.println("bb:"+bb);
    	String ans = alu.floatDivision(aa, bb, 8, 23);
    	System.out.println("ans: "+ans);
    	String get = alu.floatTrueValue(ans.substring(1), 8, 23);
    	System.out.println("get:   "+get);
    	System.out.println("REAL: "+((double)0.5/0.4375));
    	//System.out.println(alu.floatRepresentation("0.5", 8, 23));
    	//System.out.println(alu.floatRepresentation("-0.4375", 8, 23));
        //System.out.println(alu.unsignedBooth("1111", "1111", 4));
     	System.out.println(alu.integerDivision("010000000", "011100000", 12));
    	//00000000011100000

	}
}
