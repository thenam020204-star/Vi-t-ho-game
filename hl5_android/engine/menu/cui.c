/* L6b: ẩn nút ↺ (CommonUi::DrawTouchCancelUi 0x73ce8) và vòng xám phản hồi chạm (DrawTouchFeedbackSprite 0x73c50) khi Paint của state trên cùng ĐÃ do menu mới vẽ khung này
 * (cờ mui_drawn: thunk Paint ngoài cùng, dispatch.c). Rơi về đường gốc (Paint gốc, cờ MUI_F_DISABLE) thì vẫn vẽ như gốc. Điểm móc: call-site trong mainPaint 0x987c4 (hooks.def).
 * Chỉ ẩn phần VẼ; vùng chạm Back của game không đổi (state mới tự có nút "<" HK_KEY -16 = phím Back gốc). */
#include "mui.h"

extern H void CommonUi_DrawTouchFeedbackSprite(void *cui);
extern H void CommonUi_DrawTouchCancelUi(void *cui);

void l6_hide_fb(void *cui) { if (mui_drawn && !(mui_flags & MUI_F_DISABLE)) return; CommonUi_DrawTouchFeedbackSprite(cui); }
void l6_hide_cancel(void *cui) { if (mui_drawn && !(mui_flags & MUI_F_DISABLE)) return; CommonUi_DrawTouchCancelUi(cui); }
