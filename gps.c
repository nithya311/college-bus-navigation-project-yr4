#include <pic.h>
#include"delay.c"
#include"adc_driver.c"
#include "uart_driver.c"
#include "lcd_driver.c"

__CONFIG(0x1932);

#define GPS_BAUD_DEFAULT	9600

/*------------------------------------------*/

void gps_read(void);
void gps_init(void);
void display_number(long);

/*------------------------------------------*/

static char NMEA_DIVIDER = ',';

bank1 unsigned char utcTime[16];
bank1 unsigned char bufLatitude[16];
bank1 unsigned char bufLongitude[16];
bank1 long latitude;
bank1 long longitude;

/*------------------------------------------*/

void main()
{
	setup_lcd_port();	
	lcd_init();

TRISB = 0xff;
PORTB = 0x00;
TRISC=0x00;
PORTC=0x00;

 unsigned int i;
 unsigned int j;
	
	gps_init();
	
	
lcd_goto_pos(1);
 lcd_puts("Bus Tracking");
 lcd_goto_pos(17);
 lcd_puts("Using GPS");
 for(i=0;i<2;i++)
 DelayMs(250);
	lcd_clrscr();
	
	while(1)
	{
		gps_read();
	//	lcd_goto(21);
	//	lcd_put_array(utcTime);
		lcd_goto_pos(1);
		display_number(latitude);
		lcd_goto_pos(17);
		display_number(longitude);
	}
}


void convertCordinateToDegree(unsigned char *pBuf, long * pDegree, char len) {
	long index = 0;
	long sum = 0;
	long deg = 0;
	long min = 0;
	long div = 0;
	long pow = 1;

	 for (index = len; index >=0; index--) {
		if (pBuf[index] == '.') {
			div = 1;
			continue;
		}
		sum += pow * (pBuf[index] & 0x0F);

		if (index > 0) {
			pow *= 10;
			div *= 10;
		}
	}

	div = pow / div;
	deg = sum / (div*100);
	min = sum - (deg*div*100);
	// convert to decimal minutes
	min = (min * 100) / 60;
	*pDegree = (deg*div*100) + min;

	if (div > 10000) {
		 // normalize minutes to 6 decimal places
		*pDegree /= (div / 10000);
	}
}
/*------------------------------------------*/

void gps_read(void) {
	char header[] = "$GPGGA,";
	char val;
	unsigned char i = 0;

	/* match header */
	while (1) {
		val =uart_getc();
		if (val == header[i]) {
			i++;
			if (header[i] == '\0') {
				break;
			}
		} else {
			i = 0;
		}
	}


	/* read utc time field */
	i = 0;
	while ((val =uart_getc()) != '.') {
		utcTime[i++] = val;
		if (i == 2 || i == 5) {
			utcTime[i++] = ':';
		}
	}
	utcTime[i] = '\0';


	/* skip rest of the utc time field */
	while ((val =uart_getc()) != NMEA_DIVIDER)
		;


	/* read latitude field */
	i = 0;
	while ((val =uart_getc()) != NMEA_DIVIDER) {
		bufLatitude[i++] = val;
	}
	bufLatitude[i] = '\0';



	/* skip N/S indicator field */
	while ((val =uart_getc()) != NMEA_DIVIDER)
		;


	/* read longitude field */
	i = 0;
	while ((val =uart_getc()) != NMEA_DIVIDER) {
		bufLongitude[i++] = val;
	}
	bufLongitude[i] = '\0';



	/* skip rest of the frame....*/



	/* find latitude, longitude */
	convertCordinateToDegree((unsigned char *) &bufLatitude, &latitude, 8);
	convertCordinateToDegree((unsigned char *) &bufLongitude, &longitude, 9);

}
/*--------------------------------------------*/

void gps_init(void) {
	uart_init(GPS_BAUD_DEFAULT,16);		// gps default baud = 9600
}
/*--------------------------------------------*/

void display_number(long val)
{
	long num[]={1000000000,100000000,10000000,1000000,100000,10000,1000,100,10,1};
	char pos,cnt;
	for(pos = 0; pos < 10; pos++)
	{
		cnt = 0;
		while(val >= num[pos])
		{
			cnt++;
			val-=num[pos];
		}
		lcd_write(cnt+48,DATA_REG);
  		DelayMs(1);
 	} 		
}  		