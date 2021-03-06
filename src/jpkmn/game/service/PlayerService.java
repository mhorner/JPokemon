package jpkmn.game.service;

import jpkmn.exceptions.ServiceException;
import jpkmn.game.player.Player;
import jpkmn.game.player.PlayerRegistry;
import jpkmn.map.Area;
import jpkmn.map.AreaConnection;
import jpkmn.map.Direction;

import org.json.JSONException;
import org.json.JSONObject;

public class PlayerService {
  public static JSONObject areaInfo(int playerID) throws ServiceException {
    Player player = PlayerRegistry.get(playerID);

    if (player == null)
      throw new ServiceException("PlayerID " + playerID + " not found");

    Area area = player.area();

    if (area == null)
      throw new ServiceException(player.name() + " has no registered area!");

    try {
      return JSONMaker.make(area);
    } catch (JSONException e) {
      throw new ServiceException("There was an error. It's not your fault.");
    }
  }

  public static void areaChange(int pID, String dir) throws ServiceException {
    Player player = PlayerRegistry.get(pID);

    if (player == null)
      throw new ServiceException("PlayerID " + pID + " not found");

    // HACKY - But i'm too tired to do it the right way right now
    Direction direction = null;
    for (Direction d : Direction.values())
      if (d.toString().equals(dir)) direction = d;
    // END HACK

    Area area = player.area();

    if (area == null)
      throw new ServiceException(player.name() + " has no registered area!");

    AreaConnection connection = area.neighbor(direction);

    if (connection == null)
      throw new ServiceException(area.name() + " cannot access " + direction);
    if (!connection.test(player))
      throw new ServiceException("You are not qualified to go  " + direction);

    connection.use(player);
  }
}