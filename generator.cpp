#include <time.h>
#include <fstream>
#include <string>
#include <iostream>
using namespace std;

int main (){
	time_t theTime = time(NULL);
	struct tm *aTime = localtime(&theTime);		

	int hour=aTime->tm_hour;
	int min=aTime->tm_min;
   	int sec=aTime->tm_sec;
   	
	string line;
	ifstream fe("DATA.txt");
	
	ofstream fo("news.txt");
	int cont=30;
	
	while(getline(fe, line)){
		if(line.compare("$") == 0){
		fo<<"$";fo<<hour;fo<<":";fo<<min;fo<<":";fo<<sec+cont;fo<<"\n";
		cont++;
		}
		else{
			fo<<line;
			fo<<"\n";
		}
	}
return 0;
}
