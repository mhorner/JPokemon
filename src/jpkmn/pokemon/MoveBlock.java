package jpkmn.pokemon;

import java.util.ArrayList;

import lib.MoveMap;

import jpkmn.Driver;
import jpkmn.gui.Tools;
import jpkmn.pokemon.move.Move;

public class MoveBlock {
  public MoveBlock(Pokemon p) {
    pkmn = p;
    moves = new Move[Driver.MOVENUMBER];

    setDefaults();
  }

  public int amount() {
    return amount;
  }

  public Move get(int i) {
    if (i > amount || i < 0) return null;
    return moves[i];
  }

  public void restoreAll() {
    for (int i = 0; i < amount; i++) {
      if (moves[i] != null) moves[i].restore();
    }
  }

  public String list() {
    String response = "[";

    for (int i = 0; i < amount; ++i) {
      if (moves[i] != null) response += moves[i].name();
      if (i != 3) response += " ";
    }

    return response + "]";
  }

  public void check() {
    MoveMap m = MoveMap.getMapForPokemonNumberAtLevel(pkmn.number(),
        pkmn.level());
    if (m == null) return;

    add(m.getMove_number());
  }

  public void add(int number) {
    int position;

    if (amount < moves.length)
      position = amount++;
    else
      position = Tools.askMove(pkmn, new Move(number, pkmn));

    add(number, position);
  }

  public void add(int number, int position) {
    if (!contains(number))
      moves[position] = new Move(number, pkmn);
  }

  /**
   * Picks up to 4 moves randomly from the list of moves that this Pokemon
   * could have learned by this level, and assigns them.
   */
  private void setDefaults() {
    ArrayList<Integer> possible = new ArrayList<Integer>();

    for (int l = 1; l <= pkmn.level(); l++) {
      MoveMap m = MoveMap.getMapForPokemonNumberAtLevel(pkmn.number(), l);

      if (m != null && !possible.contains(m)) possible.add(m.getMove_number());
    }

    while (!possible.isEmpty() && amount < moves.length)
      add(possible.remove((int) (Math.random() * possible.size())), amount++);
  }

  private boolean contains(int number) {
    for (Move m : moves)
      if (m != null && m.number() == number) return true;
    return false;
  }

  private int amount;
  private Move[] moves;
  private Pokemon pkmn;
}