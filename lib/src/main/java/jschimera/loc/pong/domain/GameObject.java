package jschimera.loc.pong.domain;

import org.jbox2d.dynamics.Body;
import org.joml.Vector2f;
import org.joml.Vector4f;

import jschimera.loc.render.data.RenderInfo;
import jschimera.loc.render.physics.PhysicalObject;
import jschimera.loc.render.physics.Physics2D;
import jschimera.loc.render.physics.Physics2D.STATE;

public abstract class GameObject implements RenderInfo, PhysicalObject{

	protected Physics2D.STATE state = STATE.RUNNING;
	private final long uniqueRenderId;
	private final int zIndex;
	private int positionOfObjectInGpuBuffer = -1;
	private boolean reDraw = true;
	private Vector4f color = new Vector4f(0,1,0,1);
	private Vector2f position = new Vector2f(0,0);
	private Vector2f scale = new Vector2f(25,25);
	private Vector2f[] textureCoordinates = new Vector2f[]{
            new Vector2f(1, 0),
            new Vector2f(0, 1),
            new Vector2f(1, 1),
            new Vector2f(0, 0)
    };
	
	private Vector2f initialPosition;
	
	protected Body body;
	
	protected GameObject(long uniqueRenderId, int zIndex){
		this.uniqueRenderId = uniqueRenderId;
		this.zIndex = zIndex;
	}
	
	public void setInitialResetStatePosition(Vector2f position) {
		this.initialPosition= position;
	}
	
	protected Vector2f getInitialPosition() {
		return initialPosition;
	}
	
	public Body getBody() {
		return body;
	}
	
	
	@Override
	public int getPositionInGpuDrawData() {
		return positionOfObjectInGpuBuffer;
	}
	
	@Override
	public void setPositionInGpuDrawData(int positionInGpuDrawData) {
		this.positionOfObjectInGpuBuffer = positionInGpuDrawData;
	}
	
	@Override
	public Vector4f getColor() {
		return color;
	}

	public void setColor(Vector4f color) {
		this.color = color;
	}
	
	@Override
	public Vector2f[] getTextureCoordinates() {
		return textureCoordinates;
	}
	
	public void setTextureCoordinates(Vector2f[] textureCoordinates) {
		this.textureCoordinates = textureCoordinates;
	}

	@Override
	public float getRotation() {
		return 0;
	}
	
	public void setPosition(Vector2f position) {
		this.position = position;
	}

	@Override
	public Vector2f getPosition() {
		return position;
	}
	
	@Override
	public Vector2f getScale() {
		return scale;
	}
	
	public void setScale(Vector2f scale) {
		this.scale = scale;
	}

	@Override
	public long getUniqueRenderId() {
		return uniqueRenderId;
	}

	@Override
	public int getZindex() {
		return zIndex;
	}
	
	@Override
	public boolean isRedraw() {
		return reDraw;
	}
	
	@Override
	public void setRedraw(boolean reDraw) {
		this.reDraw = reDraw;
	}
	
	@Override
	public void setRawBody(Body body) {
		this.body = body;
	}
	
	@Override
	public void setState(STATE state) {
		this.state = state;
	}
	
}
