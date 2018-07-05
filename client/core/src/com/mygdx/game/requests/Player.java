package com.mygdx.game.requests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Player {
    private	int	id;
    private int	points;
    private TreeMap<Integer, String>	characterList;

    private Player(int id) {
        this.id = id;
        this.points = 0;
        this.characterList = null;
    }

    private Player(int id, int points) {
        this.id = id;
        this.points = points;
        this.characterList = null;
    }

    public ArrayList<String>     getCharactersName() {
        return (ArrayList<String>) characterList.values();
    }

    public void	addCharacter(String name, int id) {
        if (name == null)
            return;
        if (characterList == null)
            characterList = new TreeMap<Integer, String>();
        characterList.put(id, name);
        System.out.println("Added + " + name);
    }

    public int	getId() {
        return id;
    }

    public void	spendPoints(int points) {
        // Send an URL request to spend points

    }

    public static boolean	loginExists(String login) throws IOException, JSONException {
       // ArrayList<BasicNameValuePair> urlParameters;
        String      urlParameters;
        String		url;
        JSONObject  jsonObject;
        String		result;


     //   new NameValuePair("234324", "323432");
        url = JsonHandler.domain + "/player_exist";

        //String urlParameters = "name=Jack&occupation=programmer";
        urlParameters = "login=" + login;

        /*urlParameters = new ArrayList<BasicNameValuePair>();
        urlParameters.add(new BasicNameValuePair("login", login + ""));*/
        //url = JsonHandler.domain;
        jsonObject = JsonHandler.readJsonFromUrl(url, urlParameters, true);
        if (jsonObject == null)
            return true;
        result = jsonObject.getString("result");
        if (result.equals("false"))
            return false;
        return true;
    }

    private static void	setErrorMessage(String message) {
        JsonHandler.errorMessage = message;
    }

    private static Player	getPlayerFromUrl(String url, String urlParameters, boolean isPostMethod) throws JSONException, IOException {
        Player      player;
        String		field;
        String      characterName;
        JSONObject	jsonObject;
        int			points;
        int			id;
        int         characterId;

        jsonObject = JsonHandler.readJsonFromUrl(url, urlParameters, isPostMethod);
        if (jsonObject == null)
            return null;
        try {
            field = jsonObject.getString("id");
            id = Integer.parseInt(field);
        } catch (NumberFormatException e) {
            setErrorMessage("Invalid number format for \"id\" field");
            return null;
        }
        try {
            field = jsonObject.getString("points");
            points = Integer.parseInt(field);
        } catch (NumberFormatException e) {
            setErrorMessage("Invalid number format for \"points\" field");
            return null;
        }
        player = new Player(id, points);
        if (jsonObject.has("characters") == false)
            return player;
        JSONArray arr = jsonObject.getJSONArray("characters");

        for (int i = 0; i < arr.length(); i++)
        {
            field = arr.getJSONObject(i).getString("id");
            characterId = Integer.parseInt(field);
            characterName = arr.getJSONObject(i).getString("nickname");
            player.addCharacter(characterName, characterId);
        }
        return player;
    }

    public static Player	loginPlayer(String login, String password) throws IOException, JSONException {
        String url;
      //  String  urlParameters;
       // List<BasicNameValuePair> urlParameters;

        if (loginExists(login) == false) {
            setErrorMessage("Player does not exist");
            return null;
        }

        url = JsonHandler.domain + "/login";
//        urlParameters = new ArrayList<BasicNameValuePair>();
//        urlParameters.add(new BasicNameValuePair("login", login + ""));
//        urlParameters.add(new BasicNameValuePair("password", password + ""));
        String urlParameters = "login=" + login + "&password=" + password;

        //String urlParameters = "name=Jack&occupation=programmer";
        return getPlayerFromUrl(url, urlParameters, true);
    }

    public static Player	registerNewPlayer(String login, String password) throws IOException, JSONException {
       // List<BasicNameValuePair>    urlParameters;
        String                      url;

        if (loginExists(login)) {
            setErrorMessage("Login already exist");
            return null;
        }
        url = JsonHandler.domain + "/register_player";
//        urlParameters = new ArrayList<BasicNameValuePair>();
//        urlParameters.add(new BasicNameValuePair("login", login + ""));
//        urlParameters.add(new BasicNameValuePair("password", password + ""));

        String urlParameters = "login=" + login + "&password=" + password;
        return getPlayerFromUrl(url, urlParameters, true);
    }
}

