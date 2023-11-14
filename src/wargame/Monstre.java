package wargame;

public class Monstre extends Soldat {
	
	private TypesM typeMonstreAlea;
	
	public Monstre() {
        super(TypesM.getTypeMAlea().getPoints(), TypesM.getTypeMAlea().getPortee());
        this.typeMonstreAlea = TypesM.getTypeMAlea();
	}
}
