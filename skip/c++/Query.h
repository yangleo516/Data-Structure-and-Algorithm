//
// QUERY.H
// Describes a query operation on the event list
//

#ifndef __QUERY_H
#define __QUERY_H

#include <iostream>
#include <string>

class Query {
public:
  
  enum Command {FINDRANGE, FINDMOSTRECENT, DELETE};
  
  Command command;
  int year1, year2;
  
  Query(Command icommand, int iyear)
    {
	command = icommand;
	year1 = iyear;
    }

  Query(Command icommand, int iyear1, int iyear2)
    {
	command = icommand;
	year1 = iyear1;
	year2 = iyear2;
    }
};

std::ostream &operator<<(std::ostream &os, const Query &q);

#endif
