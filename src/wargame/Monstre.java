package wargame;

public class Monstre extends Soldat {
	
	private TypesM typeMonstreAlea;
	
	/**
	 * Constructeur qui initialise aléatoirement un objet Monstre avec une des valeurs de enm TypesM
	 */
	public Monstre() {
        super(TypesM.getTypeMAlea());
		this.typeMonstreAlea = super.monstre;
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
	
	@Override
	public void combat(Soldat soldat) { // le parametre soldat est un monstre dans ce cas
		soldat.setPoints(soldat.getPoints() - typeMonstreAlea.getPuissance());
	}
}
