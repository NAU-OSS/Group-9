import java.io.BufferedReader;
import java.io.InputStreamReader;

class Comparison {

  // variables from exercise

  static char[] number;

  static int c = 0;
  static int n = 0;
  static int d = 0;

  // not sure how to implement yet
  public static boolean characteristic(char[] numString, int c){

    return true;
  }

  // not sure how to implement yet
  public static boolean mantissa(char[] numString, int n, int d){

    return true;
  }
  public static void main(String[] args) {

    try {
      // positive or negative multiplier
      int multiplier = 1;
      // start position of read string
      int starter = 0;
      // position right after first '.'
      int mantissaIndex = 0;
      // whether there is a '.'
      boolean mantissaAvailable = false;

      // temporary read string
      String str;

      do {
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
          multiplier = -1;
          starter = 1;
        }

        // check if the first position is positive character
        // start going through numbers at position 1 instead of 0 if so
        if(number[0] == '+'){
          starter = 1;
        }

        // start number at 0
        int result = 0;

        // keep track of overflow
        boolean overflowed = false;

        // go through the char array starting at either 0
        // or starting at 1 if a '+' or '-' was found
        for(int i = starter; i < number.length; i++){
          // make sure the digit is between 0 and 9 to start
          if(number[i] >= '0' && number[i] <= '9') {
            // get integer value from char
            int num = number[i] - '0';
            // add another 0 to the result
            result *= 10;
            // add that number to the result
            result += num;
            // check for overflow / underflow
            // if we know it's a negative number to start
            // and we multiply the result by it, 
            if(multiplier == 1 && result * multiplier < 0) {
              result = Integer.MAX_VALUE;
              System.out.println("OVERFLOWED CHARACTERISTIC");
              overflowed = true;
              break;
            }
            if(multiplier == -1 && result * multiplier > 0) {
              result = Integer.MIN_VALUE;
              overflowed = true;
              System.out.println("UNDERFLOWED CHARACTERISTIC");
              break;
            }
            // check if it's not 0 - 9, only other character allowed is a . to start the mantissa
          } else if (number[i] == '.') {
            // if this is the last number, just return because we're missing the decimal here
            if(number.length - 1 == i){
              NaN();
              return;
              
            }
            // if we found a '.' set the character variable to the final result, but multiply by the multiple for negative or positive
            c = result * multiplier;
            // set mantissa vailable as true
            mantissaAvailable = true;
            // set the starting spot as right after the mantissaa
            mantissaIndex = i + 1;
            break; 
          } else {
            //neither a number or ., return not a number
            NaN();
            return;
          }

          // if there was no '.', just set the final characteristic variable to the result * multiplier
          c = result * multiplier;
        }
                  
        if(overflowed == true){
          c = result;
        }
        // set temp numerator and denominators
        // denominator starts at 1, since we're going to just multiply it by 10 for every index to get the bottom half of the fraction
        int tempNum = 0;
        int tempDen = 1;

        //if we found a . from earlier
        if(mantissaAvailable){
          // keep track of how many you have, only 9 decimals allowed with integers
          int track = 0;
          //let's go through the rest of the char array starting after the .
          for(int i = mantissaIndex; i < number.length; i++){
            track++;
            if(track > 9){
              System.out.println("Too man numbers after the decimal");
              NaN();
              return;
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
              NaN();
              break;
            }
            // set the denominator based on the digit
            tempDen *= 10;
          }
          // after all is said and done set the global numerator and denominator
          n = tempNum;
          d = tempDen;
        }

        // inspect the values
        debug();

        // not sure how to implement this part w/ java yet
        if(characteristic(number, c) && mantissa(number, n, d))
        {
            //do some math with c, n, and d;
            // System.out.println("Numerator cast as double: " + (double)n);
            // System.out.println("Denominator cast as double: " + (double)d);
            // System.out.println((double)n / (double)d);
            return;
        } else {
          return;
        }
        // stop waiting for another number if they type "stop"
      } while(!str.equals("stop"));
      
    } catch (Exception e) {
      System.out.println("Error: " + e);
    }
  }


  private static void debug() {
    System.out.println();
    System.out.println("--------------------------------------------");
    System.out.println("String input: " + new String(number));
    System.out.println("Characteristic: " + c);
    System.out.println("Numerator: " + n);
    System.out.println("Denominator: " + d);
    System.out.println("--------------------------------------------");
    System.out.println();
  }
  private static void NaN(){
    System.out.println("Not a number");
    
    return;
  }

}