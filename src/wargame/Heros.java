package wargame;

public class Heros extends Soldat{
	private TypesH typeHerosAlea;
	
	/**
	 * Constructeur qui initialise aléatoirement un objet Heros avec une des valeurs de enm TypesH
	 */
	public Heros() {
        super(TypesH.getTypeHAlea().getPoints(), TypesH.getTypeHAlea().getPortee());
        this.typeHerosAlea = TypesH.getTypeHAlea();
	}
	
	/**
	 * Renvoie quelle valeur de enum est l'objet Heros 
	 */
	@Override
	public TypesH getTypeHeros() {
		return this.typeHerosAlea;
	}
	
	@Override
	protected TypesM getTypeMonstre() {return null;} // on ignore cette methode dans Heros
	
}