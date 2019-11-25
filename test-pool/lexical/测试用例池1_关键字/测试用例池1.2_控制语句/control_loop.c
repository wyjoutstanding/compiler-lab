#include<stdio.h>
int main(){
    int i=0;
    for(i=0;i<3;i++){
        if(i==2)continue;
        printf("%d\n",i);
    }
    i=0;
    do{
        i++;
        if(i==2)break;
    }while(i<3);
    return 0;
}