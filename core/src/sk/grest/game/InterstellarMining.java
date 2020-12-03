package sk.grest.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/*
import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.cloud.firestore.v1.FirestoreAdminClient;
import com.google.cloud.firestore.v1.FirestoreClient;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firestore.v1.CreateDocumentRequest;
*/

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import sk.grest.game.screens.MainMenuScreen;

public class InterstellarMining extends Game {

	private Texture background;

	private SpriteBatch batch;
	private BitmapFont defaultFont;

	private Skin spriteSkin;
	private Skin uiskin;

	@Override
	public void create () {

		/*

		FirebaseApp firebaseApp = null;
		FileInputStream serviceAccount = null;
		try {
			serviceAccount = new FileInputStream(String.valueOf(Gdx.files.internal("service_key\\service_key.json")));
		FirebaseOptions options = new FirebaseOptions.Builder()
				.setCredentials(GoogleCredentials.fromStream(serviceAccount))
				.setDatabaseUrl("https://interstellarmining-e052e.firebaseio.com")
				.build();
		firebaseApp = FirebaseApp.initializeApp(options);

		} catch (IOException e) {
			e.printStackTrace();
		}

		FirestoreClient client;
		Firestore

		client.createDocument(new CreateDocumentRequest());
		DocumentReference docRef = db.collection("users").document("alovelace");

		// Add document data  with id "alovelace" using a hashmap
		Map<String, Object> data = new HashMap<>();
		data.put("first", "Ada");
		data.put("last", "Lovelace");
		data.put("born", 1815);
		//asynchronously write data
		ApiFuture<WriteResult> result = docRef.set(data);
		// ...
		// result.get() blocks on response
		System.out.println("Update time : " + result.get().getUpdateTime());


		 */

		background = new Texture(Gdx.files.internal("sprites\\background.png"));

		defaultFont = new BitmapFont(Gdx.files.internal("default.fnt"), Gdx.files.internal("default.png"), false);
		batch = new SpriteBatch();

		TextureAtlas area = new TextureAtlas(Gdx.files.internal("sprites\\sprite.atlas"));
		spriteSkin = new Skin(area);

		TextureAtlas uiarea = new TextureAtlas(Gdx.files.internal("skins\\uiskin.atlas"));
		uiskin = new Skin(Gdx.files.internal("skins\\uiskin.json"), uiarea);

		setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {
		batch.dispose();
	}

	public SpriteBatch getBatch() {
		return batch;
	}
	public Texture getBackground() {
		return background;
	}

	public Skin getUISkin() {
		return uiskin;
	}
	public Skin getSpriteSkin() {return spriteSkin;}
}
