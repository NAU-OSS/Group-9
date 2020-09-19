import java.io.BufferedReader;
import java.io.InputStreamReader;

class Comparison {
  // variables from exercise
  static char[] number;

  private int c;
  private int n;
  private int d;	  
  
  // positive or negative multiplier
  private int multiplier;
  // start position of read string
  private int starter;
  // position right after first '.'
  private int mantissaIndex;
  // initialize result to 0
  private int result;
  // keep track of overflow
  private boolean overflowed;
  // whether there is a '.'
  private boolean mantissaAvailable;
	  
  public Comparison() {
    // Initialize variables
	c = 0;
	n = 0;
	d = 0;
	multiplier = 1;
	starter = 0;
	mantissaIndex = 0;
	result = 0;
	overflowed = false;
	mantissaAvailable = false;
  }

  public static boolean characteristic(char[] numString, Comparison compData){ 
   // go through the char array starting at either 0
   // or starting at 1 if a '+' or '-' was found
   for(int i = compData.starter; i < number.length; i++){
     // make sure the digit is between 0 and 9 to start
     if(number[i] >= '0' && number[i] <= '9') {
       // get integer value from char
       int num = number[i] - '0';
       // add another 0 to the result
       compData.result *= 10;
       // add that number to the result
       compData.result += num;
       // check for overflow / underflow
       // if we know it's a negative number to start
       // and we multiply the result by it, 
       if(compData.multiplier == 1 && compData.result * compData.multiplier < 0) {
         compData.result = Integer.MAX_VALUE;
         System.out.println("OVERFLOWED CHARACTERISTIC");
         compData.overflowed = true;
         break;
       }
       if(compData.multiplier == -1 && compData.result * compData.multiplier > 0) {
    	 compData.result = Integer.MIN_VALUE;
    	 compData.overflowed = true;
         System.out.println("UNDERFLOWED CHARACTERISTIC");
         break;
       }
       // check if it's not 0 - 9, only other character allowed is a . to start the mantissa
     } else if (number[i] == '.') {
       // if this is the last number, just return because we're missing the decimal here
       if(number.length - 1 == i){
    	 System.out.println("Not a number");
         return false; // test for this case, we may have to change the logic in this statement
         
       }
       // if we found a '.' set the character variable to the final result, but multiply by the multiple for negative or positive
       compData.c = compData.result * compData.multiplier;
       // set mantissa variable as true
       compData.mantissaAvailable = true;
       // set the starting spot as right after the mantissa
       compData.mantissaIndex = i + 1;
       break; 
     } else {
       //neither a number or ., return not a number
       System.out.println("Not a number");
       return false;
     }
     
     // if there was no '.', just set the final characteristic variable to the result * multiplier
     compData.c = compData.result * compData.multiplier;
   }
   
   if(compData.overflowed == true){
	   compData.c = compData.result;
   }
   
   return true;
  }

  public static boolean mantissa(char[] numString, Comparison compData){
   // set temp numerator and denominators
   // denominator starts at 1, since we're going to just multiply it by 10 for every index to get the bottom half of the fraction
   int tempNum = 0;
   int tempDen = 1;

   //if we found a . from earlier
   if(compData.mantissaAvailable){
     // keep track of how many you have, only 9 decimals allowed with integers
     int track = 0;
     //let's go through the rest of the char array starting after the .
     for(int i = compData.mantissaIndex; i < number.length; i++){
       track++;
       if(track > 9){
         System.out.println("Too many numbers after the decimal\n");
         System.out.println("Not a number");
         return false;
       }
       // make sure the item is a number
       if(number[i] >= '0' && number[i] <= '9') {
         // set the character to an int
         int num = number[i] - '0';

         // set the power
         tempNum *= 10;
         // add the number to the total
         tempNum += num;
       } else {
         // cannot allow anything other than a number after the decimal
    	 System.out.println("Not a number");
         return false;
       }
       // set the denominator based on the digit
       tempDen *= 10;
     }
     // after all is said and done set the global numerator and denominator
     compData.n = tempNum;
     compData.d = tempDen;
   }
	  
   return true;
  }
  
  public static void main(String[] args) {
	  
	Comparison compData;

    try {

      // temporary read string
      String str;

      do {
    	
    	// create a new comparison object
    	// clear previous data if another number is entered
    	compData = new Comparison();
    	
        BufferedReader obj = new BufferedReader(new InputStreamReader(System.in));   
        
        // start reading numbers
        System.out.println("Enter a number.");
        System.out.println();
        System.out.println("Format: [+/-]X[.Y]");
        System.out.println("[] = Optional");
        System.out.println("Lowest X Value: -2147483648");
        System.out.println("Highest X Value: 2147483647");
        System.out.println("(X will turn into either if these if they are over / under)");
        System.out.println("Highest Y Value: 999999999");
        System.out.println("Lowest Y Value: 0");
        System.out.println("(Program will fail if Y does not meet this requirement)");
        System.out.println();   
        System.out.println("Enter 'stop' to exit.");   
        str = obj.readLine();

        // set number to the string as a char array, trimming spaces before and after
        number = str.trim().toCharArray();

        // check if the first position is negative
        // set final multiplier to -1 as opposed to starting positive at 1
        // start going through numbers at position 1 instead of 0 if so
        if(number[0] == '-') {
          compData.multiplier = -1;
          compData.starter = 1;
        }

        // check if the first position is positive character
        // start going through numbers at position 1 instead of 0 if so
        if(number[0] == '+'){
          compData.starter = 1;
        }
        
        // check if valid input was sent to the characteristic and mantissa functions
        if(characteristic(number, compData) && mantissa(number, compData))
        {   
          // inspect the values
          debug(compData);
        } else {
          // display the error message and terminate the program
          System.out.println("Error: Invalid input!");
          return;
        }
        
        // stop waiting for another number if they type "stop"
      } while(!str.equals("stop"));
      
    } catch (Exception e) {
      System.out.println("Error: " + e);
    }
  }

  private static void debug(Comparison compData) {
    System.out.println();
    System.out.println("--------------------------------------------");
    System.out.println("String input: " + new String(number));
    System.out.println("Characteristic: " + compData.c);
    System.out.println("Numerator: " + compData.n);
    System.out.println("Denominator: " + compData.d);
    System.out.println("--------------------------------------------");
    System.out.println();
  }

}