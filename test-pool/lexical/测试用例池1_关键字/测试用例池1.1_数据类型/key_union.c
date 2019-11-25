#include<stdio.h>
int main(){
    union Test{
        int val=0;
        char c='$';
    }
    return 0;
}