package kone;

public class ForceRegistration {
	
	protected Body body;
	protected ForceGenerator fg;
	
	public ForceRegistration()
	{
		body = null;
		fg = null;
	}
	
	public ForceRegistration(Body body, ForceGenerator fg)
	{
		this.body = body;
		this.fg = fg;
	}

}
