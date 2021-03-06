package dev.codenmore.tilegame.entities.creatures;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import dev.codenmore.tilegame.Handler;
import dev.codenmore.tilegame.gfx.Animation;
import dev.codenmore.tilegame.gfx.Assets;
import dev.codenmore.tilegame.inventory.Inventory;
import dev.codenmore.tilegame.tiles.Tile;

public class Bike extends Creature {

	//Animations
	private Animation animDown, animUp, animLeft, animRight,animNitror,animNitrol;
	public static int count=0;
	int flag=-1;
	private Inventory inventory;
	Tile t;
	Creature creature;



	public Bike(Handler handler, float x, float y,Creature creature) {
		super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT,creature);

		bounds.x = 22;
		bounds.y = 44;
		bounds.width = 19;
		bounds.height = 19;

		//Animatons
		animDown = new Animation(100, Assets.bike_l);
		animUp = new Animation(100, Assets.bike_l);
		animLeft = new Animation(100, Assets.bike_l);
		animRight = new Animation(100, Assets.bike_r);
		animNitrol= new Animation(100,Assets.nitro_l);
		animNitror= new Animation(100,Assets.nitro_r);
		inventory = new Inventory(handler);
		setCreature(creature);

	}


	public void tick() {
		//Animations
		animDown.tick();
		animUp.tick();
		animRight.tick();
		animLeft.tick();
		animNitror.tick();
		animNitrol.tick();
		//Movement				
		getInput();
		move();
		handler.getGameCamera().centerOnEntity(this);

		// Inventory
		inventory.tick();
	}

	private void getInput(){
		xMove = 0;
		yMove = 0;

		if(inventory.isActive())
			return;

		if(count==-1 || count ==1)

		{
			count=0;
		}
		if(handler.getKeyManager().left)
		{
			xMove = -speed ;
		}
		if(handler.getKeyManager().right)
		{
			xMove = speed ;
		}
		if(handler.getKeyManager().up)
		{
			yMove = -speed;
		}
		if(handler.getKeyManager().down)
		{
			yMove = speed;
		}

		if((handler.getKeyManager().jump) && handler.getKeyManager().left )
		{
			xMove = -speed-speed;
			count=-1;

		}
		if((handler.getKeyManager().jump) && handler.getKeyManager().right )
		{
			xMove = speed + speed;
			count=1;

		} 


	}


	public void render(Graphics g) {
		g.drawImage(getCurrentAnimationFrame(), (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
		if(handler.getKeyManager().jump)
		{
			if(handler.getKeyManager().left)
			{
				g.drawImage(animNitrol.getCurrentFrame(), (int) (x - handler.getGameCamera().getxOffset()+75), (int) (y - handler.getGameCamera().getyOffset()+20), width-20, height-20, null);

			}
			else if(handler.getKeyManager().right)
			{
				g.drawImage(animNitror.getCurrentFrame(), (int) (x - handler.getGameCamera().getxOffset()-60), (int) (y - handler.getGameCamera().getyOffset()+20), width-20, height-20, null);

			}

		}


		//		g.setColor(Color.red);
		//		g.fillRect((int) (x + bounds.x - handler.getGameCamera().getxOffset()),
		//				(int) (y + bounds.y - handler.getGameCamera().getyOffset()),
		//				bounds.width, bounds.height);
	}

	private BufferedImage getCurrentAnimationFrame(){
		if(xMove < 0){
			return animLeft.getCurrentFrame();
		}else if(xMove > 0){
			return animRight.getCurrentFrame();
		}
		else if(yMove < 0  ){
			return animUp.getCurrentFrame();
		}
		else{
			return animDown.getCurrentFrame();
		}
	}
	public void die(){
		System.out.println("You lose");
	}
	public void postRender(Graphics g){
		inventory.render(g);
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}
	public void setCreature(Creature creature)
	{
		this.creature=creature;
	}
	public Creature getCreature()
	{
		return creature;
	}




}
