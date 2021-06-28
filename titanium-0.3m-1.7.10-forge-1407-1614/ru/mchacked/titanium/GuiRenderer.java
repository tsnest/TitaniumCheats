package ru.mchacked.titanium;

import net.minecraft.client.gui.FontRenderer;
import ru.mchacked.titanium.TGui;
import ru.mchacked.titanium.Titanium;
import ru.mchacked.titanium.api.IWindow;

public class GuiRenderer {

   public static final int HEADER_SIZE = 12;
   public static final int Field152 = 2;
   private int Field153;
   private int Field154;
   public IWindow Field155;
   public int Field156;
   public int Field157;
   public boolean Field158 = true;

   public GuiRenderer() {
      FontRenderer fr = Titanium.getTitanium().getTGui().getFontRenderer();
      this.Field153 = fr.getCharWidth('x');
      this.Field154 = Math.max(fr.getCharWidth('-'), fr.getCharWidth('+'));
   }

   public int Method184() {
      return this.Field155.getWidth() + 4;
   }

   public int Method185() {
      return (this.Field158?this.Field155.getHeight() + 2:0) + 2 + 12;
   }

   public void Method186(int x, int y) {
      if(y < 14) {
         int exX;
         if(this.Field155.isCloseable()) {
            exX = 2 + this.Field155.getWidth() - this.Field153;
            if(TGui.Method69(x, y, exX, 2, this.Field153, 12)) {
               Titanium.getTitanium().getTGui().hideWindow(this.Field155);
               return;
            }
         }

         exX = 2 + this.Field155.getWidth() - this.Field154 - (this.Field155.isCloseable()?this.Field153:0) - 2;
         if(TGui.Method69(x, y, exX, 2, this.Field154, 12)) {
            this.Field158 = !this.Field158;
         }
      } else {
         this.Field155.onClick(x - 2, y - 2 - 12);
      }
   }

   public void scroll(int x, int y, int delta) {
      if(y >= 14) {
         this.Field155.onMouseScroll(x - 2, y - 2 - 12, delta);
      }
   }

   public void Method188(FontRenderer fr, int mx, int my) {
      TGui.drawRect(this.Field156, this.Field157, this.Field156 + this.Method184(), this.Field157 + this.Method185(), -1610612736);
      int exX;
      boolean onEx;
      if(this.Field155.isCloseable()) {
         exX = 2 + this.Field155.getWidth() - this.Field153;
         onEx = TGui.Method69(mx, my, exX, 2, this.Field153, 12);
         fr.drawString((onEx?"§c":"§7") + "x", this.Field156 + exX, this.Field157 + 2, 16777215);
      }

      exX = 2 + this.Field155.getWidth() - this.Field154 - (this.Field155.isCloseable()?this.Field153:0) - 2;
      onEx = TGui.Method69(mx, my, exX, 2, this.Field154, 12);
      fr.drawString((onEx?"§c":"§7") + (this.Field158?"-":"+"), this.Field156 + exX, this.Field157 + 2, 16777215);
      int captW = fr.getStringWidth(this.Field155.getCaption());
      int captX = 2 + (this.Field155.getWidth() - this.Field154 - (this.Field155.isCloseable()?this.Field153:0) - 2) / 2 - captW / 2;
      fr.drawString("§6" + this.Field155.getCaption(), this.Field156 + captX, this.Field157 + 2, 16777215);
      if(this.Field158) {
         byte ox = 2;
         byte oy = 14;
         this.Field155.onRender(this.Field156 + ox, this.Field157 + oy, mx - ox, my - oy);
      }

   }

   public boolean Method189(int x, int y) {
      return x >= 0 && y >= 0?(x > this.Field155.getWidth() - ((this.Field155.isCloseable()?this.Field153:0) + this.Field154 + 2)?false:y < 14):false;
   }
}
