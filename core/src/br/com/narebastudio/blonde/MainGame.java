package br.com.narebastudio.blonde;

import br.com.narebastudio.blonde.conection.TwitchConection;
import br.com.narebastudio.blonde.conection.TwitchInfo;
import br.com.narebastudio.blonde.controller.Controller;
import br.com.narebastudio.blonde.personagem.BlondeJoseph;
import br.com.narebastudio.blonde.personagem.BlondeSoundSource;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.io.IOException;

import static com.badlogic.gdx.Gdx.audio;

public class MainGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	TwitchInfo accountInfo;
	TwitchConection twitchConection;
	BlondeJoseph blonde;
	Controller controller;
	Texture personagensTex;
	Music somTest;
	@Override
	public void create () {
		loadTextures();
		batch = new SpriteBatch();
		blonde = new BlondeJoseph(personagensTex, new Vector2(180,180), 0, 0, 128, 128, 1,1);
		controller = new Controller();
		controller.setBlonde(blonde);
		loadSounds();
		//region Dados Sensíveis
		accountInfo = new TwitchInfo("narebabr","oauth:dutpzoq8g066qz8xecxin3csxk7u99","narebabr");
		//endregion
		twitchConection = new TwitchConection("irc.chat.twitch.tv", 6667, controller);
		try {
			twitchConection.connect(accountInfo.getApiKey(), accountInfo.getUsername(), accountInfo.getChannel());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		try   {
			twitchConection.readChat();
			blonde.update();
		}catch (Exception e)   {

		}
		draw();
	}
	public void draw()   {
		batch.begin();
		blonde.desenhar(batch);
		batch.end();
	}
	@Override
	public void dispose () {
		batch.dispose();
	}
	public void loadTextures()   {
		personagensTex = new Texture("sprites/blonde_joseph_spritesheet.png");
	}
	public void loadSounds()   {
		BlondeSoundSource.loadSounds();
	}
}