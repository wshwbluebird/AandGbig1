package ALUJ;
/**
 * 模拟ALU进行整数和浮点数的四则运算
 * @author [请将此处修改为“学号_姓名”]
 *
 */

public class ALU {
	
	

	/**
	 * 生成十进制整数的二进制补码表示。<br/>
	 * 例：integerRepresentation("9", 8)
	 * @param number 十进制整数。若为负数；则第一位为“-”；若为正数或 0，则无符号位
	 * @param length 二进制补码表示的长度
	 * @return number的二进制补码表示，长度为length
	 */
	public String integerRepresentation (String number, int length) {
	    boolean isMinus = false;
	    if('-'==number.charAt(0)){
	    	isMinus = true;
	    	number = number.substring(1);
	    }
	    String unsign = "";	    
	    long num = Integer.parseInt(number);
	    if(num == 0) unsign = "0";
	    else{
	        while(num!=0){
	    	   if(num%2==0)   unsign="0"+unsign;  
	    	   else unsign ="1"+unsign;
	    	   num= num/2;
	         }
	    }
	    int comp = length-unsign.length();
	    String pre = "";
	    while(comp!=0){
	    	pre=pre+"0";
	    	comp--;
	    }
	    pre = pre + unsign;
	    boolean meetone = false;
	    char[] ans = pre.toCharArray();	   
	    if(isMinus){
	    	for (int i = length-1; i >= 0; i--) {
				if(meetone){				
					if('0'==ans[i]) ans[i] = '1';
					else   ans[i] = '0';
				}else{
					if('1'==ans[i] ) meetone=true;
				}
			}
	    }
	    
		return String.valueOf(ans);
	}
	
	/**
	 * 生成十进制浮点数的二进制表示。
	 * 需要考虑 0、反规格化、正负无穷（“+Inf”和“-Inf”）、 NaN等因素，具体借鉴 IEEE 754。
	 * 舍入策略为向0舍入。<br/>
	 * 例：floatRepresentation("11.375", 8, 11)
	 * @param number 十进制浮点数，包含小数点。若为负数；则第一位为“-”；若为正数或 0，则无符号位
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @return number的二进制表示，长度为 1+eLength+sLength。从左向右，依次为符号、指数（移码表示）、尾数（首位隐藏）
	 */
	public String floatRepresentation (String number, int eLength, int sLength) {
		 String first = "0";
		 String sig = "";//表示 规格化位的字符串
		 String exp = "";
		 int nsig;
		 int nexp;
		 int maxexp = (int) Math.pow(2, eLength)-2;  //14
		 System.out.println("max:  "+maxexp);
		 int offexp = (int) Math.pow(2, eLength-1)-1;   //7		
		 System.out.println("off:  "+offexp);
		 int minexp = -offexp +1;
		 System.out.println("min:  "+minexp);
		 
		    if('-'==number.charAt(0)){
		    	first = "1";
		    	number = number.substring(1);
		    }
		   number = number.replace('.', ';');
		   String[]  data= number.split(";");
		   long nint = Integer.parseInt(data[0]);
		   double nfloat = Double.parseDouble("0."+data[1]);
		  System.out.println("int:   "+nint);
		  System.out.println("float:    "+nfloat);
		 // 开始判断
//		  if(nint >= (int) Math.pow(2, maxexp-offexp+1)){    //此边界条件 问任彤韦   
//			  System.out.println(first+"inf");
//		  }
			//检测是否超出   
		   
		 if(nint!=0){//如果整数部分不是0
			   String sint="";
			   while(nint!=0){
				   if(nint%2==0)  sint = "0"+sint;
				   else sint = "1"	+ sint;
				   nint = nint/2;
				   }
			   sint = sint.substring(1);
			   System.out.println(sint);
			   nexp = sint.length()+offexp;
			   if(nexp>maxexp){
				   exp="1111";
				   if(exp.length()<eLength) exp+="1";
				   sig="0000";
				   if(sig.length()<sLength) sig+="0";
				   return first+" "+exp+" "+sig;
			   } else{
				   while(nexp!=0){
					   if(nexp%2==0)   exp = "0" + exp;
					   else exp = "1" + exp;
					   nexp = nexp/2;
				   }
				   while(exp.length()<eLength)
					   exp = "0"+exp;
			   }
			   String sfloat = "";
			   if(sint.length()>=sLength){
				   sig = sint.substring(0, sLength);
				   return first+" "+exp+" "+sig;
				   
			   }
			   int stillneed = sLength - sint.length();
			   System.out.println("stillneed:  "+stillneed);
			   while(sfloat.length()<stillneed){
				   nfloat = nfloat*2;
				   if(nfloat>=1)  {
					   sfloat += "1" ;
					   nfloat = nfloat-1;					   
				   }else{
					   sfloat += "0";
				   }
				   
			   }
			  
			   sig = sint + sfloat;
			   return first+" "+exp+" "+sig;
		   }
		 else{//如果整数部分是0
			  double small =  nfloat * 2;
			  nexp = -1;
			 while(nexp>=minexp){
				 if(small>=1){
					 small = small-1;
					 nexp = nexp + offexp;
					 while(nexp!=0){
						   if(nexp%2==0)   exp = "0" + exp;
						   else exp = "1" + exp;
						   nexp = nexp/2;
					   }
					   while(exp.length()<eLength)
						   exp = "0"+exp;
					   //确定了指数
					   while(sig.length()<sLength){
						   small = small*2;
						   if(small>=1){
							   small = small -1;
							   sig += "1";
						   }else {
							   sig += "0";
						   }
					   }
						
					return first+" "+exp+" "+sig;
				 }
				 nexp--;
				 small = small*2;
			 }
			 nexp = 0;
			 while(sig.length()<sLength){
				  // small = small*2;
				   if(small>=1){
					   small = small -1;
					   sig += "1";
				   }else {
					   sig += "0";
				   }
				   small=small*2;
			   }
			 exp="0000";
			 while(exp.length()<eLength)
				   exp = "0"+exp;
			     
			   
			return first+" "+exp+" "+sig; 
			
		 }
		
	}
	
	/**
	 * 生成十进制浮点数的IEEE 754表示，要求调用{@link #floatRepresentation(String, int, int) floatRepresentation}实现。<br/>
	 * 例：ieee754("11.375", 32)
	 * @param number 十进制浮点数，包含小数点。若为负数；则第一位为“-”；若为正数或 0，则无符号位
	 * @param length 二进制表示的长度，为32或64
	 * @return number的IEEE 754表示，长度为length。从左向右，依次为符号、指数（移码表示）、尾数（首位隐藏）
	 */
	public String ieee754 (String number, int length) {
		if(length==32)
		return floatRepresentation(number, 8, 23);
		else
		return floatRepresentation(number, 11, 52);
	}
	
	/**
	 * 计算二进制补码表示的整数的真值。<br/>
	 * 例：integerTrueValue("00001001")
	 * @param operand 二进制补码表示的操作数
	 * @return operand的真值。若为负数；则第一位为“-”；若为正数或 0，则无符号位
	 */
	public String integerTrueValue (String operand) {
		int ans = 0;
		for (int i = 0; i < operand.length(); i++) {
			//System.out.println(operand.charAt(i));
			if(operand.charAt(i)=='1'){
			   if(i==0){
				ans = (int) (ans-Math.pow(2, operand.length()-1-i));
			    }
			   else ans = (int) (ans+Math.pow(2, operand.length()-1-i));
		    }
		}
		
		return String.valueOf(ans);
	}
	
	/**
	 * 计算二进制原码表示的浮点数的真值。<br/>
	 * 例：floatTrueValue("01000001001101100000", 8, 11)
	 * @param operand 二进制表示的操作数
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @return operand的真值。若为负数；则第一位为“-”；若为正数或 0，则无符号位。正负无穷分别表示为“+Inf”和“-Inf”， NaN表示为“NaN”
	 */
	public String floatTrueValue (String operand, int eLength, int sLength) {
		int offexp = (int) (Math.pow(2, eLength-1)-1);
		String first = operand.substring(0,1);
		String minus = "";
		if("1".equals(first)) minus = "-";
		String sexp = operand.substring(1,1+eLength);
		System.out.println("sexp:   "+sexp);
		String ssig = operand.substring(1+eLength);
		System.out.println("ssig:   "+ssig);
		
		
		int iexp = 0;
		for (int i = 0; i < sexp.length(); i++) {
			if(sexp.charAt(i)=='1'){
			   iexp = (int) (iexp+Math.pow(2, sexp.length()-1-i));
		    }
		}
		System.out.println("offest:  "+offexp);
		System.out.println("iexp:   "+iexp);
		
		if(iexp==Math.pow(2, eLength)-1){
		    long temp =Integer.parseInt(ssig);
		    if(temp==0){
		      if("".equals(minus))	minus = "+";
		       return minus+"INF";
		    }else{
		    	return "NaN";
		    }
		    
		}
		double ans = 0;
		if(iexp ==0){//如果指数等于0
			int exp = -offexp;
			for (int i = 0; i < ssig.length(); i++) {
				if(ssig.charAt(i)=='1'){
				    ans = (double) (ans+Math.pow(2, exp-i));
			    }
			}
			return minus+String.valueOf(ans);
		}
		iexp = iexp - offexp;// 如果指数不等于0
		String sint;
		String soat;		
		System.out.println(iexp);
		if(iexp>=0&&iexp<sLength){
			sint = "1"+ssig.substring(0, iexp);
			soat = ssig.substring(iexp);
			for (int i = 0; i < sint.length(); i++) {
				if(sint.charAt(i)=='1'){
				    ans = (int) (ans+Math.pow(2, sint.length()-1-i));
			    }
			}
			for (int i = 0; i < soat.length(); i++) {
				if(soat.charAt(i)=='1'){
				    ans = (double) (ans+Math.pow(2, -i-1));
			    }
			}
			return minus+String.valueOf(ans);
		}else if(iexp<0){
			soat = "1"+ssig;
			int com = -1-iexp;
			while(com!=0){
				soat = "0" + soat;
				com--;
			}
         System.out.println(soat);
			for (int i = 0; i < soat.length(); i++) {
				if(soat.charAt(i)=='1'){
				    ans = (double) (ans+Math.pow(2, -i-1));
			    }
			}
			return minus+String.valueOf(ans);
		}else if(iexp>=sLength){
			sint = "1" + ssig;
			System.out.println("sint:  "+sint);
			for (int i = 0; i < sint.length(); i++) {
				if(sint.charAt(i)=='1'){
				    ans = (int) (ans+Math.pow(2, sint.length()-1-i));
			    }
			}
			int com = iexp -sLength;
			while(com!=0){
				ans = ans *2;
				com--;
			}
			return minus+String.valueOf(ans);
		}
		
		
		
		return null;
	}
	
	@SuppressWarnings("unused")
	private String  DoubleTostring(double d){
		//待定的方法， 不知道无限小数精确到多少位
		return null;
	}
	/**
	 * 按位取反操作。<br/>
	 * 例：negation("00001001")
	 * @param operand 二进制表示的操作数
	 * @return operand按位取反的结果
	 */
	public String negation (String operand) {
		//System.out.println("negation was called");
		//System.out.println(operand);
		String ans = "";
		for (int i = 0; i < operand.length(); i++) {
			char cur = operand.charAt(i)=='1'? '0':'1';
			ans = ans + cur;
		}
		return ans;
		
	}
	
	/**
	 * 左移操作。<br/>
	 * 例：leftShift("00001001", 2)
	 * @param operand 二进制表示的操作数
	 * @param n 左移的位数
	 * @return operand左移n位的结果
	 */
	public String leftShift (String operand, int n) {
		if(operand.length()==1)
			return "0";
		char[]  ans = operand.toCharArray();
		for (int j = 0; j < n; j++) {
		    for (int i = 1; i < operand.length(); i++) {
			    ans[i-1]  = ans[i];
		       }
		    ans[operand.length()-1] = '0';
		}
		return String.valueOf(ans);
	}
	
	/**
	 * 逻辑右移操作。<br/>
	 * 例：logRightShift("11110110", 2)
	 * @param operand 二进制表示的操作数
	 * @param n 右移的位数
	 * @return operand逻辑右移n位的结果
	 */
	public String logRightShift (String operand, int n) {
		if(operand.length()==1)
			return "0";
		System.out.println(operand);
		char[]  ans = operand.toCharArray();
		for (int i = 0; i < n; i++) {
			for (int j = ans.length-1; j >0; j--) {
				ans[j] = ans[j-1];
			}
			ans[0] = '0';
		}
		return String.valueOf(ans);
	}
	
	/**
	 * 算术右移操作。<br/>
	 * 例：logRightShift("11110110", 2)
	 * @param operand 二进制表示的操作数
	 * @param n 右移的位数
	 * @return operand算术右移n位的结果
	 */
	public String ariRightShift (String operand, int n) {
		if(operand.length()==1)
			return operand;
		System.out.println(operand);
		char[]  ans = operand.toCharArray();
		for (int i = 0; i < n; i++) {
			for (int j = ans.length-1; j >0; j--) {
				ans[j] = ans[j-1];
			}
			ans[0] = ans[1];
		}
		return String.valueOf(ans);
	
	}
	
	/**
	 * 全加器，对两位以及进位进行加法运算。<br/>
	 * 例：fullAdder('1', '1', '0')
	 * @param x 被加数的某一位，取0或1
	 * @param y 加数的某一位，取0或1
	 * @param c 低位对当前位的进位，取0或1
	 * @return 相加的结果，用长度为2的字符串表示，第1位表示进位，第2位表示和
	 */
	public String fullAdder (char x, char y, char c) {
	    char carry = Or(And(x,y),And(y,c),And(x,c));
	    char sum = Xor(x,y,c);
	    String ans = "";
	   ans = ans + carry + sum;
		return ans;
	}
	
	/**
	 * 4位先行进位加法器。<br/>
	 * 例：claAdder("1001", "0001", '1')
	 * @param operand1 4位二进制表示的被加数
	 * @param operand2 4位二进制表示的加数
	 * @param c 低位对当前位的进位，取0或1
	 * @return 长度为5的字符串表示的计算结果，其中第1位是最高位进位，后4位是相加结果，其中进位不可以由循环获得
	 */
	public String claAdder (String operand1, String operand2, char c) {
		char[] ans = new char[operand1.length()];
		char[] carry = getCarry(operand1, operand2, c);//传过来的carry 是从右往左的，运算的时候
		                                               //要把它转换成从左往右 并从第3位开始（从0开始数）
		for (int i = 0; i < 4; i++) {
			//System.out.println(operand1.charAt(i)+" "+operand2.charAt(i)+" "+carry[3-i]);
			ans[i] = fullAdder(operand1.charAt(i),operand2.charAt(i),carry[3-i]).charAt(1);
			//调用 fulladder
		}
		return carry[4]+String.valueOf(ans);
	}
	
	/**
	 * 加一器，实现操作数加1的运算。
	 * 需要模拟{@link #fullAdder(char, char, char) fullAdder}来实现，但不可以调用{@link #fullAdder(char, char, char) fullAdder}。<br/>
	 * 例：oneAdder("00001001")
	 * @param operand 二进制补码表示的操作数
	 * @return operand加1的结果，长度为operand的长度加1，其中第1位指示是否溢出（溢出为1，否则为0），其余位为相加结果
	 */
	public String oneAdder (String operand) {
		char c = '1';
		char[] ans = operand.toCharArray(); 
		for (int i = operand.length()-1;i>=0; i--) {
			//System.out.println(c+" "+ans[i]);
			char p = And(c,ans[i]);
			ans[i] = Xor(c,ans[i]);
			c = p;
		}
		return c+String.valueOf(ans);
	}
	
	/**
	 * 加法器，要求调用{@link #claAdder(String, String, char)}方法实现。<br/>
	 * 例：adder("0100", "0011", ‘0’, 8)
	 * @param operand1 二进制补码表示的被加数
	 * @param operand2 二进制补码表示的加数
	 * @param c 最低位进位
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，需要在高位补符号位
	 * @return 长度为length+1的字符串表示的计算结果，其中第1位指示是否溢出（溢出为1，否则为0），后length位是相加结果
	 */
	public String adder (String operand1, String operand2, char c, int length) {
	    //补全符号位
		char sign1 = operand1.charAt(0);
		while(operand1.length()!=length)   operand1 = sign1 + operand1;
		char sign2 = operand2.charAt(0);
		while(operand2.length()!=length)   operand2 = sign2 + operand2;
		//四位一截
		int groupsnum = length/4;
		String[] group = new String[groupsnum];
		char[] groupCarry = new char[groupsnum+1];
		groupCarry[0] = c;
		
		//调用cla进行迭代运算
	    String ans = "";
		for (int i = 0; i < groupsnum; i++) {
			String get = claAdder(operand1.substring(operand1.length()-4,operand1.length()), 
					              operand2.substring(operand2.length()-4,operand2.length()),
					              groupCarry[i]);
			groupCarry[i+1] = get.charAt(0);
			group[i] = get.substring(1);
			ans = group[i] + ans;
			operand1 = operand1.substring(0,operand1.length()-4);
			operand2 = operand2.substring(0,operand2.length()-4);
		}
		//判断溢出的可能性
		boolean overflow = And(Not(Xor(sign1,sign2)),Xor(ans.charAt(0),sign1))=='1'?true:false;
				
		if(overflow) return "1" + ans;
		        else return "0" + ans;
		
				
	}
	/**
	 * 整数加法，要求调用{@link #claAdder(String, String, char) claAdder}方法实现。<br/>
	 * 例：integerAddition("0100", "0011", 8)
	 * @param operand1 二进制补码表示的被加数
	 * @param operand2 二进制补码表示的加数
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，需要在高位补符号位
	 * @return 长度为length+1的字符串表示的计算结果，其中第1位指示是否溢出（溢出为1，否则为0），后length位是相加结果
	 */
	public String integerAddition (String operand1, String operand2, int length) {
		 
		return adder(operand1, operand2, '0', length);
	}
	
	/**
	 * 整数减法，可调用{@link #integerAddition(String, String, char, int) integerAddition}方法实现。<br/>
	 * 例：integerSubtraction("0100", "0011", 8)
	 * @param operand1 二进制补码表示的被减数
	 * @param operand2 二进制补码表示的减数
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，需要在高位补符号位
	 * @return 长度为length+1的字符串表示的计算结果，其中第1位指示是否溢出（溢出为1，否则为0），后length位是相减结果
	 */
	public String integerSubtraction (String operand1, String operand2, int length) {
		operand2 = negation(operand2);
		//System.out.println(operand2);
		return adder(operand1, operand2, '1', length);
	}
	
	/**
	 * 整数乘法，使用Booth算法实现，可调用{@link #integerAddition(String, String, char, int) integerAddition}等方法。<br/>
	 * 例：integerMultiplication("0100", "0011", 8)
	 * @param operand1 二进制补码表示的被乘数
	 * @param operand2 二进制补码表示的乘数
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，需要在高位补符号位
	 * @return 长度为length+1的字符串表示的相乘结果，其中第1位指示是否溢出（溢出为1，否则为0），后length位是相乘结果
	 */
	public String integerMultiplication (String operand1, String operand2, int length) {
		// TODO YOUR CODE HERE.
		return null;
	}
	
	/**
	 * 整数的不恢复余数除法，可调用{@link #integerAddition(String, String, char, int) integerAddition}等方法实现。<br/>
	 * 例：integerDivision("0100", "0011", 8)
	 * @param operand1 二进制补码表示的被除数
	 * @param operand2 二进制补码表示的除数
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，需要在高位补符号位
	 * @return 长度为2*length+1的字符串表示的相除结果，其中第1位指示是否溢出（溢出为1，否则为0），其后length位为商，最后length位为余数
	 */
	public String integerDivision (String operand1, String operand2, int length) {
		//分组 补位
		while(operand1.length()!=length) operand1 = operand1.charAt(0) + operand1;//除数
		while(operand2.length()!=length) operand2 = operand2.charAt(0) + operand2;//被除数
		String register = operand1;
		while(register.length()!=2*length) register = register.charAt(0) + register;
		//System.out.println("最开始"+register);
		//System.out.println("divisor:  "+operand2);
		//第一次加	
		if(register.charAt(0)==operand2.charAt(0)){
			String temp = adder(register.substring(0, length), 
					            negation(operand2), '1', length).substring(1);
			register = temp + register.substring(length);
		}else{
			String temp = adder(register.substring(0, length), 
		                        operand2, '0', length).substring(1);
			//System.out.println(temp);
            register = temp + register.substring(length);
            //System.out.println("rr");
		}
			if(register.charAt(0)==operand2.charAt(0)) register = register + '1';
			else register = register + '0';
			//System.out.println("最第一次加完后：  "+register);	
		
		//循环
		for (int i = 0; i < length; i++) {
			//左移
			register = leftShift(register, 1);
			System.out.println("移动后"+register);
		    //判断 被除数 除数，同减异加
			if(register.charAt(0)==operand2.charAt(0)){
				//System.out.println("-");
				String temp = adder(register.substring(0, length), 
						            negation(operand2), '1', length).substring(1);
				register = temp + register.substring(length);
			}else{
				System.out.println("+");
				String temp = adder(register.substring(0, length), 
                        operand2, '0', length).substring(1);
                register = temp + register.substring(length);
                
            }
		    //判断加后 余数 和被除数 同1 不同0
			if(register.charAt(0)==operand2.charAt(0)) register = register.substring(0,length *2) +"1";
			System.out.println("加完后"+register);
		}	   
		//善后
		String remainder = register.substring(0,length);
		String quotient = register.substring(length);
		if(operand1.charAt(0)!=remainder.charAt(0)){
		     if(operand1.charAt(0)==operand2.charAt(0))  
			        remainder = adder(remainder, operand2, '0', length).substring(1);
		     else
			        remainder = adder(remainder, negation(operand2), '1', length).substring(1);
		}
		//System.out.println("remainder: "+remainder);
		quotient = leftShift(quotient, 1).substring(0, length);
		//System.out.println("quotient: "+quotient);
		if(operand1.charAt(0)!=operand2.charAt(0))  quotient = oneAdder(quotient).substring(1);
		
		return quotient+remainder;
	}
	 
	/**
	 * 带符号整数加法，要求调用{@link #integerAddition(String, String, int) integerAddition}、{@link #integerSubtraction(String, String, int) integerSubtraction}等方法实现。
	 * 但符号的确定、结果是否修正等需要按照相关算法进行，不能直接转为补码表示后运算再转回来<br/>
	 * 例：signedAddition("1100", "1011", 8)
	 * @param operand1 二进制原码表示的被加数，其中第1位为符号位
	 * @param operand2 二进制原码表示的加数，其中第1位为符号位
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度（不包含符号），当某个操作数的长度小于length时，需要将其长度扩展到length
	 * @return 长度为length+2的字符串表示的计算结果，其中第1位指示是否溢出（溢出为1，否则为0），第2位为符号位，后length位是相加结果
	 */
	public String signedAddition (String operand1, String operand2, int length) {
		// TODO YOUR CODE HERE.
		
		return null;
	}
	
	/**
	 * 浮点数加法，可调用{@link #integerAddition(String, String, char, int) intergerAddition}等方法实现。<br/>
	 * 例：floatAddition("00111111010100000", "00111111001000000", 8, 8, 8)
	 * @param operand1 二进制表示的被加数
	 * @param operand2 二进制表示的加数
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @param gLength 保护位的长度
	 * @return 长度为2+eLength+sLength的字符串表示的相加结果，其中第1位指示是否指数上溢（溢出为1，否则为0），其余位从左到右依次为符号、指数（移码表示）、尾数（首位隐藏）。舍入策略为向0舍入
	 */
	public String floatAddition (String operand1, String operand2, int eLength, int sLength, int gLength) {
		// TODO YOUR CODE HERE.
		return null;
	}
	
	/**
	 * 浮点数减法，可调用{@link #floatAddition(String, String, int, int, int) floatAddition}方法实现。<br/>
	 * 例：floatSubtraction("00111111010100000", "00111111001000000", 8, 8, 8)
	 * @param operand1 二进制表示的被减数
	 * @param operand2 二进制表示的减数
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @param gLength 保护位的长度
	 * @return 长度为2+eLength+sLength的字符串表示的相减结果，其中第1位指示是否指数上溢（溢出为1，否则为0），其余位从左到右依次为符号、指数（移码表示）、尾数（首位隐藏）。舍入策略为向0舍入
	 */
	public String floatSubtraction (String operand1, String operand2, int eLength, int sLength, int gLength) {
		// TODO YOUR CODE HERE.
		return null;
	}
	
	/**
	 * 浮点数乘法，可调用{@link #integerAddition(String, String, char, int) integerAddition}等方法实现。<br/>
	 * 例：floatMultiplication("00111110111000000", "00111111000000000", 8, 8)
	 * @param operand1 二进制表示的被乘数
	 * @param operand2 二进制表示的乘数
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @return 长度为2+eLength+sLength的字符串表示的相乘结果,其中第1位指示是否指数上溢（溢出为1，否则为0），其余位从左到右依次为符号、指数（移码表示）、尾数（首位隐藏）。舍入策略为向0舍入
	 */
	public String floatMultiplication (String operand1, String operand2, int eLength, int sLength) {
		// TODO YOUR CODE HERE.
		return null;
	}
	
	/**
	 * 浮点数除法，可调用{@link #integerAddition(String, String, char, int) integerAddition}等方法实现。<br/>
	 * 例：floatDivision("00111110111000000", "00111111000000000", 8, 8)
	 * @param operand1 二进制表示的被除数
	 * @param operand2 二进制表示的除数
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @return 长度为2+eLength+sLength的字符串表示的相乘结果,其中第1位指示是否指数上溢（溢出为1，否则为0），其余位从左到右依次为符号、指数（移码表示）、尾数（首位隐藏）。舍入策略为向0舍入
	 */
	public String floatDivision (String operand1, String operand2, int eLength, int sLength) {
		// TODO YOUR CODE HERE.
		return null;
	}
//*****************************************************************************************************************	
	
	private  char And(char...a){
		for (int i = 0; i < a.length; i++) {
			if(a[i]=='0')  return '0';			
		}
		return '1';	
	}
	
	private  char Or(char...a){
		for (int i = 0; i < a.length; i++) {
			if(a[i]=='1')  return '1';			
		}
		return '0';	
	}
	
	private  char Xor(char...a){
		char ans = a[0];
		for (int i = 1; i < a.length; i++) {
			if(ans == a[i]) ans='0';
			else ans = '1';
		}
		return ans;
	}
	private char Not(char a){
		return a=='1'?'0':'1';
	}
//*****************************************************************************************************************	
	private char[] getCarry(String operand1, String operand2, char c){
		char[] p = new char[5];
		char[] g = new char[5];
		char[] carry = new char[5];
		carry[0] = c;
		for (int i = 1; i <= 4; i++) {
			p[i] = Or(operand1.charAt(4-i),operand2.charAt(4-i));
			g[i] = And(operand1.charAt(4-i),operand2.charAt(4-i));
		}
		carry[1] =  Or(g[1],And(p[1],c));
		carry[2] =  Or(g[2],And(p[2],g[1]),And(p[2],p[1],c));
		carry[3] =  Or(g[3],And(p[3],g[2]),And(p[3],p[2],g[1]),And(p[3],p[2],p[1],c));
		carry[4] =  Or(g[4],And(p[4],g[3]),And(p[4],p[3],g[2]),And(p[4],p[3],p[2],g[1]),And(p[4],p[3],p[2],p[1],c));
		
//		for (int i = 0; i < carry.length; i++) {
//			System.out.println(carry[i]);
//		}
		return carry; //返回的carry 是从右往左数的
	}
	
	
}
