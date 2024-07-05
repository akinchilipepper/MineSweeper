package game;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GameFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel minePanel;
	private MineButton[][] buttons = new MineButton[10][10];
	
	private ImageIcon originalMineIcon = new ImageIcon(GameFrame.class.getResource("/icons/mine.png"));
	private Image originalMineImage = originalMineIcon.getImage();
	private Image resizedMineImage = originalMineImage.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
	
	private ImageIcon originalFlagIcon = new ImageIcon(GameFrame.class.getResource("/icons/flag.png"));
	private Image originalFlagImage = originalFlagIcon.getImage();
	private Image resizedFlagImage = originalFlagImage.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
	
	private int openedCount = 0;

	/**
	 * Create the frame.
	 */
	public GameFrame() {
		initComponents();
		setMinePanel();
		generateRandomMines();
		updateMineCount();
	}

	private void initComponents() {
		setResizable(false);
		setTitle("Mayın Tarlası");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 750);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		minePanel = new JPanel();
		minePanel.setBackground(new Color(255, 255, 255));
		minePanel.setBounds(10, 143, 562, 550);
		contentPane.add(minePanel);
		minePanel.setLayout(new GridLayout(10, 10, 0, 0));
		
		JLabel lblHeader = new JLabel("Mayın Tarlası");
		lblHeader.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 20));
		lblHeader.setBounds(232, 53, 118, 25);
		contentPane.add(lblHeader);
		
		JButton btnReload = new JButton("Yeni Oyun");
		btnReload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new GameFrame().setVisible(true);
			}
		});
		btnReload.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		btnReload.setBounds(447, 10, 125, 30);
		btnReload.setBorder(BorderFactory.createBevelBorder(0));
		btnReload.setBackground(Color.WHITE);
		contentPane.add(btnReload);

		setLocationRelativeTo(null);
	}

	private void setMinePanel() {
		for (int row = 0; row < buttons.length; row++) {
			for (int col = 0; col < buttons[0].length; col++) {
				MineButton button = new MineButton(row, col);
				button.addMouseListener(mouseAdapter);
				minePanel.add(button);
				buttons[row][col] = button;
			}
		}
	}

	private void generateRandomMines() {
		for (int mines = 0; mines < 10; mines++) {
			int randX;
			int randY;
			do {
				randX = (int) (Math.random() * buttons.length);
				randY = (int) (Math.random() * buttons[0].length);
			} while (buttons[randX][randY].isMine());
			buttons[randX][randY].setMine(true);
		}
	}

	private void printMines() {
		for(int row = 0; row < buttons.length; row++) {
			for(int col = 0; col < buttons[0].length; col++) {
				if (buttons[row][col].isMine()) {
					buttons[row][col].setIcon(new ImageIcon(resizedMineImage));
					buttons[row][col].setBorder(BorderFactory.createBevelBorder(1));
				} 
			}
		}
	}

	private void updateMineCount() {
		for (int row = 0; row < buttons.length; row++) {
			for (int col = 0; col < buttons[0].length; col++) {
				if (buttons[row][col].isMine()) {
					mineCounting(row, col);
				}
			}
		}
	}

	private void mineCounting(int row, int col) {
		for (int i = row - 1; i <= row + 1; i++) {
			for (int j = col - 1; j <= col + 1; j++) {
				if (i < 0 || j < 0 || i >= buttons.length || j >= buttons[0].length || (i == row && j == col)) {
					continue;
				}
				if (!buttons[i][j].isMine()) {
					int minesAround = buttons[i][j].getMinesAround();
					buttons[i][j].setMinesAround(++minesAround);
				}
			}
		}
	}

	private void openButton(int row, int col) {
	    if (row < 0 || col < 0 || row >= buttons.length || col >= buttons[0].length) {
	        return;
	    }

	    if (!buttons[row][col].isEnabled()) {
	        return;
	    }

	    int minesAround = buttons[row][col].getMinesAround();
	    buttons[row][col].setEnabled(false);
	    buttons[row][col].setBorder(BorderFactory.createBevelBorder(1));
		openedCount++;   

	    if (minesAround != 0) {
	        buttons[row][col].setText(String.valueOf(minesAround));
	    } else {
	    	openButton(row - 1, col - 1);
	    	openButton(row - 1, col);  
	    	openButton(row - 1, col + 1);
	    	openButton(row, col - 1);  
	        openButton(row, col + 1);
	        openButton(row + 1, col - 1);
	        openButton(row + 1, col);
	        openButton(row + 1, col + 1);
	    }
	}
	
	private void disableAllButtons() {
	    for (int row = 0; row < buttons.length; row++) {
	        for (int col = 0; col < buttons[0].length; col++) {
	            buttons[row][col].setEnabled(false);
	        }
	    }
	}

	MouseAdapter mouseAdapter = new MouseAdapter() {

		@Override
		public void mouseClicked(MouseEvent e) {
			MineButton button = (MineButton) e.getComponent();
			if (e.getButton() == 1) {
				if (button.isMine()) {
					printMines();
					disableAllButtons();
					JOptionPane.showMessageDialog(rootPane, "Kaybettiniz...");
				} else {
					openButton(button.getxLoc(), button.getyLoc());
					if(openedCount == (buttons.length * buttons[0].length) - 10) {
						disableAllButtons();
						JOptionPane.showMessageDialog(rootPane, "Tebrikler...");
					}
				}
			} else if (e.getButton() == 3) {
				if (!button.isFlag()) {
					button.setIcon(new ImageIcon(resizedFlagImage));
					button.setFlag(true);
				} else {
					button.setIcon(null);
					button.setFlag(false);
				}
			}
		}
		
	};
}
