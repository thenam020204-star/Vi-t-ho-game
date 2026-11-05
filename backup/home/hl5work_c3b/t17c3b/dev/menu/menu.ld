ENTRY(mui_begin)
SECTIONS {
  . = 0x171000;
  .text : { *(.text) *(.text.*) }
  .rodata : ALIGN(4) { *(.rodata) *(.rodata.*) }
  .data : ALIGN(4) { *(.data) *(.data.*) }
  mui_file_end = .;
  .bss : ALIGN(16) { *(.bss) *(.bss.*) *(COMMON) }
  mui_seg_end = .;
  /DISCARD/ : { *(.ARM.exidx*) *(.ARM.extab*) *(.comment) *(.note*) *(.ARM.attributes) }
}
