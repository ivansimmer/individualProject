# Additional clean files
cmake_minimum_required(VERSION 3.16)

if("${CONFIG}" STREQUAL "" OR "${CONFIG}" STREQUAL "Debug")
  file(REMOVE_RECURSE
  "CMakeFiles\\provaM13_autogen.dir\\AutogenUsed.txt"
  "CMakeFiles\\provaM13_autogen.dir\\ParseCache.txt"
  "provaM13_autogen"
  )
endif()
