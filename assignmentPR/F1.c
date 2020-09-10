#include <stdlib.h>
#include <stdio.h>
#include <math.h>

// Function prototypes
bool characteristic(char numString[], int& c);
bool mantissa(char numString[], int& numerator, int& denominator);

int main(int argc, char *argv[])
{
  char number[] = "123.456";
  int c, n, d;

  //if the conversion from C string to integers can take place
  if(characteristic(number, c) && mantissa(number, n, d))
  {
      //do some math with c, n, and d
  }
  else
  {
      //handle the error on input
  }
}

bool characteristic(char numString[], int& c)
{
 // Check for leading whitespace
 
}

bool mantissa(char numString[], int& numerator, int& denominator)
{
 // Check for leading whitespace
 
}
