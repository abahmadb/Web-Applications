public class Topic {
    
    private final int topicid;
    private final String label;

    public Topic(final int tid, final String l) {
        topicid = tid;
        label = l;
    }//Topic

    public final int getTopicid() {
        return topicid;
    }//getTopicID
    
    public final String getLabel() {
        return label;
    }//getLabel

}//Topic