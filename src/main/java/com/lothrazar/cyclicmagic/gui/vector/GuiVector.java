package com.lothrazar.cyclicmagic.gui.vector;
import java.io.IOException;
import java.util.ArrayList;
import com.lothrazar.cyclicmagic.block.tileentity.TileVector;
import com.lothrazar.cyclicmagic.block.tileentity.TileVector.Fields;
import com.lothrazar.cyclicmagic.gui.GuiBaseContainer;
import com.lothrazar.cyclicmagic.util.UtilChat;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GuiVector extends GuiBaseContainer {
  static final int GUI_ROWS = 2;
  private TileVector tile;
  private int leftColX;
  private int sizeY;
  private int limitColX;
  private int[] yRows = new int[3];
  private ButtonVector greaterLessBtn;
  private ButtonVector entityBtn;
  private GuiTextField txtAngle;
  private ArrayList<GuiTextField> txtBoxes = new ArrayList<GuiTextField>();
  private GuiTextField txtPower;
  public GuiVector(InventoryPlayer inventoryPlayer, TileVector tileEntity) {
    super(new ContainerVector(inventoryPlayer, tileEntity), tileEntity);
    tile = tileEntity;
  }
  public String getTitle() {
    return "tile.plate_vector.name";
  }
  @Override
  public void initGui() {
    super.initGui();
    int id = 1;
    //    int vButtonSpacing = 12;
    //    sizeY = 58;//save now as reuse for textbox
    //    leftColX = 176 - 148;
    //    limitColX = leftColX + 108;
    int x = 20, y = 40;
    txtAngle = addTextbox(id++, x, y,tile.getAngle() + "");
    txtAngle.setFocused(true);//default
    x += 40;
    txtPower = addTextbox(id++, x, y,tile.getPower() + "");
    //    addPatternButtonAt(id++, limitColX, sizeY - vButtonSpacing, true, Fields.LIMIT);
    //    addPatternButtonAt(id++, limitColX, sizeY + vButtonSpacing, false, Fields.LIMIT);
    //    int x = leftColX + 40;
    //    int y = sizeY - 5;
    //    this.greaterLessBtn = addPatternButtonAt(id++, x, y, true, Fields.GREATERTHAN, 60, 20);
    //    this.entityBtn = addPatternButtonAt(id++, x, 18, true, Fields.ENTITYTYPE, 60, 20);
    //    int xOffset = 18;
    //    int yOffset = 12;
    //    yRows[0] = 30 + yOffset;
    //    addPatternButtonAt(id++, leftColX + xOffset, yRows[0], true, Fields.RANGEX);
    //    addPatternButtonAt(id++, leftColX - xOffset - 4, yRows[0], false, Fields.RANGEX);
    //    yRows[1] = yRows[0] + yOffset;
    //    addPatternButtonAt(id++, leftColX + xOffset, yRows[1], true, Fields.RANGEY);
    //    addPatternButtonAt(id++, leftColX - xOffset - 4, yRows[1], false, Fields.RANGEY);
    //    yRows[2] = yRows[1] + yOffset;
    //    addPatternButtonAt(id++, leftColX + xOffset, yRows[2], true, Fields.RANGEZ);
    //    addPatternButtonAt(id++, leftColX - xOffset - 4, yRows[2], false, Fields.RANGEZ);
    //TODO: PREVIEW BUTTON
  }
  private GuiTextField addTextbox(int id, int x, int y, String text) {
    int width = 20, height = 20;
    GuiTextField txt = new GuiTextField(id, this.fontRendererObj, x, y, width, height);
    txt.setMaxStringLength(2);
    txt.setText(text);
    txtBoxes.add(txt);
    return txt;
  }
  private ButtonVector addPatternButtonAt(int id, int x, int y, boolean isUp, Fields f, int w, int h) {
    ButtonVector btn = new ButtonVector(tile.getPos(), id,
        this.guiLeft + x,
        this.guiTop + y,
        isUp, f, w, h);
    this.buttonList.add(btn);
    return btn;
  }
  //  private ButtonVector addPatternButtonAt(int id, int x, int y, boolean isUp, Fields f) {
  //    return this.addPatternButtonAt(id, x, y, isUp, f, 15, 10);
  //  }
  //  private void drawFieldAt(int x, int y, Fields f) {
  //    this.drawFieldAt(x, y, f.ordinal());
  //  }
  @SideOnly(Side.CLIENT)
  @Override
  protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
    for (GuiTextField txt : txtBoxes) {
      if (txt != null) {
        txt.drawTextBox();
      }
    }
    //draw all text fields
    //    drawFieldAt(limitColX + 3, sizeY, Fields.LIMIT);
    //    drawFieldAt(leftColX, yRows[0], Fields.RANGEX);
    //    drawFieldAt(leftColX, yRows[1], Fields.RANGEY);
    //    drawFieldAt(leftColX, yRows[2], Fields.RANGEZ);
    //    //update button text
    //    EntityType t = this.tile.getEntityType();
    //    this.entityBtn.displayString = UtilChat.lang("tile.entity_detector." + t.name().toLowerCase());
    //    int greater = this.tile.getField(Fields.GREATERTHAN);
    //    String dir = CompareType.values()[greater].name().toLowerCase();
    //    this.greaterLessBtn.displayString = UtilChat.lang("tile.entity_detector." + dir);
    super.drawGuiContainerForegroundLayer(mouseX, mouseY);
  }
  // http://www.minecraftforge.net/forum/index.php?topic=22378.0
  // below is all the stuff that makes the text box NOT broken
  @Override
  public void updateScreen() {
    super.updateScreen();
    for (GuiTextField txt : txtBoxes) {
      if (txt != null) {
        txt.updateCursorCounter();
      }
    }
  }
  @Override
  protected void keyTyped(char par1, int par2) throws IOException {
    super.keyTyped(par1, par2);
    if (txtAngle != null && txtAngle.isFocused()) {
      txtAngle.textboxKeyTyped(par1, par2);
      System.out.println("TODO: save txtAngle" + txtAngle.getText());
      // ModCyclic.network.sendToServer(new PacketTilePassword(txtPassword.getText(), ctr.tile.getPos()));
    }
    if (txtPower != null && txtPower.isFocused()) {
      txtPower.textboxKeyTyped(par1, par2);
      System.out.println("TODO: save txtPower" + txtPower.getText());
      // ModCyclic.network.sendToServer(new PacketTilePassword(txtPassword.getText(), ctr.tile.getPos()));
    }
  }
  @Override
  protected void mouseClicked(int mouseX, int mouseY, int btn) throws IOException {
    super.mouseClicked(mouseX, mouseY, btn);// x/y pos is 33/30
    for (GuiTextField txt : txtBoxes) {
      txt.mouseClicked(mouseX, mouseY, btn);
      if (btn == 0) {//basically left click
        boolean flag = mouseX >= this.guiLeft + txt.xPosition && mouseX < this.guiLeft + txt.xPosition + txt.width
            && mouseY >= this.guiTop + txt.yPosition && mouseY < this.guiTop + txt.yPosition + txt.height;
        txt.setFocused(flag);
      }
    }
  }
  // ok end of textbox fixing stuff
}
