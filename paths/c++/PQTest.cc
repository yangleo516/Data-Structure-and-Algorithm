//
// PQTEST.CC
// A unit test for your priority queue class.
//

#include <iostream>
#include <string>

using namespace std;

#include "PriorityQueue.h"

// local prototypes
static void pqUnitTestOne();
static void pqUnitTestTwo();
static void pqUnitTestThree();

void PQTest(int arg)
{
  switch (arg)
    {
    case 1:
      cout << "*** UNIT TEST ONE ***\n";
      pqUnitTestOne();
      break;
      
    case 2:
      cout << "*** UNIT TEST TWO ***\n";
      pqUnitTestTwo();
      break;
      
    case 3:
      cout << "*** UNIT TEST THREE ***\n";
      pqUnitTestThree();
      break;
      
    default:
      cout << "ERROR: no unit test " << arg << '\n';
      break;
    }  
}

// Start by testing just insertion, min, and extraction.
static void pqUnitTestOne()
{
  PriorityQueue<string> q;
  
  // build up the queue
  q.insert(4, string("four"));
  q.insert(7, string("seven"));
  q.insert(1, string("one"));
  q.insert(6, string("six"));
  q.insert(12, string("twelve"));
  q.insert(3, string("three"));
  q.insert(11, string("eleven"));
  q.insert(5, string("five"));
  q.insert(8, string("eight"));
  q.insert(3, string("three-prime"));
  q.insert(2, string("two"));
  
  cout << "Initial queue:\n" << q << '\n';
  
  // extract the smallest elements from the queue
  cout << "Extractions:\n";
  for (int j = 0; j < 6; j++)
    {
      int k = q.min();
      string s = q.extractMin();
      
      cout << "@" << k << " " << s << '\n';
    }
  
  cout << "\nAfter extractions:\n" << q << '\n';
  
  // add a few more values
  q.insert(8, string("eight-prime"));
  q.insert(14, string("fourteen"));
  q.insert(9, string("nine"));
  
  cout << "After more insertions:\n" << q << '\n';
  
  // extract the smallest elements from the queue
  cout << "Extractions:\n";
  while (!q.isEmpty())
    {
      int k = q.min();
      string s = q.extractMin();
      
      cout << "@" << k << " " << s << '\n';
    }
}


// Make sure Handles are working, then test decreaseKey.
static void pqUnitTestTwo()
{
  PriorityQueue<string> q;
  Handle **handles = new Handle* [20];
  
  // build up the queue
  handles[9] = q.insert(9, string("nine"));
  handles[7] = q.insert(7, string("seven"));
  handles[2] = q.insert(2, string("two"));	
  handles[8] = q.insert(8, string("eight"));
  handles[1] = q.insert(1, string("one"));
  handles[6] = q.insert(6, string("six"));
  handles[10] = q.insert(10, string("ten"));
  handles[5] = q.insert(5, string("five"));
  handles[3] = q.insert(3, string("three"));
  handles[4] = q.insert(4, string("four"));
  handles[0] = q.insert(3, string("element zero"));
  
  cout << "Elements: \n";
  // Do the handles point to the correct elements?
  for (int j = 0; j <= 10; j++)
    {
      cout << "@" << 
	q.handleGetKey(handles[j]) << " " <<
	q.handleGetValue(handles[j]) << '\n';
    }
  
  // extract the smallest elements from the queue
  for (int j = 0; j < 5; j++)
    q.extractMin();
  
  cout << "\nAfter extractions: \n";
	
  // Do the handles *still* point to the correct elements?
  cout << "Elements: \n";
  for (int j = 5; j <= 10; j++)
    {
      cout << "@" << 
	q.handleGetKey(handles[j]) << " " <<
	q.handleGetValue(handles[j]) << '\n';
    }
  
  // add some more elements
  handles[12] = q.insert(12, string("twelveX"));
  handles[15] = q.insert(15, string("fifteenX"));
  handles[13] = q.insert(13, string("thirteenX"));
  handles[11] = q.insert(11, string("elevenX"));
  handles[14] = q.insert(14, string("fourteenX"));
  
  // should have no effect
  q.decreaseKey(handles[11], 11);
  q.decreaseKey(handles[11], 15);
  
  // now move some keys around
  q.decreaseKey(handles[12], 1);
  q.decreaseKey(handles[6], 3);
  q.decreaseKey(handles[14], 4);
  q.decreaseKey(handles[9], 2);
  
  // should rearrange the heap and fix up the handles
  cout << "Elements: \n";
  for (int j = 5; j <= 15; j++)
    {
      cout << "@" <<
	q.handleGetKey(handles[j]) << " " <<
	q.handleGetValue(handles[j]) << '\n';
    }
  
  // should rearrange the heap and fix up the handles
  cout << "Extractions: \n";
  while (!q.isEmpty())
    {
      int k = q.min();
      string s = q.extractMin();
      
      cout << "@" << k << " " << s << '\n';
    }
}

// A lame PRNG that acts the same for both Java and C++
// To avoid undefined overflow behavior in C++, we must work
// entirely in unsigned arithmetic and convert the final
// result to a (non-negative) 31-bit integer.

static unsigned int state = 840287439;

static int lcRand() 
{
  const unsigned int A = 14357867;
  const unsigned int B = 23341349;
  
  state = A * state + B;
  
  // return low-order 31 bits
  return (int) (state & ((1U << 31) - 1));
}

struct Integer {
  int v;
  Integer (int i) { v = i; }
};

ostream &operator<<(ostream &os, const Integer &i)
{
  os << i.v;
  return os;
}

// A little more exercise.
static void pqUnitTestThree()
{
  PriorityQueue<int> q;
  Handle **handles = new Handle *[1000];
  
  // Insert a bunch of random elements.
  for (int j = 0; j < 1000; j++)
    {
      int x = lcRand();
      handles[j] = q.insert(x, x);
    }
	
  // Extract the smallest elements.
  cout << "Extractions: \n";
  for (int j = 0; j < 50; j++)
    {
      int k = q.min();
      int s = q.extractMin();
      
      cout << "@" << k << " " << s << '\n';
    }
  
  cout << "************************************\n";

  // Decrease the keys of random elements to random values.
  for (int j = 0; j < 1000; j++)
    {
      int idx = lcRand() % 1000;
      int key = lcRand();
      q.decreaseKey(handles[idx], key);
    }
	
  // Extract the smallest elements.
  cout << "Extractions: \n";
  for (int j = 0; j < 50; j++)
    {
      int k = q.min();
      int s = q.extractMin();
      
      cout << "@" << k << " " << s << '\n';
    }
}
