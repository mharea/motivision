package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.gameSets.GGame;
import com.mygdx.game.requests.Player;
import com.mygdx.game.requests.PlayerAccount;
import com.mygdx.game.requests.Profile;
import com.mygdx.game.requests.Team;

import org.json.JSONException;

import java.io.IOException;
import java.util.LinkedHashMap;

import sun.awt.image.ImageWatched;

public class CreateCharacterScreen implements Screen {

    private GGame parent;
    private Stage stage;
    private Viewport viewport;
    private Skin skin;
    private TextField nameText;
    private TextField teamText;
    private CheckBox checkboxTeam;
    private CheckBox checkboxMale;
    private CheckBox checkboxFemale;
    private Label labelHeadNumber;
    private Label labelBodyNumber;
    private Texture textureCastle;
    private Integer castleChoice = 1;
    private Integer bodyType = 1;
    private Integer headType = 1;
    private Boolean checkboxMaleBoolean = true;
    private Boolean checkboxFemaleBoolean = false;
    private Boolean checkBoxTeamBoolean = false;
    private String defaultNameText;
    private String defaultTeamText;


    public CreateCharacterScreen(GGame g) {
        parent = g;
        skin = new Skin(Gdx.files.internal("skin1/neon-ui.json"));
        stage = new Stage();
        viewport = new StretchViewport(800, 480, stage.getCamera());
        stage.setViewport(viewport);
        onCreate();
    }

    public void onCreate() {
        nameText = new TextField(null, skin);
        nameText.setMessageText(defaultNameText);
        teamText = new TextField(null, skin);
        teamText.setMessageText(defaultTeamText);
        textureCastle = new Texture("teamCastle1.png");
        defaultNameText = "Enter name here";
        defaultTeamText = "Enter team name here";
    }

    @Override
    public void show() {
        Texture texture;
        Image image;

        stage.clear();
        float pad = 5;

        // Character Sprite
        texture = PlayerAccount.getTexture(headType, bodyType);
        image = new Image(texture);

        final Image imageCastle = new Image(textureCastle);

        //making labels
        final Label labelName = new Label("Name", skin);
        final Label labelGender = new Label("Gender", skin);

        final Label labelHead = new Label("Head", skin);
        labelHeadNumber = new Label("1", skin);
        labelHeadNumber.setText(String.valueOf(headType));

        final Label labelBody = new Label("Body", skin);
        labelBodyNumber = new Label("1", skin);
        labelBodyNumber.setText(String.valueOf(bodyType));

        final Label labelTeam = new Label("Team", skin);

        //creating checkboxes for gender
        checkboxMale = new CheckBox("Male", skin);
        checkboxMale.setChecked(checkboxMaleBoolean);
        checkboxFemale = new CheckBox("Female", skin);
        checkboxFemale.setChecked(checkboxFemaleBoolean);

        //group up 2 gender choice checkboxes
        ButtonGroup genderCheckBoxGroup = new ButtonGroup(checkboxFemale, checkboxMale);
        genderCheckBoxGroup.setMaxCheckCount(1);

        //creating checkbox for team
        checkboxTeam = new CheckBox("Create new Team", skin);
        checkboxTeam.setChecked(checkBoxTeamBoolean);

        //textfields for team and name


        //making arrow buttons
        TextButton arrowHeadLeft = new TextButton("<", skin);
        TextButton arrowHeadRight = new TextButton(">", skin);
        TextButton arrowBodyLeft = new TextButton("<", skin);
        TextButton arrowBodyRight = new TextButton(">", skin);
        TextButton arrowCastleLeft = new TextButton("<", skin);
        TextButton arrowCastleRight = new TextButton(">", skin);
//        ImageButton arrowCastleLeft = new ImageButton(skin);

        //text button
        TextButton buttonBack = new TextButton("Back", skin);
        TextButton buttonOk = new TextButton("Ok", skin);

        //creating table with Character Settings
        Table tableActivities = new Table();
        Table headTable = new Table();
        Table bodyTable = new Table();
        final Table castleTable = new Table();
        Table buttonTable = new Table();
        Table screenTable = new Table();

        checkboxMale.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                checkboxMaleBoolean = true;
                checkboxFemaleBoolean = false;
            }
        });

        checkboxFemale.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                checkboxFemaleBoolean = true;
                checkboxMaleBoolean = false;
            }
        });

        checkboxTeam.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (checkBoxTeamBoolean == true){
                    checkBoxTeamBoolean = false;
                }
                else
                    checkBoxTeamBoolean = true;
            }
        });

        buttonBack.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(parent.getCharacterSelect());
            }
        });

        buttonOk.addListener(new CreateCharacter());

        // Previous head type
        arrowHeadLeft.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                if(headType == 1) {
                    headType = 3;
                }else
                if(headType == 2 || headType == 3){
                    headType--;
                }
                show();
            }
        });

        // Next head type
        arrowHeadRight.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                if(headType == 3) {
                    headType = 1;
                }else
                if(headType == 2 || headType == 1){
                    headType++;
                }
                show();
            }
        });

        // Previous body type
        arrowBodyLeft.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                if(bodyType == 1) {
                    bodyType = 3;
                }else
                if(bodyType == 2 || bodyType == 3){
                    bodyType--;
                }
                show();
            }
        });

        // Next body type
        arrowBodyRight.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                if(bodyType == 3) {
                    bodyType = 1;
                }else
                if(bodyType == 2 || bodyType == 1){
                    bodyType++;
                }
                show();
            }
        });

        arrowCastleLeft.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                if(castleChoice == 1) {
                    castleChoice = 3;
                    textureCastle = new Texture("teamCastle3.png");
                }else
                if(castleChoice == 2){
                    castleChoice--;
                    textureCastle = new Texture("teamCastle1.png");
                }else
                if(castleChoice == 3){
                    castleChoice--;
                    textureCastle = new Texture("teamCastle2.png");
                }
                show();
            }
        });

        arrowCastleRight.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                if(castleChoice == 1){
                    castleChoice++;
                    textureCastle = new Texture("teamCastle2.png");
                }else
                if(castleChoice == 2){
                    castleChoice++;
                    textureCastle = new Texture("teamCastle3.png");
                }else
                if(castleChoice == 3){
                    castleChoice = 1;
                    textureCastle = new Texture("teamCastle1.png");
                }
                show();
            }
        });

        tableActivities.add(labelName).left().padLeft(Value.percentWidth(0.1f, tableActivities));
        tableActivities.add(nameText).fillX().left().colspan(2);
        tableActivities.row().pad(10, 0, 0, 0);

        tableActivities.add(labelGender).left().padLeft(Value.percentWidth(0.1f, tableActivities));
        tableActivities.add(checkboxMale)
                .expand().fill();
        //.getActor().getCells().get(0).size(Value.percentHeight(1.0f, checkboxMale));
        tableActivities.add(checkboxFemale)
                .expand().fill();
        //.getActor().getCells().get(0).size(Value.percentHeight(1.0f, checkboxMale));
        tableActivities.row().pad(10, 0, 0, 0);

        headTable.add(arrowHeadLeft);
        labelHeadNumber.setAlignment(Align.center);
        headTable.add(labelHeadNumber).width(Value.percentWidth(0.3f, tableActivities));
        headTable.add(arrowHeadRight);

        bodyTable.add(arrowBodyLeft);
        labelBodyNumber.setAlignment(Align.center);
        bodyTable.add(labelBodyNumber).width(Value.percentWidth(0.3f, tableActivities));
        bodyTable.add(arrowBodyRight);

        tableActivities.add(labelHead)
                .left().padLeft(Value.percentWidth(0.1f, tableActivities));
        tableActivities.add(headTable).colspan(2);
        tableActivities.row().pad(10, 0, 0, 0);
        tableActivities.add(labelBody)
                .left().padLeft(Value.percentWidth(0.1f, tableActivities));
        tableActivities.add(bodyTable).colspan(2);
        tableActivities.row().pad(10, 0, 0, 0);
        tableActivities.add(labelTeam)
                .left().padLeft(Value.percentWidth(0.1f, tableActivities));
        tableActivities.add(teamText).fillX().left().colspan(2);
        tableActivities.row().pad(10, 0, 0, 0);
        tableActivities.add(checkboxTeam)
                .left().colspan(3).padLeft(Value.percentWidth(0.1f, tableActivities));
        tableActivities.row().pad(10, 0, 0, 0);

        castleTable.add(arrowCastleLeft);
        castleTable.add(imageCastle);
        castleTable.add(arrowCastleRight);

        tableActivities.add(castleTable).colspan(3);
        tableActivities.row().pad(10, 0, 0, 0);

        buttonTable.add(buttonBack).fill().expand();
        buttonTable.add(buttonOk).fill().expand();

        tableActivities.add(buttonTable).fill().expand().colspan(3);

        //making table for whole screen in filling it up with image and table
        screenTable.setFillParent(true);
        screenTable.add(image).fill().expand().uniform().pad(pad, pad, pad, pad / 2);
        screenTable.add(tableActivities).fill().expand().uniform().pad(pad, pad / 2, pad, pad);
        stage.addActor(screenTable);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        // clear the screen ready for next set of images to be drawn
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // tell our stage to do actions and draw itself
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();

    }

    class CreateCharacter extends ChangeListener {
        public boolean validateName(String profileName) {
            boolean nameExist;

            if (profileName.length() < 5) {
                nameText.setColor(Color.RED);
                show();
                return false;
            }
            try {
                nameExist = Profile.nameExist(profileName);
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            } catch (JSONException e) {
                e.printStackTrace();
                return false;
            }
            if (nameExist == true) {
                nameText.setColor(Color.RED);
                return false;
            } else {
                nameText.setColor(Color.WHITE);
                return true;
            }
        }

        @Override
        public void changed(ChangeEvent event, Actor actor){
            String  gender;
            String  profileName;
            String  teamName;
            int     teamId;
            boolean                         nameExist;
            LinkedHashMap<String, String>   teamParams;
            //PLACE_HOLDER for registration
            LinkedHashMap<String, String>   characterParameters;

            characterParameters = new LinkedHashMap<String, String>();

            profileName = nameText.getText();
            if (validateName(profileName) == false)
                return;
            characterParameters.put(Profile.NAME, nameText.getText());

            if (checkboxMale.isChecked())
                gender = "M";
            else
                gender = "F";
            characterParameters.put(Profile.GENDER, gender);
            characterParameters.put(Profile.HEAD_TYPE, labelHeadNumber.getText() + "");
            characterParameters.put(Profile.BODY_TYPE, labelBodyNumber.getText() + "");

            teamName = teamText.getText();
            if (teamName.length() < 5) {
                teamText.setColor(Color.RED);
                return;
            }
            try {
                teamId = Team.getTeamId(teamName);
                if (checkboxTeam.isChecked()) {
                    if (teamId != -1) {
                        teamText.setColor(Color.RED);
                        return;
                    }
                    teamParams = new LinkedHashMap<String, String>();
                    teamParams.put(Team.NAME, teamName);
                    teamParams.put(Team.LOGO, "teamCastle" + castleChoice);
                    teamParams.put(Team.BATTLE, "7");
                    teamId = Team.createNewTeam(teamParams);
                    teamText.setColor(Color.WHITE);
                    characterParameters.put(Profile.IS_ADMIN, "true");
                } else if (teamId == -1) {
                    teamText.setColor(Color.RED);
                    return;
                } else {
                    characterParameters.put(Profile.IS_ADMIN, "false");
                }
            } catch (IOException e) {
                e.printStackTrace();
                teamText.setColor(Color.RED);
                return;
            } catch (JSONException e) {
                e.printStackTrace();
                teamText.setColor(Color.RED);
                return;
            }
            characterParameters.put(Profile.TEAM_ID, teamId + "");
            characterParameters.put(Profile.PLAYER_ID, PlayerAccount.getPlayerId() + "");
            try {
                if (PlayerAccount.createNewProfile(characterParameters))
                    parent.changeScreen(parent.getCharacterSelect());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}