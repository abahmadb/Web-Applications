public class SearchBean{

    private final int userid, tariff, counter;
    private final String name;
    private final float avgscore;
    private boolean identity, certificate;

    public SearchBean(int uid, int t, int c, String n, float as, boolean i, boolean q){
        userid = uid;
        tariff = t;
        counter = c;
        name = n;
        avgscore = as;
        identity = i;
        certificate = q;
    }//SearchBean

    public int getUserid(){
        return userid;
    }//getUserid

    public int getTariff(){
        return tariff;
    }//getTariff

    public int getCounter(){
        return counter;
    }//getCounter

    public String getName(){
        return name;
    }//getName

    public float getAvgscore(){
        return avgscore;
    }//getAvgscore
    
    public boolean isIdentity(){
        return identity;
    }//isIdentity
    
    public boolean isCertificate(){
        return certificate;
    }//isCertificate

}//SearchBean