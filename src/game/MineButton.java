package game;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;

/**
 *
 * @author akinc
 */
public class MineButton extends JButton {
	private static final long serialVersionUID = 1L;
	private int xLoc;
    private int yLoc;
    private int minesAround;
    private boolean mine;
    private boolean flag;

    public MineButton(int xLoc, int yLoc) {
        this.xLoc = xLoc;
        this.yLoc = yLoc;
        this.minesAround = 0;
        this.mine = false;
        this.flag = false;
        
        setBackground(Color.gray);
        setFont(new Font("Segoe UI", Font.BOLD, 20));
        setBorder(BorderFactory.createBevelBorder(0));
    }

    public int getxLoc() {
        return xLoc;
    }

    public void setxLoc(int xLoc) {
        this.xLoc = xLoc;
    }

    public int getyLoc() {
        return yLoc;
    }

    public void setyLoc(int yLoc) {
        this.yLoc = yLoc;
    }

    public int getMinesAround() {
        return minesAround;
    }

    public void setMinesAround(int minesAround) {
        this.minesAround = minesAround;
    }

    public boolean isMine() {
        return mine;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
    
}
