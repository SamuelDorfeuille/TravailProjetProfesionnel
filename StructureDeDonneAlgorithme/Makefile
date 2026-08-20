CXX = g++
CXXFLAGS = -std=c++17 -Wall -Wextra -pedantic -g

TEST = test_avl
TEST_SETS = test_set_operations

all: $(TEST)

$(TEST): test_avl.cpp ArbreAVL.h
	$(CXX) $(CXXFLAGS) test_avl.cpp -o $(TEST)

$(TEST_SETS): test_set_operations.cpp ArbreAVL.h
	$(CXX) $(CXXFLAGS) test_set_operations.cpp -o $(TEST_SETS)

# Tests principaux : insertion, suppression, rotations, copie,
# affectation, égalité, vider, taille, contient et templates.
test: $(TEST)
	./$(TEST)

# Tests séparés pour | et &.
# Avec le code fourni actuellement, ces tests exposent un problème
# dans l'implémentation de operator| (récursion infinie).
test-sets: $(TEST_SETS)
	./$(TEST_SETS)

run: test

clean:
	rm -f $(TEST) $(TEST_SETS) test_asan

rebuild: clean all

.PHONY: all test test-sets run clean rebuild
