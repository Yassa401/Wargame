package wargame;

public class Monstre extends Soldat {
	
	private TypesM typeMonstreAlea;
	
	/**
	 * Constructeur qui initialise aléatoirement un objet Monstre avec une des valeurs de enm TypesM
	 */
	public Monstre() {
        super(TypesM.getTypeMAlea().getPoints(), TypesM.getTypeMAlea().getPortee());
        this.typeMonstreAlea = TypesM.getTypeMAlea();
	}
	
	/**
	 * Renvoie quelle valeur de enum est l'objet Monstre
	 */
	@Override
	public TypesM getTypeMonstre() {
		return this.typeMonstreAlea;
	}

	@Override
	protected TypesH getTypeHeros() {return null;} // on ignore cette methode dans Monstre
}
