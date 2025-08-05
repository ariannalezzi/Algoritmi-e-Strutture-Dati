#ifndef SKIP_LIST_H
#define SKIP_LIST_H
#define MAX_HEIGHT 20	//Max height of the SkipList

typedef struct _SkipList SkipList;
typedef struct _Node Node;

//Struct of the SkipList used
struct _SkipList{
	Node *head;
	unsigned int max_level;
	int (*compare)(void *, void *);
};

//Struct of the Node used
struct _Node{
	Node **next;
	unsigned int size;
	void *item;
};


SkipList *create_skip_list(int (*) (void*, void*));

int search_skip_list(SkipList *, void *);

void insert_skip_list(SkipList *, void *);

Node *create_node(void *, unsigned int);

unsigned int random_level();

void free_skip_list(SkipList *, int);

#endif
