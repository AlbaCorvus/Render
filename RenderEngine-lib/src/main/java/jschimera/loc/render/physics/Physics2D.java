package jschimera.loc.render.physics;

import java.util.ArrayList;
import java.util.List;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import jschimera.loc.render.physics.observe.Observable;
import jschimera.loc.render.physics.observe.Observer;

public class Physics2D implements Observer<PhysicalObject>{

	private Vec2 gravity = new Vec2(0, -10.0f);
    private World world = new World(gravity);

    private float physicsTime = 0.0f;
    private float physicsTimeStep = 1.0f / 60.0f;
    private int velocityIterations = 8;
    private int positionIterations = 3;
    
    private List<PhysicalObject> observables = new ArrayList<>();
    
    public Physics2D() {
    	world.setContactListener(new PhysicsListener());
    }
    
    private BodyDef createBodyDefinition(PhysicalObject object) {
    	BodyDef bodyDef = new BodyDef();
        bodyDef.angle = (float)Math.toRadians(object.getRotation());
        bodyDef.position.set(object.getPosition().x, object.getPosition().y);
        bodyDef.angularDamping = object.getAngularDamping();
        bodyDef.linearDamping = object.getLinearDamping();
        bodyDef.fixedRotation = object.isFixedRotation();
        bodyDef.bullet = object.isContinuousCollision();
        bodyDef.gravityScale = object.getGravityScale();
        bodyDef.angularVelocity = object.getAngularVelocity();
        bodyDef.userData = object;
        bodyDef.type = object.getBodyType();
        return bodyDef;
    }
    
    public void addPhysicalObject(PhysicalObject object) {
    	observables.add(object);
        addBox2DCollider(object);
    }
    
    private void addBox2DCollider(PhysicalObject object) {
    	BodyDef bodyDef = createBodyDefinition(object);
    	Body body = this.world.createBody(bodyDef);
        body.m_mass = object.getMass();
        assert body != null : "Raw body must not be null";

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(object.getScale().x, object.getScale().y, new Vec2(body.getLocalCenter()), 0);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = object.getFriction();
        fixtureDef.userData = object;
        fixtureDef.isSensor = object.isSensor();
        body.createFixture(fixtureDef);
        object.setState(STATE.RUNNING);
        object.setRawBody(body);
    }
    
    public void update(float dt) {
        physicsTime += dt;
        if (physicsTime >= 0.0f) {
            physicsTime -= physicsTimeStep;
            world.step(physicsTimeStep, velocityIterations, positionIterations);
            resetIfRequired();
            notifyObservables();
        }
    }

	private void resetIfRequired() {
		observables.stream().filter(phyObj -> phyObj.getState() == STATE.RESET).forEach(Physics2D.this::destroy);
		observables.stream().filter(phyObj -> phyObj.getState() == STATE.STANDBY).forEach(Physics2D.this::addOnStandBy);
	}
	
	private void destroy(PhysicalObject object) {
		world.destroyBody(object.getBody());
		object.setState(STATE.STANDBY);
	}
	
	private void addOnStandBy(PhysicalObject object) {
		addBox2DCollider(object);
	}
	
	public enum STATE{
		RESET, RUNNING, STANDBY
	}



	@Override
	public void addObservable(PhysicalObject observable) {
		observables.add(observable);
	}

	@Override
	public void notifyObservables() {
		observables.forEach(Observable::receiveUpdate);
	}

	
	
}
