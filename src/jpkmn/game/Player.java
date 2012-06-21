package jpkmn.game;

import java.io.PrintWriter;

import jpkmn.Constants;
import jpkmn.exceptions.LoadException;
import jpkmn.exe.Driver;
import jpkmn.game.item.Bag;
import jpkmn.game.pokemon.Pokedex;
import jpkmn.game.pokemon.Pokemon;
import jpkmn.game.pokemon.storage.PCStorage;

public class Player extends PokemonTrainer {
  private int a;

  public final Bag bag;
  public final PCStorage box;
  public final Pokedex dex;

  public Player(String serial) throws LoadException {
    super();

    if (!serial.equals(Driver.officialSerial))
      throw new LoadException("Improper file version: " + _serial);

    _serial = serial;
    dex = new Pokedex();
    bag = new Bag();
    box = new PCStorage();
  }

  public int cash() {
    return _cash;
  }

  public void addCash(int change) {
    _cash += change;
  }

  public void setCash(int c) {
    _cash = c;
  }

  public int badges() {
    return _badge;
  }

  public int addBadge() {
    return ++_badge;
  }

  public void setBadge(int b) {
    _badge = b;
  }

  public void toFile(PrintWriter p) {
    p.println(_serial);
    p.println(_name);
    p.println(_cash);
    p.println(_badge);

    for (int i = 0; i < Constants.PARTYSIZE; i++)
      if (party.get(i) != null)
        p.println(party.get(i).saveToString());
      else
        p.println("||");

    p.println(bag.saveToString());

    p.println(dex.saveSeenToString());
    p.println(dex.saveOwnToString());

    for (Pokemon pkmn : box)
      p.println(pkmn.saveToString());
  }

  public boolean equals(Object o) {
    if (!(o instanceof Player)) return false;
    return ((Player) o)._id == _id;
  }

  @Override
  public void notify(String... s) {
    // TODO Complete this
  }

  int _id;
  private String _serial;
  private int _cash, _badge;
}