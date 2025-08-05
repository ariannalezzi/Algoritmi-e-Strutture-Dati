#include "sort.h"
#include <string.h>
#include <stdlib.h>
#include <stdio.h>
#include <errno.h>
#include <time.h>
#define PROGRAM "sort_main"
#define INITIAL_CAPACITY 1024
#define EXIT usage(); exit(EXIT_FAILURE);

//Struct that represents a line of the csv file.
typedef struct _Line{
	int id;
	char *field1;
	int field2;
	double field3;
}Line;

Line **array;					//array where put the elements that have to be sorted
int size = INITIAL_CAPACITY;	//initial capacity of the array

//It frees evry byte of memory allocated during the execution
void free_memory(){				
	int j = 0;
	while(j<size){
		free(array[j]->field1);
		free(array[j]);
		j++;
	}
	free(array);
}


//Method to compare two lines with field1
int field1_compare(void *elem_1, void *elem_2){
	Line *l1 = (Line *)elem_1;
	Line *l2 = (Line *)elem_2;
	char *string_1 = l1->field1;
	char *string_2 = l2->field1;
	while(*string_1 != '\0' && *string_2 != '\0' && *string_1 == *string_2){
		string_1++;
		string_2++;
	}
	return *string_1 - *string_2;
}


//Method to compare two lines with field2
int field2_compare(void *elem_1, void *elem_2){
	Line *l1 = (Line *)elem_1;
	Line *l2 = (Line *)elem_2;
	return l1->field2 - l2->field2;
}

//Method to compare two lines with field3
int field3_compare(void *elem_1, void *elem_2){
	Line *l1 = (Line *)elem_1;
	Line *l2 = (Line *)elem_2;
	if(l1->field3 < l2->field3)return -1;
	else if(l1->field3 > l2->field3)return 1;
	else return 0;
}

//It prints the usage
void usage(){
	fprintf(stderr, "\nUSAGE\t: \t%s \n", PROGRAM);
	fprintf(stderr, "First argument is the name of the input file\n");
	fprintf(stderr, "Second argument in an integer which indicates the alghoritm to use:\n0-Bynary insertion sort\n1-Quick Sort\n2-Randomized quick sort\n");
	fprintf(stderr, "Third argument is an integer who indicates which field (1 2 or 3) has to be sorted\n");
	fprintf(stderr, "Fourth arguement indicates the name of the output file\n");
	fprintf(stderr, "Example : sort_main records.csv 0 2 output_field2.csv\n");
	exit(1);
}


//It write on the array every line of the csv file pointed by fp and return the number of element written
int write_on_array(FILE *fp){
	char *line = NULL;
	size_t len = 0;
	int i = 0;
	while(getline(&line, &len, fp) != -1){
		Line *record = (Line *) malloc(sizeof(Line));
		if(i==size){
			size = size*2;
			array = (Line **) realloc(array, (unsigned int) size*sizeof(Line *));
			if(array == NULL){
				printf("Error %d\n", errno);
				printf("1 Unable to reallocate memory for the array\n");
				exit(EXIT_FAILURE);
			}
		}
		record->id = atoi(strtok(line, ","));
		char *string = strtok(NULL, ",");
		record->field1 = (char *) malloc(sizeof(char)*(1+strlen(string)));
		if(record->field1 == NULL){
			printf("Unable to allocate memory for field1\n");
			printf("%d\n",errno);
			exit(EXIT_FAILURE);
		}
		strcpy(record->field1, string);
		record->field2 = atoi(strtok(NULL, ","));
		record->field3 = atof(strtok(NULL, "\n"));
		array[i] = record;
		i++;	
	}
	free(line);
	return i;
}

//It set the size of the array to the correct value after the insertion of all the elements in the array
void resize_array(int actual_size){
	if(actual_size==0){
		free(array);
		array=NULL;
		size=0;
	}else if(size>actual_size){
		size = actual_size;
		array = (Line **) realloc(array,(unsigned int) size*sizeof(Line *));
		if(array == NULL){
			printf("2 Unable to reallocate memory for the array\n");
			exit(EXIT_FAILURE);
		}
	}
}

//It open the csv file that has to be oredered and it calls the method to write on the array and the one to set the right capacity
void read_record(const char *file_name){
	FILE *fp;	
	array = (Line **) calloc((size_t)size, sizeof(Line *));
	if(array == NULL){
		printf("Unable to allocate memory for the array\n");
		exit(EXIT_FAILURE);
	}
	
	fp = fopen(file_name, "r");
	if(fp == NULL){
		printf("Unable to open input file\n");
		printf("Error: %d\n", errno);
		exit(EXIT_FAILURE);
	}

	int actual_size = write_on_array(fp);
	fclose(fp);
	resize_array(actual_size);
}


//It writes on the output file the elements of the ordered array
void write_ordered_array(const char *output_file){
	FILE *fp = fopen(output_file, "w");
	
	if(fp == NULL){
		printf("Unable to create the output file\n");
		exit(EXIT_FAILURE);
	}

	int i=0;
	while(i<size){
		fprintf(fp,"%d,%s,%d,%f\n", array[i]->id, array[i]->field1, array[i]->field2, array[i]->field3);
		i++;
	}
	fclose(fp);
}

//It calls the binary insertion sort method with the right compare method according to the user request
void bin_sort(char const* argv[]){
	if(atoi(argv[3]) == 1){
		binary_insertion_sort((void**)array,size, field1_compare);
	}else if(atoi(argv[3]) == 2){
		binary_insertion_sort((void **)array,size, field2_compare);
	}else if(atoi(argv[3]) == 3){
		binary_insertion_sort((void **)array,size,field3_compare);
	}else{
		printf("Wrong field input\n");
		EXIT
	}
	
}

//It calls the quick sort method with the right compare method according to the user request
void q_sort(char const* argv[]){
	if(atoi(argv[3]) == 1){
		quick_sort((void **)array,size, field1_compare);
	}else if(atoi(argv[3]) == 2){
		quick_sort((void **)array,size, field2_compare);
	}else if(atoi(argv[3]) == 3){
		quick_sort((void **)array,size,field3_compare);
	}else{
		printf("Wrong field input\n");
		EXIT
	}
}

//It calls the randomized quick sort method with the right compare method according to the user request
void r_q_sort(char const* argv[]){
	if(atoi(argv[3]) == 1){
		randomized_quick_sort((void **)array,size, field1_compare);
	}else if(atoi(argv[3]) == 2){
		randomized_quick_sort((void **)array,size,field2_compare);
	}else if(atoi(argv[3]) == 3){
		randomized_quick_sort((void **)array,size,field3_compare);
	}else{
		printf("Wrong field input\n");
		EXIT
	}
}

//It determines the right alghoritm to use according to user request
void choose_sort(char const *argv[]){
	if(atoi(argv[2]) == 0){
		bin_sort(argv);		
	}else if(atoi(argv[2]) == 1){
		q_sort(argv);
	}else if(atoi(argv[2]) == 2){
		r_q_sort(argv);
	}else{
		printf("Wrong method input\n");
		EXIT
	}	
}

//It calls methods to initialize the array, order it and write the result and it measures the time taken to sort the array
int main(int argc, char const *argv[]){
	if(argc < 5){
		usage();
		exit(EXIT_FAILURE);
	}
	read_record(argv[1]);	
	
	clock_t begin = clock();
	
	choose_sort(argv);
	
	clock_t end = clock();	
	
	double time_sec = (double)(end-begin)/ (double)(CLOCKS_PER_SEC);
	
	write_ordered_array(argv[4]);	
	free_memory();	
	printf("sorted in %.2lf seconds\n", time_sec);	
	exit(EXIT_SUCCESS);
}
