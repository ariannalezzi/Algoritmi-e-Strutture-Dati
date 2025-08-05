#include "skip_list.h"
#include <stdlib.h>
#include <time.h>
#include <stdio.h>
#include <errno.h>

//It creates an empty SkipList with a node used as starting point(it does not contain any item)
SkipList *create_skip_list(int (*precedes) (void*, void*)){
	srand((unsigned int) time(NULL));
	
	SkipList *list = (SkipList *)malloc(sizeof(SkipList));
	if(list == NULL){
				printf("Error %d\n", errno);
				printf("1 Unable to allocate memory for the SkipList\n");
				exit(EXIT_FAILURE);
			}
	Node *x = (Node *)malloc(sizeof(Node));
	if(x == NULL){
				printf("Error %d\n", errno);
				printf("1 Unable to allocate memory for the node\n");
				exit(EXIT_FAILURE);
			}
	x->next = (Node **) calloc(MAX_HEIGHT, sizeof(Node *));
	if(x->next == NULL){
				printf("Error %d\n", errno);
				printf("1 Unable to allocate memory for the node's array\n");
				exit(EXIT_FAILURE);
			}
	for(int i = 0; i<MAX_HEIGHT; i++){
		x->next[i] = NULL;
	}
	x->item = NULL;
	list->head = x;
	list->max_level = 1;
	list->compare = precedes;
	return list;
}

//It implements the alghoritm to search an element in the SkipList starting from the maximum level of the List
int search_skip_list(SkipList *list, void *I){
	Node *x = list->head;
	for(int i = (int) list->max_level-1; i>-1; i--){
		while(x->next[i] != NULL && list->compare(x->next[i]->item, I) < 0){
			x = x->next[i];
		}
	}
	x = x->next[0];
	if(x != NULL){ 
		return list->compare(x->item, I);
	}else return -1;
}

//It implements the alghoritm to insert an element in the SkipList
void insert_skip_list(SkipList *list, void *I){
	Node *new = create_node(I, random_level());
	if(new->size > list->max_level) list->max_level = new->size;
	Node *x = list->head;
	for(int k = (int) list->max_level-1; k>-1; k--){
		if(x->next[k] == NULL || list->compare(I, x->next[k]->item)<0){
			if(k < (int) new->size){
				new->next[k] = x->next[k];
				x->next[k] = new;
			}
		}else{
			x = x->next[k];
			k++;
		}
	}
}

//It creates a new Node of lvl number of levels that will contain item I. It will be inserted in the SkipList
Node *create_node(void *I, unsigned int lvl){
	Node *node = (Node*) malloc (sizeof(Node));
	if(node == NULL){
				printf("Error %d\n", errno);
				printf("1 Unable to allocate memory for the node\n");
				exit(EXIT_FAILURE);
			}
	node->next = (Node **) calloc(lvl, sizeof(Node *));
	if(node->next == NULL){
				printf("Error %d\n", errno);
				printf("1 Unable to allocate memory for the node's array\n");
				exit(EXIT_FAILURE);
			}
	node->size = lvl;
	node->item = I;
	return node;
}

//It calculates the number of levels for a node from 1 to MAX_HEIGHT
unsigned int random_level(){
	unsigned int lvl = 1;
	
	while(((double)(rand()) / (double) (RAND_MAX)) < 0.5 && lvl < MAX_HEIGHT){
		lvl++;
	}
	return lvl;
}

//It frees all the bytes allocated during the use of the skiplist (also the bytes of the items if they were dinamically allocated)
void free_skip_list(SkipList *list, int i){
	Node *x = list->head;
	while(x->next[0] != NULL){
		Node *tmp = x->next[0]; 
		free(x->next);
		free(x);
		x = tmp;
		if(i==0)free(x->item);
	}
	free(x->next);
	free(x);
	free(list);
}
