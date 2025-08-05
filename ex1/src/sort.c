#include "sort.h"
#include <unistd.h>
#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include <time.h>

void swap(void **array, int i, int j){	//It swaps A[i] with A[j] in the given array
	void *tmp = array[i];
	array[i] = array[j];
	array[j] = tmp;
}


//Method that orders the given array with Binary Insertion Sort alghoritm
void binary_insertion_sort(void **array, int size, int (*compare)(void *, void *)){  
	if(array !=NULL){
		for(int j=1; j<size; j++){
			void *key = array[j];
			int index = sorted_index(array, 0, j-1, key, compare);
			int x = j;
			while(x>index){
				array[x] = array[x-1];
				x--;
			}
			array[index] = key;
		}
	}
}


//It returns the correct index in the already sorted array for the key element using binary search
int sorted_index(void **array, int left, int right, void *key, int (*compare)(void *, void *)){
	while(left<=right){
		int med = (left + right)/2;
		if(compare(key, array[med])==0)return med+1;
		else if (compare(key, array[med])>0) left = med+1;
		else right = med-1;
	}
	return left;
}

//If the array is not null it calls the aux method to order the array with quick sort alghoritm
void quick_sort(void **array,int size, int (*compare)(void *, void *)){
	if(array!=NULL){
		quick_sort_aux(array, 0, size-1, compare);
	}
}

//Method that orders the given array with Quick Sort alghoritm
void quick_sort_aux(void **array, int left, int right, int (*compare)(void *, void *)){
	if(left<right){
		int lt;
		int gt;
		partition(array, left, right, compare, &lt, &gt);
		quick_sort_aux(array, left, lt-1, compare);
		quick_sort_aux(array, gt+1, right, compare);
	}
}

//3-way partitioning. At the end the element lower than pivot are from left to lt in the array,
//the equals elements are from lt to gt and the greater are from gt to right
void partition(void **array, int left, int right, int (*compare)(void *, void *), int* lt, int* gt){
	void *pivot = array[left];
	*lt = left;
	*gt = right;
	int index = left+1;
	while(index <= *gt){
		if(compare(array[index],pivot)<0){
			swap(array, index, *lt);
			(*lt)++;
			index++;
		}else if(compare(array[index],pivot)>0){
			swap(array, index, *gt);
			(*gt)--;
		}else{
			index++;
		}
	}
}


//If the array is not null it calls the aux method to order the array with randomized quick sort alghoritm
void randomized_quick_sort(void **array,int size, int (*compare)(void *,void *)){
	if(array!=NULL){
		srand((unsigned int) time(NULL));
		randomized_quick_sort_aux(array, 0, size-1, compare);
	}
}

//Method that orders the given array with Randomized Quick Sort alghoritm
void randomized_quick_sort_aux(void **array, int left, int right, int (*compare)(void *,void *)){
	if(left<right){
		int lt;
		int gt;
		randomized_partition(array, left, right, compare, &lt, &gt);
		randomized_quick_sort_aux(array, left, lt-1, compare);
		randomized_quick_sort_aux(array, gt+1, right, compare);
	}
}

//It select a random element as pivot for the partition and then it calls the partition method.
void randomized_partition(void **array, int left, int right,  int (*compare)(void *, void *), int *lt, int *gt){
	int pivot = rand()%(right-left);
	pivot = pivot + left;
	swap(array, left, pivot);
	partition(array, left, right, compare, lt, gt);
}