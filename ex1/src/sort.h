#ifndef SORT_H
#define SORT_H


void binary_insertion_sort(void **, int, int (*)(void *, void *));

int sorted_index(void **, int, int, void *, int (*)(void *, void *));

void quick_sort(void **,int, int (*)(void *, void *));

void quick_sort_aux(void **, int , int , int (*)(void *, void *));

void partition(void **array, int, int, int (*)(void *, void *), int*, int*);

void randomized_quick_sort(void **, int, int (*)(void *,void *));

void randomized_quick_sort_aux(void **, int, int, int (*)(void *, void *));

void randomized_partition(void **, int, int, int (*)(void *, void *), int*, int*);

void swap(void **, int, int);

#endif
