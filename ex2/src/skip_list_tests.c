#include <stdio.h>
#include <stdlib.h>
#include "../unity/unity.h"
#include "skip_list.h"
#include <string.h>

//Method to compare integer items of the skiplist
static int int_compare (void *elem_1, void *elem_2){
	int *int_1 = (int *)elem_1;
	int *int_2 = (int *)elem_2;
	return *int_1 - *int_2;
}

//Method to compare string items of the skiplist
static int string_compare (void *elem_1, void *elem_2){
	if(elem_1 == NULL && elem_2 != NULL)return 1;
	if(elem_1 != NULL && elem_2 == NULL)return -1;
	if(elem_1 == NULL && elem_2 == NULL)return 0;
	char *string_1 = (char *)elem_1;
	char *string_2 = (char *)elem_2;
	return strcmp(string_1 ,string_2);
}

//It checks the right behaviour of the create and insert methods with integers
static void insert_int_skip_list(){
	int a = 3;
	SkipList *list = create_skip_list(int_compare);
	insert_skip_list(list, &a);
	TEST_ASSERT_EQUAL_PTR(&a, (int *)(list->head->next[0]->item));
	free_skip_list(list, -1);
}

//It checks the right behaviour of the search method with integers
static void search_int_skip_list(){
	int a = 3;
	int b = 3;
	SkipList *list = create_skip_list(int_compare);
	insert_skip_list(list, &a);
	TEST_ASSERT_EQUAL_INT(0, search_skip_list(list, &b));
	free_skip_list(list, -1);
}

//It checks the right behaviour of the create and insert method with strings
static void insert_string_skip_list(){
	char *a = "Ciao";
	SkipList *list = create_skip_list(string_compare);
	insert_skip_list(list, a);
	TEST_ASSERT_EQUAL_STRING(a, (char *)(list->head->next[0]->item));
	free_skip_list(list, -1);
}

//It checks the right behaviour of the search method with strings
static void search_string_skip_list(){
	char *a = "Ciao";
	char *b = "Ciao";
	char *c = "Aiuto";
	SkipList *list = create_skip_list(string_compare);
	insert_skip_list(list, a);
	insert_skip_list(list, c);	
	TEST_ASSERT_EQUAL_INT(0, search_skip_list(list, b));
	free_skip_list(list, -1);
}

//It checks the right behaviour of the create, insert and search method with null items
static void insert_null_skip_list(){
	char *a = NULL;
	SkipList *list = create_skip_list(string_compare);
	insert_skip_list(list, a);	
	TEST_ASSERT_EQUAL_INT(0, search_skip_list(list, NULL));
	free_skip_list(list, -1);
}

//Main that runs the tests
int main(){
	UNITY_BEGIN();
	RUN_TEST(insert_int_skip_list);
	RUN_TEST(search_int_skip_list);
	RUN_TEST(insert_string_skip_list);
	RUN_TEST(search_string_skip_list);
	RUN_TEST(insert_null_skip_list);
	return UNITY_END();
}