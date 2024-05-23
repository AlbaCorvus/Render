package pong;

import org.joml.Vector2f;
import org.joml.Vector4f;

import jschimera.loc.pong.domain.Ball;
import jschimera.loc.pong.domain.Boundary;
import jschimera.loc.pong.domain.GameController;
import jschimera.loc.pong.domain.GameObject;
import jschimera.loc.pong.domain.GoalBoundary;
import jschimera.loc.pong.domain.Paddle;
import jschimera.loc.pong.domain.Player1;
import jschimera.loc.pong.domain.Player2;
import jschimera.loc.pong.domain.Text;
import jschimera.loc.pong.listener.KeyListener;
import jschimera.loc.render.Renderer;
import jschimera.loc.render.thread.Controller;

public class Pong {

	public void run() {
		runRendering();
	}
	
	private void runRendering() {
		Renderer renderer = new Renderer();
		renderer.init(null, null, null, KeyListener::keyCallback, null);
		GameController gameController = new GameController(renderer);
		GameObject paddle = new Paddle(0l, 1);
		paddle.setColor(new Vector4f(1, 0, 0, 1));
		paddle.setPosition(new Vector2f(-230, 50));
		paddle.setScale(new Vector2f(5, 25));
		Controller controller = new Player1(paddle);

		GameObject paddle2 = new Paddle(1l, 1);
		paddle2.setColor(new Vector4f(1, 1, 0, 1));
		paddle2.setPosition(new Vector2f(370, 50));
		paddle2.setScale(new Vector2f(5, 25));

		Controller controller2 = new Player2(paddle2);
		GameObject ball = new Ball(2l, 1);
		ball.setColor(new Vector4f(1, 1, 1, 1));
		ball.setPosition(new Vector2f(65, 150));
		ball.setScale(new Vector2f(5, 5));
		ball.setInitialResetStatePosition(ball.getPosition());

		Text scorePlayer1 = new Text(3l, 1, '0');
		scorePlayer1.setPosition(new Vector2f(60, 285));
		Text scorePlayer2 = new Text(3l, 1, '0');
		scorePlayer2.setPosition(new Vector2f(80, 285));

		Boundary top = new Boundary(new Vector2f(-230, 305), new Vector2f(700, 1));
		Boundary bottom = new Boundary(new Vector2f(-230, 0), new Vector2f(700, 1));
		Boundary left = new GoalBoundary(new Vector2f(-250, 0), new Vector2f(1, 350));
		left.setCustomCallback(() -> {
			int score = scorePlayer2.getValue();
			scorePlayer2.setValue((char) ++score);
			scorePlayer2.setRedraw(true);
		});
		Boundary right = new GoalBoundary(new Vector2f(400, 0), new Vector2f(1, 350));
		right.setCustomCallback(() -> {
			int score = scorePlayer1.getValue();
			scorePlayer1.setValue((char) ++score);
			scorePlayer1.setRedraw(true);
		});

		renderer.addRenderInfoObject(paddle);
		renderer.addRenderInfoObject(paddle2);
		renderer.addRenderInfoObject(ball);
		renderer.addPhysicsObject(top);
		renderer.addPhysicsObject(bottom);
		renderer.addPhysicsObject(left);
		renderer.addPhysicsObject(right);
		renderer.addRenderInfoObject(scorePlayer1);
		renderer.addRenderInfoObject(scorePlayer2);
		renderer.addController(controller);
		renderer.addController(controller2);
		renderer.addRController(gameController);
		renderer.render();
	}

}
