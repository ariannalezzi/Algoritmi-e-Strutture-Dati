#include <stdio.h>
#include <stdlib.h>
#include "../unity/unity.h"
#include "sort.h"


//Method to compare elements used in the tests
static int compare (void *elem_1, void *elem_2){
	int *int_1 = (int *)elem_1;
	int *int_2 = (int *)elem_2;
	return *int_1 - *int_2;
}

//It checks the right behaviour of the binary insertion sort with a null array
static void binary_insertion_sort_null_array(){
	void **array = NULL;
	int size=0;
	binary_insertion_sort(array, size, compare);
	TEST_ASSERT_TRUE(array==NULL);
}

//It checks the right behaviour of the quick sort with a null array
static void quick_sort_null_array(){
	void **array = NULL;
	int size=0;
	quick_sort(array, size, compare);
	TEST_ASSERT_TRUE(array==NULL);
}

//It checks the right behaviour of the randomized quick sort with a null array
static void randomized_quick_sort_null_array(){
	void **array = NULL;
	int size=0;
	randomized_quick_sort(array, size, compare);
	TEST_ASSERT_TRUE(array==NULL);
}

//It checks the right behaviour of the binary insertion sort with an already sorted array
static void binary_insertion_sort_already_sorted(){
	int e1 = 0;
	int e2 = 1;
	int e3 = 9;
	int size = 3;
	int * array[] = {&e1, &e2, &e3};
	int *exp_array[] = {&e1, &e2, &e3};
	binary_insertion_sort((void **)array, size, compare);
	TEST_ASSERT_EQUAL_PTR_ARRAY(exp_array, array, size);
}

//It checks the right behaviour of the quick sort with an already sorted array
static void quick_sort_already_sorted(){
	int e1 = 0;
	int e2 = 1;
	int e3 = 9;
	int size = 3;
	int * array[] = {&e1, &e2, &e3};
	int *exp_array[] = {&e1, &e2, &e3};
	quick_sort((void **)array, size, compare);
	TEST_ASSERT_EQUAL_PTR_ARRAY(exp_array, array, size);
}

//It checks the right behaviour of the randomized quick sort with an already sorted array
static void randomized_quick_sort_already_sorted(){
	int e1 = 0;
	int e2 = 1;
	int e3 = 9;
	int size = 3;
	int * array[] = {&e1, &e2, &e3};
	int *exp_array[] = {&e1, &e2, &e3};
	randomized_quick_sort((void**)array, size, compare);
	TEST_ASSERT_EQUAL_PTR_ARRAY(exp_array, array, size);
}

//It checks the right behaviour of the binary insertion sort with a normal array
static void binary_insertion_sort_not_sorted(){
	int e1 = 0;
	int e2 = 1;
	int e3 = 9;
	int size = 3;
	int * array[] = {&e2, &e3, &e1};
	int *exp_array[] = {&e1, &e2, &e3};
	binary_insertion_sort((void **)array, size, compare);
	TEST_ASSERT_EQUAL_PTR_ARRAY(exp_array, array, size);
}

//It checks the right behaviour of the quick sort with a normal array
static void quick_sort_not_sorted(){
	int e1 = 0;
	int e2 = 1;
	int e3 = 9;
	int size = 3;
	int * array[] = {&e2, &e1, &e3};
	int *exp_array[] = {&e1, &e2, &e3};
	quick_sort((void **)array, size, compare);
	TEST_ASSERT_EQUAL_PTR_ARRAY(exp_array, array, size);
}

//It checks the right behaviour of the randomized quick sort with a normal array
static void randomized_quick_sort_not_sorted(){
	int e1 = 0;
	int e2 = 1;
	int e3 = 9;
	int size = 3;
	int * array[] = {&e1, &e3, &e2};
	int *exp_array[] = {&e1, &e2, &e3};
	randomized_quick_sort((void**)array, size, compare);
	TEST_ASSERT_EQUAL_PTR_ARRAY(exp_array, array, size);
}

//It checks the right behaviour of the binary insertion sort with an array with repeated elements
static void binary_insertion_sort_equal_el(){
	int e1 = 0;
	int e2 = 1;
	int e3 = 9;
	int size = 4;
	int * array[] = {&e2, &e1, &e3, &e1};
	int *exp_array[] = {&e1, &e1, &e2, &e3};
	binary_insertion_sort((void **)array, size, compare);
	TEST_ASSERT_EQUAL_PTR_ARRAY(exp_array, array, size);
}

//It checks the right behaviour of the quick sort with an array with repeated elements
static void quick_sort_equal_el(){
	int e1 = 0;
	int e2 = 1;
	int e3 = 9;
	int size = 4;
	int * array[] = {&e2, &e1, &e3, &e3};
	int *exp_array[] = {&e1, &e2, &e3, &e3};
	quick_sort((void **)array, size, compare);
	TEST_ASSERT_EQUAL_PTR_ARRAY(exp_array, array, size);
}

//It checks the right behaviour of the randomized quick sort with an array with repeated elements
static void randomized_quick_sort_equal_el(){
	int e1 = 0;
	int e2 = 1;
	int e3 = 9;
	int size = 4;
	int * array[] = {&e2, &e3, &e2, &e1};
	int *exp_array[] = {&e1, &e2, &e2, &e3};
	randomized_quick_sort((void**)array, size, compare);
	TEST_ASSERT_EQUAL_PTR_ARRAY(exp_array, array, size);
}

//It checks the right behaviour of the binary insertion sort with an array backward sorted
static void binary_insertion_sort_backward_sorted(){
	int e1 = 0;
	int e2 = 1;
	int e3 = 9;
	int size = 3;
	int * array[] = {&e3, &e2, &e1};
	int *exp_array[] = {&e1, &e2, &e3};
	binary_insertion_sort((void **)array, size, compare);
	TEST_ASSERT_EQUAL_PTR_ARRAY(exp_array, array, size);
}

//It checks the right behaviour of the quick sort with an array backward sorted
static void quick_sort_backward_sorted(){
	int e1 = 0;
	int e2 = 1;
	int e3 = 9;
	int size = 3;
	int * array[] = {&e3, &e2, &e1};
	int *exp_array[] = {&e1, &e2, &e3};
	quick_sort((void **)array, size, compare);
	TEST_ASSERT_EQUAL_PTR_ARRAY(exp_array, array, size);
}

//It checks the right behaviour of the randomized quick sort with an array backward sorted
static void randomized_quick_sort_backward_sorted(){
	int e1 = 0;
	int e2 = 1;
	int e3 = 9;
	int size = 3;
	int * array[] = {&e3, &e2, &e1};
	int *exp_array[] = {&e1, &e2, &e3};
	randomized_quick_sort((void**)array, size, compare);
	TEST_ASSERT_EQUAL_PTR_ARRAY(exp_array, array, size);
}

//Main that runs the tests
int main(){
	UNITY_BEGIN();
	RUN_TEST(binary_insertion_sort_null_array);
	RUN_TEST(quick_sort_null_array);
	RUN_TEST(randomized_quick_sort_null_array);
	RUN_TEST(binary_insertion_sort_already_sorted);
	RUN_TEST(quick_sort_already_sorted);
	RUN_TEST(randomized_quick_sort_already_sorted);
	RUN_TEST(binary_insertion_sort_not_sorted);
	RUN_TEST(quick_sort_not_sorted);
	RUN_TEST(randomized_quick_sort_not_sorted);
	RUN_TEST(binary_insertion_sort_equal_el);
	RUN_TEST(quick_sort_equal_el);
	RUN_TEST(randomized_quick_sort_equal_el);
	RUN_TEST(binary_insertion_sort_backward_sorted);
	RUN_TEST(quick_sort_backward_sorted);
	RUN_TEST(randomized_quick_sort_backward_sorted);
	return UNITY_END();
}