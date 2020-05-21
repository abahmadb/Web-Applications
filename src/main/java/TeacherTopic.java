public class TeacherTopic {
    
	private final int teacherId;
    private final int topicId;
    private final int tariff;
	private final String topicName;

    public TeacherTopic(final int teacherId, final int topicId, final int tariff, final String topicName) {
        this.teacherId = teacherId;
        this.topicId = topicId;
		this.tariff = tariff;
		this.topicName = topicName;
    }

	public final int getTeacherId() {
		return teacherId;	
	}

    public final int getTopicId() {
        return topicId;
    }
    
    public final int getTariff() {
        return tariff;
    }
	
	public final String getTopicName() {
        return topicName;
    }

}