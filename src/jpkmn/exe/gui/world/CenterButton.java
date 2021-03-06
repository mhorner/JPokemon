package jpkmn.exe.gui.world;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import jpkmn.exe.gui.GameWindow;

public class CenterButton extends JButton implements ActionListener {
  public CenterButton(WorldView view, int areaID) {
    super("Pokemon Center");

    _window = view.window;
    _areaID = areaID;

    addActionListener(this);
  }

  @Override
  public void actionPerformed(ActionEvent arg0) {
    System.out.println("You clicked Center");
    // TODO : Generate battle
  }

  private int _areaID;
  private GameWindow _window;
  private static final long serialVersionUID = 1L;
}