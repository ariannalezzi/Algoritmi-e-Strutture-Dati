CFLAGS			=  -O1 -Wall -Wextra -Wpedantic -Wconversion
COMMON_DEPS		= src/*.h unity/*.h makefile
CC				= gcc

all:  build bin bin/sort_main bin/sort_tests

build: 
	mkdir build
	
bin: 
	mkdir bin


tests:build bin bin/sort_tests 
	bin/sort_tests
	
build/%.o: src/%.c $(COMMON_DEPS)
	$(CC) $(CFLAGS) -c $< -o $@

unity/%.o: src/%.c $(COMMON_DEPS)
	$(CC) $(CFLAGS) -c $< -o $@
	
bin/sort_main : build/sort.o build/sort_main.o $(COMMON_DEPS)
	$(CC) -o bin/sort_main build/sort.o build/sort_main.o
	
bin/sort_tests : build/sort.o build/sort_tests.o unity/unity.o $(COMMON_DEPS)
	$(CC) -o bin/sort_tests build/sort_tests.o build/sort.o unity/unity.o
	
