#include <max7219.h>

MAX7219 max7219;

String incomingBytes;
char blk = '\0';
char rpm[5] = {blk,blk,blk,blk,blk};
short pos = -1;
const char MY_END = 'x';

void setup() {
  Serial.begin(9600);
  max7219.Begin();
  max7219.DisplayChar(0,'H',false);
  max7219.DisplayChar(1,'E',false);
  max7219.DisplayChar(2,'L',false);
  max7219.DisplayChar(3,'L',false);
  max7219.DisplayChar(4,'O',false);
}

void waitAndGet() {
  if( Serial.available() > 0 ) {

    char temp = Serial.read();

    if(pos == -1) {
      if(temp == MY_END) {
        pos = 0;
        Serial.println("Starting!");
      } else {
        Serial.print("Waiting still, got \"");
        Serial.print(temp);
        Serial.println("\"");
      }
    } else if( temp == MY_END ) {
      max7219.DisplayChar(0,rpm[0],false);
      max7219.DisplayChar(1,rpm[1],false);
      max7219.DisplayChar(2,rpm[2],false);
      max7219.DisplayChar(3,rpm[3],false);
      max7219.DisplayChar(4,rpm[4],false);
      pos = 0;
    } else {
      rpm[pos] = temp;
      pos = pos + 1;
    }
  }
}

void loop() {


  waitAndGet();
//  if( Serial.available() > 0 ) {
//    char myChar = Serial.read();
//    Serial.print("GOT: ");
//    Serial.println(myChar);
//  }
    

}
