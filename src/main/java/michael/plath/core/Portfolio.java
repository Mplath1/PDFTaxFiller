package michael.plath.core;

public abstract class Portfolio {

    protected String name;
    protected String id;

    public Portfolio(String id){
        this.id =id;
    }

    /*
    public Portfolio(String name,String id){
        this.name = name;
        this.id = id;
    }
    */

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Portfolio portfolio = (Portfolio) o;

        return id.equals(portfolio.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
