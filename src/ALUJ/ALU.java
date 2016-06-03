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
		 //System.out.println("max:  "+maxexp);
		 int offexp = (int) Math.pow(2, eLength-1)-1;   //7		
		 //System.out.println("off:  "+offexp);
		 int minexp = -offexp +1;
		 //System.out.println("min:  "+minexp);
		 
		    if('-'==number.charAt(0)){
		    	first = "1";
		    	number = number.substring(1);
		    }
		   number = number.replace('.', ';');
		   String[]  data= number.split(";");
		   long nint = Integer.parseInt(data[0]);
		   double nfloat = Double.parseDouble("0."+data[1]);
		 // System.out.println("int:   "+nint);
		 // System.out.println("float:    "+nfloat);
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
			  // System.out.println(sint);
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
				   return first+""+exp+""+sig;
				   
			   }
			   int stillneed = sLength - sint.length();
			   //System.out.println("stillneed:  "+stillneed);
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
			   return first+""+exp+""+sig;
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
						
					return first+""+exp+""+sig;
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
			     
			   
			return first+""+exp+""+sig; 
			
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
		int offexp = (int) (Math.pow(2, eLength-1)-1);//偏移指数
		String first = operand.substring(0,1);  //正负
		String minus = "";
		if("1".equals(first)) minus = "-";
		String sexp = operand.substring(1,1+eLength);//指数的字符串
		//System.out.println("sexp:   "+sexp);
		String ssig = operand.substring(1+eLength);//小数的字符串
		//System.out.println("ssig:   "+ssig);
		
		
		int iexp = 0; //指数的数值
		for (int i = 0; i < sexp.length(); i++) {
			if(sexp.charAt(i)=='1'){
			   iexp = (int) (iexp+Math.pow(2, sexp.length()-1-i));//计算 字符串表示的指数 整数值
		    }
		}
		//System.out.println("offest:  "+offexp);
		//System.out.println("iexp:   "+iexp);
		
		//if(iexp==Math.pow(2, eLength)-1){   //检查指数位是否全为1
		if(And(sexp.toCharArray())=='1'){     //检查指数位是否全为1
		    long temp =Integer.parseInt(ssig);
		    if(temp==0){
		      if("".equals(minus))	minus = "+";
		       return minus+"INF";
		    }else{
		    	return "NaN";
		    }
		    
		}
		//double ans = 0; //
		String sans="0.0";
		if(iexp ==0){//如果指数等于0
			int exp = -offexp;
			for (int i = 0; i < ssig.length(); i++) {
				if(ssig.charAt(i)=='1'){
				    //ans = (double) (ans+Math.pow(2, exp-i));
					sans = bigadd(sans, twoexp(exp-i));
			    }
			}
			return minus+sans;
		}
		iexp = iexp - offexp;// 如果指数不等于0
		String sint;
		String soat;		
		//System.out.println(iexp);
		if(iexp>=0&&iexp<sLength){
			sint = "1"+ssig.substring(0, iexp);
			soat = ssig.substring(iexp);
			for (int i = 0; i < sint.length(); i++) {
				if(sint.charAt(i)=='1'){
				    //ans = (int) (ans+Math.pow(2, sint.length()-1-i));
					sans = bigadd(sans, twoexp(sint.length()-1-i));
			    }
			}
			for (int i = 0; i < soat.length(); i++) {
				if(soat.charAt(i)=='1'){
				    //ans = (double) (ans+Math.pow(2, -i-1));
					sans = bigadd(sans, twoexp(-1-i));
			    }
			}
			return minus+sans;
		}else if(iexp<0){
			soat = "1"+ssig;
			int com = -1-iexp;
			while(com!=0){
				soat = "0" + soat;
				com--;
			}
         //System.out.println(soat);
			for (int i = 0; i < soat.length(); i++) {
				if(soat.charAt(i)=='1'){
				    //ans = (double) (ans+Math.pow(2, -i-1));
					sans = bigadd(sans, twoexp(-1-i));
			    }
			}
			return minus+sans;
		}else if(iexp>=sLength){
			sint = "1" + ssig;
			//System.out.println("sint:  "+sint);
			for (int i = 0; i < sint.length(); i++) {
				if(sint.charAt(i)=='1'){
				    //ans = (int) (ans+Math.pow(2, sint.length()-1-i));
					sans = bigadd(sans, twoexp(sint.length()-1-i));
			    }
			}
			int com = iexp -sLength;
			while(com!=0){
				//ans = ans *2;
				sans = times2(sans);
				com--;
			}
			return minus+sans;
		}
		
		
		
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
		//System.out.println(operand);
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
		char c = '1';//判断是否溢出
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
		//System.out.println(operand2);
		return adder(operand1, negation(operand2), '1', length);
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
		//分组
		while(operand1.length()<length) operand1 = operand1.charAt(0) + operand1;
		while(operand2.length()<length) operand2 = operand2.charAt(0) + operand2;
		String opr = operand2 + '0';
		while(opr.length()<2*length+1){
			opr = "0"+opr;
		}
		//System.out.println("opr  "+opr);
		//循环
		for (int i = 0; i < operand2.length(); i++) {
			 //判断
			int yy = boothJudgeY(opr.charAt(2*length),opr.charAt(2*length-1));//yy带表y0-y1
			 //加减
			//System.out.println(yy);
			if(yy==1){
				String temp = adder(operand1, opr.substring(0,operand1.length()), '0', operand1.length())
						.substring(1);
				opr = temp + opr.substring(operand1.length());
			}
			else if(yy==-1){
				String temp = adder(negation(operand1), opr.substring(0,operand1.length()), '1', operand1.length())
						.substring(1);
				
				opr = temp + opr.substring(operand1.length());
				//System.out.println("o1   "+operand1);
				//System.out.println("temp: "+temp);
			}
			 //System.out.println("加减"+opr);
			 opr = ariRightShift(opr, 1);
			 //System.out.println(opr);
		}
		//善后
		char[] pd = opr.substring(0,length).toCharArray();
		char overflow;
		if(And(pd)=='1'||Or(pd)=='0')
			 overflow = '0';
		else 
			overflow = '1';
		return overflow+opr.substring(length,2*length);
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
		String overflow = "0";
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
			//System.out.println("移动后"+register);
		    //判断 被除数 除数，同减异加
			if(register.charAt(0)==operand2.charAt(0)){
				//System.out.println("-");
				String temp = adder(register.substring(0, length), 
						            negation(operand2), '1', length).substring(1);
				register = temp + register.substring(length);
			}else{
				//System.out.println("+");
				String temp = adder(register.substring(0, length), 
                        operand2, '0', length).substring(1);
                register = temp + register.substring(length);
                
            }
		    //判断加后 余数 和被除数 同1 不同0
			if(register.charAt(0)==operand2.charAt(0)) register = register.substring(0,length *2) +"1";
			//System.out.println("加完后"+register);
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
		
		return overflow+quotient+remainder;// 如何溢出？
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
		// 设置变量
		String ans;
		char anssign;
		char overflow;
		char sign1 = operand1.charAt(0);
		operand1 = operand1.substring(1);
		char sign2 = operand2.charAt(0);
		operand2 = operand2.substring(1);
		while(operand1.length()!=length) operand1 = "0"+operand1;//减数
		while(operand2.length()!=length) operand2 = "0"+operand2;//被减数
		// 同号相加
		if(sign1 == sign2){
			ans = adder("0000"+operand1,"0000"+operand2, '0', length+4);	//保证是同号的相加 出现异号现象		
			anssign = sign1;
			overflow = Or(ans.substring(1, 5).toCharArray());
			ans = ans.substring(5);
			//System.out.println("dfs");
			return overflow+(anssign+ans);
			
		}
		// 异号相减
		else {
			ans = adder("0000"+operand1, negation("0000"+operand2), '1', length+4);
			//System.out.println(ans);
			char over = And(ans.substring(1, 5).toCharArray());//判断是否溢出
			ans = ans.substring(5);
			overflow ='0';
			if(over=='0'){
				anssign = sign1;
				return overflow+(anssign+ans);
			}else{
				
				String temp = oneAdder(negation(ans));
//				if(temp.charAt(0)=='1') anssign ='1';
//				else    //算出来是正0 就是正0 算出来不是正0 则负0
				anssign = sign2;
				ans = temp.substring(1);
				return overflow+(anssign+ans);
				
				
			}
			
		}
		

		
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
		//建立变量 每个变量要求写注释
		char sign1 = operand1.charAt(0);
		char sign2 = operand2.charAt(0);//储存两个浮点数的符号位
		String sexp1 = operand1.substring(1,1+eLength);
		String sexp2 = operand2.substring(1,1+eLength);//储存表示指数的字符串
		String ssig1 = operand1.substring(1+eLength);
		String ssig2 = operand2.substring(1+eLength);//存储小数位		
		//将两个操作数 扩展到最大位数 含保护位
		while(ssig1.length()<sLength+gLength) ssig1 = ssig1 + "0";
		while(ssig2.length()<sLength+gLength) ssig2 = ssig2 + "0";//将位数补全
		if(Or(sexp1.toCharArray())=='0')   {
			ssig1= "0"+ssig1;  //检测指数位是否为0 若为0   sig第一位为0
			sexp1 = oneAdder(sexp1).substring(1);
		}
		else   ssig1 = "1"+ssig1;  //否则位1
		if(Or(sexp2.toCharArray())=='0')   {
			ssig2 = "0"+ssig2;  //检测指数位是否为0 若为0   sig第一位为0
			sexp2 = oneAdder(sexp2).substring(1);
		}
		else   ssig2 = "1"+ssig2; 				
		//检验是否有零
		if(Or(ssig1.toCharArray())=='0') return "0"+operand2.substring(0,1+eLength+sLength);
		if(Or(ssig2.toCharArray())=='0') return "0"+operand1.substring(0,1+eLength+sLength);  //需要一种数据进行检验
		//拆分指数 精度 注意非规格化
		//比较指数 并移动为相等
	    //先移动一次  
	    //循环移动直到相等  
	    //判断是否为0 
		long iexp1 = UnsignedBtoH(sexp1);
		long iexp2 = UnsignedBtoH(sexp2);
		System.out.println("iexp1: "+iexp1);
		System.out.println("iexp2: "+iexp2);
		while(iexp1>iexp2){
			iexp2++;
			sexp2=oneAdder(sexp2).substring(1);
			ssig2 = logRightShift(ssig2, 1);
			if(Or(ssig2.toCharArray())=='0')  return "0"+operand1.substring(0,1+eLength+sLength);
		}
		while(iexp1<iexp2){
			iexp1++;
			sexp1=oneAdder(sexp1).substring(1);
			ssig1 = logRightShift(ssig1, 1);
			if(Or(ssig1.toCharArray())=='0')  return "0"+operand2.substring(0,1+eLength+sLength);
		}//将两个数的指数变为相同 并且在过程中随时判断是否有0的出现
		System.out.println(sexp1);
		System.out.println(sexp2);
		System.out.println(ssig1);
		System.out.println(ssig2);
	   String ans = "";	        
	   String ssigans = "";
	   String sexpans = "";
	   char signans;
		//运算带符号的sig加法
	   System.out.println("before addsign");
	   System.out.println("ssig2.length: "+ssig2.length());
	   String addedsig = signedAddition(sign1+ssig1, sign2+ssig2, sLength+gLength+1);
	   System.out.println("Addsign: "+addedsig);
		    //看看是否为0  设置为0
	         if(addedsig.charAt(0)=='0'&&Or(addedsig.substring(2).toCharArray())=='0'){
	        	 ans = "0"+sign1+addedsig.substring(2,addedsig.length()-gLength-1);
	        	 while(ans.length()<2+eLength+sLength){
	        		 ans = ans+"0";
	        	 }
	        	 return ans;
	         }
	       signans = addedsig.charAt(1);
	       sexpans=sexp1;
	       System.out.println("sexpan:  "+sexpans);
	       ssigans = addedsig.substring(2);
		//检测加法是否有溢出
	     if(addedsig.charAt(0)=='1'){
	    	//溢出 则向右移动指数加一      	 
	         ssigans =logRightShift(ssigans, 1);
	         ssigans = "1"+ssigans.substring(1,ssigans.length());
	         System.out.println("ssig+1:  "+ssigans);
	    	 sexpans = oneAdder(sexpans).substring(1);
	    	 System.out.println("sexpan+1:  "+sexpans);
	    	//判断指数是否溢出
	    	 if(And(sexpans.toCharArray())=='1'){
//	    		 String maxexp="1111";
//	    		 while(maxexp.length()<eLength){
//	    			 maxexp = maxexp+"1";
//	    		 }
//	    		 String ansinf = signans + maxexp;
//	    		 while(ansinf.length()<)
	    		 return "1"+signans+sexpans+ssigans.substring(0,sLength);
	    	 }
	     }		  
		//恢复规格化 判断低位溢出  注意指数为0的情＝情况   应该不会低位溢出 低位溢出报0
	    while(Or(sexpans.toCharArray())!='0'){
	       if(ssigans.charAt(0)=='1'){
	    	ssigans = ssigans.substring(1,1+sLength);
	    	return "0"+ signans + sexpans +  ssigans;
	        }
	       sexpans = minus1(sexpans);
	       ssigans =leftShift(ssigans, 1);
	    }
		return "0"+ signans + sexpans +  ssigans.substring(1,1+sLength);
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
		operand2 = Not(operand2.charAt(0))+operand2.substring(1);
		return floatAddition(operand1, operand2, eLength, sLength, gLength); 
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
		//检查是否有0
		if(Or(operand1.substring(1).toCharArray())=='0')  return operand1;
		if(Or(operand2.substring(1).toCharArray())=='0')  return operand2;
		//判断符号
		char signans = Xor(operand1.charAt(0),operand2.charAt(0));
	
		//分段处理
		String sexp1 = operand1.substring(1,1+eLength);
		String sexp2 = operand2.substring(1,1+eLength);//储存表示指数的字符串
		String ssig1 = operand1.substring(1+eLength);
		String ssig2 = operand2.substring(1+eLength);
		//增加隐去的第一位
		if(Or(sexp1.toCharArray())=='0')   {
			ssig1= "0"+ssig1;  //检测指数位是否为0 若为0   sig第一位为0
			sexp1 = oneAdder(sexp1).substring(1);
		}
		else   ssig1 = "1"+ssig1;  //否则位1
		if(Or(sexp2.toCharArray())=='0')   {
			ssig2 = "0"+ssig2;  //检测指数位是否为0 若为0   sig第一位为0
			sexp2 = oneAdder(sexp2).substring(1);
		}
		else   ssig2 = "1"+ssig2;
		//减去指数偏移量
		int offexp = (int) (Math.pow(2, eLength-1)-1);
		int iexp1 = (int) UnsignedBtoH(operand1.substring(1, 1+eLength));
		int iexp2 = (int) UnsignedBtoH(operand2.substring(1, 1+eLength));
		int maxexp = (int) (Math.pow(2, eLength)-1);
		
		//越界判定
		   //高位
		if(iexp1+iexp2-offexp>maxexp){
			return signans+"INF";
		}
		    //低位
		    //
            if(iexp1+iexp2-offexp<0)	{
            	String str =  signans+"0000000";
            	while(str.length()<2+eLength+sLength){
            		str = str+"0";
            	}
            }
		//小数位数相乘
		String sexpans="";
		String ssigans;
		int iexpans = iexp1+iexp2-offexp;
		while(iexpans!=0){
			if(iexpans%2==1)  sexpans = "1" + sexpans;
			else sexpans = "0" + sexpans;
			iexpans = iexpans/2;
		}
		while(sexpans.length()<eLength){
			sexpans = "0"+ sexpans;
		}
		String temp = unsignedBooth(ssig1, ssig2, ssig1.length());
		 if(temp.charAt(0)=='1')  {
			 temp = logRightShift(temp, 1);
			 sexpans = oneAdder(sexpans).substring(1);			 
		 }
		 ssigans = temp.substring(1);
		 if(And(sexpans.toCharArray())=='1') {
			 return "INF"; //溢出 其余不变 还是返回无穷大
		 }
		//规格化
		 while(Or(sexpans.toCharArray())!='0'){
		       if(ssigans.charAt(0)=='1'){
		    	ssigans = ssigans.substring(1,1+sLength);
		    	return "0"+ signans + sexpans +  ssigans;
		        }
		       sexpans = minus1(sexpans);
		       ssigans =leftShift(ssigans, 1);
		    }
	return "0"+ signans + sexpans +  ssigans.substring(1,1+sLength);
		
		
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
		//判断符号
		char signans = Xor(operand1.charAt(0),operand2.charAt(0));
		//判断为0的情况
		if(Or(operand2.substring(1).toCharArray())=='0') {
			String inf ="1111";
			while(inf.length()<eLength)  inf = inf+"1";
			while(inf.length()<eLength+sLength) inf = inf+"0";
			return "0"+signans+inf;
		}
		if(Or(operand1.substring(1).toCharArray())=='0')  {
			String zero = "00000000";
			while(zero.length()<eLength+sLength) zero = zero+"0";
			return "0"+signans+zero;
		}
		//字符串分离
		String sexp1 = operand1.substring(1,1+eLength);
		String sexp2 = operand2.substring(1,1+eLength);//储存表示指数的字符串
		String ssig1 = operand1.substring(1+eLength);
		String ssig2 = operand2.substring(1+eLength);
		//指数相减 再加回偏移量
				int offexp = (int) (Math.pow(2, eLength-1)-1);
				int iexp1 = (int) UnsignedBtoH(operand1.substring(1, 1+eLength));
				int iexp2 = (int) UnsignedBtoH(operand2.substring(1, 1+eLength));
				int maxexp = (int) (Math.pow(2, eLength)-1);
		//增加隐去的第一位
		if(Or(sexp1.toCharArray())=='0')   {
			ssig1= "0"+ssig1;  //检测指数位是否为0 若为0   sig第一位为0
			iexp1++;
		}else   ssig1 = "1"+ssig1;  //否则位1
		while(ssig1.charAt(0)!='1'){
			iexp1--;
			ssig1=leftShift(ssig1, 1);//一定存在这个1 不然之前就0了
		}
		if(Or(sexp2.toCharArray())=='0')   {
			ssig2 = "0"+ssig2;  //检测指数位是否为0 若为0   sig第一位为0
			sexp2 = oneAdder(sexp2).substring(1);
		}else   ssig2 = "1"+ssig2;
		while(ssig2.charAt(0)!='1'){
			iexp2--;
			ssig2=leftShift(ssig1, 1);//一定存在这个1 不然之前就0了
		}
		
		
		//越界判定
		   //高位
		if(iexp1-iexp2+offexp>maxexp){
			return signans+"INF";//指数溢出 后面的怎么算
		}
		    //低位
		    //
        if(iexp1-iexp2+offexp<0){
            	String str =  signans+"0000000";
            	while(str.length()<2+eLength+sLength){
            		str = str+"0";
            	}
            }
		//整数除法
        String ssigans = sigDivision(ssig1, ssig2, ssig1.length());
        //小数处理
        int iexpans = iexp1-iexp2+offexp;
        System.out.println("iexp:"+ (iexp1-iexp2));
        if(ssigans.charAt(0)=='0')  {
        	ssigans = ssigans.substring(1);
        	iexpans--;
        }
        
		//善后
        
        String sexpans="";
        while(iexpans!=0){
			if(iexpans%2==1)  sexpans = "1" + sexpans;
			else sexpans = "0" + sexpans;
			iexpans = iexpans/2;
		}
		while(sexpans.length()<eLength){
			sexpans = "0"+ sexpans;
		}
        while(Or(sexpans.toCharArray())!='0'){
		       if(ssigans.charAt(0)=='1'){
		    	ssigans = ssigans.substring(1,1+sLength);
		    	return "0"+ signans + sexpans +  ssigans;
		        }
		       sexpans = minus1(sexpans);
		       ssigans =leftShift(ssigans, 1);
		    }
	return "0"+ signans + sexpans +  ssigans.substring(1,1+sLength);
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

		return carry; //返回的carry 是从右往左数的
	}
//*****************************************************************************************************************	
	private int boothJudgeY(char a , char b){
		//System.out.println(a+"   "+b);
		if(a==b)  return 0;
		else return a=='1'? 1:-1;
	}
//*****************************************************************************************************************	
	private String divide2(String str){
		boolean flag = false; //检验进位是否余1
		char[]  opr = str.toCharArray();
		for (int i = 0; i < opr.length; i++) {
			if(opr[i]!='.'){
				int numchar = opr[i]-'0';
				if(flag)  numchar = numchar +10;
				opr[i] = (char) ((numchar/2)+'0');
				if(numchar%2==0)  flag = false;
				else flag = true;
			}
		}
		str = String.valueOf(opr);
		if(flag)   str = str+"5";
		return str;		
	}
	
	private String times2(String str){
		boolean flag = false; //检验进位是否余1
		char[]  opr = str.toCharArray();
		for (int i = opr.length-1; i >= 0; i--) {
			if(opr[i]!='.'){
				int numchar = opr[i]-'0';
				numchar = numchar*2;
				if(flag)  numchar++;				
				opr[i] = (char) ((numchar%10)+'0');
				if(numchar/10==0)  flag = false;
				else flag = true;
			}
		}
		str = String.valueOf(opr);
		if(flag)   str ="1"+ str;
		return str;		
	}
	private  String twoexp(int n){
		String ans="1.0";
		if(n==0) return ans;
		if(n<0){
		    for (int i = 0; i < -n ; i++) 
			  ans = divide2(ans);
		}else{
		    for (int i = 0; i < n ; i++) 
				ans = times2(ans);
		}
		
		return ans;
	}
	private String bigadd(String op1, String op2){
		op1 = op1.replace(".", ";");
		op2 = op2.replace(".", ";");
		String[] part1 = op1.split(";");
		String[] part2 = op2.split(";");
		op1 = op1.replace(";", ".");
		op2 = op2.replace(";", ".");
		int diff = part1[1].length() - part2[1].length();
		while(diff!=0){
			if(diff>0){
				diff--;
				op2=op2 +"0";
			}else{
				diff++;
				op1 = op1+"0";
			}
		}
		
		diff = part1[0].length() - part2[0].length();
		while(diff!=0){
			if(diff>0){
				diff--;
				op2="0" + op2 ;
			}else{
				diff++;
				op1 ="0"+ op1;
			}
		}
		
		char[] opchar1 = op1.toCharArray();
		char[] opchar2 = op2.toCharArray();
		char[] ans = new char[opchar1.length];
		boolean flag = false;
		
		for (int i = opchar2.length-1; i >= 0 ; i--) {
			if(opchar1[i]=='.') ans[i]='.';
			else{
			int temp = opchar1[i]-'0'+opchar2[i]-'0';
			if(flag) temp++;
			 ans[i] = (char) (temp%10+'0');
			 flag=temp/10==1?true:false;
			}			
		}
		String str = String.valueOf(ans);
		if(flag) str = "1"+str;
		while(str.charAt(str.length()-1)=='0'&&str.charAt(str.length()-2)!='.'){
			str = str.substring(0,str.length()-1);
		}
		return str;
	}
	
//*****************************************************************************************************************	
	private long UnsignedBtoH(String str){
		long ans = 0;;
		for (int i = str.length()-1; i >=0; i--) {
			if(str.charAt(i)=='1'){
				ans = (long) (ans + Math.pow(2, str.length()-1-i));
			}
		}
		return ans;		
	}
	private String minus1(String str){
		char[]  ans = new char[str.length()];
		boolean flag =true;
		for (int i = ans.length-1; i >=0; i--) {
			if(flag){
				if(str.charAt(i)=='1')  {
					ans[i] = '0'; flag = false;
				}else{
					ans[i] = '1'; flag = true;
				}
			}else{
				ans[i] = str.charAt(i);
			}
		}
		return String.valueOf(ans);
		
	}
//*****************************************************************************************************************	
	private String unsignedBooth(String operand1 ,String operand2,int length){
		length = length *2;
		while(operand1.length()<length) operand1 = "0" + operand1;
		while(operand2.length()<length) operand2 = "0" + operand2;
		String opr = operand2 + '0';
		while(opr.length()<2*length+1){
			opr = "0"+opr;
		}
		//System.out.println("opr  "+opr);
		//循环
		for (int i = 0; i < operand2.length(); i++) {
			 //判断
			int yy = boothJudgeY(opr.charAt(2*length),opr.charAt(2*length-1));//yy带表y0-y1
			 //加减
			if(yy==1){
				String temp = adder(operand1, opr.substring(0,operand1.length()), '0', operand1.length())
						.substring(1);
				opr = temp + opr.substring(operand1.length());
			}
			else if(yy==-1){
				String temp = adder(negation(operand1), opr.substring(0,operand1.length()), '1', operand1.length())
						.substring(1);				
				opr = temp + opr.substring(operand1.length());
			}
			 //System.out.println("加减"+opr);
			 opr = ariRightShift(opr, 1);
			 //System.out.println(opr);
		}
		//善后		
		return opr.substring(length,2*length+1);
		
	}

//*****************************************************************************************************************	
      public String sigDivision (String operand1, String operand2, int length) {
		//分组 补位
		while(operand1.length()!=length) operand1 = operand1.charAt(0) + operand1;//除数
		while(operand2.length()!=length) operand2 = operand2.charAt(0) + operand2;//被除数
		String register = operand1;
//		while(register.length()!=2*length) register = register.charAt(0) + register;
		while(register.length()!=2*length) register = register + "0";
		//System.out.println("最开始"+register);
		//System.out.println("divisor:  "+operand2);
		String quotient = "";
		boolean flag = false;
		String temp;
		for (int i = 0; i <= length; i++) {
			if(flag){
				temp = minusDiv(register.substring(i-1, i+length), operand2,length);
			}else{
				temp = minusDiv(register.substring(i, i+length), operand2,length);
			}
			//System.out.println("temp: "+temp);
			quotient = quotient + temp.charAt(0);
           if(temp.charAt(0)=='0')  flag=true;
           else flag =  false;
			String pre = "";
			String aft = register.substring(i+length);
			if(i!=0)  pre = register.substring(0,i);
			//System.out.println("reg:"+register);
			register = pre+temp.substring(1)+aft;
			//System.out.println("reg:"+register);
			
		}
		//第一次加	
		return quotient;
      }
      private  String minusDiv(String a,String b,int length){
    	  if(a.length()==length) a="0000"+a;
    	  else a="000"+a;
    	   b="0000"+b;
    	   //System.out.println("a:"+a);
    	   //System.out.println("b:"+b);
    	   String temp = adder(a, negation(b), '1', length+4);
    	   temp = temp.substring(1);
    	   //System.out.println("adder: "+temp.substring(beginIndex));
    	   if(Or(temp.substring(0, 4).toCharArray())=='1'){
    		   return '0' +a.substring(4);
    	   }else{
    		   return '1'+temp.substring(4);
    	   }
    	   
      }
      
}
