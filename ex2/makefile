CFLAGS			=  -O1 -Wall -Wextra -Wpedantic -Wconversion
COMMON_DEPS		= src/*.h unity/*.h makefile
CC				= gcc

all:  obj build build/ex2_main build/ex2_tests

obj: 
	mkdir obj
	
build: 
	mkdir build


tests: obj build build/ex2_tests
	build/ex2_tests
	
obj/%.o: src/%.c $(COMMON_DEPS)
	$(CC) $(CFLAGS) -c $< -o $@

unity/%.o: src/%.c $(COMMON_DEPS)
	$(CC) $(CFLAGS) -c $< -o $@
	
build/ex2_main : obj/skip_list.o obj/skip_list_main.o $(COMMON_DEPS)
	$(CC) -o build/ex2_main obj/skip_list.o obj/skip_list_main.o
	
build/ex2_tests : obj/skip_list.o obj/skip_list_tests.o unity/unity.o $(COMMON_DEPS)
	$(CC) -o build/ex2_tests obj/skip_list_tests.o obj/skip_list.o unity/unity.o
	
