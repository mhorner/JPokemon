package jpkmn.exe.launcher;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import jpkmn.exceptions.LoadException;
import jpkmn.game.player.PlayerRegistry;

public class PlayButton extends JButton implements ActionListener {
  public PlayButton(Launcher l) {
    super("Play");

    _launcher = l;

    setFocusable(false);
    setBorderPainted(false);
    setBounds(550, 60, 110, 30);
    setBackground(new Color(206, 77, 77));

    addActionListener(this);
  }

  @Override
  public void actionPerformed(ActionEvent event) {
    String name = JOptionPane.showInputDialog(_launcher,
        "Please enter your username", "LOGIN", JOptionPane.QUESTION_MESSAGE);

    if (name == null) return;

    try {
      // Should do this with PlayerService... but i'm tired and whatever
      PlayerRegistry.fromFile(name).screen.showWorld();
      _launcher.dispose();
    } catch (LoadException l) {
      JOptionPane.showMessageDialog(_launcher, l.getMessage(), "LOGIN ERROR",
          JOptionPane.ERROR_MESSAGE);
    } catch (Exception e) {
      JOptionPane.showMessageDialog(_launcher, e.toString(), "LOGIN ERROR",
          JOptionPane.ERROR_MESSAGE);
    }
  }

  private Launcher _launcher;
  private static final long serialVersionUID = 1L;
}