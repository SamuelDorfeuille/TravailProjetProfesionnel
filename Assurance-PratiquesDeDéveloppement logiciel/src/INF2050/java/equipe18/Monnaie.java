package equipe18;

public class Monnaie {

    private int totalCents = 0;
    public int dollars = 0;
    public int cents = 0;



   public Monnaie (int i) {

   }

    /**
     * @param dollars
     * @param cents
     */
    public Monnaie(int dollars, int cents) {
        if (dollars < 0 || cents < 0 || cents > 99) {
            throw new IllegalArgumentException();
        }
        this.totalCents = dollars * 100 + cents;
    }

    public int getTotalCents () {
        return this.totalCents;
    }

    public int getDollars() {
        return dollars = this.totalCents / 100;
    }

    public int getCents() {
        return cents = this.totalCents % 100;
    }

    public void additioner (Monnaie autreMontant) {
        this.totalCents += autreMontant.totalCents;
    }

    public void pourcentage(int pourcentage) {
        this.totalCents = this.totalCents * pourcentage / 100;
    }

    public void setMontant(int montant){
        this.totalCents = montant;
    }
@Override
    public String toString(){
        return this.getDollars() + "." + getCents() + "$";
    }


}
