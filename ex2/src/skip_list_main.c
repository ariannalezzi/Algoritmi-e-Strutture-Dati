#include "skip_list.h"
#include <string.h>
#include <stdlib.h>
#include <stdio.h>
#include <errno.h>
#include <time.h>
#include <ctype.h>
#define PROGRAM "ex2_main"

//Method to compare items of the SkipList
int compare(void *a, void *b){
	char *string1 = (char *)a;
	char *string2 = (char *)b;
	return strcmp(string1, string2);
}

//It prints the usage
void usage(){
	fprintf(stderr, "\nUSAGE\t: \t%s \n", PROGRAM);
	fprintf(stderr, "First argument is the name of the dictionary\n");
	fprintf(stderr, "Second argument is the name of the file to correct\n");
	fprintf(stderr, "Example : ex2_main dictionary.txt correctme.txt\n");	
	exit(1);
}

//It reads from the input file the item to insert in the skiplist
void upload_dictionary(SkipList *list, const char *dictionary){
	FILE *fp;
	char *line = NULL;
	size_t len = 0;
	
	fp = fopen(dictionary, "r");
	if(fp == NULL){
		printf("Unable to open dictionary\n");
		printf("Error: %d\n", errno);
		exit(EXIT_FAILURE);
	}
	
	while(getline(&line, &len, fp) != -1){
		char *token = strtok(line, " \n");
		char *string = (char *)malloc ((strlen(token)+1)*sizeof(char *));
		if(string == NULL){
				printf("Error %d\n", errno);
				printf("1 Unable to allocate memory for the string\n");
				exit(EXIT_FAILURE);
			}
		strcpy(string, line);
		insert_skip_list(list, string);
	}
	fclose(fp);
	free(line);
}

//It converts strings into lower case
char * lower_case(char *s){
	for(int i = 0; i< (int) strlen(s); i++)
		s[i] = (char)tolower(s[i]);
	return s;
}

//It read the elements from the file to correct and search them in the skiplist. If the search fails, the words are written on stdout
void check(SkipList *list, const char *correct_me){
	FILE *fp;
	char *line = NULL;
	size_t len = 0;
	
	fp = fopen(correct_me, "r");
	if(fp == NULL){
		printf("Unable to open file to correct\n");
		printf("Error: %d\n", errno);
		exit(EXIT_FAILURE);
	}
	
	while(getline(&line, &len, fp) != -1){
		char *string = strtok(line, " ,.;:!?");
		while(string != NULL){
			if(search_skip_list(list, lower_case(string)) != 0){
				printf("%s\n", string);
			}
			string = strtok(NULL, " ,.;:!?");
		}
	}
	fclose(fp);
	free(line);
}

//It create the skiplist that will be used in the program, calls the method to insert the dictionary on the list and the one to 
//correct the file. It takes the time of insertion and search and, at the end, it frees the skiplist and print the time calculated
int main(int argc, char *argv[]){
	if(argc < 3){
		usage();
		exit(EXIT_FAILURE);
	}
	
	SkipList * list = create_skip_list(compare);	
	
	clock_t begin = clock();
	
	upload_dictionary(list, argv[1]);
	
	clock_t end = clock();
	
	double time_sec_1 = (double)(end-begin)/ (double)(CLOCKS_PER_SEC);
	
	begin = clock();
	
	check(list, argv[2]);
	
	end = clock();
	
	double time_sec_2 = (double)(end-begin)/ (double)(CLOCKS_PER_SEC);
	
	free_skip_list(list, 0);
	
	printf("Value of MAX_HEIGHT %d\n", MAX_HEIGHT);
	printf("Insertion in %.2lf seconds\n", time_sec_1);
	printf("Search in %.2lf seconds\n", time_sec_2);
}
