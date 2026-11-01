ENTRY(vn_th0)
SECTIONS {
  . = 0xaef38;
  .text : { *(.text) *(.text.*) *(.rodata) *(.rodata.*) }
  /DISCARD/ : { *(.ARM.exidx*) *(.ARM.extab*) *(.comment) *(.note*) *(.ARM.attributes) }
}
