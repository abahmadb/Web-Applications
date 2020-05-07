public class SearchBean{

    private final int userid, tariff, counter;
    private final String name;
    private final float avgscore;

    public SearchBean(int uid, int t, int c, String n, float as){
        userid = uid;
        tariff = t;
        counter = c;
        name = n;
        avgscore = as;
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

}//SearchBean