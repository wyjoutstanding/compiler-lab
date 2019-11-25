#include<stdio.h>
int main(){
    int n=0;
    int flag=0;
    printf("Please enter a number\n");
    scanf("%d",&n);
    switch(n){
        case 1:
           flag=0;
           break;
        default:
            flag=1;
    }
    printf("%d\n",flag);
    return 0;
}
