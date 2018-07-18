package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.music.GameMusic;
import com.mygdx.game.logger.Logger;
import com.mygdx.game.requests.JsonHandler;
import com.mygdx.game.requests.Player;
import com.mygdx.game.requests.PlayerAccount;
import com.mygdx.game.gameSets.GGame;

import de.tomgrill.gdxdialogs.core.GDXDialogs;
import de.tomgrill.gdxdialogs.core.GDXDialogsSystem;

public class LoginScreen implements Screen{
    // New version
	private Logger log = new Logger();
    private GGame parent;
	private Stage stage;
	private Skin skin;
	private Label label;
	private Label labelName;
	private Label labelPassword;
    private TextButton forgotPassword;
	private Viewport viewport;
	private Camera camera;
	private Music loginMusic;
	private GDXDialogs dialogs;
	private BackgroundAnimation animationScreenTest;

	//trying...
    private Label titleLabel;
    private Label volumeMusicLabel;
    private Label volumeSoundLabel;
    private Label musicOnOffLabel;
    private Label soundOnOffLabel;
    //trying...

	private Sound click;
	private Skin skin2;


	public LoginScreen(GGame g){
    	parent = g;
    	dialogs = GDXDialogsSystem.install();
		GameMusic.startLoginMusic();
		click = parent.assetsManager.aManager.get("data/click.wav");
		stage = new Stage();
		viewport = new StretchViewport(800, 480, stage.getCamera());
		stage.setViewport(viewport);
        animationScreenTest = new BackgroundAnimation(parent);
		// tells our asset manger that we want to load the images set in loadImages method
		parent.assetsManager.loadImages();
// tells the asset manager to load the images and wait until finished loading.
		parent.assetsManager.aManager.finishLoading();
        }

    @Override
        public void show() {
    	stage.clear();

		skin = new Skin(Gdx.files.internal("skin2/clean-crispy-ui.json"));

		// Create a table that fills the screen. Everything else will go inside this table.
		Table table = new Table();
		table.setFillParent(true);
//		table.setDebug(true);

		stage.addActor(table);

		/*Settings! trying...*/



        //music volume
        final Slider volumeMusicSlider = new Slider( 0f, 1f, 0.1f,false, skin );
        volumeMusicSlider.setValue(parent.getPreferences().getMusicVolume());
        volumeMusicSlider.addListener( new EventListener() {
            @Override
            public boolean handle(Event event) {
                parent.getPreferences().setMusicVolume( volumeMusicSlider.getValue() );
                return false;
            }
        });
        //sound volume
        final Slider volumeSoundSlider = new Slider( 0f, 1f, 0.1f,false, skin );
        volumeSoundSlider.setValue( parent.getPreferences().getSoundVolume());
        volumeSoundSlider.addListener( new EventListener() {
            @Override
            public boolean handle(Event event) {
                parent.getPreferences().setSoundVolume(volumeSoundSlider.getValue());
                return false;
            }
        });



        //music
        final CheckBox musicCheckbox = new CheckBox(null, skin);
        musicCheckbox.setChecked( parent.getPreferences().isMusicEnabled() );
        musicCheckbox.addListener( new EventListener() {
            @Override
            public boolean handle(Event event) {
                boolean enabled = musicCheckbox.isChecked();
                parent.getPreferences().setMusicEnabled( enabled );
                return false;
            }
        });
        //sound
        final CheckBox soundCheckbox = new CheckBox(null, skin );
        soundCheckbox.setChecked( parent.getPreferences().isSoundEnabled() );
        soundCheckbox.addListener( new EventListener() {
            @Override
            public boolean handle(Event event) {
                boolean enabled = soundCheckbox.isChecked();
                parent.getPreferences().setSoundEnabled( enabled );
                return false;
            }
        });

        //return to main screen
        final TextButton back = new TextButton("Back", skin);
        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(parent.getBackFromSettings());
            }
        });

        //making labels
        titleLabel = new Label( "Preferences", skin);
        volumeMusicLabel = new Label( "Music Volume", skin );
        volumeSoundLabel = new Label( "Sound Volume", skin  );
        musicOnOffLabel = new Label( "Music Effect", skin  );
        soundOnOffLabel = new Label( "Sound Effect", skin  );

        /*Settings! trying...*/





		//add label
		label = new Label("", skin, "error");
		labelName = new Label(null, skin, "fancy");
		labelName.setText("User name: ");
		labelPassword = new Label(null, skin, "fancy");
		labelPassword.setText("Password: ");
		//add text fields login/password
		final TextField loginField = new TextField(null,skin);
		loginField.setMessageText("Login goes here");
		final TextField passwordField = new TextField(null, skin);
		passwordField.setPasswordCharacter('*');
		passwordField.setPasswordMode(true);
		passwordField.setMessageText("Password goes here");

		//Forgot password
		forgotPassword = new TextButton("Forgot password?", skin);
		//add buttons to table
		TextButton register = new TextButton("Register", skin);
		TextButton submit = new TextButton("Submit", skin);
		TextButton settings = new TextButton("Settings", skin);

		register.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				click.play();
				parent.changeScreen(parent.getRegister());
			}
		});


		//add listeners to buttons
        passwordField.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //TODO Add support for ENTER KEY
            }
        });
		submit.addListener(new SubmitListener(loginField, passwordField));
		forgotPassword.addListener(new ForgotPassword());
		settings.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor){
				click.play();

                Dialog dialog = new Dialog("Settings", skin) {
                    public void result(Object obj) {
                        System.out.println("result "+obj);
                    }
                };
                dialog.getContentTable().row();
                dialog.getContentTable().add(volumeMusicLabel);
                dialog.getContentTable().add(volumeMusicSlider);
                dialog.getContentTable().row();
                dialog.getContentTable().add(musicOnOffLabel);
                dialog.getContentTable().add(musicCheckbox);
                dialog.getContentTable().row();
                dialog.getContentTable().add(volumeSoundLabel);
                dialog.getContentTable().add(volumeSoundSlider);
                dialog.getContentTable().row();
                dialog.getContentTable().add(soundOnOffLabel);
                dialog.getContentTable().add(soundCheckbox);
                dialog.getContentTable().row();
                dialog.getContentTable().add(back).colspan(2);
                dialog.show(stage);

                //parent.changeScreen(parent.getSettings());
			}
		});

		animationScreenTest.setFillParent(true);
		animationScreenTest.setZIndex(0);
		table.addActor(animationScreenTest);

		//add everything into table
		table.add(label).fillX().uniformX().colspan(2).padTop(10);
		table.row();//.pad(0, 0, 0, 0);
		table.add(labelName);
		table.add(loginField).fillX().uniformX();
		table.row().pad(5, 0, 5, 0);
		table.add(labelPassword);
		table.add(passwordField).fillX().uniformX();
		table.row().pad(40, 10, 0, 10);
		table.add(register);
		table.add(submit);
		table.row().pad(20, 0, 0, 0);
		table.add(forgotPassword);
		table.add(settings);
		table.top();

        //screenTable.add(animationScreenTest).fill().expand().uniform().pad(pad, pad, pad, pad / 2);





        Gdx.input.setInputProcessor(stage);
	}

	@Override
        public void render(float delta) {
    	//camera.update();
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// tell our stage to do actions and draw itself
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
	}

	@Override
        public void resize(int width, int height) {


    	stage.getViewport().update(width,height,true);
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

	class SubmitListener extends ChangeListener {
    	private TextField	loginField;
    	private TextField	passwordField;

		public SubmitListener(TextField loginField, TextField passwordField) {
			this.loginField = loginField;
			this.passwordField = passwordField;
		}

        @Override
        public void changed(ChangeListener.ChangeEvent event, Actor actor) {
            Player player = null;
            click.play();
            if (loginField.getText() == "") {
                log.warn("Login not inputted");
                label.setText("Write the login please");
            } else if (passwordField.getText() == "") {
                log.warn("Password not inputted");
                label.setText("Write the password please");
            } else {
                log.info("Login and Password inputted");
                String encryptedPassword;
                encryptedPassword = EncryptPassword.encrypt(passwordField.getText());
                //encryptedPassword = passwordField.getText();
                try {
                    if (PlayerAccount.loginPlayer(loginField.getText(), encryptedPassword)) {
                        log.info("Login success");
                        GameMusic.stopAndDisposeLoginOst();
                        parent.changeScreen(parent.getCharacterSelect());
                    } else {
                        log.info("Incorrect login/password");
                        label.setText(JsonHandler.errorMessage);
                    }
                    //player = Player.loginPlayer(loginField.getText(), encryptedPassword);
                } catch (Exception e) {
                    log.error("Something went wrong occurred");
                    e.printStackTrace();
                    if (JsonHandler.errorMessage != null)
                        label.setText(JsonHandler.errorMessage);
                    else
                        label.setText("Something went wrong");
                }
                passwordField.setText("");
            }
        }
    }

	class ForgotPassword extends ChangeListener{

        @Override
        public void changed(ChangeEvent changeEvent, Actor actor) {
            forgotPassword();
        }
    }

    private void forgotPassword(){
		click.play();
        Dialog dialog = new Dialog("Lmao", skin) {
            public void result(Object obj) {
                System.out.println("result "+obj);
            }
        };

        dialog.text("Sucks to be you");
        dialog.button("ok", "ok"); //sends "true" as the result
        dialog.show(stage);
    }
}
