cmake_minimum_required(VERSION 3.18)
project(msmod C CXX)

# Dung: cmake -S mods/native -B work/build/msmod -G Ninja -DCMAKE_TOOLCHAIN_FILE=$NDK/build/cmake/android.toolchain.cmake \
#         -DANDROID_ABI=arm64-v8a -DANDROID_PLATFORM=android-24 -DDOBBY_DIR=<Dobby src> -DDOBBY_LIB=<libdobby.a>
set(DOBBY_DIR "" CACHE PATH "thu muc nguon Dobby (co include/dobby.h)")
set(DOBBY_LIB "" CACHE FILEPATH "libdobby.a (arm64, build static)")
if(NOT DOBBY_DIR OR NOT DOBBY_LIB)
  message(FATAL_ERROR "can -DDOBBY_DIR va -DDOBBY_LIB (xem tools/build_mod.sh)")
endif()

add_library(msmod SHARED msmod.c)
target_include_directories(msmod PRIVATE ${DOBBY_DIR}/include ${CMAKE_CURRENT_SOURCE_DIR})
target_compile_options(msmod PRIVATE -O2 -Wall -Wextra -fvisibility=hidden -fno-exceptions -fno-unwind-tables -fstack-protector-strong -std=c11)
# Dobby la C++ (tinystl, khong exception/rtti): liên kết libc++ tinh de khong them NEEDED libc++_shared.so.
target_link_libraries(msmod PRIVATE ${DOBBY_LIB} log)
target_link_options(msmod PRIVATE -Wl,--gc-sections -Wl,-z,max-page-size=16384 -Wl,-z,noexecstack -Wl,--exclude-libs,ALL -static-libstdc++ -Wl,--build-id=none)
set_target_properties(msmod PROPERTIES LINKER_LANGUAGE CXX)
