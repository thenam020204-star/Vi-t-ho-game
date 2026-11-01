ENTRY(t10_R)
SECTIONS {
  . = 0x14afb4;
  .text : { *(.text) *(.text.*) *(.rodata) *(.rodata.*) }
  /DISCARD/ : { *(.ARM.exidx*) *(.ARM.extab*) *(.comment) *(.note*) *(.ARM.attributes) }
}
